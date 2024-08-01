# REST API for Authentication and Authorization

![Java](https://img.shields.io/badge/Java-17-orange) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3-green) ![Spring Security](https://img.shields.io/badge/Spring%20Security-6-green) [![LinkedIn](https://img.shields.io/badge/Connect%20on-LinkedIn-blue)](https://www.linkedin.com/in/gabrieudev) ![GPL License](https://img.shields.io/badge/License-GPL-blue)

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

This project was created to facilitate the process of user authentication and authorization, serving as a base to be reused in any REST API that requires such functionalities. Additionally, the project implements authentication with JWTs and authorization through user roles, using the best and most up-to-date market practices to ensure the integrity of sensitive data.

## Features

- Sends an email for user registration confirmation.
- Password reset.
- Paginated searches.
- Documentation using Swagger.
- User login with JWT authentication.
- Role-based authorization for access control to different API endpoints.
- Encrypted passwords using best practices.
- Integration with MySQL database.

## Technologies

- ![Java](https://img.shields.io/badge/Java-17-orange): Programming language.
- ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3-green): Framework used for building applications.
- ![Spring Security](https://img.shields.io/badge/Spring%20Security-6-green): Framework for securing Spring applications.
- ![MySQL](https://img.shields.io/badge/MySQL-Database-blue): Relational database.

## Getting Started

Follow these steps to run the project on your machine (docker must be installed):

1. Clone the repository: `git clone https://github.com/gabrieudev/auth.git`
2. Navigate to the project directory: `cd <path>`
3. Build the project: `./mvnw clean install` (for Windows: `mvnw.cmd clean install`)
4. Run the application: `./mvnw spring-boot:run` (for Windows: `mvnw.cmd spring-boot:run`)

## Configuration

- Before starting the application, some variables need to be changed for JavaMailSender to work. You can do this by navigating to the file: `cd <path>/src/main/resources/application.properties`.

## Usage

1. When the project starts, a user with the admin role is inserted into the database automatically. Their information can be changed in `auth/src/main/resources/application.properties`.
2. Along with the user, the roles are also inserted into the database according to the values in `auth/src/main/java/com/api/auth/model/enums/RoleEnum.java`. If you need to add more, just follow the enum model.
3. Use a user with the admin role to access the protected endpoints.

## Endpoints

- `POST /auth/register`: Registers a user and sends a confirmation link to their email.
- `GET /users/confirm`: Email verification.
- `POST /auth/login`: Logs in and receives a JWT.
- `ADMIN Role` `GET /users`: Retrieves all users.
- `ADMIN Role` `DELETE /users/{userId}`: Deletes a user.
- `BASIC Role` `GET /users/{userId}`: Retrieves a user by ID.
- `BASIC Role` `POST /users/change-password`: Changes a user's password.

Access the full documentation at the endpoint `/swagger-ui.html`

## Contributions

Contributions are very welcome! If you want to contribute, fork the repository and create a pull request.

## Contact

If you have any suggestions or questions, contact me on [LinkedIn](https://www.linkedin.com/in/gabrieudev)

---

**License:** This project is licensed under the terms of the [GNU General Public License (GPL)](LICENSE).