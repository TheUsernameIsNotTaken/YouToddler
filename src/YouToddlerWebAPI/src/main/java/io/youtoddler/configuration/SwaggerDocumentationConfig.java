package io.youtoddler.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-04-22T11:09:03.174853304Z[GMT]")
@Configuration
public class SwaggerDocumentationConfig {

    @Bean
    public Docket customImplementation(){
        return new Docket(DocumentationType.OAS_30)
                .select()
                    .apis(RequestHandlerSelectors.basePackage("io.swagger.api"))
                    .build()
                .directModelSubstitute(org.threeten.bp.LocalDate.class, java.sql.Date.class)
                .directModelSubstitute(org.threeten.bp.OffsetDateTime.class, java.util.Date.class)
                .apiInfo(apiInfo());
    }

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("YouToddler - OpenAPI 3.0")
            .description("This is an on-premise download manager for youtube-dlp. It should be based on the OpenAPI 3.0 specification.   _If you're looking for the sprint board, then click [here](https://manhatten.atlassian.net/jira/software/projects/MAN/boards/1)._  Some useful links: - [The YouToddler repository](https://github.com/cant0r/YouToddler) - [The backend API definition for YouToddler](https://manhatten.atlassian.net/wiki/spaces/AT/pages/6094879/YouToddler+Backend) - [The WebAPi definition for YouToddler](https://manhatten.atlassian.net/wiki/spaces/AT/pages/4063276/REST+API+interfaces)")
            .license("Apache 2.0")
            .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
            .termsOfServiceUrl("https://github.com/TheUsernameIsNotTaken/")
            .version("0.2.1")
            .contact(new Contact("","", "pixelbetyar@mailbox.unideb.hu"))
            .build();
    }

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
            .info(new Info()
                .title("YouToddler - OpenAPI 3.0")
                .description("This is an on-premise download manager for youtube-dlp. It should be based on the OpenAPI 3.0 specification.   _If you're looking for the sprint board, then click [here](https://manhatten.atlassian.net/jira/software/projects/MAN/boards/1)._  Some useful links: - [The YouToddler repository](https://github.com/cant0r/YouToddler) - [The backend API definition for YouToddler](https://manhatten.atlassian.net/wiki/spaces/AT/pages/6094879/YouToddler+Backend) - [The WebAPi definition for YouToddler](https://manhatten.atlassian.net/wiki/spaces/AT/pages/4063276/REST+API+interfaces)")
                .termsOfService("https://github.com/TheUsernameIsNotTaken/")
                .version("0.2.1")
                .license(new License()
                    .name("Apache 2.0")
                    .url("http://www.apache.org/licenses/LICENSE-2.0.html"))
                .contact(new io.swagger.v3.oas.models.info.Contact()
                    .email("pixelbetyar@mailbox.unideb.hu")));
    }

}
