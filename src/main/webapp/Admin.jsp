<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="com.pahanaedu.billing.model.Admin" %>
<%
  Admin admin = (session != null) ? (Admin) session.getAttribute("user") : null;
  String role = (session != null) ? (String) session.getAttribute("role") : null;

  if (admin == null || role == null || !"admin".equals(role)) {
    response.sendRedirect("index.jsp");
    return;
  }
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <title>Admin Dashboard - PahanaEdu Book Shop</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      background: #f0f2f5;
      margin: 0; padding: 0;
    }
    header {
      background-color: #007bff;
      color: white;
      padding: 15px 30px;
      font-size: 1.8rem;
      font-weight: bold;
      text-align: center;
    }
    nav {
      background: #e9ecef;
      display: flex;
      justify-content: center;
      padding: 10px;
      gap: 15px;
    }
    nav button {
      background: #007bff;
      border: none;
      color: white;
      padding: 10px 18px;
      border-radius: 4px;
      cursor: pointer;
      font-weight: 600;
      transition: background-color 0.3s ease;
    }
    nav button:hover {
      background: #0056b3;
    }
    main {
      padding: 20px 40px;
      max-width: 1200px;
      margin: 0 auto;
    }
    section {
      display: none;
      background: white;
      padding: 20px;
      border-radius: 8px;
      box-shadow: 0 0 12px rgba(0,0,0,0.1);
      margin-bottom: 40px;
    }
    section.active {
      display: block;
    }
    h2 {
      margin-top: 0;
    }
    form label {
      display: block;
      margin-top: 12px;
      font-weight: 600;
    }
    input, textarea, select {
      width: 100%;
      box-sizing: border-box;
      padding: 8px;
      margin-top: 6px;
      border-radius: 4px;
      border: 1px solid #ccc;
    }
    button.submit-btn {
      background: #28a745;
      color: white;
      margin-top: 15px;
      padding: 10px 20px;
      border: none;
      cursor: pointer;
      font-weight: 700;
      border-radius: 5px;
    }
    button.submit-btn:hover {
      background: #218838;
    }
    table {
      width: 100%;
      border-collapse: collapse;
      margin-top: 15px;
    }
    th, td {
      border: 1px solid #dee2e6;
      padding: 10px;
      text-align: left;
    }
    th {
      background-color: #e9ecef;
    }
    .action-btn {
      padding: 6px 10px;
      border: none;
      border-radius: 4px;
      cursor: pointer;
      font-weight: 600;
    }
    .edit-btn {
      background-color: #ffc107;
      color: #212529;
    }
    .delete-btn {
      background-color: #dc3545;
      color: white;
    }
    .info-message {
      color: green;
      font-weight: 600;
      margin-top: 15px;
    }
    .error-message {
      color: red;
      font-weight: 600;
      margin-top: 15px;
    }
    textarea {
      resize: vertical;
    }
  </style>
</head>
<body>

<header>PahanaEdu Book Shop - Admin Dashboard</header>

<nav>
  <button onclick="showSection('newCustomerSection')">Add New Customer</button>
  <button onclick="showSection('editCustomerSection')">Edit Customer Info</button>
  <button onclick="showSection('manageItemsSection')">Manage Items</button>
  <button onclick="showSection('displayAccountsSection')">Display Accounts</button>
  <button onclick="showSection('helpSection')">Help</button>
  <button onclick="logout()">Logout</button> <!-- Changed from exitSystem to logout -->
</nav>

