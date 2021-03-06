package com.keqi.springbootmvcparam.controller;

import com.alibaba.fastjson.JSON;
import com.keqi.springbootmvcparam.common.AjaxEntity;
import com.keqi.springbootmvcparam.common.AjaxEntityBuilder;
import com.keqi.springbootmvcparam.domain.AccountParam;
import com.keqi.springbootmvcparam.domain.Border;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * 测试Content-Type不同时，前端传递参数是""或者是null时，后台接收到的值是什么样的
 */
@RestController
public class AccountController {


	/*
		1)
			Content-Type: form-data和application/x-www-form-urlencoded都会出现下面这种情况
			1、某个参数不传递时，后台对应接收对象的属性肯定是null
			2、某个参数传递时，后台对应接收对象的属性就是对应的值
			3、某个参数值为""时，后台接收到的也是""

			4、某个数值类型的参数传递到后台对应的Long/Integer类型的参数时，前端赋值的是null，后台接收到的实际上
			是一个字符串值，这个值是"null"，所以对应到后台肯定会出现类型转换错误。字符串同理，只不过这个时候不会
			出现类型转换错误而已，而是一个字符串"null"

			其实很正常啊，毕竟null是JSON中特有的东西，不是JSON肯定是不认识的

		2)
			Content-Type: application/json

			1、某个参数不传递时，后台对应接收对象的属性肯定是null
			2、某个参数传递时，后台对应接收对象的属性就是对应的值
			3、某个参数值为""时，后台接收到的也是""

			3、某个参数把null值传递给后端时，无论后台是字符串还是数值类型的，都是null(前提是没有使用基本数据类型)


		3)
			前面两种都是POST类型的，如果是GET类型的呢？
			1、某个参数不传递时，后台对应接收对象的属性肯定是null
			2、某个参数传递时，后台对应接收对象的属性就是对应的值
			3、某个参数值为""时，后台接收到的也是""

			4、某个数值类型的参数传递到后台对应的Long/Integer类型的参数时，前端赋值的是null，后台接收到的实际上
			是一个字符串值，这个值是"null"，所以对应到后台肯定会出现类型转换错误。字符串同理，只不过这个时候不会
			出现类型转换错误而已，而是一个字符串"null"


		总结：

		1) 如果是POST类型且Content-Type是application/json类型的时候，前端直接把不需要的使用的参数值设置为null即可

		2) 如果是POST类型且不是application/json时，前端就只能把不需要的参数值设置为""，后台就需要两者都判断。
			代码中就是用isBlank()去判断，MyBatis中就使用ognl表达式同时判断 != null and != ''

		3) 如果是GET类型，那么需要和 2) 一样的处理方式，也就是两者都需要判断

	 */

	@PostMapping("/account/create")
	public AjaxEntity create(AccountParam accountParam) {
		Long id = accountParam.getId();
		String account = accountParam.getAccount();
		String remark = accountParam.getRemark();
		String jsonStr = accountParam.getJsonStr();

		List<Border> borders = JSON.parseArray(jsonStr, Border.class);

		return AjaxEntityBuilder.success(borders);
	}

	@PostMapping("/account/createJSON")
	public AjaxEntity createJSON(@RequestBody AccountParam accountParam) {
		Long id = accountParam.getId();
		String account = accountParam.getAccount();
		String remark = accountParam.getRemark();
		BigDecimal longitude = accountParam.getLongitude();

		return AjaxEntityBuilder.success(accountParam);
	}

	@GetMapping("/account/createGET")
	public AjaxEntity createGET(AccountParam accountParam) {
		Long id = accountParam.getId();
		String account = accountParam.getAccount();
		String remark = accountParam.getRemark();

		return AjaxEntityBuilder.success();
	}

	// 测试返回值为 AjaxEntity 时
	@GetMapping("/account/testAjaxEntity")
	public AjaxEntity testAjaxEntity(AccountParam accountParam) {
		Long id = accountParam.getId();
		String account = accountParam.getAccount();
		String remark = accountParam.getRemark();

		return AjaxEntityBuilder.success(accountParam);
	}

	// 测试返回值为 String 时
	@GetMapping("/account/testString")
	public String testString(AccountParam accountParam) {
		Long id = accountParam.getId();
		String account = accountParam.getAccount();
		String remark = accountParam.getRemark();

		return account;
	}

	// 测试没有返回值时
	@GetMapping("/account/testVoid")
	public void testVoid(AccountParam accountParam) {
		System.out.println("void");
	}

	// 测试返回值为 Object 时
	@GetMapping("/account/testObject")
	public AccountParam testObject(AccountParam accountParam) {
		Long id = accountParam.getId();
		String account = accountParam.getAccount();
		String remark = accountParam.getRemark();

		return accountParam;
	}

	// 测试返回值为 List 类型对象时
	@GetMapping("/account/testList")
	public List<AccountParam> testList(AccountParam accountParam) {
		return Collections.singletonList(accountParam);
	}








}
