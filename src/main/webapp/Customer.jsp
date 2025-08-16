<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="com.pahanaedu.billing.model.Customer" %>
<%
  // Check logged-in customer in session, else redirect to login
  Customer customer = (session != null) ? (Customer) session.getAttribute("user") : null;
  String role = (session != null) ? (String) session.getAttribute("role") : null;

  if (customer == null || role == null || !"customer".equals(role)) {
    response.sendRedirect("index.jsp");  // redirect to login if not authenticated as customer
    return;
  }
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <title>Customer Dashboard - PahanaEdu Book Shop</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      background: #f8f9fa;
      margin: 0; padding: 0;
      color: #333;
    }
    header {
      background-color: #007bff;
      color: white;
      padding: 15px 20px;
      font-size: 1.6rem;
      text-align: center;
    }
    nav {
      background: #e9ecef;
      padding: 10px 20px;
      display: flex;
      justify-content: flex-end;
      gap: 15px;
    }
    nav button {
      background: #dc3545;
      border: none;
      color: white;
      padding: 8px 15px;
      border-radius: 4px;
      cursor: pointer;
      font-weight: 600;
      transition: background-color 0.3s ease;
    }
    nav button:hover {
      background: #b02a37;
    }
    main {
      max-width: 900px;
      margin: 30px auto;
      background: white;
      border-radius: 8px;
      padding: 25px 40px;
      box-shadow: 0 0 10px rgba(0,0,0,0.1);
    }
    h2 {
      color: #007bff;
      margin-bottom: 20px;
    }
    section {
      margin-bottom: 40px;
    }
    dl {
      display: grid;
      grid-template-columns: max-content 1fr;
      row-gap: 10px;
      column-gap: 20px;
      font-size: 1.1rem;
    }
    dt {
      font-weight: bold;
      text-align: right;
      color: #495057;
    }
    dd {
      margin: 0;
    }
    ul {
      list-style-type: disc;
      padding-left: 20px;
      font-size: 1.05rem;
    }
  </style>
  <script>
    function logout() {
      if(confirm("Are you sure you want to logout?")) {
        window.location.href = "LogoutServlet";  // Implement LogoutServlet to invalidate session
      }
    }
  </script>
</head>
<body>

<header>PahanaEdu Book Shop - Customer Dashboard</header>
<nav>
  <button onclick="logout()">Logout</button>
</nav>

<main>
  <!-- Account Details Section -->
  <section>
    <h2>Your Account Details</h2>
    <dt>Account Number:</dt>
    <dd><%= customer.getAccountNo() %></dd>

    <dt>Name:</dt>
    <dd><%= customer.getCustName() %></dd>

    <dt>Address:</dt>
    <dd><%= customer.getAddress() %></dd>

    <dt>Email:</dt>
    <dd><%= customer.getEmail() %></dd>

    <dt>Telephone:</dt>
    <dd><%= customer.getTelephone() %></dd>

    </dl>
  </section>

  <!-- Help Section -->
  <section>
    <h2>Help & Guidelines</h2>
    <p>Welcome to your customer dashboard. Here are some tips to help you navigate:</p>
    <ul>
      <li>You can view your account details here but cannot modify them. Contact admin for updates.</li>
      <li>To log out safely, use the Logout button above.</li>
      <li>For further assistance, please contact support@example.com or call 123-456-7890.</li>
    </ul>
  </section>
</main>

</body>
</html>
