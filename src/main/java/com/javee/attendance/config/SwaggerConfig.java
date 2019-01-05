package com.javee.attendance.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig
{
    /*--------------------------------------------
    |             C O N S T A N T S             |
    ============================================*/

	private static final Logger LOGGER = LoggerFactory.getLogger( SwaggerConfig.class );
	
    /*--------------------------------------------
    |    I N S T A N C E   V A R I A B L E S    |
    ============================================*/	
	
    /*--------------------------------------------
    |         C O N S T R U C T O R S           |
    ============================================*/
	
    /*--------------------------------------------
    |   P U B L I C    A P I    M E T H O D S   |
    ============================================*/

	@Bean
	public Docket attendanceApi()
	{
		return new Docket( DocumentationType.SWAGGER_2 )
				.select()
				.apis( RequestHandlerSelectors.basePackage( "com.javee.attendance.controllers" ) )
				.build()
				.apiInfo( metaData() );
	}

	private ApiInfo metaData()
	{
		ApiInfo apiInfo = new ApiInfo(
				"Javee Attendance REST API",
				"Javee Attendance REST API for Online Attendance",
				"1.0",
				"Terms of service",
				new Contact( "Dipesh Gandhi", "http://javeeinfotech.com/", "dipesh@javeeinfotech.com" ),
				"Apache License Version 2.0",
				"https://www.apache.org/licenses/LICENSE-2.0" );
		return apiInfo;
	}
	
    /*--------------------------------------------
    |    N O N - P U B L I C    M E T H O D S   |
    ============================================*/	
	
    /*--------------------------------------------
    |  A C C E S S O R S / M O D I F I E R S    |
    ============================================*/
	
	/*--------------------------------------------
	|       I N L I N E    C L A S S E S        |
	============================================*/
}

