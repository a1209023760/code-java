package com.keqi.iotplatform.core.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * 日期类型转换器（禁用java.util.Date类，所以这里不对它做转换）
 * 参考链接：https://juejin.im/post/5e62817fe51d4526d05962a2#heading-8
 *
 *  LocalDateTime ——> "yyyy-MM-dd HH:mm:ss"
 *  LocalDate ——> "yyyy-MM-dd"
 *  LocalTime ——> "HH:mm:ss"
 *
 */
@Configuration
public class DateHandlerConfig {

	/** 默认日期时间格式 */
	public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	/** 默认日期格式 */
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	/** 默认时间格式 */
	public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

	/**
	 * LocalDate转换器，用于转换RequestParam和PathVariable参数
	 * `@ConditionalOnBean(name = "requestMappingHandlerAdapter")`: 等requestMappingHandlerAdapter bean注册完成之后
	 * 再添加自己的`converter`就不会注册到`FormattingConversionService`中
	 */
	@Bean
	@ConditionalOnBean(name = "requestMappingHandlerAdapter")
	public Converter<String, LocalDate> localDateConverter() {
		return source -> StringUtils.isEmpty(source) ? null : LocalDate.parse(source, DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT));
	}

	/**
	 * LocalDateTime转换器，用于转换RequestParam和PathVariable参数
	 */
	@Bean
	@ConditionalOnBean(name = "requestMappingHandlerAdapter")
	public Converter<String, LocalDateTime> localDateTimeConverter() {
		return source -> StringUtils.isEmpty(source) ? null : LocalDateTime.parse(source, DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT));
	}

	/**
	 * LocalTime转换器，用于转换RequestParam和PathVariable参数
	 */
	@Bean
	@ConditionalOnBean(name = "requestMappingHandlerAdapter")
	public Converter<String, LocalTime> localTimeConverter() {
		return source -> StringUtils.isEmpty(source) ? null : LocalTime.parse(source, DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT));
	}

	/**
	 * Json序列化和反序列化转换器，用于转换Post请求体中的json以及将我们的对象序列化为返回响应的json
	 */
	@Bean
	public ObjectMapper objectMapper(){
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);

		//LocalDateTime系列序列化和反序列化模块，继承自jsr310，我们在这里修改了日期格式
		JavaTimeModule javaTimeModule = new JavaTimeModule();
		javaTimeModule.addSerializer(LocalDateTime.class,new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)));
		javaTimeModule.addSerializer(LocalDate.class,new LocalDateSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
		javaTimeModule.addSerializer(LocalTime.class,new LocalTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));
		javaTimeModule.addDeserializer(LocalDateTime.class,new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)));
		javaTimeModule.addDeserializer(LocalDate.class,new LocalDateDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
		javaTimeModule.addDeserializer(LocalTime.class,new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));

		objectMapper.registerModule(javaTimeModule);
		return objectMapper;
	}
}

