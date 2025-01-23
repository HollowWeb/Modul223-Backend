## **Article API Documentation**

### **1. Create Article**
- **URL:** `POST /api/articles`
- **Description:** Create a new article.
- **Request Headers:**
    - `Content-Type: application/json`
    - `Authorization: Bearer <JWT Token>`
- **Request Body:**
  ```json
  {
    "title": "string",
    "content": "string", 
    "tags": ["tag1", "tag2"]
  }
  ```
- **Response:**
  ```json
  {
    "id": "number",
    "title": "string",
    "content": "string",
    "status": "string",
    "tags": ["string"],
    "createdAt": "string",
    "updatedAt": "string",
    "createdBy": "string"
  }
  ```

---

### **2. Get Article by ID**
- **URL:** `GET /api/articles/{id}`
- **Description:** Fetch an article by its ID.
- **Request Headers:**
    - `Authorization: Bearer <JWT Token>`
- **Response:**
  ```json
  {
    "id": "number",
    "title": "string",
    "content": "string",
    "status": "string",
    "tags": ["string"],
    "createdAt": "string",
    "updatedAt": "string",
    "createdBy": "string"
  }
  ```

---

### **3. Update Article**
- **URL:** `PUT /api/articles/{id}`
- **Description:** Update an existing article.
- **Request Headers:**
    - `Content-Type: application/json`
    - `Authorization: Bearer <JWT Token>`
- **Request Body:**
  ```json
  {
    "title": "string",
    "content": "string",
    "status": "string",
    "tags": ["string"]
  }
  ```
- **Response:**
  ```json
  {
    "id": "number",
    "title": "string",
    "content": "string",
    "status": "string",
    "tags": ["string"],
    "createdAt": "string",
    "updatedAt": "string",
    "createdBy": "string"
  }
  ```

---

### **4. Delete Article**
- **URL:** `DELETE /api/articles/{id}`
- **Description:** Soft-delete an article.
- **Request Headers:**
    - `Authorization: Bearer <JWT Token>`
- **Response:**
  ```json
  "Article deleted successfully."
  ```

---

### **5. List Articles**
- **URL:** `GET /api/articles`
- **Description:** List articles with optional filters.
- **Request Headers:**
    - `Authorization: Bearer <JWT Token>`
- **Query Parameters:**
    - `tag` (optional): Filter by tag.
    - `status` (optional): Filter by status (e.g., draft, published, archived).
    - `search` (optional): Search by title or content.
- **Response:**
  ```json
  [
    {
      "id": "number",
      "title": "string",
      "content": "string",
      "status": "string",
      "tags": ["string"],
      "createdAt": "string",
      "updatedAt": "string",
      "createdBy": "string"
    }
  ]
  ```

---

### **6. Request Approval for Article**
- **URL:** `PUT /api/articles/{id}/request-approval`
- **Description:** Change the article status to `PENDING_APPROVAL`.
- **Request Headers:**
    - `Authorization: Bearer <JWT Token>`
- **Response:**
  ```json
  {
    "id": "number",
    "status": "PENDING_APPROVAL",
    ...
  }
  ```

---

### **7. Approve Article**
- **URL:** `PUT /api/articles/{id}/approve`
- **Description:** Approve an article (admin/editor only).
- **Request Headers:**
    - `Authorization: Bearer <Admin/Editor JWT Token>`
- **Response:**
  ```json
  {
    "id": "number",
    "status": "PUBLISHED"
  }
  ```

---