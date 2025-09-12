## Score System

## Overview
The **project** is a web application consisting of two main components:

1. **Backend (infosystem)**: A Spring Boot application responsible for managing data related to students, teachers, courses, and grades.
2. **Frontend (infosystem-client)**: A Spring Boot web application that uses Thymeleaf for the user interface and communicates with the backend via HTTP.

The system provides functionality for both students and teachers:

- **Students**: Can register, log in, view their courses and grades.
- **Teachers**: Can manage students, courses, grades, and other administrative tasks.

## Technologies

- **Backend**: 
  - Spring Boot
  - Java
  - JPA for database interaction
  - JWT for authentication

- **Frontend**: 
  - Spring Boot
  - Thymeleaf
  - WebClient for API requests
  - HTML

## Setup

### Running the Backend (infosystem)
The backend will be available at http://localhost:8080.

### Running the Frontend (infosystem-client)
The frontend will be available at http://localhost:8081.

### Database
The backend uses a relational database to store user and course data. You can configure the database connection in the application.properties file located in src/main/resources.

### Authentication
The system uses JWT (JSON Web Token) for user authentication. When a user logs in, a JWT is issued, which must be included in subsequent requests to access protected endpoints.

- **Roles**:
  - STUDENT: Can view their own courses, grades, and profile.
  - TEACHER: Can manage students, courses, grades, and other administrative tasks.
  
## GitLab CI/CD
This project includes a GitLab CI/CD pipeline configuration (.gitlab-ci.yml) for continuous integration and deployment.

The pipeline performs the following tasks:

- Run unit tests for both frontend and backend.  
