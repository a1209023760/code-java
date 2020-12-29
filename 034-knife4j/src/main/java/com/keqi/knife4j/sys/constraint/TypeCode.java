package com.keqi.knife4j.sys.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 配合 DictUtil 工具类校验 typeCode 对应的 itemCode 是否合法
 *
 * @author keqi
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {TypeCodeValidator.class})
public @interface TypeCode {

	String message() default "itemCode 错误";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	/**
	 * 指定 dictType 的值
	 *
	 * @return r
	 */
	String value();

	/**
	 * 接收的值中是否会有多个 itemCode（如："zhhb,zhyq" 的格式）
	 *
	 * @return r
	 */
	boolean multiple() default false;
}