<main>

  <!-- 1. Add New Customer -->
  <section id="newCustomerSection" class="active">
    <h2>Add New Customer Account</h2>
    <form method="post" action="AddCustomerServlet" id="addCustomerForm">

      <label for="newAccountNumber">Account Number</label>
      <input type="number" id="newAccountNumber" name="accountNo" required />

      <label for="newName">Name</label>
      <input type="text" id="newName" name="custName" required />

      <label for="newAddress">Address</label>
      <textarea id="newAddress" name="address" rows="3" required></textarea>

      <label for="newTelephone">Telephone Number</label>
      <input type="tel" id="newTelephone" name="telephone" required pattern="^\d{10}$" placeholder="Enter exactly 10 digits" title="Telephone number must be exactly 10 digits" />

      <label for="newEmail">Email</label>
      <input type="email" id="newEmail" name="email" required />

      <!-- Optionally, add password and status fields -->
      <label for="newPassword">Password</label>
      <input type="password" id="newPassword" name="password" required />

      <input type="hidden" name="status" value="active" />

      <button type="submit" class="submit-btn">Add Customer</button>
      <div id="addCustomerMsg" class="info-message"></div>
    </form>

  </section>

  <!-- 2. Edit Customer -->
  <section id="editCustomerSection" style="padding: 20px;">
    <h2>Edit Customer Info</h2>

    <label for="editAccountNumberSearch">Enter Account Number to Edit:</label>
    <input type="number" id="editAccountNumberSearch" placeholder="Enter account number" />
    <button type="button" onclick="loadCustomerForEdit()">Load Customer</button>

    <!-- The existing edit form -->
    <form id="editCustomerForm" style="display:none; margin-top:20px;">
      <input type="hidden" id="editAccountNoHidden" name="accountNo" />

      <label for="editName">Name</label>
      <input type="text" id="editName" name="custName" required />

      <label for="editAddress">Address</label>
      <textarea id="editAddress" name="address" rows="3" required></textarea>

      <label for="editTelephone">Telephone Number</label>
      <input type="tel" id="editTelephone" name="telephone" required pattern="^\d{10,15}$" />

      <label for="editEmail">Email</label>
      <input type="email" id="editEmail" name="email" required />

      <button type="submit" class="submit-btn">Update Customer</button>
      <div id="editCustomerMsg" class="info-message"></div>
    </form>
  </section>


  <!-- 3. Manage Items -->
  <section id="manageItemsSection">
    <h2>Manage Item Information</h2>

    <!-- Manage item form -->
    <form id="itemForm">
      <input type="hidden" id="itemId" /> <!-- hidden field for update -->

      <label for="itemName">Item Name</label>
      <input type="text" id="itemName" required />

      <label for="itemDescription">Description</label>
      <textarea id="itemDescription" rows="3"></textarea>

      <label for="itemPrice">Price</label>
      <input type="number" id="itemPrice" step="0.01" min="0" required />

      <label for="itemQuantity">Quantity Available</label>
      <input type="number" id="itemQuantity" min="0" required />

      <label for="itemCategory">Category</label>
      <input type="text" id="itemCategory" />

      <button type="submit" class="submit-btn">Add Item</button>
      <div id="itemFormMsg" class="info-message"></div>
    </form>

    <!-- Item list -->
    <table id="itemsTable" style="margin-top: 20px;">
      <thead>
      <tr>
        <th>Item ID</th><th>Name</th><th>Description</th><th>Price</th><th>Quantity</th><th>Category</th><th>Actions</th>
      </tr>
      </thead>
      <tbody>
      <!-- Items will populate here -->
      </tbody>
    </table>
  </section>

  <!-- 4. Display Accounts -->
  <section id="displayAccountsSection">
    <h2>Customer Account Details</h2>
    <button onclick="loadAllCustomers()">Load All Customers</button>

    <table id="customersTable" style="margin-top: 20px;">
      <thead>
      <tr>
        <th>Account #</th><th>Name</th><th>Address</th><th>Telephone</th><th>Email</th>
      </tr>
      </thead>
      <tbody>
      <!-- Customer rows will appear here -->
      </tbody>
    </table>
  </section>

  <!-- 7. Help Section -->
  <section id="helpSection">
    <h2>Help - System Usage Guidelines</h2>
    <h3>Admin User Guide</h3>
    <ul>
      <li><strong>Add New Customer:</strong> Use the "Add New Customer" section to create new customer accounts.</li>
      <li><strong>Edit Customer:</strong> Search customer by account number to update their details.</li>
      <li><strong>Manage Items:</strong> Add, update, or delete items available for sale.</li>
      <li><strong>Display Account Details:</strong> View all customer accounts registered in the system.</li>
      <li><strong>Logout:</strong> Safely log out using the Logout button.</li>
    </ul>
  </section>

</main>

