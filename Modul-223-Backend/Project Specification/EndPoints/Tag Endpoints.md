## **Tag API Documentation**

### **1. Create Tag**
- **URL:** `POST /api/tags`
- **Request Body:**
  ```json
  {
    "tagName": "string"
  }
  ```
- **Response:**
  ```json
  {
    "id": "number",
    "tagName": "string"
  }
  ```

---

### **2. Get All Tags**
- **URL:** `GET /api/tags`
- **Response:**
  ```json
  [
    {
      "id": "number",
      "tagName": "string"
    }
  ]
  ```

---

### **3. Update Tag**
- **URL:** `PUT /api/tags/{id}`
- **Request Body:** Same as create tag.
- **Response:** Updated tag.

---

### **4. Delete Tag**
- **URL:** `DELETE /api/tags/{id}`
- **Response:** `204 No Content`

---