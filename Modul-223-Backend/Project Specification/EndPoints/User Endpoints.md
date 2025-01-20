## **User API Documentation**

### **1. Register User**
- **URL:** `POST /api/users/register`
- **Description:** Register a new user with a default `USER` role.
- **Request Headers:**
  - `Content-Type: application/json`
- **Request Body:**
  ```json
  {
    "username": "string",
    "email": "string",
    "password": "string"
  }
  ```
- **Response:**
  - **Status:** `200 OK`
  - **Headers:**
    - `Authorization: Bearer <JWT Token>` (JWT token for the registered user)
  - **Body:**
    ```json
    {
      "user": {
        "id": "number",
        "username": "string",
        "email": "string",
        "roles": ["USER"]
      },
      "message": "Registration successful"
    }
    ```

---

### **2. Login User**
- **URL:** `POST /api/users/login`
- **Description:** Authenticate a user and get a JWT token.
- **Request Headers:**
  - `Content-Type: application/json`
- **Request Body:**
  ```json
  {
    "username": "string",
    "password": "string"
  }
  ```
- **Response:**
  - **Status:** `200 OK`
  - **Headers:**
    - `Authorization: Bearer <JWT Token>` (JWT token for the authenticated user)
  - **Body:**
    ```json
    {
      "message": "Login successful"
    }
    ```

---

### **3. Get Current User Details**
- **URL:** `GET /api/users/me`
- **Description:** Retrieve details of the currently authenticated user.
- **Request Headers:**
  - `Authorization: Bearer <JWT Token>`
- **Response:**
  - **Status:** `200 OK`
  - **Body:**
    ```json
    {
      "id": "number",
      "username": "string",
      "email": "string",
      "roles": ["string"]
    }
    ```

---

### **4. Get User By ID**
- **URL:** `GET /api/users/{id}`
- **Description:** Fetch a user's details by their ID.
- **Request Headers:**
  - `Authorization: Bearer <JWT Token>`
- **Response:**
  - **Status:** `200 OK`
  - **Body:**
    ```json
    {
      "id": "number",
      "username": "string",
      "email": "string",
      "roles": ["string"]
    }
    ```

---

### **5. Get All Users**
- **URL:** `GET /api/users`
- **Description:** Fetch all users (excluding soft-deleted ones).
- **Request Headers:**
  - `Authorization: Bearer <JWT Token>`
- **Response:**
  - **Status:** `200 OK`
  - **Body:**
    ```json
    [
      {
        "id": "number",
        "username": "string",
        "email": "string",
        "roles": ["string"]
      }
    ]
    ```

---

### **6. Search Users**
- **URL:** `GET /api/users/search`
- **Description:** Search for users by username, email, or role. Only accessible to admins.
- **Request Headers:**
  - `Authorization: Bearer <Admin JWT Token>`
- **Query Parameters:**
  - `username`: Filter by username (optional).
  - `email`: Filter by email (optional).
  - `role`: Filter by role (optional).
- **Response:**
  - **Status:** `200 OK`
  - **Body:**
    ```json
    [
      {
        "id": "number",
        "username": "string",
        "email": "string",
        "roles": ["string"]
      }
    ]
    ```

---

### **7. Update User**
- **URL:** `PUT /api/users/{id}`
- **Description:** Update a user's details. Only the user themselves or an admin can perform this action.
- **Request Headers:**
  - `Authorization: Bearer <JWT Token>`
- **Request Body:**
  ```json
  {
    "id": "number",
    "username": "string",
    "email": "string",
    "roles": ["string"]
  }
  ```
- **Response:**
  - **Status:** `200 OK`
  - **Body:**
    ```json
    {
      "id": "number",
      "username": "string",
      "email": "string",
      "roles": ["string"]
    }
    ```

---

### **8. Update User Settings**
- **URL:** `PUT /api/users/settings`
- **Description:** Allow a user to update their own settings.
- **Request Headers:**
  - `Authorization: Bearer <JWT Token>`
- **Request Body:**
  ```json
  {
    "username": "new_username",
    "email": "new_email@example.com",
    "password": "new_password"
  }
  ```
- **Response:**
  - **Status:** `200 OK`
  - **Body:**
    ```json
    {
      "id": "number",
      "username": "new_username",
      "email": "new_email@example.com",
      "roles": ["USER"]
    }
    ```

---

### **9. Change Password**
- **URL:** `PUT /api/users/{id}/change-password`
- **Description:** Allow a user to change their password. Admins can change passwords for other users.
- **Request Headers:**
  - `Authorization: Bearer <JWT Token>`
- **Request Body:**
  ```json
  {
    "oldPassword": "string", 
    "newPassword": "string"
  }
  ```
- **Response:**
  - **Status:** `200 OK`
  - **Body:**
    ```json
    "Password updated successfully."
    ```

---

### **10. Admin Update User**
- **URL:** `PUT /api/users/{id}/admin-update`
- **Description:** Allow an admin to update any user's details, including roles.
- **Request Headers:**
  - `Authorization: Bearer <Admin JWT Token>`
- **Request Body:**
  ```json
  {
    "username": "updated_username",
    "email": "updated_email@example.com",
    "password": "updated_password",
    "roles": ["USER", "ADMIN"]
  }
  ```
- **Response:**
  - **Status:** `200 OK`
  - **Body:**
    ```json
    {
      "id": "number",
      "username": "updated_username",
      "email": "updated_email@example.com",
      "roles": ["USER", "ADMIN"]
    }
    ```

---

### **11. Reactivate User**
- **URL:** `POST /api/users/{id}/reactivate`
- **Description:** Allow an admin to reactivate a soft-deleted user.
- **Request Headers:**
  - `Authorization: Bearer <Admin JWT Token>`
- **Response:**
  - **Status:** `200 OK`
  - **Body:**
    ```json
    {
      "message": "User reactivated successfully."
    }
    ```

---

### **12. Delete User (Soft Delete)**
- **URL:** `DELETE /api/users/{id}/delete`
- **Description:** Soft delete a user by setting their `deleted` flag to `true`. Only the user themselves or an admin can perform this action.
- **Request Headers:**
  - `Authorization: Bearer <JWT Token>`
- **Response:**
  - **Status:** `200 OK`
  - **Body:**
    ```json
    "User deleted successfully."
    ```

---