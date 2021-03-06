package com.keqi.springbootmybatistypehandler.common;

import java.io.Serializable;

/**
 * 基础实体类(其他实体类需要继承此类)
 *
 * @author keqi
 */
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = -123017340005452171L;

	/** 登录用户对象 */
	protected LoginUserBO loginUser;

}
