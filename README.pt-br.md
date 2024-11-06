# API REST para autenticação e autorização

![Java](https://img.shields.io/badge/Java-17-orange) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3-green) ![Spring Security](https://img.shields.io/badge/Spring%20Security-6-green) [![LinkedIn](https://img.shields.io/badge/Connect%20on-LinkedIn-blue)](https://www.linkedin.com/in/gabrieudev) ![GPL License](https://img.shields.io/badge/License-GPL-blue)

Seja bem vindo(a) ao meu projeto de **API REST para autenticação e autorização** de usuários.

## Tabela de conteúdos

- [Introdução](#introdução)
- [Funcionalidades](#funcionalidades)
- [Tecnologias](#tecnologias)
- [Iniciando](#iniciando)
- [Endpoints](#endpoints)
- [Contribuições](#contribuições)
- [Contato](#contato)

## Introdução

O projeto foi criado para faciltar o processo de autenticação e autorização de usuários, servindo como base para ser reutilizado em qualquer API REST que necessite de tais funcionalidades. Além disso, o projeto implementa autenticação com JWTs e autorização por meio de roles para os usuários, utilizando as melhores e mais atualizadas práticas do mercado para assegurar a integridade dos dados sensíveis.

## Funcionalidades

- Login de usuários com autenticação através de JWT.
- Autorização com roles para o controle de acesso de diferentes endpoints da API.
- Implementação de profiles para desenvolvimento e produção.
- Senhas criptografadas utilizando as melhores práticas.
- Integração com o banco de dados PostgreSQL.
- Documentação utilizando Swagger.

## Tecnologias

- ![Java](https://img.shields.io/badge/Java-17-orange): Linguagem de programação.
- ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3-green): Framework usado na construção de APIs REST.
- ![Spring Security](https://img.shields.io/badge/Spring%20Security-6-green): Utilizado em conjunto com o Spring Boot para a segurança das aplicações.
- ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue): Banco de dados relacional.

## Iniciando

O projeto pode ser executado através de dois profiles: dev ou prod.

**Profile de dev** (recomendado para testes e execução local):

1. Clone o repositório:

   ```bash
   git clone https://github.com/gabrieudev/auth.git
   ```

2. Vá até o arquivo `application.properties` e substitua "prod" por "dev".

3. Construa o projeto:

   ```bash
   ./mvnw clean install
   ```

   Para Windows:

   ```bash
   mvnw.cmd clean install
   ```

4. Execute a aplicação:

   ```bash
   ./mvnw spring-boot:run
   ```

   Para Windows:

   ```bash
   mvnw.cmd spring-boot:run
   ```

5. Faça login na rota `/login` utilizando o email `admin@gmail.com` e senha `admin` para acessar os endpoints protegidos.

**Profile de prod** (recomendado para deploy em produção):

1. Clone o repositório:

   ```bash
   git clone https://github.com/gabrieudev/auth.git
   ```

2. Antes de realizar o deploy da aplicação, defina algumas variáveis de ambiente no seu serviço (o SGBD utilizado deverá ser o PostgreSQL, com as tabelas definidas exatamente como no projeto)
   - DATABASE_URL: URL para conexão com o banco de dados.
   - DATABASE_USERNAME: Nome de usuário para acesso ao banco de dados.
   - DATABASE_PASSWORD: Senha para acesso ao banco de dados.
   - PUBLIC_KEY: Chave pública para o uso de JWT.
   - PRIVATE_KEY: Chave privada para o uso de JWT.
3. Finalize o deploy.

## Endpoints

Caso tenha executado a aplicação em modo dev, acesse a [documentação](http://localhost:9090/swagger-ui/index.html) para visualizar todas as rotas. Caso contrário, o endpoint é `/swagger-ui/index.html`

## Contribuições

Contribuições são muito bem vindas! Caso queira contribuir, faça um fork do repositório e crie um pull request.

## Contato

Caso tenha alguma sugestão ou dúvida, entre em contato comigo em [LinkedIn](https://www.linkedin.com/in/gabrieudev)

---

**Licença:** Esse projeto é licenciado sob os termos da [GNU General Public License (GPL)](LICENSE).
