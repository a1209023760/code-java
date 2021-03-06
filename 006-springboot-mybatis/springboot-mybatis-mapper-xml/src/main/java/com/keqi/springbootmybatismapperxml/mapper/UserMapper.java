package com.keqi.springbootmybatismapperxml.mapper;

import com.keqi.springbootmybatismapperxml.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface UserMapper {

	//=======================1、测试增删改方法的使用=========================================//

	/*
		其中insert/delete/update类型的接口方法可以不设置方法返回值，也可以设置
		但是方法返回值仅仅代表本次操作影响的行数
	 */
	int insertUser(User user);

	int deleteUserById(Long id);

	int updateUserById(User user);

	//=======================2、测试基本查询方法的使用==================================//

	// 只有select类型的方法返回值，才是真正查询出来的记录封装成的对象

	User selectUserById(Long id);

	User selectUserByUsername(String username);

	//=======================3、测试方法参数的映射===========================================//

	User singleMethodParam(String username);

	User singleEntityParam(User user);

	// 多个参数必须使用@Param注解指定参数的别名，否则mybatis无法解析
	User multiMethodParam(@Param("id") Long id, @Param("username") String username);

	// 多个参数必须使用@Param注解指定参数的别名，否则mybatis无法解析
	User multiEntityParam(@Param("user1") User user1, @Param("user2") User user2);

	// 批量增加记录(使用List类型的参数且不使用@Param注解)
	int listParam1(List<User> userList);

	// 批量增加记录(使用List类型的参数且使用@Param注解)
	int listParam2(@Param("userList") List<User> userList);

	// 批量增加记录(使用Set类型的参数且不使用@Param注解)
	int setParam1(Set<User> userSet);

	// 批量增加记录(使用Set类型的参数且使用@Param注解)
	int setParam2(@Param("userSet") Set<User> userSet);

	// 批量增加记录(使用Array类型的参数)
	int arrayParam1(User[] users);

	// 批量增加记录(使用Array类型的参数)
	int arrayParam2(@Param("users") User[] users);

	// 总结：对于这几种集合类型的数据，直接使用@Param注解指定一下别名即可，不需要记住那么多，哈哈


	//======================4、动态SQL的使用=======================================//

	// <if/>标签
	List<User> dynamicIf(String sortOrder);

	// <choose/>标签
	List<User> dynamicChoose(String sortOrder);

	// <foreach/>标签
	int dynamicForeach(List<User> userList);

	// <where/>标签
	User dynamicWhere(@Param("id") Long id, @Param("username") String username);

	// <set/>标签
	int dynamicSet(@Param("id") Long id, @Param("username") String username);

	// <trim/>标签
	int dynamicTrim(@Param("id") Long id, @Param("username") String username);

	//=======================5、<resultMap>标签的使用===============================//



}
