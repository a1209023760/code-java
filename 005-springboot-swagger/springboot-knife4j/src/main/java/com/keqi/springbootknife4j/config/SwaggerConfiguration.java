package com.keqi.springbootknife4j.config;

import com.google.common.collect.Ordering;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Operation;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Getter
@Setter
@Configuration
@EnableSwagger2
@ConditionalOnClass(EnableSwagger2.class)
@ConfigurationProperties(prefix = "swagger")
public class SwaggerConfiguration {

	/**
	 * API接口包路径
	 */
	private String basePackage;

	/**
	 * API页面标题
	 */
	private String title;

	/**
	 * API描述
	 */
	private String description;

	/**
	 * 服务条款地址
	 */
	private String termsOfServiceUrl;

	/**
	 * 版本号
	 */
	private String version;

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.operationOrdering(new Ordering<Operation>() {
					@Override
					public int compare(Operation left, Operation right) {
						return left.getMethod().name().compareTo(right.getMethod().name());
					}
				})
				// 关闭默认的响应信息
				.useDefaultResponseMessages(false)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage(basePackage))
				.paths(PathSelectors.any())

				.build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title(title)
				.description(description)
				.termsOfServiceUrl(termsOfServiceUrl)
				.version(version)
				.build();
	}

}
