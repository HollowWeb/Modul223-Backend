# **Test Plan**

---

## **A. Description:**
This test plan outlines the strategy for testing the wYZen application, ensuring functionality, performance, security, and usability. It defines test scenarios and actions to verify the application's features align with requirements.

---

## **B. Version Information:**
- **Version:** 1.0
- **Prepared By:** Zen Zalapski, Yoel Arcos
- **Last Updated:** 25.12.2024

---

## **C. Test Scenarios:**

### **1. User Account Management:**
**Test Case 1:** Create a new account.
- **Steps:**
    1. Open the registration page.
    2. Fill in username, email, and password.
    3. Submit the form.
- **Expected Result:**
    - Account is created successfully.
    - User receives a confirmation email.

**Test Case 2:** Login to the system.
- **Steps:**
    1. Open the login page.
    2. Enter valid credentials.
    3. Submit the form.
- **Expected Result:**
    - User is logged in successfully and redirected to the dashboard.

### **2. Wiki Page Management:**
**Test Case 3:** Create a new wiki page.
- **Steps:**
    1. Click on 'Create New Page'.
    2. Add a title and content.
    3. Save the page.
- **Expected Result:**
    - Page is created successfully and displayed.

**Test Case 4:** Edit an existing wiki page.
- **Steps:**
    1. Open an existing page.
    2. Click on 'Edit'.
    3. Modify content and save.
- **Expected Result:**
    - Changes are saved and updated.

**Test Case 5:** View and restore previous versions.
- **Steps:**
    1. Open the page version history.
    2. Select a previous version.
    3. Restore it.
- **Expected Result:**
    - Selected version is restored.

### **3. Comments and Discussions:**
**Test Case 6:** Add a comment.
- **Steps:**
    1. Open an article.
    2. Add a comment in the discussion section.
    3. Submit the comment.
- **Expected Result:**
    - Comment is posted successfully.

**Test Case 7:** Reply to a comment.
- **Steps:**
    1. Select an existing comment.
    2. Add a reply.
    3. Submit the reply.
- **Expected Result:**
    - Reply is nested under the original comment.

### **4. Image Management:**
**Test Case 8:** Upload an image.
- **Steps:**
    1. Open the article editor.
    2. Click on 'Upload Image'.
    3. Select and upload an image.
- **Expected Result:**
    - Image is uploaded and displayed.

**Test Case 9:** View uploaded images.
- **Steps:**
    1. Open an article with images.
    2. Confirm the images are displayed.
- **Expected Result:**
    - Images are visible.

### **5. Permissions and Roles:**
**Test Case 10:** Assign permissions.
- **Steps:**
    1. Open admin settings.
    2. Assign read/write permissions to a user for a specific page.
- **Expected Result:**
    - Permissions are updated.

**Test Case 11:** Change user roles.
- **Steps:**
    1. Open user management.
    2. Change the role of a user (e.g., Viewer to Editor).
- **Expected Result:**
    - User's role is updated and reflected in the permissions.

### **6. Search and Filters:**
**Test Case 12:** Search for articles.
- **Steps:**
    1. Enter keywords in the search bar.
    2. Apply filters (e.g., tags or categories).
    3. View results.
- **Expected Result:**
    - Matching articles are displayed.

### **7. Export Features:**
**Test Case 13:** Export an article as PDF.
- **Steps:**
    1. Open an article.
    2. Click 'Export as PDF'.
- **Expected Result:**
    - PDF file is downloaded.

**Test Case 14:** Export an article as HTML.
- **Steps:**
    1. Open an article.
    2. Click 'Export as HTML'.
- **Expected Result:**
    - HTML file is downloaded.

### **8. Performance Testing:**
**Test Case 15:** Load testing for 100+ concurrent users.
- **Expected Result:**
    - System maintains performance without errors.


