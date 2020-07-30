package com.egorhristoforov.eventikrestapi.configuration.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

    @Bean
    public Docket restApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.egorhristoforov.eventikrestapi.controllers.app"))
                .build()
                .apiInfo(getApiInfo())
                .securitySchemes(Arrays.asList(new ApiKey("Access token", "Authorization", "header")));
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot REST API - Eventik")
                .description("REST API for Eventik app")
                .version("1.0.0")
                .build();
    }
}
