# REST API for Authentication and Authorization

![Java](https://img.shields.io/badge/Java-21-orange) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3-green) ![Spring Security](https://img.shields.io/badge/Spring%20Security-6-green) [![LinkedIn](https://img.shields.io/badge/Connect%20on-LinkedIn-blue)](https://www.linkedin.com/in/gabrieudev) ![GPL License](https://img.shields.io/badge/License-GPL-blue)

Welcome to my **REST API for user authentication and authorization** project.

Please select your preferred language:

- [English](README.md)
- [Português (Brasil)](README.pt-br.md)

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Technologies](#technologies)
- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [Usage](#usage)
- [Endpoints](#endpoints)
- [Contributions](#contributions)
- [Contact](#contact)

## Introduction

This project was created to facilitate the process of user authentication and authorization, serving as a base that can be reused in any REST API that requires these functionalities. Moreover, the project implements authentication with JWTs and authorization through user roles, using the best and most up-to-date practices in the market to ensure the integrity of sensitive data.

## Features

- Email confirmation for user registration.
- Pagination for searches.
- Documentation with endpoints using Swagger.
- User login with JWT authentication.
- Authorization with roles for access control to different API endpoints.
- Passwords encrypted using best practices.
- Integration with PostgreSQL database.

## Technologies

- ![Java](https://img.shields.io/badge/Java-21-orange): Programming language.
- ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3-green): Framework for building applications.
- ![Spring Security](https://img.shields.io/badge/Spring%20Security-6-green): Security framework for Spring applications.
- ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue): Relational database.

## Getting Started

Follow these steps to run the project on your machine:

1. Clone the repository: `git clone https://github.com/gabrieudev/auth.git`
2. Navigate to the project directory: `cd <path>`
3. Update general configurations in `application.properties`.
4. Build the project: `./mvnw clean install` (for Windows: `mvnw.cmd clean install`)
5. Run the application: `./mvnw spring-boot:run` (for Windows: `mvnw.cmd spring-boot:run`)

## Configuration

- Update the `application.properties` file with all necessary information.

## Usage

1. When starting the project, an admin user role is automatically inserted into the database in `AdminDataLoader.java`. You can change its information there or in `application.properties`.
2. In addition to the user, roles are also inserted into the database according to the values in `RoleEnum.java`. If you need to add more, just follow the enum model.
3. Use a user with the admin role to access protected endpoints.

## Endpoints

- `POST /auth/register`: Registers a user and sends a confirmation link to their email.
- `GET /users/confirm`: Verifies the email.
- `POST /auth/login`: Logs in and receives a JWT.
- `ADMIN Role` `GET /users`: Retrieves all users.
- `ADMIN Role` `DELETE /users/{userId}`: Deletes a user.
- `BASIC Role` `GET /users/{userId}`: Retrieves a user by ID.

Access the complete documentation at the `/swagger-ui.html` endpoint.

## Contributions

Contributions are very welcome! If you want to contribute, fork the repository and create a pull request.

## Contact

If you have any suggestions or questions, please contact me on [LinkedIn](https://www.linkedin.com/in/gabrieudev).

---

**License:** This project is licensed under the terms of the [GNU General Public License (GPL)](LICENSE).