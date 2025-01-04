[JAVA_BADGE]: https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white
[SPRING_BADGE]: https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white
[JWT_BADGE]: https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens
[SWAGGER_BADGE]: https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white
[POSTGRES_BADGE]: https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white
[REDIS_BADGE]: https://img.shields.io/badge/redis-%23DD0031.svg?style=for-the-badge&logo=redis&logoColor=white
[DOCKER_BADGE]: https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white

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
 <a href="#inicio">Primeiros Passos</a> • 
 <a href="#rotas">Endpoints da API</a> •
 <a href="#contribuir">Contribuir</a>
</p>

<p align="center">
  <b>O projeto foi criado para faciltar o processo de autenticação e autorização de usuários, servindo como base para ser reutilizado em qualquer API REST em Java que necessite de tais funcionalidades. Além disso, o projeto foi desenvolvido seguindo os princípios da <a href=https://medium.com/@gabrielfernandeslemos/clean-architecture-uma-abordagem-baseada-em-princ%C3%ADpios-bf9866da1f9c>Arquitetura Limpa</a> e utilizando as melhores e mais atualizadas práticas do mercado para assegurar a integridade dos dados sensíveis.</b>
</p>

<h2 id="inicio">🚀 Primeiros Passos</h2>

<h3>Pré-requisitos</h3>

- [Docker](https://www.docker.com/get-started/)
- [Git](https://git-scm.com/downloads)

<h3>Clonando</h3>

```bash
git clone https://github.com/gabrieudev/auth.git
```

<h3>Variáveis de Ambiente</h3>

Caso precise executar a aplicação em modo de produção, você precisará definir variáveis de ambiente com as seguintes chaves antes de realizar o deploy. Caso contrário, apenas ignore este tópico.

```yaml
SERVER_PORT (porta da aplicação)
PUBLIC_KEY (chave JWT pública)
PRIVATE_KEY (chave JWT privada)
DATASOURCE_URL (URL do banco de dados)
DATASOURCE_USERNAME (usuário do banco de dados)
DATASOURCE_PASSWORD (senha do banco de dados)
REDIS_HOST (host do Redis)
REDIS_PASSWORD (senha do Redis)
REDIS_PORT (porta do Redis)
```

Observações: ambas as chaves devem estar no formato Base64, sem cabeçalhos e escritas em uma única linha. Exemplo:

```yaml
MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwD7Nqlp0R8ZyzRjcu8mI8CjGUTtuwPwUkw5ncPT7tYfZuWIdEAcLiYRuopLnynVi6GIJuAgi1CZHPKc+eNe0f1acPHs2Lkh0ruT0ZTpvjMe4mePY55GAd7EzKHpN8h4IH9Xg3Nclk8NQ3GHsaWvxatgcAbbe+giBDwox6q81/QYvHZaFUmqrN2ei+TGtfY4KwjZFthM6d6yZ75Zv7Q8nhXMwEmCb9RwfZcdhPi/txXuU49dPzxttTz6UW1gCZrK8LH58uevGSa2IOk84vVN3d1FiSk9pj2tf1Q1nli6ntO1JFLDJcRxGJz0DnjV8CgzZv8BViG+EfzjPnApJpP3JfQIDAQAB
```

<h3>Inicializando</h3>

Como inicializar o projeto:

```bash
cd auth
docker compose up -d
```

<h2 id="rotas">📍 Endpoints da API</h2>

Caso tenha executado a aplicação, você poderá interagir com as rotas acessando a [documentação](http://localhost:8080/swagger-ui/index.html).

Observação: Assim que a aplicação é inicializada (somente em modo de desenvolvimento), é criado um usuário administrador no banco de dados com as seguintes informações:

```yaml
{ "email": "admin@gmail.com", "password": "adminpassword" }
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
