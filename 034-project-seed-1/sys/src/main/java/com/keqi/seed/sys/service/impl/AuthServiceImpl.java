package com.keqi.seed.sys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.keqi.seed.core.exception.BusinessException;
import com.keqi.seed.core.util.JsonUtil;
import com.keqi.seed.core.exception.NoAuthException;
import com.keqi.seed.sys.domain.db.AccountDO;
import com.keqi.seed.sys.domain.param.LoginParam;
import com.keqi.seed.sys.domain.vo.AccountDetailVO;
import com.keqi.seed.sys.domain.vo.LoginVO;
import com.keqi.seed.sys.domain.vo.MenuVO;
import com.keqi.seed.sys.domain.vo.RoleVO;
import com.keqi.seed.sys.mapper.AccountMapper;
import com.keqi.seed.sys.mapper.MenuMapper;
import com.keqi.seed.sys.pojo.Auth;
import com.keqi.seed.sys.pojo.LoginUserBO;
import com.keqi.seed.sys.pojo.SysConstant;
import com.keqi.seed.sys.service.AuthService;
import com.keqi.seed.sys.util.SysUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public LoginVO login(LoginParam param) {
        AccountDO accountDO = this.accountMapper.selectByAccount(param.getAccount());
        if (Objects.isNull(accountDO)) {
            throw new BusinessException("用户名或密码错误");
        }

        String passwordEncry = SysUtil.encryptedPassword(param.getPassword(), accountDO.getSalt());
        if (!Objects.equals(passwordEncry, accountDO.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }

        // token 有效期为 14 天
        long expirationTime = System.currentTimeMillis() + 1209600000;
        String token = UUID.randomUUID().toString().replace("-", "")  + param.getDevType();

        List<MenuVO> menuVOS = this.accountMapper.selectMenuVOListByAccountId(accountDO.getId());
        List<String> permissList = menuVOS.stream().map(MenuVO::getPermiss).collect(Collectors.toList());
        List<RoleVO> roleVOS = this.accountMapper.selectRoleVOListByAccountId(accountDO.getId());
        List<String> roleList = roleVOS.stream().map(RoleVO::getName).collect(Collectors.toList());

        LoginUserBO loginUserBO = new LoginUserBO();
        loginUserBO.setId(accountDO.getId());
        loginUserBO.setAccount(accountDO.getAccount());
        loginUserBO.setExpirationTime(expirationTime);
        loginUserBO.setToken(token);
        loginUserBO.setDevType(param.getDevType());
        loginUserBO.setPermissList(permissList);
        loginUserBO.setRoleList(roleList);
        loginUserBO.setMenuVOS(menuVOS);

        // 此处将登录用户的角色、权限列表缓存至 redis 中，在用户token有效期内未重新登录时，角色、权限列表数据不会及时更新

        String o = (String) stringRedisTemplate.opsForHash().get(SysConstant.ACCOUNT_LOGIN_INFO, accountDO.getAccount() + param.getDevType());

        stringRedisTemplate.execute(new SessionCallback<Object>() {
            @Override
            public <K, V> Object execute(RedisOperations<K, V> operations) throws DataAccessException {
                StringRedisTemplate template = (StringRedisTemplate) operations;
                template.multi();

                if (o != null) {
                    // 强制下线
                    LoginUserBO t = JsonUtil.readValue(o, LoginUserBO.class);
                    template.opsForHash().delete(SysConstant.UUID_LOGIN_INFO, t.getToken());
                    template.opsForHash().delete(SysConstant.ACCOUNT_LOGIN_INFO, t.getAccount() + t.getDevType());
                    template.opsForSet().add(SysConstant.UUID_LOGOUT_INFO, t.getToken());
                }

                String loginInfo = JsonUtil.writeValueAsString(loginUserBO);
                template.opsForHash().put(SysConstant.UUID_LOGIN_INFO, token, loginInfo);
                template.opsForHash().put(SysConstant.ACCOUNT_LOGIN_INFO, accountDO.getAccount() + param.getDevType(), loginInfo);

                return template.exec();
            }
        });

        return new LoginVO(token);
    }

    @Override
    @Transactional
    public void updatePassword(String password, String newPassword) {
        Long id = Auth.getAccountId();
        AccountDO accountDO = this.accountMapper.selectById(id);

        if (SysUtil.encryptedPassword(password, accountDO.getSalt())
                .equals(accountDO.getPassword())) {
            this.accountMapper.updatePasswordById(
                    SysUtil.encryptedPassword(newPassword, accountDO.getSalt()), id);
        } else {
            throw new BusinessException("请输入正确的密码");
        }
    }

    @Override
    public AccountDetailVO selectLoginUserInfo() {
        AccountDO accountDO = this.accountMapper.selectById(Auth.getAccountId());
        return BeanUtil.copyProperties(accountDO, AccountDetailVO.class);
    }

    @Override
    public void logout(String token) {
        String o = (String) stringRedisTemplate.opsForHash().get(SysConstant.UUID_LOGIN_INFO, token);
        LoginUserBO loginUserBO = JsonUtil.readValue(o, LoginUserBO.class);

        String accountKey = loginUserBO.getAccount() + (token.endsWith("web") ? "web" : "mobile");

        stringRedisTemplate.execute(new SessionCallback<Object>() {
            @Override
            public <K, V> Object execute(RedisOperations<K, V> operations) throws DataAccessException {
                StringRedisTemplate template = (StringRedisTemplate) operations;
                template.multi();

                template.opsForHash().delete(SysConstant.UUID_LOGIN_INFO, token);
                template.opsForHash().delete(SysConstant.ACCOUNT_LOGIN_INFO, accountKey);

                return template.exec();
            }
        });
    }

    @Override
    public List<MenuVO> selectMenusByAccountId(String token, Long accountId) {
        String o = (String) stringRedisTemplate.opsForHash().get(SysConstant.UUID_LOGIN_INFO, token);
        if (StrUtil.isBlank(o)) {
            throw new NoAuthException();
        }

        LoginUserBO loginUserBO = JsonUtil.readValue(o, LoginUserBO.class);
        return this.assembleTreeList(loginUserBO.getMenuVOS(), 0L);
    }

    /**
     * 把没有层次结构的菜单列表按照父子结构关系进行组装（递归构造树形结构）
     *
     * @param menuVOList menuVOList
     * @return r
     */
    private List<MenuVO> assembleTreeList(List<MenuVO> menuVOList, Long rootParentId) {
        List<MenuVO> menuVOTreeList = new ArrayList<>();
        for (MenuVO vo : menuVOList) {
            if (vo.getParentId().equals(rootParentId)) {
                vo.setMenuList(assembleTreeList(menuVOList, vo.getId()));
                menuVOTreeList.add(vo);
            }
        }
        menuVOTreeList.sort(Comparator.comparing(MenuVO::getOrderNum));
        return menuVOTreeList;
    }
}
