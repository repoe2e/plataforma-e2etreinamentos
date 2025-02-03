# Plataforma do Aluno

## Descrição

A **Plataforma do Aluno** é um projeto desenvolvido com **Spring Boot** que visa fornecer funcionalidades para o gerenciamento de alunos, incluindo autenticação, segurança, integração com banco de dados e APIs RESTful. A plataforma oferece uma interface simples e intuitiva, com foco em usabilidade e desempenho.

Este é um projeto de demonstração utilizando o Spring Boot para ilustrar boas práticas de desenvolvimento em uma aplicação com microserviços.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.4.1**
  - `spring-boot-starter-web` – Para criação de APIs RESTful
  - `spring-boot-starter-security` – Para autenticação e segurança
  - `spring-boot-starter-data-jpa` – Para persistência de dados com JPA
  - `spring-boot-starter-webflux` – Para suporte a programação reativa
  - `springdoc-openapi-starter-webmvc-ui` – Para integração com OpenAPI
- **MySQL 9.1.0** – Conector JDBC para integração com banco de dados
- **JWT** – Para autenticação com JSON Web Tokens
- **Mockito** – Framework para testes unitários
- **Lombok** – Para reduzir boilerplate no código

## Requisitos

Antes de rodar a aplicação, certifique-se de que você tem os seguintes requisitos:

- JDK 17 ou superior
- Maven 3.6.0 ou superior
- MySQL 5.7 ou superior

## Instalação

Siga os passos abaixo para rodar o projeto localmente:

1. **Clone o repositório:**

   ```bash
   git clone https://github.com/seu_usuario/plataforma.aluno.git
   cd plataforma.aluno

   
   
2. **Configure o banco de dados MySQL**

Crie um banco de dados chamado plataforma_e2e no MySQL.

Adapte as configurações do banco de dados no arquivo src/main/resources/application.properties:
spring.datasource.url=jdbc:mysql://localhost:3306/plataforma_aluno
spring.datasource.username=root
spring.datasource.password=sua_senha


## Endpoints da API

A aplicação oferece a documentação da API através do Swagger, acessível em:

http://localhost:8080/swagger-ui.html

matricula-controller


POST
/api/matricula/cadastro
Matricular um novo aluno

curso-crontoller


POST
/api/curso/cadastro
Cadastrar um novo curso

login-controller


POST
/api/auth/logout
Realizar logout


POST
/api/auth/login
Realizar login

aluno-controller


POST
/api/alunos/cadastro
Cadastrar um novo aluno

