<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
  <button onclick="exitSystem()">Exit</button>
</nav>

<main>

  <!-- 1. Add New Customer -->
  <section id="newCustomerSection" class="active">
    <h2>Add New Customer Account</h2>
    <form id="addCustomerForm">
      <label for="newAccountNumber">Account Number</label>
      <input type="number" id="newAccountNumber" required />

      <label for="newName">Name</label>
      <input type="text" id="newName" required />

      <label for="newAddress">Address</label>
      <textarea id="newAddress" rows="3" required></textarea>

      <label for="newTelephone">Telephone Number</label>
      <input
              type="tel"
              id="newTelephone"
              required
              pattern="^\d{10}$"
              placeholder="Enter exactly 10 digits"
              title="Telephone number must be exactly 10 digits"
      />


      <label for="newEmail">Email</label>
      <input type="email" id="newEmail" required />

      <button type="submit" class="submit-btn">Add Customer</button>
      <div id="addCustomerMsg" class="info-message"></div>
    </form>
  </section>

  <!-- 2. Edit Customer -->
  <section id="editCustomerSection">
    <h2>Edit Customer Information</h2>
    <label for="editAccountNumberSearch">Enter Customer Account Number</label>
    <input type="number" id="editAccountNumberSearch" />
    <button onclick="loadCustomerForEdit()">Search</button>

    <form id="editCustomerForm" style="display:none; margin-top:20px;">
      <label for="editName">Name</label>
      <input type="text" id="editName" required />

      <label for="editAddress">Address</label>
      <textarea id="editAddress" rows="3" required></textarea>

      <label for="editTelephone">Telephone Number</label>
      <input type="tel" id="editTelephone" required pattern="^\d{10,15}$" />

      <label for="editEmail">Email</label>
      <input type="email" id="editEmail" required />

      <button type="submit" class="submit-btn">Update Customer</button>
      <div id="editCustomerMsg" class="info-message"></div>
    </form>
  </section>

  <!-- 3. Manage Items -->
  <section id="manageItemsSection">
    <h2>Manage Item Information</h2>

    <!-- Add/update item form -->
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

      <button type="submit" class="submit-btn">Add / Update Item</button>
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
      <li><strong>Exit:</strong> Log out from the system safely using the Exit button.</li>
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
    e.preventDefault();
    resetMessages();

    const accountNumber = Number(document.getElementById('newAccountNumber').value.trim());
    const name = document.getElementById('newName').value.trim();
    const address = document.getElementById('newAddress').value.trim();
    const telephone = document.getElementById('newTelephone').value.trim();
    const email = document.getElementById('newEmail').value.trim();

    // Validate telephone number: exactly 10 digits
    const phoneRegex = /^\d{10}$/;
    if (!phoneRegex.test(telephone)) {
      const msgDiv = document.getElementById('addCustomerMsg');
      msgDiv.textContent = 'Invalid telephone number: must be exactly 10 digits.';
      msgDiv.className = 'error-message';
      return;
    }

    if (customers.find(c => c.accountNumber === accountNumber)) {
      const msgDiv = document.getElementById('addCustomerMsg');
      msgDiv.textContent = 'Account number already exists!';
      msgDiv.className = 'error-message';
      return;
    }

    customers.push({ accountNumber, name, address, telephone, email });
    const msgDiv = document.getElementById('addCustomerMsg');
    msgDiv.textContent = 'Customer added successfully!';
    msgDiv.className = 'info-message';

    e.target.reset();
  });


  /*** 2. Edit Customer Logic ***/
  function loadCustomerForEdit() {
    resetMessages();
    const accNum = Number(document.getElementById('editAccountNumberSearch').value.trim());
    if (!accNum) {
      alert('Please enter a valid account number.');
      return;
    }
    const customer = customers.find(c => c.accountNumber === accNum);
    if (!customer) {
      alert('Customer not found.');
      document.getElementById('editCustomerForm').style.display = 'none';
      return;
    }
    document.getElementById('editName').value = customer.name;
    document.getElementById('editAddress').value = customer.address;
    document.getElementById('editTelephone').value = customer.telephone;
    document.getElementById('editEmail').value = customer.email;
    document.getElementById('editCustomerForm').style.display = 'block';
  }

  document.getElementById('editCustomerForm').addEventListener('submit', function(e) {
    e.preventDefault();
    resetMessages();

    const accNum = Number(document.getElementById('editAccountNumberSearch').value.trim());
    const name = document.getElementById('editName').value.trim();
    const address = document.getElementById('editAddress').value.trim();
    const telephone = document.getElementById('editTelephone').value.trim();
    const email = document.getElementById('editEmail').value.trim();

    const customer = customers.find(c => c.accountNumber === accNum);
    if (!customer) {
      document.getElementById('editCustomerMsg').textContent = 'Customer not found!';
      document.getElementById('editCustomerMsg').className = 'error-message';
      return;
    }

    customer.name = name;
    customer.address = address;
    customer.telephone = telephone;
    customer.email = email;

    document.getElementById('editCustomerMsg').textContent = 'Customer updated successfully!';
    document.getElementById('editCustomerMsg').className = 'info-message';
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
      document.getElementById('itemFormMsg').textContent = 'Please fill in required fields with valid values.';
      document.getElementById('itemFormMsg').className = 'error-message';
      return;
    }

    if (idText) {
      const id = Number(idText);
      const item = items.find(i => i.itemId === id);
      if (item) {
        item.name = name;
        item.description = description;
        item.price = price;
        item.quantityAvailable = quantity;
        item.category = category;
        document.getElementById('itemFormMsg').textContent = 'Item updated successfully!';
        document.getElementById('itemFormMsg').className = 'info-message';
      }
    } else {
      const newId = items.length > 0 ? Math.max(...items.map(i => i.itemId)) + 1 : 1;
      items.push({
        itemId: newId,
        name,
        description,
        price,
        quantityAvailable: quantity,
        category
      });
      document.getElementById('itemFormMsg').textContent = 'Item added successfully!';
      document.getElementById('itemFormMsg').className = 'info-message';
    }

    e.target.reset();
    document.getElementById('itemId').value = '';
    renderItemsTable();
  });

  // Render items table
  function renderItemsTable() {
    const tbody = document.querySelector('#itemsTable tbody');
    tbody.innerHTML = '';
    items.forEach(item => {
      const tr = document.createElement('tr');
      tr.innerHTML = `
                <td>${item.itemId}</td>
                <td>${item.name}</td>
                <td>${item.description || ''}</td>
                <td>${item.price.toFixed(2)}</td>
                <td>${item.quantityAvailable}</td>
                <td>${item.category || ''}</td>
                <td>
                    <button class="action-btn edit-btn" onclick="editItem(${item.itemId})">Edit</button>
                    <button class="action-btn delete-btn" onclick="deleteItem(${item.itemId})">Delete</button>
                </td>
            `;
      tbody.appendChild(tr);
    });
  }

  function editItem(itemId) {
    const item = items.find(i => i.itemId === itemId);
    if (!item) return;
    document.getElementById('itemId').value = item.itemId;
    document.getElementById('itemName').value = item.name;
    document.getElementById('itemDescription').value = item.description;
    document.getElementById('itemPrice').value = item.price;
    document.getElementById('itemQuantity').value = item.quantityAvailable;
    document.getElementById('itemCategory').value = item.category;
    showSection('manageItemsSection');
  }

  function deleteItem(itemId) {
    if (!confirm('Are you sure you want to delete this item?')) return;
    items = items.filter(i => i.itemId !== itemId);
    renderItemsTable();
  }

  /*** 4. Display Customer Accounts ***/
  function loadAllCustomers() {
    const tbody = document.querySelector('#customersTable tbody');
    tbody.innerHTML = '';
    if (customers.length === 0) {
      tbody.innerHTML = '<tr><td colspan="5" style="text-align:center;">No customers available.</td></tr>';
      return;
    }
    customers.forEach(cust => {
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
  }

  /*** 8. Exit System ***/
  function exitSystem() {
    if (confirm("Are you sure you want to exit the system?")) {
      alert("Logging out...");
      window.location.href = "index.html";
    }
  }

  // Initialize on page load
  showSection('newCustomerSection');
</script>

</body>
</html>
