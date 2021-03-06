package com.keqi.apihu.sys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.keqi.apihu.core.auth.LoginUserBO;
import com.keqi.apihu.core.exception.BusinessException;
import com.keqi.apihu.core.util.CommonUtil;
import com.keqi.apihu.core.util.JwtUtil;
import com.keqi.apihu.sys.domain.db.AccountDO;
import com.keqi.apihu.sys.domain.vo.LoginVO;
import com.keqi.apihu.sys.mapper.AccountMapper;
import com.keqi.apihu.sys.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@AllArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountMapper accountMapper;

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
        String passwordEncry = CommonUtil.encryptedPassword(account, password);

        if (Objects.isNull(accountDO) || !Objects.equals(passwordEncry, accountDO.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }

        // 生成 JWT 字符串
        LoginUserBO loginUserBO = new LoginUserBO();
        BeanUtil.copyProperties(accountDO, loginUserBO);

        // 设置过期时间为第二天的凌晨 2 点钟
        LocalDateTime expirationDate = LocalDate.now().plusDays(1).atTime(2, 0, 0);
        String accessToken = JwtUtil.generateToken(BeanUtil.beanToMap(loginUserBO), DateUtil.date(expirationDate));

        LoginVO loginVO = new LoginVO();
        loginVO.setAccessToken(accessToken);

        return loginVO;
    }
}
