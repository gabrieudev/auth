# API REST para autenticação e autorização

![Java](https://img.shields.io/badge/Java-21-orange) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3-green) ![Spring Security](https://img.shields.io/badge/Spring%20Security-6-green) [![LinkedIn](https://img.shields.io/badge/Connect%20on-LinkedIn-blue)](https://www.linkedin.com/in/gabrieudev) ![GPL License](https://img.shields.io/badge/License-GPL-blue)

Seja bem vindo(a) ao meu projeto de **API REST para autenticação e autorização** de usuários. 

## Tabela de conteúdos

- [Introdução](#introdução)
- [Funcionalidades](#funcionalidades)
- [Tecnologias](#tecnologias)
- [Iniciando](#iniciando)
- [Configuração](#configuração)
- [Uso](#uso)
- [Endpoints](#endpoints)
- [Contribuições](#contribuições)
- [Contato](#contato)

## Introdução

O projeto foi criado para faciltar o processo de autenticação e autorização de usuários, servindo como base para ser reutilizado em qualquer API REST que necessite de tais funcionalidades. Além disso, o projeto implementa autenticação com JWTs e autorização por meio de roles para os usuários, utilizando as melhores e mais atualizadas práticas do mercado para assegurar a integridade dos dados sensíveis. 

## Funcionalidades

- Envio de email para confirmação de cadastro dos usuários.
- Buscas utilizando paginação.
- Documentação com endpoints utilizando Swagger.
- Login de usuários com autenticação através de JWT.
- Autorização com roles para o controle de acesso de diferentes endpoints da API. 
- Senhas criptografadas utilizando as melhores práticas.
- Integração com o banco de dados PostgreSQL.

## Tecnologias

- ![Java](https://img.shields.io/badge/Java-21-orange): Linguagem de programação.
- ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3-green): Framework usado para a construção de aplicações.
- ![Spring Security](https://img.shields.io/badge/Spring%20Security-6-green): Framework para segurança de aplicações Spring.
- ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue): Banco de dados relacional.

## Iniciando

Siga esses passos para executar o projeto na sua máquina:

1. Clone o repositório: `git clone https://github.com/gabrieudev/auth.git`
2. Navegue para o diretório do projeto: `cd <caminho>`
3. Atualize as configurações gerais em `application.properties`.
4. Construa o projeto: `./mvnw clean install` (para Windows: `mvnw.cmd clean install`)
5. Execute a aplicação: `./mvnw spring-boot:run` (para Windows: `mvnw.cmd spring-boot:run`)

## Configuração

- Atualize o arquivo `application.properties` com todas informações necessárias.

## Uso

1. Ao iniciar o projeto, um usuário com a role de administrador é inserido no banco de dados automaticamente em `AdminDataLoader.java`. Suas informações podem ser alteradas tanto lá, quanto em `application.properties`.
3. Além do usuário, as roles também são inseridas no banco de dados de acordo com os valores em `RoleEnum.java`, caso precise adicionar mais alguma, basta seguir o modelo do enum.
2. Utilize um usuário com role de administrador para ter acesso aos endpoints protegidos.

## Endpoints

- `POST /auth/register`: Registra um usuário e envia um link para confirmação ao seu email.
- `GET /users/confirm`: Faz a verificação do email.
- `POST /auth/login`: Realiza o login e recebe um JWT.
- `ADMIN Role` `GET /users`: Obtém todos usuários.
- `ADMIN Role` `DELETE /users/{userId}`: Deleta um usuário.
- `BASIC Role` `GET /users/{userId}`: Obtém um usuário de acordo com o ID.

Acesse a documentação completa no endpoint `/swagger-ui.html`

## Contribuições

Contribuições são muito bem vindas! Caso queira contribuir, faça um fork do repositório e crie um pull request.

## Contato

Caso tenha alguma sugestão ou dúvida, entre em contato comigo em [LinkedIn](https://www.linkedin.com/in/gabrieudev)

---

**Licença:** Esse projeto é licenciado sob os termos da [GNU General Public License (GPL)](LICENSE).
