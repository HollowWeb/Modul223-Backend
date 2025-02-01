**wYZen - Local Development Setup Checklist**

---

## **1. Prerequisites:**
Ensure the following tools and services are installed:
- **Git** - Version control system.
- **Node.js (v18+)** - Required for frontend development.
- **npm or yarn** - Node package managers.
- **Java 21** - Required for backend development.
- **Maven** - Build tool for the backend.
- **MySQL Server (v8.0+)** - Database server.
- **Docker** - For containerization (optional for DB setup).
- **IDE/Editor:**
    - Frontend: Visual Studio Code (recommended).
    - Backend: IntelliJ IDEA or Eclipse (recommended).

---

## **2. Repository Setup:**
1. **Clone Repositories:**
   ```bash
   git clone <frontend-repo-url>
   git clone <backend-repo-url>
   ```
2. **Checkout Develop Branch:**
   ```bash
   git checkout develop
   ```

---

## **3. Database Configuration:**
1. **Create Database:**
    - Login to MySQL:
      ```bash
      mysql -u root -p
      ```
    - Create database:
      ```sql
      CREATE DATABASE wyzen_db;
      ```
2. **Add Database User:**
   ```sql
   CREATE USER 'wyzen_user'@'localhost' IDENTIFIED BY 'password';
   GRANT ALL PRIVILEGES ON wyzen_db.* TO 'wyzen_user'@'localhost';
   FLUSH PRIVILEGES;
   ```

---

## **4. Backend Setup:**
1. **Navigate to Backend Directory:**
   ```bash
   cd backend
   ```
2. **Configure Environment Variables:**
    - Create a `.env` file in the backend directory:
      ```bash
      touch .env
      ```
    - Add the following variables:
      ```properties
      DB_HOST=localhost
      DB_PORT=3306
      DB_NAME=wyzen_db
      DB_USER=wyzen_user
      DB_PASSWORD=password
      JWT_SECRET=your_jwt_secret
      ```
3. **Build the Application:**
   ```bash
   mvn clean install
   ```
4. **Run the Application:**
   ```bash
   mvn spring-boot:run
   ```

---

## **5. Frontend Setup:**
1. **Navigate to Frontend Directory:**
   ```bash
   cd frontend
   ```
2. **Install Dependencies:**
   ```bash
   npm install
   ```
3. **Configure Environment Variables:**
    - Create a `.env` file in the frontend directory:
      ```bash
      touch .env
      ```
    - Add the following variables:
      ```bash
      VITE_API_URL=http://localhost:8080/api
      ```
4. **Run the Frontend Application:**
   ```bash
   npm run dev
   ```

---

## **6. Testing the Setup:**
- **Verify Backend API:** Open `http://localhost:8080/api/health` in the browser to check if the backend is running.
- **Verify Frontend UI:** Open `http://localhost:3000` in the browser to check the frontend.
- **Test Database Connection:**
   ```bash
   mysql -u wyzen_user -p -D wyzen_db
   ```

---

## **7. Troubleshooting:**
1. **Backend Port Conflict:** Update `server.port` in `application.properties` file if port 8080 is in use.
2. **Frontend Port Conflict:** Update the `VITE_PORT` variable in `.env` file if port 3000 is in use.
3. **Database Issues:**
    - Verify MySQL is running.
    - Check credentials and permissions.
4. **Dependency Issues:** Run the following commands:
    - Backend:
      ```bash
      mvn clean install -U
      ```
    - Frontend:
      ```bash
      npm install --force
      ```

---

