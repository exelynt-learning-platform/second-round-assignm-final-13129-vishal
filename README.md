# 🛒 E-Commerce Spring Boot Application

## 📌 Project Overview

This is a full-featured **E-Commerce Backend Application** built using Spring Boot. It provides REST APIs for user authentication, product management, shopping cart operations, order processing, and payment handling.

The application follows a **layered architecture**:

```
Controller → Service → Repository → Database
```

---

## 🚀 Key Features

### 🔐 Authentication & Security

* JWT-based authentication
* Secure login & registration
* Role-based access (extendable)
* Custom security filter

### 🛍️ Product Management

* Add / update / delete products
* Fetch all products
* Product details API

### 🛒 Shopping Cart

* Add items to cart
* Update quantity
* Remove items
* View cart items

### 📦 Order Management

* Place order from cart
* Track orders
* Store order history

### 💳 Payment Module

* Payment processing logic
* Integrated with order flow

### ⚠️ Exception Handling

* Centralized exception handling using `GlobalExceptionHandler`

---

## 🏗️ Architecture

### 📂 Project Structure

```
src/main/java/com/ecommerce/project/
 ├── controller       → Handles HTTP requests
 ├── service          → Business logic layer
 ├── repository       → Database interaction (JPA)
 ├── entity           → Database models
 ├── dto              → Request/Response objects
 ├── security         → JWT & filters
 ├── config           → Security configuration
 └── exception        → Global exception handling
```

---

## 🔄 Application Flow

### 🧑 User Flow

```
User → AuthController → AuthService → UserRepository → DB
```

### 🛒 Cart Flow

```
User → CartController → CartService → CartRepository → DB
```

### 📦 Order Flow

```
Cart → OrderController → OrderService → OrderRepository → DB
```

### 💳 Payment Flow

```
Order → PaymentController → PaymentService → Process Payment
```

---

## 🔐 Security Flow (JWT)

1. User logs in via `/auth/login`
2. Server generates JWT token
3. Token is sent in headers for protected APIs
4. `JwtFilter` validates token on each request

---

## 🗄️ Database Entities

* **User** → Stores user details
* **Product** → Product catalog
* **CartItem** → Items added to cart
* **Order** → Order details
* **OrderItem** → Items inside order

---

## 📬 API Endpoints (Sample)

### 🔐 Auth APIs

| Method | Endpoint       | Description   |
| ------ | -------------- | ------------- |
| POST   | /auth/register | Register user |
| POST   | /auth/login    | Login user    |

### 🛍️ Product APIs

| GET    | /products      | Get all products |
| POST   | /products      | Add product      |

### 🛒 Cart APIs

| POST   | /cart/add      | Add to cart      |
| GET    | /cart          | View cart        |

### 📦 Order APIs

| POST   | /orders        | Place order      |
| GET    | /orders        | Get orders       |

### 💳 Payment APIs

| POST   | /payment       | Process payment  |

---

## ⚙️ Setup Instructions

### 🔧 Prerequisites

* Java 17+
* Maven
* MySQL (or configured DB)

---

### 📥 Clone Repository

```bash
git clone <your-repo-url>
cd E_Commerce_Project
```

---

### 🛠️ Configure Database

Update `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

---

### ▶️ Run Application

```bash
mvn spring-boot:run
```

---

## 🧪 Testing

* Use Postman to test APIs
* Add JWT token in Authorization header:

```
Authorization: Bearer <token>
```

---

## 📊 Error Handling

* Centralized via `GlobalExceptionHandler`
* Handles:

  * Invalid requests
  * Authentication errors
  * Server exceptions

---

## 🚀 Future Enhancements

* 🧾 Order invoice generation
* 📦 Inventory management
* 💳 Payment gateway integration (Razorpay/Stripe)
* 📊 Admin dashboard
* 🔔 Email notifications

---

### Key Highlights:

* Used **Spring Security + JWT**
* Followed **clean architecture (Controller-Service-Repo)**
* Implemented **real-world flow: cart → order → payment**
* Used **exception handling & DTOs for clean APIs**

---

## 👨‍💻 Author

Vishal Bari

---

## 📄 License

This project is licensed under MIT License
