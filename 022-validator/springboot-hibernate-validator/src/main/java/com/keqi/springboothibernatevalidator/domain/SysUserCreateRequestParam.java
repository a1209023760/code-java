package com.keqi.springboothibernatevalidator.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysUserCreateRequestParam implements Serializable {

	private static final long serialVersionUID = 1001580478853665449L;

	@NotBlank(message = "用户名不能为空")
	@Length(min = 1, max = 50, message = "用户名长度不正确")
	private String username;

	@NotBlank(message = "用户密码不能为空")
	@Length(min = 1, max = 50, message = "用户密码长度不正确")
	private String userPassword;

	@NotBlank(message = "邮箱地址不能为空")
	@Length(min = 1, max = 50, message = "邮箱地址长度不正确")
	@Email(message = "邮箱地址格式不正确")
	private String userEmail;

	@NotBlank(message = "固话不能为空")
	@Length(min = 1, max = 20, message = "固话长度不正确")
	private String telephone;

	@NotBlank(message = "手机号不能为空")
	@Length(min = 1, max = 20, message = "手机号长度不正确")
	private String mobilePhone;

	@NotNull(message = "性别不能为空")
	@Range(min = 1, max = 4, message = "性别数值不正确")
	private Integer genderNum;
}
