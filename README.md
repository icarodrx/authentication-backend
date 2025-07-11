<div id="top" style="text-align: center;">

  <img src="logo.png" width="10%" style="margin-bottom: 10px;" alt="Project Logo" />

  <h1>authentication-backend</h1>

  <div style="margin: 10px 0;">
    <img src="https://img.shields.io/github/languages/top/icarodrx/authentication-backend?style=default&color=0080ff" alt="Top Language Badge" />
    <img src="https://img.shields.io/github/languages/count/icarodrx/authentication-backend?style=default&color=0080ff" alt="Languages Count Badge" />
  </div>

  <div style="margin: 10px 0;">
    <img src="https://img.shields.io/badge/Kotlin-0095D5?style=flat&logo=kotlin&logoColor=white" alt="Kotlin Badge" />
    <img src="https://img.shields.io/badge/Spring_Boot-6DB33F?style=flat&logo=spring-boot&logoColor=white" alt="Spring Boot Badge" />
    <img src="https://img.shields.io/badge/Apache_Maven-CC3636?style=flat&logo=apache-maven&logoColor=white" alt="Apache Maven Badge" />
    <img src="https://img.shields.io/badge/Docker-2496ED?style=flat&logo=docker&logoColor=white" alt="Docker Badge" />
  </div>

</div>

## Table of Contents

1. [Table of Contents](#table-of-contents)
2. [Overview](#overview)
3. [Demo](#demo)
4. [Project Structure](#project-structure)
5. [Getting Started](#getting-started)
   - 5.1. [Prerequisites](#prerequisites)
   - 5.2. [Installation](#installation)
   - 5.3. [Usage](#usage)

## Overview

This repository hosts **authentication-backend**, a Kotlin Spring Boot application designed as the core backend for a web-based authentication system. You can access the deployed system at the following URL:

http://authenticationsystem.s3-website-us-east-1.amazonaws.com/login

This project was developed primarily for learning purposes and demonstrates fundamental authentication functionalities, including:

- **User Registration:** Create new user accounts.
- **Forgot Password Flow:** Email-driven process to reset user passwords.
- **User Login:** Secure authentication for existing users.
- **Protected Resources:** Endpoints requiring successful authentication to access user-specific data.

The application uses a PostgreSQL database for persistent storage.

## Demo

[![Demo Video](https://img.youtube.com/vi/S3Psf9_8ppA/0.jpg)](https://www.youtube.com/watch?v=S3Psf9_8ppA)

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
        │   ├── kotlin
        │   │   └── com.icaroreis.authenticationbackend
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

This project requires:

- **Programming Language:** Kotlin
- **Package Manager:** Maven
- **Container Runtime:** Docker

### Installation

To build the project and install dependencies:

1. **Clone the repository:**

```sh
git clone https://github.com/icarodrx/authentication-backend
```

2. **Navigate to the project directory:**

```sh
cd authentication-backend
```

3. **Configure Environment Variables:**

Create a `.env` file in the root directory using `.env.example` as a reference:

```env
POSTGRES_DB_USER=insert-db-user
POSTGRES_DB_PASSWORD=insert-db-password
SMTP_PASSWORD=insert-smtp-password
JWT_SECRET=insert-jwt-secret
```

#### Explanation:

- `POSTGRES_DB_USER` and `POSTGRES_DB_PASSWORD`: Credentials for your PostgreSQL database.
- `JWT_SECRET`: A long, secure key for encoding/decoding JWTs.
- `SMTP_PASSWORD`: Email provider password for sending reset emails.  
  If omitted or invalid, the app still runs but password reset won't function.

Also, ensure the following block is correctly set in `application.yml`:

```yaml
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

Start the project using Docker:

```sh
docker compose up -d
```

<div style="text-align: right;">

[![][back-to-top]](#top)

</div>

[back-to-top]: https://img.shields.io/badge/-BACK_TO_TOP-151515?style=flat-square

---