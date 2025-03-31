# ChatTrilogy - Real-time Chat Application

## Overview
ChatTrilogy is a real-time chat application built with **Spring Boot**, **WebSocket**, and **JWT Authentication**. It supports text and image messages, friend requests, activity tracking, and local message storage.

## Features
- **Real-time messaging** (Text & Image) via WebSocket
- **Friend request management**
- **User activity tracking** (Online/Offline status)
- **View friend profiles**
- **Edit personal information**
- **JWT-based API authentication**
- **Upload and store profile images**
- **View last seen status of friends (See when a friend was last online)**

## WebSocket Handlers
1. **Chat Real-time** - Handles instant messaging
2. **Friend Requests** - Manages sending and receiving friend requests
3. **User Activity Tracking** - Updates online/offline status based on session login

## Prerequisites
- Java 17+
- Spring Boot 3+
- MySQL 8+
- Lombok
- Gradle (or Maven)
- Firebase (for notifications)

## Installation
### 1. Clone the repository
```sh
git clone https://github.com/your-repo/chattrilogy.git
cd chattrilogy
```

### 2. Configure `application.properties`
```properties
spring.application.name=chattrilogy

spring.datasource.url=jdbc:mysql://localhost:3306/chattrilogy
spring.datasource.username=root
spring.datasource.password=abc123
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA settings
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

server.address=127.0.0.1
server.port=9090

# JWT Configuration
minhdan.jwt.base64-secret=your-secret-key
minhdan.jwt.token-validity-in-seconds=2592000

# File Upload Configuration
spring.servlet.multipart.max-file-size=50MB
spring.servlet.multipart.max-request-size=50MB
minhdan.upload-file.base-path=C:/Workspace/JAVA_SPRING/upload

# Note: Ensure the base path is correctly set according to your project structure.
# The path must exist and have proper read/write permissions.

# Firebase Configuration
app.firebase-configuration-file=minhdanmessage-firebase-adminsdk.json
```

### 3. Build and Run the Application
#### Using Gradle:
```sh
./gradlew bootRun
```
#### Using Maven:
```sh
mvn spring-boot:run
```

## API Endpoints
### Authentication

## Troubleshooting
1. **Lombok Not Working?**
   - Ensure Lombok plugin is installed in your IDE.
   - Run `gradlew clean build` or `mvn clean compile` to verify annotation processing.

2. **WebSocket Not Connecting?**
   - Ensure the WebSocket URL is correctly configured in the frontend.
   - Check server logs for connection errors.

3. **File Not Uploading?**
   - Verify that `base-path` exists and has write permissions.
   - Ensure multipart settings are correctly configured in `application.properties`.

## License
This project is licensed under the MIT License.

---
Developed with ❤️ by Minh Dan.
