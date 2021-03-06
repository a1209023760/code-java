package com.keqi.seed.sys.domain.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class AccountCreateParam {

	@ApiModelProperty(value = "用户名", example = "jack", required = true)
	@Size(min = 2, max = 10, message = "用户名长度必须在2-10个字符之间")
	private String account;

	@ApiModelProperty(value = "姓名", example = "杰克", required = true)
	@NotNull
	@Size(min = 2, max = 5, message = "姓名长度必须在2-5个字符之间")
	private String nickName;

	@ApiModelProperty(value = "岗位", example = "java", required = true)
	private String post;

	@ApiModelProperty(value = "密码", example = "123456", required = true)
	@NotNull
	@Size(min = 6, max = 20, message = "密码长度必须在6-20个字符之间")
	private String password;

	@ApiModelProperty(value = "角色ID列表", example = "[1,2,3,4,5,6]")
	private List<Long> roleIdList;
}