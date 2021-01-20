package com.keqi.knife4j.sys.domain.db;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 角色表
 */
@Data
@TableName(value = "sys_role")
public class RoleDO {

	/**
	 * 角色id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	/**
	 * 角色名称
	 */
	@TableField(value = "name")
	private String name;

	/**
	 * 创建时间
	 */
	@TableField(value = "create_time", fill = FieldFill.INSERT)
	private LocalDateTime createTime;

	/**
	 * 修改时间
	 */
	@TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime updateTime;
}