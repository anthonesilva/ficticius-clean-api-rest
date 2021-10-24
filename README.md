<h1 align="center">
  Ficticius Clean - API REST
</h1>

<p align="center">API REST para cadastro de veículos e cálculo de previsão de gastos com combustível</p>

<p align="center">
  <img alt="GitHub language count" src="https://img.shields.io/github/languages/count/anthonesilva/ficticius-clean-api-rest?color=%2304D361">

  <a href="http://www.linkedin.com/in/anthonesilva">
    <img alt="Created by Anthone Silva" src="https://img.shields.io/badge/Created%20by-Anthone%20Silva-%2304D361">
  </a>

  <img alt="License" src="https://img.shields.io/badge/license-MIT-blue">
</p>

## :page_with_curl: Sobre o projeto

> API REST para cadastro de veículos e cálculo de previsão de gastos com combustível

O propósito do projeto é realizar um cálculo que será uma previsão de gastos com combustível dos veículos da empresa Ficticius Clean, através da API REST podem ser cadastrados os dados dos veículos atráves do conjunto de recursos `/veiculos` e, pode ser também, calculada a previsão dos gastos com combustível através do recurso disponível em `/previsao-gastos`.

## :man_technologist: Tecnologias utilizadas

O projeto foi desenvolvido com as seguintes tecnologias:

* **Java 11 (Java Development Kit - JDK: 11.0.12)**
* **Spring Boot 2.5.6**
* **Maven**
* **JUnit 5**
* **H2 Database**
* **Swagger 3.0.0**

## :link: Links <a name="-links"/></a>

- [Documentação - Postman](https://documenter.getpostman.com/view/6022129/UV5afvfd) - Documentação da API REST;

## :desktop_computer: Instalação e execução

0. Instale o gerenciador de dependências [Maven](https://maven.apache.org/download.cgi);
1. Faça um clone desse repositório;
2. Entre na pasta com o comando `cd ficticius-clean-api-rest`;
3. Rode `mvn spring-boot:run` para iniciar o servidor;
4. Abra no navegador `http://localhost:8080/swagger-ui/` para ver o projeto.

## :page_facing_up: Licença

Esta API é licenciada através da licença MIT.
