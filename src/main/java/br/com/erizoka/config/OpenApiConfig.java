package br.com.erizoka.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

	@Bean
	OpenAPI customOpenApi() {
		return new OpenAPI()
				.info(new Info()
						.title("RESTful API with Java and SpringBoot")
						.version("v1")
						.description("Journey to the wonderful world of apis :D")
						.termsOfService("https://erizoka.github.io/myPortfolio/")
						.license(
								new License()
									.name("Apache 2.0")
									.url("https://erizoka.github.io/myPortfolio/")
									)
						);
	}
}
