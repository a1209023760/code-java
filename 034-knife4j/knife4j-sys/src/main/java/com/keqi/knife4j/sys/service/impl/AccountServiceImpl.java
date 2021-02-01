package com.keqi.knife4j.sys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageSerializable;
import com.keqi.knife4j.core.auth.Auth;
import com.keqi.knife4j.core.auth.LoginUserBO;
import com.keqi.knife4j.core.exception.BusinessException;
import com.keqi.knife4j.core.pojo.PageVO;
import com.keqi.knife4j.core.util.CommonUtil;
import com.keqi.knife4j.core.util.JwtUtil;
import com.keqi.knife4j.sys.domain.db.AccountDO;
import com.keqi.knife4j.sys.domain.db.AccountRoleDO;
import com.keqi.knife4j.sys.domain.param.AccountPageParam;
import com.keqi.knife4j.sys.domain.param.AccountParam;
import com.keqi.knife4j.sys.domain.vo.AccountDetailVO;
import com.keqi.knife4j.sys.domain.vo.AccountVO;
import com.keqi.knife4j.sys.domain.vo.LoginVO;
import com.keqi.knife4j.sys.mapper.AccountMapper;
import com.keqi.knife4j.sys.mapper.AccountRoleMapper;
import com.keqi.knife4j.sys.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountMapper accountMapper;
	@Autowired
	private AccountRoleMapper accountRoleMapper;

	@Override
	@Transactional
	public void insert(AccountParam param) {
		// 新增用户记录
		AccountDO accountDO = BeanUtil.copyProperties(param, AccountDO.class);

		accountDO.setSalt(RandomUtil.randomString(20));
		accountDO.setPassword(CommonUtil.encryptedPassword(accountDO.getPassword(), accountDO.getSalt()));
		this.accountMapper.insert(accountDO);

		// 新增用户-角色关联记录
		List<Long> roleIdList = param.getRoleIdList();
		if (CollUtil.isNotEmpty(roleIdList)) {
			List<AccountRoleDO> list = new ArrayList<>();
			for (Long roleId : roleIdList) {
				AccountRoleDO t = new AccountRoleDO();
				t.setRoleId(roleId);
				t.setAccountId(accountDO.getId());
				list.add(t);
			}
			this.accountRoleMapper.insertList(list);
		}
	}

	@Override
	@Transactional
	public void updateById(AccountParam param) {
		// 修改用户记录
		param.setAccount(null); // 不允许修改
		param.setPassword(null); // 不允许通过此接口修改密码
		AccountDO accountDO = BeanUtil.copyProperties(param, AccountDO.class);
		this.accountMapper.updateById(accountDO);

		// 修改用户-角色关联记录
		List<Long> roleIdList = param.getRoleIdList();
		if (CollUtil.isNotEmpty(roleIdList)) {
			// 先删除
			this.accountRoleMapper.deleteByAccountId(param.getId());

			// 再增加
			List<AccountRoleDO> list = new ArrayList<>();
			for (Long roleId : roleIdList) {
				AccountRoleDO t = new AccountRoleDO();
				t.setRoleId(roleId);
				t.setAccountId(param.getId());
				list.add(t);
			}
			this.accountRoleMapper.insertList(list);
		}
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		this.accountRoleMapper.deleteByAccountId(id);
		this.accountMapper.deleteById(id);
	}

	@Override
	public PageVO<AccountVO> page(AccountPageParam param) {
		PageHelper.startPage(param.getPageNum(), param.getPageSize());
		List<AccountVO> result = this.accountMapper.page(param);

		return new PageVO<>(new PageSerializable<>(result).getTotal(), result);
	}

	/**
	 * 登录
	 *
	 * @param account  account
	 * @param password password
	 * @return r
	 */
	@Override
	public LoginVO login(String account, String password) {
		AccountDO accountDO = this.accountMapper.getByAccount(account);
		if (Objects.isNull(accountDO)) {
			throw new BusinessException("用户名不存在");
		}

		String passwordEncry = CommonUtil.encryptedPassword(password, accountDO.getSalt());
		if (!Objects.equals(passwordEncry, accountDO.getPassword())) {
			throw new BusinessException("密码错误");
		}

		// 生成 JWT 字符串
		LoginUserBO loginUserBO = new LoginUserBO();
		loginUserBO.setId(accountDO.getId());
		loginUserBO.setAccount(accountDO.getAccount());

		// 设置过期时间为第二天的凌晨 2 点钟
		LocalDateTime expirationDate = LocalDate.now().plusDays(1).atTime(2, 0, 0);
		String accessToken = JwtUtil.generateToken(BeanUtil.beanToMap(loginUserBO), DateUtil.date(expirationDate));

		return new LoginVO(accessToken);
	}

	/**
	 * 修改密码
	 *
	 * @param password    password
	 * @param newPassword newPassword
	 */
	@Override
	@Transactional
	public void updatePassword(String password, String newPassword) {
		Long id = Auth.getLoginAccountId();
		AccountDO accountDO = this.accountMapper.selectById(id);

		if (CommonUtil.encryptedPassword(password, accountDO.getSalt())
				.equals(accountDO.getPassword())) {
			// 密码正确，修改密码
			this.accountMapper.updatePasswordById(
					CommonUtil.encryptedPassword(newPassword, accountDO.getSalt()), id);
		} else {
			throw new BusinessException("请输入正确的密码");
		}
	}

	/**
	 * 获取登录用户信息
	 *
	 * @return r
	 */
	@Override
	public AccountDetailVO getLoginUserInfo() {
		AccountDO accountDO = this.accountMapper.selectById(Auth.getLoginAccountId());
		return BeanUtil.copyProperties(accountDO, AccountDetailVO.class);
	}
}