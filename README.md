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
    - Structured endpoints accessible via Swagger UI (`/swagger-ui`).
- **Email Services**
    - JavaMailSender for email authentication and notifications.
- **Scalability and Documentation**
    - Built on Spring Boot with Swagger for API documentation.

---

## Tech Stack
- **Language:** Java (Spring Boot)
- **Security:** JWT, OAuth
- **Database:** PostgreSQL
- **Build Tool:** Maven
- **Testing:** JUnit
- **API Documentation:** Swagger
- **Other:** REST API, Hibernate ORM, JavaMailSender

---

## Prerequisites
- **Java Development Kit (JDK):** Version 11 or higher
- **PostgreSQL:** Ensure a running PostgreSQL instance
- **Maven:** Build tool for dependency management
- **Environment Variables:**
    - `JWT_SECRET`: 1234567890ABCDEF...
    - `OAUTH_CLIENT_ID`: 123456789012-...
    - `OAUTH_CLIENT_SECRET`: ABCDEF-...
    - `GMAIL_MAIL_USERNAME`: newemail@gmail.com
    - `GMAIL_MAIL_PASSWORD`: newpassword123

---

## Installation
1. **Clone the Repository:**
   ```bash
   git clone <repository_url>
   cd <repository_folder>
   ```
2. **Configure Environment Variables:** Add the required variables to your system or a `.env` file.
3. **Set up the Database:** Update `application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/new-database
   spring.datasource.username=newuser
   spring.datasource.password=newpassword
   spring.jpa.hibernate.ddl-auto=update

   # OAuth properties
   google.redirect-uri=http://localhost:3000/new-callback
   google.token-uri=https://oauth2.googleapis.com/new-token
   google.user-info-uri=https://openidconnect.googleapis.com/v1/new-userinfo

   # SpringDoc for Swagger
   springdoc.swagger-ui.path=/swagger-ui

   # Gmail SMTP Configuration
   spring.mail.host=smtp.gmail.com
   spring.mail.port=587
   spring.mail.properties.mail.smtp.auth=true
   spring.mail.properties.mail.smtp.starttls.enable=true
   spring.mail.properties.mail.smtp.starttls.required=true
   spring.mail.properties.mail.debug=true
   ```
4. **Run the Application:**
   ```bash
   mvn spring-boot:run
   ```

---

## API Endpoints (Viewable via `/swagger-ui`)
### Authentication
- `POST /auth/login` - User login
- `POST /auth/register` - User registration
- `POST /auth/refresh` - Refresh JWT token
---

## Testing
- **Run JUnit Tests:**
  ```bash
  mvn test
  ```

---

## Contributing
We welcome contributions! Please fork the repository and submit a pull request.

---

## License
[MIT License](./LICENSE)

---

## Contact
For inquiries or support, contact me at andresfg2002@gmail.com.

