#  Task Management System

A RESTful API for managing **tasks**, **users**, **comments**, and **tags** built with Spring Boot and JPA.

---

##  How to Run the Application

###  Prerequisites
- Java 17+
- Maven
- H2 Database
- IntelliJ IDEA (recommended)
- Postman (for API testing)

###  Steps to Run
1. Clone the repository:
   ```bash
   git clone https://github.com/PremKumar1974/TaskManagementSystem.git

##  Steps to Run the Application

### 2. Open the Project

- **In IntelliJ IDEA** → `File → Open` → select the project folder.  
- **In Eclipse** → `File → Import → Existing Maven Project` → choose the folder.

---

### 3. Build the Project

Maven will automatically download all required dependencies.

---

### 4. Run the Spring Boot Application

- **In IntelliJ IDEA** → right-click your main class (the one with `@SpringBootApplication`) → **Run**.  
- **In Eclipse** → right-click the main class → **Run As → Spring Boot App**.

---

### 5. Once Started, the Application Runs On:

👉 [http://localhost:8080](http://localhost:8080)

---

### 6. Access the H2 Console (for viewing data)

👉 [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

| Property | Value |
|:----------|:------|
| **JDBC URL** | `jdbc:h2:mem:testdb` |
| **Username** | `sa` |
| **Password** | *(leave blank)* |


##  Postman Collection

A Postman collection is included for easy testing of all API endpoints.

 **File:** `postman_collection.json`  
 You can import it into Postman by clicking:  
**File → Import → Upload Files → select postman_collection.json**

