# **wYZen**

---

## **Project Description:**

wYZen is a Collaborative Knowledge Management System (Wiki) designed to streamline the process of organizing, sharing, and managing information within teams and organizations. It provides a central repository for documentation, brainstorming, and collaborative knowledge sharing, allowing multiple users to create, edit, and discuss articles in real time.

With robust features like user authentication, version control, full-text search, and responsive design, the system ensures secure and accessible information management. Built using React with Vite for the frontend and Spring Boot with Java 21 for the backend, it leverages a MySQL database for scalable data storage. Security is enforced through JWT-based authentication, and deployment is simplified using Docker containers.

The system is ideal for team documentation, learning resources, brainstorming platforms, and building knowledge bases, with potential extensions including real-time collaboration, advanced search, analytics dashboards, and mobile app integration.

---

## **Technology Stack:**

- **Frontend:** React with Vite, React Router for navigation, Material-UI for modern UI components, and Axios for API communication.
- **Backend:** Java 21 with Spring Boot for RESTful APIs.
- **Database:** MySQL for data storage, ensuring scalability and reliability.
- **Security:** JWT (JSON Web Tokens) for authentication.
- **Deployment:** Docker containers for simplified and scalable deployment.

---

## **Implementation Details:**

### **User Authentication and Authorization:**

- Implement secure user registration and login using Spring Security and JWT for authentication.
- Define role-based access control (Admin, Editor, Viewer) in the backend.

### **Article Management:**

- Develop CRUD operations for articles with Markdown support for formatting.
- Associate tags and categories with articles for better organization.

### **Version Control:**

- Track and store article versions to allow users to view and restore previous versions.

### **Search and Navigation:**

- Implement full-text search queries using MySQL indexes.
- Provide filters based on categories, tags, and authors.

### **Comment and Discussion System:**

- Enable comments on articles with parent-child relationships to support threaded discussions.

### **Image Handling:**

- Store images as **BLOBs** in the MySQL database.
- Create a table to associate images with articles by storing metadata (e.g., filename, upload timestamp).
- Implement API endpoints for uploading, retrieving, and displaying images.

### **Export Options (Optional):**

- Generate PDF and HTML exports of articles using libraries such as iText or jsPDF.

### **Responsive Design:**

- Use Material-UI for responsive and modern UI design across devices.

---

## **System Architecture:**

1. **Frontend (React with Vite):**
   - User interface and routing.
   - API integration with the backend.
2. **Backend (Spring Boot):**
   - Handles business logic and database communication.
   - Provides RESTful APIs.
3. **Database (MySQL):**
   - Stores users, articles, versions, comments, and images (as BLOBs).
4. **Deployment:**
   - Docker containers for easy deployment.

---

## **Version Control Workflow:**

- **Git Repositories:** Separate repositories for frontend and backend hosted on GitHub.
- **Branching Strategy:**
   - Main branch is protected (no direct pushes, only merges via pull requests).
   - Development happens on the `develop` branch.
   - Feature branches are created from `develop` and merged back after code review.
- **Pull Requests:** Required for merging changes into the `main` branch.
- **CI/CD Integration:** Use GitHub Actions for automated builds, testing, and deployment.

---

## **Example Use Cases:**

1. **Team Documentation:** Teams can create and organize technical or project documentation collaboratively.
2. **Learning Resources:** Manage tutorials, guides, and FAQs for internal training.
3. **Brainstorming Platform:** Collect and discuss ideas with versioned updates.
4. **Knowledge Base:** Build a searchable repository of organizational knowledge.

---

## **Future Extensions:**

- **Real-time Collaboration:** Use WebSockets for simultaneous editing.
- **Advanced Search Capabilities:** Implement Elasticsearch for better indexing.
- **Analytics Dashboard:** Track article views, edits, and contributions.
- **Mobile App Integration:** Provide native apps for iOS and Android.



