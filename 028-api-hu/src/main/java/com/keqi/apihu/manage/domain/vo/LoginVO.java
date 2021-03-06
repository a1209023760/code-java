package com.keqi.apihu.manage.domain.vo;

import com.keqi.apihu.manage.domain.enums.UserTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录VO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginVO {

	private String accessToken;

	private UserTypeEnum userType;
}
