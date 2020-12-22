package cl.hector.arquetipo_api.spring_config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import java.util.Locale;

@Configuration
@EnableSwagger2
public class ApiSwagger2Configuration {

	private static final String ARTIFACT_ID = "arquetipo-api";
	private static final String NOMBRE_SISTEMA = "api-spring-boot";
	
    @Bean
    public Docket loginApi(ReloadableResourceBundleMessageSource messageSource) {

        return new Docket(DocumentationType.SWAGGER_2)   
        	.groupName(ARTIFACT_ID)
        	.apiInfo(apiInfo(messageSource))      
        	.forCodeGeneration(true)
        	.select()
        	.apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework")))
        	.build()
        	.enableUrlTemplating(false)
        	.useDefaultResponseMessages(false);
    }

    private ApiInfo apiInfo(ReloadableResourceBundleMessageSource messageSource) {
    	return new ApiInfoBuilder().title(NOMBRE_SISTEMA)
				.version(messageSource.getMessage("api-spring-boot.api.version", null, Locale.getDefault()))
				.description(NOMBRE_SISTEMA).build();
    } 

}
