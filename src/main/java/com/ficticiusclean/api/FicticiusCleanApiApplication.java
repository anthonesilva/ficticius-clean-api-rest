package com.ficticiusclean.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FicticiusCleanApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FicticiusCleanApiApplication.class, args);
	}

	/*
	 * TODO: 5 - Considerações finais
	 * 
	 * O projeto cumpre a sua função principal que seguindo a especificação
	 * dos requisitos é: cadastrar veículos e calcular uma previsão de gastos
	 * com combustível para cada veículo retornando uma lista ranqueada do maior
	 * valor para o menor valor, auxiliando assim, identificar qual veículo está
	 * com o maior consumo de combustível dadas as informações de consumo médio,
	 * quilometragem e preço do combustível
	 * 
	 * Uma melhoria para este projeto seria, criar um tratamento de exceções mais
	 * elaborado com respostas mais específicas e retorno de erros com campos
	 * padronizados para melhorar a usabilidade da API em um ponto futuro
	 * 
	 * Outra questão a considerar é a cobertura de testes para o projeto que
	 * segundo a análise do JUnit está em 73.7%.
	 * 
	 * Maiores detalhes da documentação do projeto estão disponíveis na página do
	 * do respositório do projeto.
	 */
}
