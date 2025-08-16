# 📚 **PahanaEdu Billing System**

## 📌 Overview

The **PahanaEdu Billing System** is a Java-based web application designed for **Book Shop** to manage:
✅ Customer records
✅ Item inventory
✅ Billing & invoicing
✅ Sales reporting

The system supports **role-based access control** (Admin & Cashier) to ensure efficient operations, real-time inventory updates, and secure session handling.

---

## 🛠 **Technologies Used**

**Frontend:**

* 🌐 HTML
* 🎨 CSS
* ⚡ JavaScript

**Backend:**

* ☕ Java (Servlets, JSP)

**Database:**

* 🗄 MySQL

**Server:**

* 🚀 Apache Tomcat

**Libraries & Frameworks:**

* 🔌 JDBC
* 🐬 MySQL Connector (`mysql-connector-j:9.3.0`)
* 📦 Gson (JSON handling)
* 🧪 JUnit (Testing)
* 📜 Java Servlet API

---

## 📂 **Project Structure**

```
PahanaEdu-Billing/
│
├── src/main/java/com/pahanaedu/billing/
│   ├── servlet/   # All servlet classes (AddItemServlet, LogoutServlet, GetBillsServlet, etc.)
│   ├── util/      # Utility classes (DBConnection.java)
│   ├── model/     # JavaBeans (Customer, Item, Bill, etc.)
│   └── dao/       # Data Access Objects (CustomerDAO, ItemDAO, BillDAO, etc.)
│
├── WebContent/
│   ├── index.jsp      # Login page
│   ├── register.jsp   # Registration page
│   ├── admin.jsp      # Admin dashboard
│   ├── cashier.jsp    # Cashier dashboard
│   └── customers.jsp  # Customer management page
│
├── pom.xml       # Maven dependencies
└── README.md     # Documentation
```

---

## ⚙ **Setup Instructions**

### 1️⃣ Clone the Repository

```bash
git clone https://github.com/yourusername/pahanaedu-billing.git
```

### 2️⃣ Import into IDE

* Open **Eclipse** or **IntelliJ IDEA**
* Import as a **Maven Web Project**

### 3️⃣ Database Setup

```sql
CREATE DATABASE pahanaedu_billing;
```

* Import the provided SQL dump: `pahanedu_billing.sql`

### 4️⃣ Configure Database Connection

Edit `DBConnection.java`:

```java
private static final String URL = "jdbc:mysql://localhost:3306/pahanaedu_billing";
private static final String USER = "root";
private static final String PASSWORD = "your_password";
```

### 5️⃣ Deploy on Tomcat

* Add the project to **Apache Tomcat Server**
* Run the application

### 6️⃣ Access the Application

```
http://localhost:8080/PahanaEdu-Billing
```

---

## 🔑 **Key Features**

* 👤 **Role-based Access:** Admin & Cashier
* 📝 **Customer Management:** Add, edit, delete, and view customers
* 📦 **Item Management:** Admin can manage available items dynamically
* 💰 **Billing System:**

  * Select items & auto-calculate totals
  * Apply discounts
* 🖨 **Bill Printing:**

  * PAHANA EDU BOOK SHOP header
  * Address & contact info
  * Customer name & ID
  * Itemized list with prices & totals
* 💾 **Real-time Bill Storage:** Bills saved in MySQL database
* 📊 **Bill Viewing:** Both Admin & Cashier can view saved bills
* 🔐 **Secure Session Management:** Login/logout with confirmation prompts



