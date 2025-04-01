<h1 align="center" style="font-weight: bold;">User Authentication and Authorization 🔒</h1>

<p align="center">
  <a href="#inicio">English</a> •
  <a href="README.pt-br.md">Português</a>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java">
  <img src="https://img.shields.io/badge/typescript-%23007ACC.svg?style=for-the-badge&logo=typescript&logoColor=white" alt="TypeScript">
  <img src="https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white" alt="Spring">
  <img src="https://img.shields.io/badge/react-%2320232a.svg?style=for-the-badge&logo=react&logoColor=%2361DAFB" alt="React">
  <img src="https://img.shields.io/badge/vite-%23646CFF.svg?style=for-the-badge&logo=vite&logoColor=white" alt="Vite">
  <img src="https://img.shields.io/badge/tailwindcss-%2338B2AC.svg?style=for-the-badge&logo=tailwind-css&logoColor=white" alt="TailwindCSS">
  <img src="https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens" alt="JWT">
  <img src="https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white" alt="Postgres">
  <img src="https://img.shields.io/badge/redis-%23DD0031.svg?style=for-the-badge&logo=redis&logoColor=white" alt="Redis">
  <img src="https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white" alt="Docker">
  <img src="https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white" alt="Swagger">
</p>

<p align="center">
 <a href="#structure">Project Structure</a> •
 <a href="#getting-started">Getting Started</a> •
 <a href="#interaction">Interaction</a> •
 <a href="#contribute">Contribute</a>
</p>

<p align="center">
  <b>The project was created to facilitate the user authentication and authorization process, serving as a reusable base for any application using the same technologies that require such functionalities. Furthermore, the API was developed following the principles of <a href=https://medium.com/@marcio.kgr/arquitetura-hexagonal-8958fb3e5507>Hexagonal Architecture</a> and using the best and most up-to-date market practices, including unit tests with 100% coverage and HTTP-Only Cookies to ensure data integrity.</b>
</p>

<h2 id="structure">📂 Project Structure</h2>

```yaml
├── backend/
│   ├── src/main/java/br/com/gabrieudev/auth/
│   │   ├── application/                      # Application layer
│   │   │   ├── exceptions/                   # Exceptions
│   │   │   ├── ports/                        # Ports
│   │   │   │   ├── input/                    # Input ports
│   │   │   │   └── output/                   # Output ports
│   │   │   └── services/                     # Services
│   │   ├── domain/                           # Domain layer
│   │   ├── adapters/                         # Adapters layer
│   │   │   ├── input/                        # Input adapters
│   │   │   │   └── rest/                     # Web interaction
│   │   │   │       ├── controllers/          # Controllers
│   │   │   │       └── dtos/                 # DTOs
│   │   │   └── output/                       # Output adapters
│   │   │       └── persistence/              # Database
│   │   │           ├── entities/             # JPA Entities
│   │   │           └── repositories/         # Repositories (JPA and adapters)
│   │   └── config/                           # Spring configurations
│   └── Dockerfile                            # Backend Dockerfile
│
├── frontend/
│   ├── src/
│   │   ├── components/                       # Components
│   │   ├── types/                            # Interfaces reflecting backend responses
│   │   ├── layouts/                          # Layouts
│   │   ├── services/                         # Services and requests
│   │   ├── hooks/                            # Custom hooks
│   │   └── pages/                            # Pages
│   └── Dockerfile                            # Frontend Dockerfile
│
├── docs/                                     # Project documentation
│
└── docker-compose.yml                        # Containerization (backend, frontend, Postgres, and Redis)
```

<h2 id="getting-started">🚀 Getting Started</h2>

<h3>Prerequisites</h3>

- [Docker Compose](https://docs.docker.com/compose/install/)
- [Git](https://git-scm.com/downloads)
- Google account

<h3>Cloning</h3>

```bash
git clone https://github.com/gabrieudev/auth.git
```

<h3>Environment Variables</h3>

To run the application, you need to create a `.env` file in the project's root directory containing the following environment variables:

| Key                        | Default Value                         | Defined | Description                                                 |
| -------------------------- | ------------------------------------- | ------- | ----------------------------------------------------------- |
| `SERVER_PORT`              | 8080                                  | ✅      | server port.                                                |
| `PROFILE`                  | dev                                   | ✅      | profile in which the application will run (dev or prod).    |
| `DATASOURCE_URL`           | jdbc:postgresql://postgres:5432/auth  | ✅      | database connection URL.                                    |
| `DATASOURCE_USERNAME`      | admin                                 | ✅      | database connection username.                               |
| `DATASOURCE_PASSWORD`      | admin                                 | ✅      | database connection password.                               |
| `REDIS_HOST`               | redis                                 | ✅      | Redis connection host.                                      |
| `REDIS_PORT`               | 6379                                  | ✅      | Redis connection port.                                      |
| `REDIS_PASSWORD`           | admin                                 | ✅      | Redis connection password.                                  |
| `EMAIL_HOST`               | smtp.gmail.com                        | ✅      | email host.                                                 |
| `EMAIL_PORT`               | 587                                   | ✅      | email port.                                                 |
| `EMAIL_USERNAME`           | none                                  | ❌      | email for sending notifications through the application.    |
| `EMAIL_PASSWORD`           | none                                  | ❌      | app password.                                               |
| `API_BASE_URL`             | http://localhost:{SERVER_PORT}/api/v1 | ✅      | API base URL.                                               |
| `FRONTEND_BASE_URL`        | http://localhost:5137                 | ✅      | base URL of some interface for interaction and redirection. |
| `ACCESS_TOKEN_EXPIRATION`  | 5                                     | ✅      | access token expiration time in minutes.                    |
| `REFRESH_TOKEN_EXPIRATION` | 10080                                 | ✅      | refresh token expiration time in minutes.                   |

> If you don't know how to obtain the app password, the [Google Help Center](https://support.google.com/accounts/answer/185833?hl=en) can assist you.

<h3>Initializing</h3>

Run the following commands:

```bash
cd auth
docker compose up -d --build
```

<h2 id="interaction">🌐 Interaction</h2>

Now, you can interact with the application in two ways:

- Interface at [http://localhost:5173](http://localhost:5173)
- Swagger UI at [http://localhost:{PORT}/api/v1/swagger-ui/index.html](http://localhost:{PORT}/api/v1/swagger-ui/index.html)

Note: When the application is initialized, an admin user is created in the database with the following credentials:

```yaml
{... "email": "admin@gmail.com", "password": "adminpassword" ...}
```

Use these credentials to log in and access routes protected from regular users.

> Email-based functionalities will not work since the email is fictional.

<h2 id="contribute">📫 Contribute</h2>

Contributions are very welcome! If you would like to contribute, fork the repository and create a pull request.

1. `git clone https://github.com/gabrieudev/auth.git`
2. `git checkout -b feature/NAME`
3. Follow commit standards.
4. Open a Pull Request explaining the resolved issue or developed functionality. If applicable, attach screenshots of visual changes and wait for the review!
