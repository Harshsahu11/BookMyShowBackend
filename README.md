# 🎬 BookMyShow Backend

A backend REST API for a **BookMyShow  Backend** built using **Spring Boot**. This project provides APIs for managing movies, theatres, shows, users, and ticket bookings with a clean layered architecture.

---

## 🚀 Features

- 👤 User Registration & Login
- 🎥 Movie Management
- 🏢 Theatre Management
- 🎭 Show Management
- 🎟️ Ticket Booking
- 💺 Seat Selection
- 📋 Booking History
- 🔄 RESTful APIs
- 📑 API Documentation using Swagger UI

---

## 🛠️ Tech Stack

### Backend
- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- Spring MVC

### Database
- MySQL

### Documentation
- Swagger / OpenAPI

### Build Tool
- Maven

---

## 📁 Project Structure

```text
BookMyShowBackend
│── src
│   ├── main
│   │   ├── java
│   │   │   └── com.bookmyshow
│   │   │       ├── controller
│   │   │       ├── service
│   │   │       ├── repository
│   │   │       ├── entity
│   │   │       ├── dto
│   │   │       ├── config
│   │   │       └── exception
│   │   │
│   │   └── resources
│   │       ├── application.properties
│   │       └── static
│
└── pom.xml
```

---

## ⚙️ Technologies Used

- Java 21
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- Swagger UI

---

## ▶️ Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/BookMyShowBackend.git
```

### 2. Navigate to the Project

```bash
cd BookMyShowBackend
```

### 3. Configure MySQL

Update your `application.properties` file:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bookmyshow
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 4. Run the Application

```bash
mvn spring-boot:run
```

or run the `BookMyShowApplication` class from your IDE.

---

## 📡 API Modules

- User APIs
- Movie APIs
- Theatre APIs
- Show APIs
- Ticket Booking APIs
- Seat Management APIs

---

## 📖 API Documentation

After running the application, open Swagger UI:

```
http://localhost:8080/swagger-ui/index.html
```

---


## 👨‍💻 Author

**Harsh Sahu**

- GitHub: https://github.com/Harshsahu11
