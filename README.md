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
 <a href="#getting-started">Getting Started</a> • 
 <a href="#routes">API Endpoints</a> •
 <a href="#contribute">Contribute</a>
</p>

<p align="center">
  <b>This project was created to facilitate the process of user authentication and authorization, serving as a base to be reused in any Java REST API that requires such functionalities. Additionally, the API was developed following the principles of <a href=https://medium.com/@marcio.kgr/arquitetura-hexagonal-8958fb3e5507>Hexagonal Architecture</a> and using the best and most updated market practices to ensure the integrity of sensitive data.</b>
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

<h2 id="getting-started">🚀 Getting Started</h2>

<h3>Prerequisites</h3>

- [Docker](https://www.docker.com/get-started/)
- [Git](https://git-scm.com/downloads)
- Google account

<h3>Cloning</h3>

```bash
git clone https://github.com/gabrieudev/auth.git
```

<h3>Environment Variables</h3>

To run the application, you will need to create a `.env` file in the root directory of the project, containing the following environment variables related to your Google account, which will be used to send notifications:

```bash
EMAIL_USERNAME=<EMAIL>
EMAIL_PASSWORD=<APP_PASSWORD>
```

> If you don't know how to obtain the app passwords, the [Google Help Center](https://support.google.com/accounts/answer/185833?hl=en) can help you.

<h3>Initializing</h3>

How to initialize the project:

```bash
cd auth
docker compose up -d
```

<h2 id="routes">📍 API Endpoints</h2>

You can now interact with the routes by accessing the [Swagger interface](http://localhost:8080/swagger-ui/index.html).

Note: Once the application is initialized, an admin user is created in the database with the following information:

```yaml
{... "email": "admin@gmail.com", "password": "adminpassword" ...}
```

Use them to log in and access the routes protected against regular users.

<h2 id="contribute">📫 Contribute</h2>

Contributions are very welcome! If you want to contribute, fork the repository and create a pull request.

1. `git clone https://github.com/gabrieudev/auth.git`
2. `git checkout -b feature/NAME`
3. Follow the commit standards.
4. Open a Pull Request explaining the issue resolved or the feature developed. If applicable, attach screenshots of visual modifications and wait for the review!

<h3>Documentation that may help</h3>

[📝 How to create a Pull Request](https://www.atlassian.com/git/tutorials/making-a-pull-request)

[💾 Commit Standards](https://gist.github.com/joshbuchea/6f47e86d2510bce28f8e7f42ae84c716)
