package com.keqi.seed.sys.domain.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class RoleParam {

	@ApiModelProperty(value = "角色ID", example = "1", required = true)
	private Long id;

	@ApiModelProperty(value = "角色名称", example = "管理员", required = true)
	private String name;

	@ApiModelProperty(value = "权限标识", example = "admin", required = true)
	private String permiss;

	@ApiModelProperty(value = "菜单ID列表", example = "[1,2,3,4,5,6]")
	private List<Long> menuIdList;
}