Here’s a clear and concise documentation structure for your API endpoints that you can share with the frontend developers. It includes the endpoint, HTTP method, request structure, response, and any special notes.

---

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

### **3. Get User By ID**
- **URL:** `GET /api/users/{id}`
- **Description:** Fetch a user's details by their ID.
- **Request Headers:**
    - `Authorization: Bearer <JWT Token>` (Use the token received from login or registration)
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
- **Error Responses:**
    - **`404 Not Found`** if the user is not found.
    - **`401 Unauthorized`** if the request is not authenticated.

---

### **4. Get All Users**
- **URL:** `GET /api/users`
- **Description:** Fetch all users (excluding soft-deleted ones).
- **Request Headers:**
    - `Authorization: Bearer <JWT Token>` (Admin privileges may be required)
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

### **5. Update User**
- **URL:** `PUT /api/users/{id}`
- **Description:** Update a user's details. Only the user themselves or an admin can perform this action.
- **Request Headers:**
    - `Authorization: Bearer <JWT Token>`
    - `Content-Type: application/json`
- **Roles:**
  - Optional, requires admin privilege to modify roles
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
- **Error Responses:**
    - **`401 Unauthorized`** if the user lacks permission.
    - **`404 Not Found`** if the user is not found.

---

### **6. Delete User (Soft Delete)**
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
- **Error Responses:**
    - **`401 Unauthorized`** if the user lacks permission.
    - **`404 Not Found`** if the user is not found.

---

## **Special Notes**
- All protected endpoints require a valid JWT token in the `Authorization` header:
  ```
  Authorization: Bearer <JWT Token>
  ```
- The token can be obtained from the `register` or `login` endpoints.
- Make sure to handle `401 Unauthorized` errors gracefully on the frontend and redirect the user to the login page if their token is invalid or expired.

---

### **General Error Format**
In case of errors, the API responds with:
```json
{
  "timestamp": "string",
  "status": "number",
  "error": "string",
  "message": "string",
  "path": "string"
}
```
Example:
```json
{
  "timestamp": "2025-01-12T20:10:00.000+00:00",
  "status": 404,
  "error": "Not Found",
  "message": "User not found.",
  "path": "/api/users/10"
}
```

---
