## **Comment API Documentation**

### **1. Add Comment**
- **URL:** `POST /api/comments`
- **Description:** Add a comment to an article.
- **Request Body:**
  ```json
  {
    "articleId": "number",
    "userId": "number",
    "content": "string",
    "parentCommentId": "number" 
  }
  ```
- **Response:**
  ```json
  {
    "id": "number",
    "articleId": "number",
    "userId": "number",
    "username": "string",
    "content": "string",
    "parentCommentId": "number",
    "isReply": true,
    "createdAt": "string"
  }
  ```

---

### **2. Get Comments by Article**
- **URL:** `GET /api/comments/article/{articleId}`
- **Response:**
  ```json
  [
    {
      "id": "number",
      "username": "string",
      "content": "string",
      "createdAt": "string"
    }
  ]
  ```

---

### **3. Get Replies**
- **URL:** `GET /api/comments/replies/{parentCommentId}`
- **Response:** Similar to comments by article.

---

### **4. Delete Comment**
- **URL:** `DELETE /api/comments/{id}`
- **Request Headers:**
    - `Authorization: Bearer <JWT Token>`
- **Response:** `204 No Content`

---