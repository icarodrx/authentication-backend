<div id="top">

<img src="logo.png" width="10%" align="left" style="margin-right: 15px">

<h1 style="border-bottom: none;">authentication-backend</h1>

<em></em>

<img src="https://img.shields.io/github/last-commit/icarodrx/authentication-backend?style=default&logo=git&logoColor=white&color=0080ff" alt="last-commit">
<img src="https://img.shields.io/github/languages/top/icarodrx/authentication-backend?style=default&color=0080ff" alt="repo-top-language">
<img src="https://img.shields.io/github/languages/count/icarodrx/authentication-backend?style=default&color=0080ff" alt="repo-language-count">

<em></em>

<img src="https://img.shields.io/badge/Kotlin-0095D5?style=flat&logo=kotlin&logoColor=white" alt="kotlin-badge" />
<img src="https://img.shields.io/badge/Spring_Boot-6DB33F?style=flat&logo=spring-boot&logoColor=white" alt="spring-boot-badge" />
<img src="https://img.shields.io/badge/Apache_Maven-CC3636?style=flat&logo=apache-maven&logoColor=white" alt="maven-badge" />
<img src="https://img.shields.io/badge/Docker-2496ED?style=flat&logo=docker&logoColor=white" alt="docker-badge" />

<br/>

## Table of Contents

1. [Table of Contents](#table-of-contents)
2. [Overview](#overview)
3. [Demo](#demo)
4. [Project Structure](#project-structure)
5. [Getting Started](#getting-started)
    - 5.1. [Prerequisites](#prerequisites)
    - 5.2. [Installation](#installation)
    - 5.3. [Usage](#usage)

---

## Overview

This repository hosts authentication-backend, a Kotlin Spring Boot application designed to function as the core backend for a web-based authentication system. You can access the deployed system at {URL}.

This project was developed primarily for learning purposes, demonstrating fundamental authentication functionalities. It includes:

- <strong>User Registration:</strong> Create new user accounts.
- <strong>Forgot Password Flow:</strong> An email-driven process to reset user passwords.
- <strong>User Login:</strong> Secure authentication for existing users.
- <strong>Protected Resources:</strong> Endpoints that provide access to user-specific information, requiring successful authentication.

The application leverages a PostgreSQL relational database for all its data persistence.

---

## Demo

insert-video-here

---

## Project Structure

```sh
└── authentication-backend/
    ├── Dockerfile
    ├── docker-compose.yml
    ├── mvnw
    ├── mvnw.cmd
    ├── pom.xml
    └── src
        ├── main
        │   ├── kotlin.com.icaroreis
        │   │   └── authenticationbackend
        │   │       ├── AuthenticationBackendApplication.kt
        │   │       ├── config
        │   │       ├── controller
        │   │       ├── domain
        │   │       ├── exception
        │   │       ├── repository
        │   │       ├── security
        │   │       └── usecases
        │   └── resources
        └── test
```

## Getting Started

### Prerequisites

This project requires the following dependencies:

- **Programming Language:** Kotlin
- **Package Manager:** Maven
- **Container Runtime:** Docker

### Installation

Build authentication-backend from the source and install dependencies:

1. **Clone the repository:**

   ```sh
   ❯ git clone https://github.com/icarodrx/authentication-backend
   ```

2. **Navigate to the project directory:**

   ```sh
   ❯ cd authentication-backend
   ```

3. **Configure Environment Variables:**

   You'll need to create a .env file in the application's root directory to provide the necessary environment variables. Use the provided .env.example file as a template:

   ```sh
   POSTGRES_DB_USER=insert-db-user
   POSTGRES_DB_PASSWORD=insert-db-password
   SMTP_PASSWORD=insert-smtp-password
   JWT_SECRET=insert-jwt-secret
   ```

   Here's a breakdown of each variable:

    - POSTGRES_DB_USER and POSTGRES_DB_PASSWORD: These values will automatically serve as the credentials for your PostgreSQL database connection.
    - JWT_SECRET: This should be a long, securely encoded string. It's crucial for encoding and decoding JSON Web Tokens (JWTs) used in the application.
    - SMTP_PASSWORD: This is the application-specific password for your email server, required for sending "forgot password" emails. If this value is missing or invalid, the password reset emails won't be sent, but the application will still run.

   For email functionality to work correctly, ensure the following mail block in application.yml is also completely filled out:

   ```sh
   mail:
   	host: insert-email-host
   	port: insert-port
   	username: insert-username
   	password: ${SMTP_PASSWORD}
   	properties:
   		mail:
   			smtp:
   				auth: true
   				starttls:
   					enable: true
   					required: true
   				connectiontimeout: 5000
   				timeout: 5000
   				writetimeout: 5000
   ```

### Usage

Run the project with:

```sh
❯ docker compose up -d
```

<div align="right">

[![][back-to-top]](#top)

</div>

[back-to-top]: https://img.shields.io/badge/-BACK_TO_TOP-151515?style=flat-square

---