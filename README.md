# Library API

A RESTful API to manage a simple library system.  
This API allows users to register borrowers and books, borrow and return books, and list all books in the library.

---

## **Project Description**

The Library API is built using Spring Boot 3 and MySQL, and it provides the following functionalities:

- Register a new borrower
- Register a new book
- List all books
- Borrow a book
- Return a borrowed book

The API enforces the following rules:

- Only one borrower can borrow a book (same book ID) at a time.
- Multiple copies of the same ISBN are allowed, each with a unique book ID.
- Two books with the same ISBN must have the same title and author.
- Borrowed books are logged with borrow and return dates.

Swagger/OpenAPI documentation is available for testing and reference.

---

## **How to Run**

### **Prerequisites**

- Docker installed
- Docker Compose (optional)
- Java 17 (if running without Docker)
- Maven (if building locally)

### **Running with Docker**

1. Build the Docker image:

```bash
docker build -t library-api:latest .
docker run -d \
  -e DB_URL=jdbc:mysql://localhost:3306/library_db \
  -e DB_USERNAME=library_user \
  -e DB_PASSWORD=library_pass \
  -e SERVER_PORT=8080 \
  -p 8080:8080 \
  library-api:latest
```
Access the API at: http://localhost:8080

# Database Choice Justification

**Database Used:** MySQL

**Reasons for Choice:**
1. **Relational Data:** Borrowers, books, and borrowed book logs have structured relationships that fit relational models.
2. **ACID Transactions:** Ensures data consistency when borrowing/returning books.
3. **Maturity & Support:** MySQL is widely used, stable, and integrates well with Spring Boot.
4. **Ease of Setup:** Simple to configure locally or in Docker.
5. **Scalability:** Supports indexing and queries efficiently for small to medium library systems.

