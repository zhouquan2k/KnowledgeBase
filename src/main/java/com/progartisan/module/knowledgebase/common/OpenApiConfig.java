package com.progartisan.module.knowledgebase.common;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.leanddd.component.spring.OpenApiHelper;

@Configuration
public class OpenApiConfig  {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .openapi("3.0.0") // 明确指定 OpenAPI 版本
                .info(new Info()
                        .title("GCP API Documentation")
                        .version("1.0"));
        //.description("Overview and detailed documentation of APIs");
    }

    @Bean
    public GroupedOpenApi exampleApi() {
        return OpenApiHelper.getGroupedOpenApi("example");
    }

    @Bean
    public GroupedOpenApi knowledgeApi() {
        return OpenApiHelper.getGroupedOpenApi("knowledge");
    }

}

