<h1 align="center" style="font-weight: bold;">User Authentication and Authorization 🔒</h1>

<p align="center">
  <a href="#inicio">English</a> •
  <a href="README.pt-br.md">Português</a>
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
 <a href="#structure">Project Structure</a> • 
 <a href="#start">Getting Started</a> • 
 <a href="#routes">API Endpoints</a> •
 <a href="#contribute">Contribute</a>
</p>

<p align="center">
  <b>This project was created to simplify the user authentication and authorization process, serving as a base to be reused in any Java REST API requiring such functionalities. Additionally, the API was developed following <a href="https://medium.com/@marcio.kgr/arquitetura-hexagonal-8958fb3e5507">Hexagonal Architecture</a> principles and the latest industry best practices, including 100% unit test coverage to ensure the integrity of sensitive data.</b>
</p>

<h2 id="structure">📂 Project Structure</h2>

```yaml
src/main/java/br/com/gabrieudev/auth/
├── application # Application layer
│   ├── exception # Exceptions
│   ├── ports # Ports
│   │   ├── input # Input ports
│   │   └── output # Output ports
│   └── service # Services
├── domain # Domain layer
├── adapters # Adapters layer
│   ├── input # Input adapters
│   │   └── rest # Web interaction
│   │       ├── controllers # Controllers
│   │       └── dtos # DTOs
│   └── output # Output adapters
│       └── persistence # Database
│           ├── entities # JPA Entities
│           └── repositories # Repositories (JPA and adapters)
├── config # Spring configurations
└── AuthApplication.java # Application initializer
```

<h2 id="start">🚀 Getting Started</h2>

<h3>Prerequisites</h3>

- [Docker](https://www.docker.com/get-started/)
- [Git](https://git-scm.com/downloads)
- Google account

<h3>Cloning</h3>

```bash
git clone https://github.com/gabrieudev/auth.git
```

<h3>Environment Variables</h3>

To run the application, create a `.env` file in the project root directory with the following variables:

| Key                        | Default Value                 | Required | Description                                              |
| -------------------------- | ----------------------------- | -------- | -------------------------------------------------------- |
| `PROFILE`                  | dev                           | no       | Profile to run the application (dev or prod).            |
| `DATASOURCE_URL`           | initialized in docker-compose | no       | Database connection URL.                                 |
| `DATASOURCE_USERNAME`      | initialized in docker-compose | no       | Database username.                                       |
| `DATASOURCE_PASSWORD`      | initialized in docker-compose | no       | Database password.                                       |
| `REDIS_HOST`               | initialized in docker-compose | no       | Redis connection host.                                   |
| `REDIS_PORT`               | initialized in docker-compose | no       | Redis connection port.                                   |
| `REDIS_PASSWORD`           | initialized in docker-compose | no       | Redis password.                                          |
| `EMAIL_HOST`               | Gmail host                    | no       | Email service host.                                      |
| `EMAIL_PORT`               | Gmail port                    | no       | Email service port.                                      |
| `EMAIL_USERNAME`           | none                          | yes      | Email for sending notifications through the application. |
| `EMAIL_PASSWORD`           | none                          | yes      | App password.                                            |
| `API_BASE_URL`             | Local URL                     | no       | API base URL.                                            |
| `FRONTEND_BASE_URL`        | Swagger UI URL                | no       | Frontend URL for redirection.                            |
| `ACCESS_TOKEN_EXPIRATION`  | 5                             | no       | Access token validity in minutes.                        |
| `REFRESH_TOKEN_EXPIRATION` | 10080                         | no       | Refresh token validity in minutes.                       |

> If you don't know how to obtain app passwords, the [Google Help Center](https://support.google.com/accounts/answer/185833?hl=pt-BR) can assist you.

<h3>Initializing</h3>

Run the following commands:

```bash
cd auth
docker compose up -d --build
```

<h2 id="routes">📍 API Endpoints</h2>

You can now interact with the API routes via the [Swagger interface](http://localhost:8080/swagger-ui/index.html).

Note: Upon initialization, an admin user is created in the database with the following credentials:

```yaml
{... "email": "admin@gmail.com", "password": "adminpassword" ...}
```

Use these to log in and access routes protected against regular users.

<h2 id="contribute">📫 Contribute</h2>

Contributions are highly welcome! To contribute, fork the repository and create a pull request.

1. `git clone https://github.com/gabrieudev/auth.git`
2. `git checkout -b feature/NAME`
3. Follow commit standards.
4. Open a Pull Request explaining the resolved issue or developed feature. Include screenshots of visual changes if applicable, and await review!

<h3>Helpful Documentation</h3>

[📝 How to Create a Pull Request](https://www.atlassian.com/br/git/tutorials/making-a-pull-request)

[💾 Commit Message Standards](https://gist.github.com/joshbuchea/6f47e86d2510bce28f8e7f42ae84c716)
