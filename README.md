# REST API for Authentication and Authorization

![Java](https://img.shields.io/badge/Java-17-orange) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3-green) ![Spring Security](https://img.shields.io/badge/Spring%20Security-6-green) [![LinkedIn](https://img.shields.io/badge/Connect%20on-LinkedIn-blue)](https://www.linkedin.com/in/gabrieudev) ![GPL License](https://img.shields.io/badge/License-GPL-blue)

Please select your preferred language:

- [English](README.md)
- [Português (Brasil)](README.pt-br.md)

Welcome to my **REST API for user authentication and authorization** project.

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Technologies](#technologies)
- [Getting Started](#getting-started)
- [Endpoints](#endpoints)
- [Contributions](#contributions)
- [Contact](#contact)

## Introduction

This project was created to simplify user authentication and authorization, serving as a reusable foundation for any REST API that requires such functionalities. Additionally, the project implements JWT authentication and role-based authorization for users, using the latest industry best practices to ensure the security of sensitive data.

## Features

- User login with JWT-based authentication.
- Role-based authorization for API endpoint access control.
- Implementation of profiles for development and production.
- Password encryption following best practices.
- PostgreSQL database integration.
- Documentation using Swagger.

## Technologies

- ![Java](https://img.shields.io/badge/Java-17-orange): Programming language.
- ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3-green): Framework for building REST APIs.
- ![Spring Security](https://img.shields.io/badge/Spring%20Security-6-green): Used with Spring Boot for application security.
- ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue): Relational database.

## Getting Started

The project can be run in two profiles: dev or prod.

**Dev profile** (recommended for testing and local execution):

1. Clone the repository:

   ```bash
   git clone https://github.com/gabrieudev/auth.git
   ```

2. Go to the `application.properties` file and replace "prod" with "dev".

3. Build the project:

   ```bash
   ./mvnw clean install
   ```

   For Windows:

   ```bash
   mvnw.cmd clean install
   ```

4. Run the application:

   ```bash
   ./mvnw spring-boot:run
   ```

   For Windows:

   ```bash
   mvnw.cmd spring-boot:run
   ```

5. Log in at the `/login` route using the email `admin@gmail.com` and password `admin` to access protected endpoints.

**Prod profile** (recommended for production deployment):

1. Clone the repository:

   ```bash
   git clone https://github.com/gabrieudev/auth.git
   ```

2. Before deploying the application, set the following environment variables on your service (the database used must be PostgreSQL, with tables exactly as defined in the project):
   - DATABASE_URL: Database connection URL.
   - DATABASE_USERNAME: Username for database access.
   - DATABASE_PASSWORD: Password for database access.
   - PUBLIC_KEY: Public key for JWT usage.
   - PRIVATE_KEY: Private key for JWT usage.
3. Complete the deployment.

## Endpoints

If you ran the application in dev mode, access the [documentation](http://localhost:9090/swagger-ui/index.html) to view all routes. Otherwise, the endpoint is `/swagger-ui/index.html`.

## Contributions

Contributions are very welcome! If you’d like to contribute, fork the repository and create a pull request.

## Contact

If you have any suggestions or questions, contact me on [LinkedIn](https://www.linkedin.com/in/gabrieudev).

---

**License:** This project is licensed under the terms of the [GNU General Public License (GPL)](LICENSE).
