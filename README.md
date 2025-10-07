# 🍔 Food Delivery App

A web-based application that allows users to order food online from various restaurants.  
Built using **Java**, **Spring Boot**, **MySQL**, and **Maven**.

---

## 🚀 Features

### 👤 User Features
- Place orders from available restaurants.
- Select menu items and specify quantities.
- Enter delivery address.
- Make payments via **Cash**, **Card**, or **UPI**.
- New users can sign up while placing an order.
- Existing users can log in to link orders.

### 🔑 Admin Features
- Login with credentials:  
  **Email:** `xyz@gmail.com`  
  **Password:** `1234`
- View all users.
- Add new restaurants.
- Add menu items to restaurants.
- View all orders.

---

## 🛠️ Technologies Used
- **Frontend:** HTML, CSS, JavaScript  
- **Backend:** Java, Spring Boot  
- **Database:** MySQL  
- **Build Tool:** Maven  

---

## 🧩 Database Setup

1. Create the database:

```sql
CREATE DATABASE FoodDeliveryApp;
USE FoodDeliveryApp;

CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100),
    password VARCHAR(100)
);

CREATE TABLE restaurant (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150),
    address VARCHAR(255)
);

CREATE TABLE menu_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    restaurant_id BIGINT,
    name VARCHAR(100),
    price DECIMAL(10,2),
    FOREIGN KEY (restaurant_id) REFERENCES restaurant(id)
);

CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    total DECIMAL(10,2),
    status VARCHAR(20),
    delivery_address VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES user(id)
);


## ⚙️ How to Run the Project
1. Clone this repository:
   ```bash
   git clone https://github.com/yourusername/FoodDeliveryApp.git
   cd FoodDeliveryApp
2. Configure database:
   Ensure MySQL is running.
   Update DBConnection.java with your MySQL username and password:
  - private static final String USER = "root";
 -  private static final String PASSWORD = "your_password";
3. Build the project with Maven:
   -mvn clean install
4. Run the application:
   -java -cp target/FoodDeliveryApp-1.0-SNAPSHOT.jar com.fooddeliveryapp.App

