[JAVA_BADGE]: https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white
[SPRING_BADGE]: https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white
[JWT_BADGE]: https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens
[SWAGGER_BADGE]: https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white
[POSTGRES_BADGE]: https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white
[REDIS_BADGE]: https://img.shields.io/badge/redis-%23DD0031.svg?style=for-the-badge&logo=redis&logoColor=white
[DOCKER_BADGE]: https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white

<h1 align="center" style="font-weight: bold;">User Authentication and Authorization 🔒</h1>

<p align="center">
  <a href="#inicio">In English</a> •
  <a href="README.pt-br.md">Em português</a>
</p>

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
 <a href="#getting-started">Getting Started</a> • 
 <a href="#api-endpoints">API Endpoints</a> •
 <a href="#contributing">Contributing</a>
</p>

<p align="center">
  <b>This project was created to simplify the process of user authentication and authorization, serving as a reusable base for any Java REST API requiring such features. Moreover, the project follows the principles of <a href="https://medium.com/@gabrielfernandeslemos/clean-architecture-uma-abordagem-baseada-em-princ%C3%ADpios-bf9866da1f9c">Clean Architecture</a> and employs the best and most updated market practices to ensure the integrity of sensitive data.</b>
</p>

<h2 id="getting-started">🚀 Getting Started</h2>

<h3>Prerequisites</h3>

- [Docker](https://www.docker.com/get-started/)
- [Git](https://git-scm.com/downloads)

<h3>Cloning</h3>

```bash
git clone https://github.com/gabrieudev/auth.git
```

<h3>Environment Variables</h3>

If you need to run the application in production mode, you will need to set environment variables with the following keys before deploying. Otherwise, you can skip this section.

```yaml
SERVER_PORT (application port)
PUBLIC_KEY (public JWT key)
PRIVATE_KEY (private JWT key)
DATASOURCE_URL (database URL)
DATASOURCE_USERNAME (database username)
DATASOURCE_PASSWORD (database password)
REDIS_HOST (Redis host)
REDIS_PASSWORD (Redis password)
REDIS_PORT (Redis port)
```

Notes: Both keys must be in Base64 format, without headers, and written in a single line. Example:

```yaml
MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwD7Nqlp0R8ZyzRjcu8mI8CjGUTtuwPwUkw5ncPT7tYfZuWIdEAcLiYRuopLnynVi6GIJuAgi1CZHPKc+eNe0f1acPHs2Lkh0ruT0ZTpvjMe4mePY55GAd7EzKHpN8h4IH9Xg3Nclk8NQ3GHsaWvxatgcAbbe+giBDwox6q81/QYvHZaFUmqrN2ei+TGtfY4KwjZFthM6d6yZ75Zv7Q8nhXMwEmCb9RwfZcdhPi/txXuU49dPzxttTz6UW1gCZrK8LH58uevGSa2IOk84vVN3d1FiSk9pj2tf1Q1nli6ntO1JFLDJcRxGJz0DnjV8CgzZv8BViG+EfzjPnApJpP3JfQIDAQAB
```

<h3>Starting</h3>

How to start the project:

```bash
cd auth
docker compose up -d
```

<h2 id="api-endpoints">📍 API Endpoints</h2>

If the application is running, you can interact with the routes by accessing the [documentation](http://localhost:8080/swagger-ui/index.html).

Note: Once the application is initialized (in development mode only), an admin user is created in the database with the following credentials:

```yaml
{ "email": "admin@gmail.com", "password": "adminpassword" }
```

Use them to log in and access routes protected against regular users.

<h2 id="contributing">📫 Contributing</h2>

Contributions are highly welcome! If you'd like to contribute, fork the repository and create a pull request.

1. `git clone https://github.com/gabrieudev/auth.git`
2. `git checkout -b feature/NAME`
3. Follow commit patterns.
4. Open a Pull Request explaining the problem solved or the feature developed. If applicable, attach screenshots of visual modifications and wait for a review!

<h3>Documentation that might help</h3>

[📝 How to create a Pull Request](https://www.atlassian.com/br/git/tutorials/making-a-pull-request)

[💾 Commit Pattern](https://gist.github.com/joshbuchea/6f47e86d2510bce28f8e7f42ae84c716)
