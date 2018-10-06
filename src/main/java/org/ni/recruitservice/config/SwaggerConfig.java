package org.ni.recruitservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * Created by nazmul on 9/21/2018.
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${service.package}")
    private String servicePackage;

    @Value("${service.title}")
    private String serviceTitle;

    @Value("${service.description}")
    private String serviceDescription;

    @Value("${service.version}")
    private String serviceVersion;

    @Value("${service.termsOfServiceUrl}")
    private String serviceTermsOfServiceUrl;

    @Value("${service.contact.name}")
    private String serviceContactName;

    @Value("${service.contact.url}")
    private String serviceContactUrl;

    @Value("${service.contact.email}")
    private String serviceContactEmail;

    @Value("${service.license}")
    private String serviceLicense;

    @Value("${service.licenseUrl}")
    private String serviceLicenseUrl;

    @Bean
    @Primary
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(servicePackage))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                serviceTitle,
                serviceDescription,
                serviceVersion,
                serviceTermsOfServiceUrl,
                new Contact(serviceContactName, serviceContactUrl, serviceContactEmail),
                serviceLicense,
                serviceLicenseUrl,
                new ArrayList());
    }
}
