package com.recruitment.casttest.configuration;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(factory = YamlPropertySourceFactory.class, value = "classpath:openapi-doc.yml")
public class OpenApiConfiguration {

    @Value("${openAPI.info.title}")
    private String title;
    @Value("${openAPI.info.version}")
    private String version;
    @Value("${openAPI.info.description}")
    private String description;
    @Value("${openAPI.info.termsOfService}")
    private String termsOfService;
    @Value("${openAPI.info.licence.name}")
    private String licenceName;
    @Value("${openAPI.info.licence.url}")
    private String licenceUrl;
    @Value("${openAPI.info.contact.email}")
    private String contactEmail;
    @Value("${openAPI.info.contact.name}")
    private String contactName;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title(this.title)
                                .version(this.version)
                                .description(this.description)
                                .license(new License().name(this.licenceName).url(this.licenceUrl))
                                .termsOfService(this.termsOfService)
                                .contact(new Contact().name(this.contactName).email(this.contactEmail))
                );
    }
}
