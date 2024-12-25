# **wYZen - Application Properties Setup Checklist**

---

## **1. Backend Application Properties Setup:**

### **1.1 Create Configuration Files:**
- **Default Configuration File:**
    - Create `application.properties` (or `application.yml`) in the `src/main/resources` folder.

### **1.2 Database Configuration:**
```properties
spring.datasource.url=jdbc:mysql://${DB_HOST}:${DB_PORT}/${DB_NAME}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

### **1.3 Server Configuration:**
```properties
server.port=${SERVER_PORT}
server.error.include-message=always
```

### **1.4 JWT Security Configuration:**
```properties
security.jwt.secret-key=${JWT_SECRET}
security.jwt.expiration-time=86400000 # 24 hours
```

### **1.5 Logging Configuration:**
```properties
logging.level.org.springframework=INFO
logging.level.com.example.wyzen=DEBUG
```

### **1.6 File Upload Configuration:**
```properties
file.upload-dir=${FILE_UPLOAD_DIR}
```

### **1.7 CORS Configuration:**
```properties
cors.allowed-origins=${CORS_ALLOWED_ORIGINS}
cors.allowed-methods=GET,POST,PUT,DELETE
cors.allowed-headers=Authorization,Content-Type
```

---

## **2. Frontend Environment Setup:**

### **2.1 Create Environment File:**
- Create `.env` in the root directory of the frontend project.

### **2.2 Add Environment Variables:**
```env
VITE_API_URL=http://localhost:8080/api
VITE_IMAGE_UPLOAD_DIR=/uploads
VITE_JWT_SECRET=your_jwt_secret
VITE_APP_NAME=wYZen
```

---

## **3. Secrets Management (Optional but Recommended):**
- **Backend:** Store secrets securely using AWS Secrets Manager, HashiCorp Vault, or environment variables.
- **Frontend:** Use `.env.local` for sensitive keys and exclude it from version control.

---

## **4. Docker Configuration (Optional):**

### **4.1 Backend Dockerfile Configuration:**
```dockerfile
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### **4.2 Frontend Dockerfile Configuration:**
```dockerfile
FROM node:18-alpine
WORKDIR /app
COPY . .
RUN npm install && npm run build
EXPOSE 3000
CMD ["npm", "run", "preview"]
```

---

## **5. Testing Configuration Setup:**

### **Backend Test Configuration:**
- Add `application-test.properties` file for testing.
```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=create-drop
```

### **Frontend Test Environment:**
```env
VITE_API_URL=http://localhost:8080/api/test
```

---

## **6. Documentation and Examples:**
- Provide sample `.env.example` and `application.properties.example` files for quick configuration.
- Add README documentation to explain variable usage and setup.


