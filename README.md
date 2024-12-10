
# Backend for Tailored Software and Social Media Management Services

This repository contains the backend application for the DigiMediaDevs platform. It provides APIs to support tailored software and social media management services, ensuring secure, efficient, and scalable operations.

---

## Features

- **User Authentication and Authorization**
  - JWT (JSON Web Token) for secure user sessions.
  - OAuth integration for third-party authentication.
- **Database Management**
  - PostgreSQL for relational database needs.
- **RESTful APIs**
  - Structured endpoints for frontend communication.
- **Scalability**
  - Built on Spring Boot for high performance and easy scalability.

---

## Tech Stack

- **Language:** Java (Spring Boot)
- **Security:** JWT, OAuth
- **Database:** PostgreSQL
- **Build Tool:** Maven/Gradle
- **Other:** REST API, Hibernate ORM

---

## Prerequisites

1. **Java Development Kit (JDK)**
   - Version 11 or higher
2. **PostgreSQL**
   - Ensure a running PostgreSQL instance.
3. **Maven/Gradle**
   - Build tool for dependency management.
4. **Environment Variables**
   - Set up the following variables in your environment:
     - `DATABASE_URL`: Connection string for your PostgreSQL database.
     - `JWT_SECRET`: Secret key for JWT signing and verification.
     - `OAUTH_CLIENT_ID`: Client ID for OAuth integration.
     - `OAUTH_CLIENT_SECRET`: Client secret for OAuth integration.

---

## Installation

1. **Clone the Repository**
   ```bash
   git clone <repository_url>
   cd <repository_folder>
   ```

2. **Configure Environment Variables**
   - Add the required environment variables to your system or a `.env` file.

3. **Set up the Database**
   - Create a PostgreSQL database and update the `application.properties` file:
     ```properties
     spring.datasource.url=jdbc:postgresql://<hostname>:<port>/<database_name>
     spring.datasource.username=<db_username>
     spring.datasource.password=<db_password>
     ```

4. **Run the Application**
   ```bash
   mvn spring-boot:run
   ```
   or
   ```bash
   gradle bootRun
   ```

---

## API Endpoints

### Authentication
- `POST /auth/login` - User login
- `POST /auth/register` - User registration
- `POST /auth/refresh` - Refresh JWT token

### Services
- `GET /services` - Fetch list of services
- `POST /services` - Add a new service
- `PUT /services/:id` - Update service details
- `DELETE /services/:id` - Remove a service

### Social Media Management
- `GET /social` - Fetch social media accounts
- `POST /social` - Add a new account
- `DELETE /social/:id` - Delete an account

---

## Testing

- Run tests using Maven:
  ```bash
  mvn test
  ```
- Run tests using Gradle:
  ```bash
  gradle test
  ```

---

## Contributing

We welcome contributions! Please fork the repository and submit a pull request.

---

## License

[MIT License](./LICENSE)

---

## Contact

For inquiries or support, contact us at andresfg2002@gmail.com.