<script>
  // In-memory data stores for demo purposes — replace with AJAX backend calls in real app
  let customers = [];
  let items = [];

  // Helper to reset messages
  function resetMessages() {
    document.getElementById('addCustomerMsg').textContent = '';
    document.getElementById('editCustomerMsg').textContent = '';
    document.getElementById('itemFormMsg').textContent = '';
  }

  // Navigation to show only one section at a time
  function showSection(sectionId) {
    resetMessages();
    document.querySelectorAll('main > section').forEach(section => {
      section.classList.remove('active');
    });
    document.getElementById(sectionId).classList.add('active');

    // Clear forms on opening
    if(sectionId === 'newCustomerSection') {
      document.getElementById('addCustomerForm').reset();
    }
    if(sectionId === 'editCustomerSection') {
      document.getElementById('editCustomerForm').style.display = 'none';
      document.getElementById('editAccountNumberSearch').value = '';
    }
    if(sectionId === 'manageItemsSection') {
      document.getElementById('itemForm').reset();
      document.getElementById('itemId').value = '';
      renderItemsTable();
    }
    if(sectionId === 'displayAccountsSection') {
      loadAllCustomers();
    }
  }

  /*** 1. Add New Customer Logic ***/
  document.getElementById('addCustomerForm').addEventListener('submit', function(e) {
    e.preventDefault();  // Prevent normal form submit (page reload)

    resetMessages();
    const form = this;
    const msgDiv = document.getElementById('addCustomerMsg');

    // Collect form data values
    const accountNumber = Number(document.getElementById('newAccountNumber').value.trim());
    const name = document.getElementById('newName').value.trim();
    const address = document.getElementById('newAddress').value.trim();
    const telephone = document.getElementById('newTelephone').value.trim();
    const email = document.getElementById('newEmail').value.trim();

    // Validate telephone number: exactly 10 digits
    const phoneRegex = /^\d{10}$/;
    if (!phoneRegex.test(telephone)) {
      msgDiv.textContent = 'Invalid telephone number: must be exactly 10 digits.';
      msgDiv.className = 'error-message';
      return;
    }

    // Optional: check if accountNumber already exists locally before sending to server
    // Comment this or move to server validation if needed
    if (customers && customers.find && customers.find(c => c.accountNumber === accountNumber)) {
      msgDiv.textContent = 'Account number already exists!';
      msgDiv.className = 'error-message';
      return;
    }

    // Prepare FormData and URLSearchParams for sending to backend
    const formData = new FormData(form);
    const params = new URLSearchParams();
    for (const [key, value] of formData.entries()) {
      params.append(key, value);
    }

    // Send data asynchronously to the servlet
    fetch('AddCustomerServlet', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      body: params.toString()
    })
            .then(response => response.text())
            .then(text => {
              if (text.trim() === 'success') {
                // Update your local customers list if needed (optional)
                if (customers && Array.isArray(customers)) {
                  customers.push({ accountNumber, name, address, telephone, email });
                }

                msgDiv.textContent = 'Customer added successfully!';
                msgDiv.className = 'info-message';
                form.reset();
              } else {
                msgDiv.textContent = 'Failed to add customer.';
                msgDiv.className = 'error-message';
              }
            })
            .catch(error => {
              msgDiv.textContent = 'Error: ' + error.message;
              msgDiv.className = 'error-message';
            });
  });


  /*** 2. Edit Customer Logic ***/
  function loadCustomerForEdit() {
    const accNumInput = document.getElementById('editAccountNumberSearch');
    const accNum = accNumInput.value.trim();

    if (!accNum) {
      alert('Please enter a valid account number.');
      return;
    }

    // Fetch from server
    fetch('GetAllCustomersServlet')
            .then(response => response.json())
            .then(data => {
              customers = data;
              const customer = customers.find(c => c.accountNumber == accNum); // Use == to match string/number

              if (!customer) {
                alert('Customer not found!');
                document.getElementById('editCustomerForm').style.display = 'none';
                return;
              }

              document.getElementById('editAccountNoHidden').value = customer.accountNumber;
              document.getElementById('editName').value = customer.name || '';
              document.getElementById('editAddress').value = customer.address || '';
              document.getElementById('editTelephone').value = customer.telephone || '';
              document.getElementById('editEmail').value = customer.email || '';
              document.getElementById('editCustomerForm').style.display = 'block';
            })
            .catch(err => {
              console.error(err);
              alert('Failed to fetch customer data.');
            });
  }
  // Edit Customer form submit logic
  document.getElementById('editCustomerForm').addEventListener('submit', function (e) {
    e.preventDefault();

    const params = new URLSearchParams(new FormData(this));

    fetch('UpdateCustomerServlet', {
      method: 'POST',
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      body: params.toString()
    })
            .then(response => response.text())
            .then(result => {
              if (result.trim() === 'success') {
                document.getElementById('editCustomerMsg').textContent = 'Customer updated successfully!';
                document.getElementById('editCustomerMsg').className = 'info-message';
              } else {
                document.getElementById('editCustomerMsg').textContent = 'Failed to update customer.';
                document.getElementById('editCustomerMsg').className = 'error-message';
              }
            })
            .catch(error => {
              document.getElementById('editCustomerMsg').textContent = 'Error: ' + error.message;
              document.getElementById('editCustomerMsg').className = 'error-message';
            });
  });


  /*** 3. Manage Items Logic ***/
  document.getElementById('itemForm').addEventListener('submit', function(e) {
    e.preventDefault();
    resetMessages();

    const idText = document.getElementById('itemId').value;
    const name = document.getElementById('itemName').value.trim();
    const description = document.getElementById('itemDescription').value.trim();
    const price = parseFloat(document.getElementById('itemPrice').value);
    const quantity = Number(document.getElementById('itemQuantity').value);
    const category = document.getElementById('itemCategory').value.trim();

    if (!name || isNaN(price) || isNaN(quantity)) {
      const msgDiv = document.getElementById('itemFormMsg');
      msgDiv.textContent = 'Please fill in required fields with valid values.';
      msgDiv.className = 'error-message';
      return;
    }

    // Prepare data to send
    const params = new URLSearchParams();
    if(idText) params.append('itemId', idText);  // for update
    params.append('name', name);
    params.append('description', description);
    params.append('price', price);
    params.append('quantityAvailable', quantity);
    params.append('category', category);

    fetch('AddOrUpdateItemServlet', {
      method: 'POST',
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      body: params.toString()
    })
            .then(response => response.text())
            .then(result => {
              const msgDiv = document.getElementById('itemFormMsg');
              if(result.trim() === 'success') {
                msgDiv.textContent = idText ? 'Item updated successfully!' : 'Item added successfully!';
                msgDiv.className = 'info-message';

                e.target.reset();
                document.getElementById('itemId').value = '';

                loadItemsFromServer(); // Reload fresh items from DB
              } else {
                msgDiv.textContent = 'Failed to save item.';
                msgDiv.className = 'error-message';
              }
            })
            .catch(error => {
              const msgDiv = document.getElementById('itemFormMsg');
              msgDiv.textContent = 'Error: ' + error.message;
              msgDiv.className = 'error-message';
            });
  });

  /*** 4. Display Customer Accounts ***/
  function loadAllCustomers() {
    const tbody = document.querySelector('#customersTable tbody');
    tbody.innerHTML = '';

    fetch('GetAllCustomersServlet')
            .then(response => response.json())
            .then(data => {
              customers = data; // update local array
              if (!data || data.length === 0) {
                tbody.innerHTML = '<tr><td colspan="5" style="text-align:center;">No customers available.</td></tr>';
                return;
              }
              data.forEach(cust => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
          <td>${cust.accountNumber}</td>
          <td>${cust.name}</td>
          <td>${cust.address}</td>
          <td>${cust.telephone}</td>
          <td>${cust.email}</td>
        `;
                tbody.appendChild(tr);
              });
            })
            .catch(() => {
              tbody.innerHTML = '<tr><td colspan="5" style="text-align:center;">Failed to load customers.</td></tr>';
            });
  }

  /*** Logout Function ***/
  function logout() {
    if (confirm("Are you sure you want to logout?")) {
      // Redirect to a logout servlet or clear session manually
      window.location.href = "LogoutServlet"; // recommended to create a LogoutServlet that invalidates the session
    }
  }

  // Initialize on page load
  showSection('newCustomerSection');
</script>

</body>
</html>
