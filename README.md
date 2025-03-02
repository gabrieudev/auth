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

| Key                           | Default Value                                               | Required | Description                                                      |
| ----------------------------- | ----------------------------------------------------------- | -------- | ---------------------------------------------------------------- |
| `SERVER_PORT`                 | 8080                                                        | no       | server port.                                                     |
| `PROFILE`                     | dev                                                         | no       | profile in which the application will run (dev or prod).         |
| `DATASOURCE_URL`              | jdbc:postgresql://postgres:5432/auth                        | no       | database connection URL.                                         |
| `DATASOURCE_USERNAME`         | admin                                                       | no       | database connection username.                                    |
| `DATASOURCE_PASSWORD`         | admin                                                       | no       | database connection password.                                    |
| `REDIS_HOST`                  | redis                                                       | no       | Redis connection host.                                           |
| `REDIS_PORT`                  | 6379                                                        | no       | Redis connection port.                                           |
| `REDIS_PASSWORD`              | admin                                                       | no       | Redis connection password.                                       |
| `EMAIL_HOST`                  | smtp.gmail.com                                              | no       | email host for sending notifications.                            |
| `EMAIL_PORT`                  | 587                                                         | no       | email port for sending notifications.                            |
| `EMAIL_USERNAME`              | none                                                        | yes      | email for sending notifications through the application.         |
| `EMAIL_PASSWORD`              | none                                                        | yes      | app password.                                                    |
| `API_BASE_URL`                | http://localhost:{SERVER_PORT}/api/v1                       | no       | base URL of the API.                                             |
| `FRONTEND_BASE_URL`           | http://localhost:{SERVER_PORT}/api/v1/swagger-ui/index.html | no       | URL of some interface for redirection (Swagger as default).      |
| `FRONTEND_RESET_PASSWORD_URL` | http://localhost:{SERVER_PORT}/api/v1/swagger-ui/index.html | no       | URL of page for password reset in frontend (Swagger as default). |
| `ACCESS_TOKEN_EXPIRATION`     | 5                                                           | no       | access token expiration time in minutes.                         |
| `ACCESS_TOKEN_EXPIRATION`     | 10080                                                       | no       | refresh token expiration time in minutes.                        |

> If you don't know how to obtain app password, the [Google Help Center](https://support.google.com/accounts/answer/185833?hl=pt-BR) can assist you.

<h3>Initializing</h3>

Run the following commands:

```bash
cd auth
docker compose up -d --build
```

<h2 id="routes">📍 API Endpoints</h2>

You can now interact with the API routes via [http://localhost:{SERVER_PORT}/api/v1/swagger-ui/index.html](http://localhost:{SERVER_PORT}/api/v1/swagger-ui/index.html).

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
