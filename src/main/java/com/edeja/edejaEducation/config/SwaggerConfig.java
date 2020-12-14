package com.edeja.edejaEducation.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * taken from @see http://www.baeldung.com/swagger-2-documentation-for-spring-rest-api
 * also from @see https://dzone.com/articles/spring-boot-restful-api-documentation-with-swagger
 * https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        //.ignoredParameterTypes(AuthenticationPrincipal.class)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.edeja.edejaEducation"))
        .paths(PathSelectors.any())
        .build()
        .apiInfo(metaData());
  }

  private ApiInfo metaData() {
    ApiInfo apiInfo = new ApiInfo(
        "playGround REST API",
        "REST API for playGround",
        "1.0",
        "Terms of service",
        new Contact("playGround", "www.edeja.rs", "edeja@mail"),
        "custom",
        "www.edeja.rs");
    return apiInfo;
  }
}
