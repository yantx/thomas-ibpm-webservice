package com.thomas.ibpm.plugins.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author: yantx
 * @version: 1.0
 * @date: 2021-01-27 23:21
 * @description:
 */
//@EnableSwagger2
//@Configuration
public class SwaggerConfig {
    // 是否开启swagger，正式环境一般是需要关闭的，可根据springboot的多环境配置进行设置
    @Value(value = "${swagger.enabled}")
    Boolean swaggerEnabled;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                // 是否开启
                .enable(swaggerEnabled).select()
                .apis(RequestHandlerSelectors.basePackage("com.thoms.ibpm.controller")).paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("工作流API").description("thomas-ibpm")
                // 作者信息
                .contact(new Contact("thomas-ibpm", "https://my.oschina.net/bianxin", "yantxd@qq.com"))
                .version("1.0.0").build();
    }
}
