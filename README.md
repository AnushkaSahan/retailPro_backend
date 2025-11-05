# RetailPro POS Backend

> Enterprise-grade Point of Sale system backend built with Spring Boot 3.x and PostgreSQL

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?style=flat-square&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17-007396?style=flat-square&logo=openjdk&logoColor=white)](https://openjdk.org/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-14+-4169E1?style=flat-square&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Maven](https://img.shields.io/badge/Maven-3.8+-C71A36?style=flat-square&logo=apachemaven&logoColor=white)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg?style=flat-square)](LICENSE)

## Overview

RetailPro POS Backend is a production-ready REST API server designed for modern retail operations. Built on Spring Boot 3.x with Java 17, it delivers robust performance and scalability for managing products, inventory, sales transactions, and customer relationships.

### Key Capabilities

- **Product Management** — Full lifecycle management of product catalog with real-time inventory tracking
- **Sales Processing** — Fast transaction processing with comprehensive billing and receipt generation
- **Customer Management** — Complete CRM functionality for customer data and purchase history
- **Reporting & Analytics** — Daily, weekly, and monthly sales reports with trend analysis
- **Secure Authentication** — Industry-standard security with Spring Security Basic Authentication

## Technology Stack

```
Backend Framework    │ Spring Boot 3.x
Language            │ Java 17
Database            │ PostgreSQL 14+
ORM                 │ Spring Data JPA / Hibernate
Security            │ Spring Security
Build Tool          │ Apache Maven 3.8+
Architecture        │ RESTful API, Layered Architecture
```

## Project Architecture

```
retailpro-pos-backend/
│
├── src/main/java/com/retailpro/
│   ├── controller/              # REST API endpoints and request handling
│   ├── service/                 # Business logic and transaction management
│   ├── repository/              # Data access layer (JPA repositories)
│   ├── model/                   # Domain entities and database models
│   ├── security/                # Authentication and authorization configuration
│   ├── dto/                     # Data Transfer Objects
│   ├── exception/               # Custom exception handlers
│   └── RetailProApplication.java
│
├── src/main/resources/
│   ├── application.yml          # Application configuration
│   ├── application-dev.yml      # Development environment config
│   ├── application-prod.yml     # Production environment config
│   └── data.sql                 # Database initialization scripts
│
├── src/test/
│   ├── java/                    # Unit and integration tests
│   └── resources/               # Test configuration files
│
├── pom.xml                      # Maven project dependencies
├── .gitignore
└── README.md
```

## Prerequisites

Ensure the following software is installed on your system:

| Requirement | Version | Download |
|------------|---------|----------|
| **JDK** | 17 or higher | [OpenJDK](https://openjdk.org/) or [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) |
| **PostgreSQL** | 14 or higher | [PostgreSQL Downloads](https://www.postgresql.org/download/) |
| **Maven** | 3.8 or higher | [Apache Maven](https://maven.apache.org/download.cgi) |

## Installation

### 1. Database Setup

Create a PostgreSQL database for the application:

```sql
-- Connect to PostgreSQL as superuser
psql -U postgres

-- Create database
CREATE DATABASE retailpro_pos;

-- Create application user (optional but recommended)
CREATE USER retailpro_user WITH ENCRYPTED PASSWORD 'your_secure_password';
GRANT ALL PRIVILEGES ON DATABASE retailpro_pos TO retailpro_user;
```

### 2. Application Configuration

Configure database connection in `src/main/resources/application.yml`:

```yaml
spring:
  application:
    name: retailpro-pos-backend
  
  datasource:
    url: jdbc:postgresql://localhost:5432/retailpro_pos
    username: postgres
    password: your_database_password
    driver-class-name: org.postgresql.Driver
  
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: false
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
        format_sql: true
    
  security:
    user:
      name: admin
      password: admin123

server:
  port: 8080
  servlet:
    context-path: /api
```

### 3. Build & Run

```bash
# Clone the repository
git clone <repository-url>
cd retailpro-pos-backend

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run

# Or run the generated JAR
java -jar target/retailpro-pos-backend-1.0.0.jar
```

The API server will start at `http://localhost:8080/api`

## Authentication

The application uses HTTP Basic Authentication. Include credentials in all API requests.

**Default Credentials:**
```
Username: admin
Password: admin123
```

**Example Authentication Header:**
```
Authorization: Basic YWRtaW46YWRtaW4xMjM=
```

> **Security Notice:** Change default credentials before deploying to production. Configure user credentials in `application.yml` or use a database-backed user store.

## API Documentation

### Products API

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| `GET` | `/products` | Retrieve all products | — |
| `GET` | `/products/{id}` | Get product by ID | — |
| `POST` | `/products` | Create new product | Product JSON |
| `PUT` | `/products/{id}` | Update existing product | Product JSON |
| `DELETE` | `/products/{id}` | Delete product | — |

**Sample Product Object:**
```json
{
  "id": 1,
  "name": "Wireless Mouse",
  "description": "Ergonomic wireless mouse with USB receiver",
  "price": 29.99,
  "quantity": 150,
  "category": "Electronics",
  "sku": "WM-001"
}
```

### Sales API

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| `GET` | `/sales` | List all sales transactions | — |
| `GET` | `/sales/{id}` | Get sale details | — |
| `POST` | `/sales` | Process new sale | Sale JSON |
| `GET` | `/sales/reports/daily` | Daily sales report | — |
| `GET` | `/sales/reports/monthly` | Monthly sales report | — |

**Sample Sale Object:**
```json
{
  "customerId": 1,
  "items": [
    {
      "productId": 1,
      "quantity": 2,
      "unitPrice": 29.99
    }
  ],
  "totalAmount": 59.98,
  "paymentMethod": "CARD"
}
```

### Customers API

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| `GET` | `/customers` | List all customers | — |
| `GET` | `/customers/{id}` | Get customer details | — |
| `POST` | `/customers` | Register new customer | Customer JSON |
| `PUT` | `/customers/{id}` | Update customer information | Customer JSON |
| `DELETE` | `/customers/{id}` | Delete customer | — |

**Sample Customer Object:**
```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "phone": "+1234567890",
  "address": "123 Main Street, City, State 12345"
}
```

### Inventory API

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/inventory` | Get current inventory status |
| `GET` | `/inventory/low-stock` | Products below minimum threshold |
| `PUT` | `/inventory/{productId}/adjust` | Adjust stock levels |

## Testing

### Using cURL

**Retrieve all products:**
```bash
curl -X GET http://localhost:8080/api/products \
  -u admin:admin123 \
  -H "Accept: application/json"
```

**Create a new product:**
```bash
curl -X POST http://localhost:8080/api/products \
  -u admin:admin123 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "USB Cable",
    "price": 9.99,
    "quantity": 200,
    "category": "Accessories"
  }'
```

**Process a sale:**
```bash
curl -X POST http://localhost:8080/api/sales \
  -u admin:admin123 \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": 1,
    "items": [{"productId": 1, "quantity": 2}]
  }'
```

### Using Postman

1. Import the API collection if available: `RetailPro.postman_collection.json`
2. Configure environment variables:
   - `base_url`: `http://localhost:8080/api`
   - `username`: `admin`
   - `password`: `admin123`
3. Set Authorization type to **Basic Auth** at collection level
4. Execute requests from the collection

## Development

### Running Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=ProductServiceTest

# Run tests with coverage
mvn clean test jacoco:report
```

### Code Quality

```bash
# Check code style
mvn checkstyle:check

# Run static analysis
mvn pmd:check

# Generate project reports
mvn site
```

### Environment Profiles

Activate different profiles using the `-Dspring.profiles.active` parameter:

```bash
# Development
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Production
java -jar -Dspring.profiles.active=prod target/retailpro-pos-backend.jar
```

## Deployment

### Docker Deployment (Optional)

```dockerfile
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/retailpro-pos-backend.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

Build and run:
```bash
docker build -t retailpro-pos-backend .
docker run -p 8080:8080 -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/retailpro_pos retailpro-pos-backend
```

## Troubleshooting

**Database Connection Issues:**
- Verify PostgreSQL is running: `sudo systemctl status postgresql`
- Check database credentials in `application.yml`
- Ensure database exists and user has proper permissions

**Port Already in Use:**
- Change server port in `application.yml`: `server.port: 8081`
- Or kill process using port 8080: `lsof -ti:8080 | xargs kill -9`

**Build Failures:**
- Clear Maven cache: `mvn clean`
- Update dependencies: `mvn clean install -U`
- Verify Java version: `java -version`

## Contributing

We welcome contributions! Please follow these guidelines:

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/new-feature`
3. Commit your changes: `git commit -am 'Add new feature'`
4. Push to the branch: `git push origin feature/new-feature`
5. Submit a pull request

Please ensure your code follows the project's coding standards and includes appropriate tests.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Support

For issues, questions, or contributions:
- **Issues:** Open an issue on GitHub
- **Discussions:** Use GitHub Discussions for questions
- **Email:** anushkasahan209@gmail.com

---

**Built with Spring Boot** | **Maintained by Anushka Sahan** | **© 2025 RetailPro**
