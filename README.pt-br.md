<h1 align="center" style="font-weight: bold;">Autenticação e autorização de usuários 🔒</h1>

<p align="center">
  <img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java">
  <img src="https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white" alt="Spring">
  <img src="https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens" alt="JWT">
  <img src="https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white" alt="Postgres">
  <img src="https://img.shields.io/badge/redis-%23DD0031.svg?style=for-the-badge&logo=redis&logoColor=white" alt="Redis">
  <img src="https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white" alt="Docker">
  <img src="https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white" alt="Swagger">
</p>

<p align="center">
 <a href="#estrutura">Estrutura do projeto</a> • 
 <a href="#inicio">Primeiros Passos</a> • 
 <a href="#rotas">Endpoints da API</a> •
 <a href="#contribuir">Contribuir</a>
</p>

<p align="center">
  <b>O projeto foi criado para faciltar o processo de autenticação e autorização de usuários, servindo como base para ser reutilizado em qualquer API REST em Java que necessite de tais funcionalidades. Além disso, a API foi desenvolvida seguindo os princípios da <a href=https://medium.com/@marcio.kgr/arquitetura-hexagonal-8958fb3e5507>Arquitetura Hexagonal</a> e utilizando as melhores e mais atualizadas práticas do mercado, incluindo testes unitários com 100% de cobertura, para assegurar a integridade dos dados sensíveis.</b>
</p>

<h2 id="estrutura">📂 Estrutura do projeto</h2>

```yaml
src/main/java/br/com/gabrieudev/auth/
├── application # Camada de aplicação
│   ├── exception # Exceções
│   ├── ports # Portas
│   │   ├── input # Portas de entrada
│   │   └── output # Portas de saída
│   └── service # Serviços
├── domain # Camada de domínio
├── adapters # Camada de adaptadores
│   ├── input # Adaptadores de entrada
│   │   └── rest # Interação web
│   │       ├── controllers # Controladores
│   │       └── dtos # DTOs
│   └── output # Adaptadores de saída
│       └── persistence # Banco de dados
│           ├── entities # Entidades JPA
│           └── repositories # Repositórios (JPA e adaptadores)
├── config # Configurações do Spring
└── AuthApplication.java # Inicializador da aplicação
```

<h2 id="inicio">🚀 Primeiros Passos</h2>

<h3>Pré-requisitos</h3>

- [Docker](https://www.docker.com/get-started/)
- [Git](https://git-scm.com/downloads)
- conta Google

<h3>Clonando</h3>

```bash
git clone https://github.com/gabrieudev/auth.git
```

<h3>Variáveis de Ambiente</h3>

Para executar a aplicação, você precisará criar um arquivo `.env`, no diretório raiz do projeto, contendo as seguintes variáveis de ambiente:

| Chave                     | Valor Padrão                       | Obrigatória | Descrição                                                |
| ------------------------- | ---------------------------------- | ----------- | -------------------------------------------------------- |
| `PROFILE`                 | dev                                | não         | profile no qual a aplicação irá rodar (dev ou prod).     |
| `DATASOURCE_URL`          | inicializado em docker-compose.yml | não         | URL de conexão com o banco de dados.                     |
| `DATASOURCE_USERNAME`     | inicializado em docker-compose.yml | não         | usuário de conexão com o banco de dados.                 |
| `DATASOURCE_PASSWORD`     | inicializado em docker-compose.yml | não         | senha de conexão com o banco de dados.                   |
| `REDIS_HOST`              | inicializado em docker-compose.yml | não         | host de conexão com o Redis.                             |
| `REDIS_PORT`              | inicializado em docker-compose.yml | não         | porta de conexão com o Redis.                            |
| `REDIS_PASSWORD`          | inicializado em docker-compose.yml | não         | senha de conexão com o Redis.                            |
| `EMAIL_HOST`              | host do Gmail                      | não         | host para envio de e-mails.                              |
| `EMAIL_PORT`              | porta do Gmail                     | não         | porta para envio de e-mails.                             |
| `EMAIL_USERNAME`          | nenhum                             | sim         | e-mail para envios de notificações através da aplicação. |
| `EMAIL_PASSWORD`          | nenhum                             | sim         | senha de app.                                            |
| `API_BASE_URL`            | URL local                          | não         | URL base da API.                                         |
| `FRONTEND_BASE_URL`       | URL da interface Swagger           | não         | URL de alguma interface para redirecionamento.           |
| `ACCESS_TOKEN_EXPIRATION` | 5                                  | não         | tempo de validade do token de acesso em minutos.         |
| `ACCESS_TOKEN_EXPIRATION` | 10080                              | não         | tempo de validade do token de atualização em minutos.    |

> Caso não saiba como obter as senhas de app, a [Central de Ajuda Google](https://support.google.com/accounts/answer/185833?hl=pt-BR) pode te ajudar.

<h3>Inicializando</h3>

Execute os seguintes comandos:

```bash
cd auth
docker compose up -d --build
```

<h2 id="rotas">📍 Endpoints da API</h2>

Agora você poderá interagir com as rotas acessando a [interface Swagger](http://localhost:8080/api/v1/swagger-ui/index.html).

Observação: Assim que a aplicação é inicializada, é criado um usuário administrador no banco de dados com as seguintes informações:

```yaml
{... "email": "admin@gmail.com", "password": "adminpassword" ...}
```

Use-as para fazer login e acessar as rotas protegidas contra os usuários comuns.

<h2 id="contribuir">📫 Contribuir</h2>

Contribuições são muito bem vindas! Caso queira contribuir, faça um fork do repositório e crie um pull request.

1. `git clone https://github.com/gabrieudev/auth.git`
2. `git checkout -b feature/NOME`
3. Siga os padrões de commits.
4. Abra um Pull Request explicando o problema resolvido ou a funcionalidade desenvolvida. Se houver, anexe screenshots das modificações visuais e aguarde a revisão!

<h3>Documentações que podem ajudar</h3>

[📝 Como criar um Pull Request](https://www.atlassian.com/br/git/tutorials/making-a-pull-request)

[💾 Padrão de commits](https://gist.github.com/joshbuchea/6f47e86d2510bce28f8e7f42ae84c716)
