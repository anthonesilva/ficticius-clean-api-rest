package com.ficticiusclean.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	/*
	 * TODO: 5 - Adicionando UI do Swagger e validações
	 * 
	 * Foram adicionadas as dependências e realizadas as configurações
	 * do Swagger para disponibilizar a interface para utilização da API
	 * em localhost:8080/swagger-ui/
	 * 
	 * Foram configurados os models, DTOS e cada endpoint, incluindo também
	 * o framework de validação javax.validation para tratar algums possíveis
	 * exceções
	 * 
	 * Foi preciso retornar em alguns testes para fazer algumas validações,
	 * o detalhamento das exceções lançadas no retorno da API pode esclarecer
	 * a causa dos erros que possam ocorrer
	 */
	
	final public static String VEICULOS_TAG = "Veículos"; 
	final public static String PREVISAO_CALCULOS_TAG = "Previsão de Cálculos"; 
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
			.select()
			.apis(RequestHandlerSelectors.basePackage("com.ficticiusclean.api.controllers"))
			.paths(PathSelectors.any())
		.build()
		.apiInfo(apiInfo())
		.tags(new Tag(VEICULOS_TAG, "Resource responsável pelo cadastro de veículos"),
				new Tag(PREVISAO_CALCULOS_TAG, "Resource responsável pelo cálculo da previsão de gastos com combustível"));
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
			.title("Ficticius Clean - API REST")
			.description("API REST para cadastro de veículos e cálculo de previsão de gastos com combustível")
			.contact(new Contact("Anthone Silva", null, "anthonej@gmail.com"))
		.build();
	}

}
