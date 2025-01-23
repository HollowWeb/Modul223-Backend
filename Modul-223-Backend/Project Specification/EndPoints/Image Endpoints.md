## **Image API Documentation**

### **1. Upload Image**
- **URL:** `POST /api/images/{articleId}`
- **Description:** Upload an image for an article.
- **Request Headers:**
    - `Authorization: Bearer <JWT Token>`
- **Request Form Data:**
    - `file`: The image file to upload.
- **Response:**
  ```json
  {
    "id": "number",
    "articleId": "number",
    "filename": "string",
    "mimeType": "string",
    "size": "number",
    "uploadedAt": "string",
    "url": "string"
  }
  ```

---

### **2. Get Images by Article**
- **URL:** `GET /api/images/article/{articleId}`
- **Description:** Fetch all images for an article.
- **Response:**
  ```json
  [
    {
      "id": "number",
      "filename": "string",
      "mimeType": "string",
      "size": "number",
      "uploadedAt": "string",
      "url": "string"
    }
  ]
  ```

---

### **3. Get Image Metadata**
- **URL:** `GET /api/images/{id}`
- **Description:** Fetch metadata for a specific image.
- **Response:**
  ```json
  {
    "id": "number",
    "filename": "string",
    "mimeType": "string",
    "size": "number",
    "uploadedAt": "string",
    "url": "string"
  }
  ```

---

### **4. Get Image Content**
- **URL:** `GET /api/images/{id}/content`
- **Description:** Fetch the binary data of an image.
- **Response Headers:**
    - `Content-Type`: MIME type of the image.
    - `Content-Disposition`: Inline/attachment with filename.
- **Response:** Binary data.

---

### **5. Delete Image**
- **URL:** `DELETE /api/images/{id}`
- **Description:** Soft-delete an image.
- **Request Headers:**
    - `Authorization: Bearer <JWT Token>`

---