#  Task Management System

A RESTful API for managing **tasks**, **users**, **comments**, and **tags** built with Spring Boot and JPA.

---

##  How to Run the Application

###  Prerequisites
- Java 17+
- Maven
- H2 Database
- IntelliJ IDEA (recommended) or Spring Tool Suite
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

##  Entity Relationships

###  **User and Task (One-to-Many)**
A **User** can create many **Tasks**, but each **Task** belongs to only one **User**.   

- In the database, the **Task** table stores a `user_id` foreign key that points back to the **User** who created it.  
- When you fetch a **User**, you can access all their **Tasks** through this relationship.  
- When you fetch a **Task**, you can see which **User** created it.

---

###  **Task and Tag (Many-to-Many)**
A **Task** can have multiple **Tags**, and a **Tag** can be applied to multiple **Tasks**.  
Think of it like articles and categories on a blog — one article can belong to multiple categories, and one category contains multiple articles.  

- This relationship uses a **join (bridge) table** in the database that stores pairs of task IDs and tag IDs.  
- The `TaskService` includes methods like `assignTagToTask` and `removeTagFromTask` to manage this many-to-many connection.  
- When deleting a task, **orphaned tags** (tags with no tasks) are automatically removed.

---

###  **Task and Comment (One-to-Many)**
A **Task** can have many **Comments**, but each **Comment** belongs to only one **Task**.  

- The **Comment** table stores a `task_id` foreign key linking each comment to its parent task.  
- You can retrieve all comments for a specific task using `findByTaskId`.

---

###  **User and Comment (One-to-Many)**
A **User** can write many **Comments**, but each **Comment** is written by only one **User**.  

- The **Comment** table stores a `user_id` foreign key to track who wrote each comment.  
- When you fetch comments, you can see both the **Task** and the **User** who wrote the comment.

##  API Endpoints Documentation

###  Create User
**POST** `/api/users`

**Request:**
```json
{
  "name": "John Doe",
  "email": "john.doe@example.com"
}
```
**Response:**
```
{
    "id": 1,
    "name": "John Doe",
    "email": "john.doe@example.com",
    "tasks": []
}
```

### Create Tag

**POST** `/api/tags`

**Request:**
```
{
  "name": "Urgent"
}
```


**Response:**
```
{
  "id": 1,
  "name": "Urgent"
}
```
### Create Task

**POST** ```/api/tasks```

**Request:**
```
{
  "title": "Prepare presentation",
  "description": "Create slides for quarterly review",
  "userId": 1,
  "status": "TODO"
}
```


***Response:***
```
{
    "id": 1,
    "title": "Complete assignment",
    "description": "Finish the backend intern assignment",
    "status": "TODO",
    "comments": [],
    "tags": [],
    "createdAt": "2025-11-04T14:12:24.273658",
    "updatedAt": "2025-11-04T14:12:24.273658"
}
```
### Assign Tag to Task

**POST** ```/api/tasks/{taskId}/tags/{tagId}```
**Example:** ```/api/tasks/1/tags/1```

**Response:**
```
{
    "id": 1,
    "title": "Complete assignment",
    "tags": [
        {
            "id": 1,
            "name": "bug"
        }
    ]
}
```

### Add Comment to Task

**POST** ```/api/tasks/{taskId}/comments?userId={userId}```
**Example:** ```/api/tasks/1/comments?userId=2```

**Request:**
```
{
  "text": "I can help with the design slides"
}
```

***Response:***
```
{
    "id": 1,
    "text": "Completed the task",
    "user": {
        "id": 1,
        "name": "John Doe"
    },
    "createdAt": "2025-11-04T14:35:33.1509824"
}
```

### Get All Tasks (with Pagination)

**GET** ```/api/tasks?page=0&size=10```

**Response:**
```
{
    "content": [
        {
            "id": 1,
            "title": "Complete assignment",
            "description": "Finish the backend intern assignment",
            "status": "TODO",
            "user": {
                "id": 1,
                "name": "John Doe",
                "email": "john.doe@example.com"
            },
            "tags": [
                {
                    "id": 1,
                    "name": "bug"
                }
            ],
            "createdAt": "2025-11-04T14:35:23.237203",
            "updatedAt": "2025-11-04T14:35:23.237203"
        }
    ],
    "pageable": {
        "pageNumber": 0,
        "pageSize": 20,
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "offset": 0,
        "paged": true,
        "unpaged": false
    },
    "last": true,
    "totalElements": 1,
    "totalPages": 1,
    "numberOfElements": 1,
    "first": true,
    "size": 20,
    "number": 0,
    "sort": {
        "empty": true,
        "sorted": false,
        "unsorted": true
    },
    "empty": false
}
```






