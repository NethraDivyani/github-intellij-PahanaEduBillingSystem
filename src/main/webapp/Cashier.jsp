<%@ page import="com.pahanaedu.billing.model.Customer" %>
<%@ page import="com.pahanaedu.billing.dao.CustomerDAO" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cashier - PahanaEdu Book Shop</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f0f2f5; margin: 0; padding: 0; }
        header { background-color: #007bff; color: white; padding: 15px 30px; font-size: 1.8rem; font-weight: bold; text-align: center; }
        nav { background: #e9ecef; display: flex; justify-content: center; align-items: center; padding: 10px; gap: 15px; }
        nav button, nav form button { background: #007bff; border: none; color: white; padding: 10px 18px; border-radius: 4px; cursor: pointer; font-weight: 600; transition: background-color 0.3s ease; }
        nav button:hover, nav form button:hover { background: #0056b3; }
        main { padding: 20px 40px; max-width: 1200px; margin: 0 auto; }
        section { display: none; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 0 12px rgba(0,0,0,0.1); margin-bottom: 40px; }
        section.active { display: block; }
        h2 { margin-top: 0; }
        table { width: 100%; border-collapse: collapse; margin-top: 15px; }
        th, td { border: 1px solid #dee2e6; padding: 10px; text-align: left; }
        th { background-color: #e9ecef; }
        .calc-section { margin-top: 20px; padding: 15px; background-color: #f8f9fa; border-radius: 6px; }
        .calc-section input[type=number] { width: 120px; margin-right: 10px; }
        .calc-section span { font-weight: bold; }
        .error-message { color: red; font-weight: 600; margin-top: 15px; }
        button.submit-btn { background: #28a745; color: white; margin-top: 15px; padding: 10px 20px; border: none; cursor: pointer; font-weight: 700; border-radius: 5px; }
        button.submit-btn:hover { background: #218838; }
        .selected-item { margin-bottom: 5px; }
    </style>

    <script>
        let billItems = [];

        function showSection(id) {
            document.querySelectorAll('section').forEach(sec => sec.classList.remove('active'));
            document.getElementById(id).classList.add('active');
        }

        function searchCustomer() {
            const accountNo = document.getElementById("searchAccountNo").value.trim();
            if (!accountNo) return alert("Please enter account number");

            fetch(`GetCustomerServlet?accountNo=${accountNo}`)
                .then(res => res.json())
                .then(c => {
                    const div = document.getElementById("customerResult");
                    billItems = [];
                    document.getElementById("selectedItems").innerHTML = '';
                    document.getElementById("totalAmount").innerText = '0.00';
                    document.getElementById("finalAmount").innerText = '0.00';
                    document.getElementById("discount").value = '';

                    if (!c || !c.accountNumber) {
                        div.innerHTML = '<p class="error-message">No customer found with this account number.</p>';
                        document.getElementById("itemSelect").innerHTML = '<option value="">--Select Item--</option>';
                        return;
                    }

                    div.innerHTML = `
                        <table>
                            <tr><th>Field</th><th>Details</th></tr>
                            <tr><td>Account Number</td><td id="custAccount">${c.accountNumber}</td></tr>
                            <tr><td>Name</td><td id="custName">${c.name}</td></tr>
                            <tr><td>Address</td><td>${c.address}</td></tr>
                            <tr><td>Telephone</td><td>${c.telephone}</td></tr>
                            <tr><td>Email</td><td>${c.email}</td></tr>
                            <tr><td>Status</td><td>${c.status}</td></tr>
                        </table>
                    `;

                    loadItems();
                })
                .catch(err => console.error(err));
        }

        function loadItems() {
            fetch('GetItemsServlet')
                .then(res => res.json())
                .then(items => {
                    const select = document.getElementById("itemSelect");
                    select.innerHTML = '<option value="">--Select Item--</option>';
                    items.forEach(i => {
                        select.innerHTML += `<option value="${i.name}:${i.price}">${i.name} - $${i.price.toFixed(2)}</option>`;
                    });
                });
        }

        function addItemToBill() {
            const select = document.getElementById("itemSelect");
            const value = select.value;
            if (!value) return;

            const [itemName, itemPrice] = value.split(":");
            billItems.push({name: itemName, price: parseFloat(itemPrice)});
            renderBillItems();
            select.value = '';
        }

        function removeItem(index) {
            billItems.splice(index, 1);
            renderBillItems();
        }

        function renderBillItems() {
            const div = document.getElementById("selectedItems");
            div.innerHTML = '';
            billItems.forEach((item, index) => {
                div.innerHTML += `<div class="selected-item">${item.name} - $${item.price.toFixed(2)}
                    <button type="button" onclick="removeItem(${index})">Remove</button></div>`;
            });
            calculateFinalAmount();
        }

        function calculateFinalAmount() {
            let total = billItems.reduce((sum, item) => sum + item.price, 0);
            let discount = parseFloat(document.getElementById("discount").value) || 0;
            let final = total - (total * discount / 100);

            document.getElementById("totalAmount").innerText = total.toFixed(2);
            document.getElementById("finalAmount").innerText = final.toFixed(2);

            document.getElementById("billItemsList").innerHTML = billItems.map(i => `<p>${i.name} - $${i.price.toFixed(2)}</p>`).join('');
            document.getElementById("billTotal").innerText = total.toFixed(2);
            document.getElementById("billDiscount").innerText = discount.toFixed(2);
            document.getElementById("billFinal").innerText = final.toFixed(2);
        }

        function calculateAndPrint() {
            const accountNumber = document.getElementById("custAccount")?.innerText || '';
            const customerName = document.getElementById("custName")?.innerText || '';

            calculateFinalAmount();

            // Save bill to database
            fetch('SaveBillServlet', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    accountNumber: accountNumber,
                    totalAmount: parseFloat(document.getElementById("totalAmount").innerText),
                    discount: parseFloat(document.getElementById("discount").value || 0),
                    finalAmount: parseFloat(document.getElementById("finalAmount").innerText),
                    items: billItems
                })
            }).then(res => res.json())
                .then(data => {
                    if (data.status === 'success') {
                        alert("Bill saved successfully!");
                    } else {
                        alert("Error saving bill.");
                    }
                });

            // Print bill
            document.getElementById("customerNameBill").innerText = customerName;
            document.getElementById("customerAccountBill").innerText = accountNumber;

            const content = document.getElementById("billContent").innerHTML;
            const win = window.open('', 'PrintBill', 'height=600,width=800');
            win.document.write('<html><head><title>Print Bill</title></head><body>');
            win.document.write('<h2>PAHANA EDU BOOK SHOP</h2>');
            win.document.write('<p>Address: No. 123, Main Street, Colombo</p>');
            win.document.write('<p>Contact: 011-1234567</p>');
            win.document.write(content);
            win.document.write('</body></html>');
            win.document.close();
            win.print();
        }

        // Load all bills for cashier
        function loadAllBills() {
            fetch('GetBillsServlet')
                .then(res => res.json())
                .then(bills => {
                    const table = document.getElementById("billsTable");
                    table.innerHTML = `<tr>
                        <th>Bill ID</th>
                        <th>Account Number</th>
                        <th>Date</th>
                        <th>Total</th>
                        <th>Discount</th>
                        <th>Final Amount</th>
                        <th>Items</th>
                    </tr>`; // Reset table
                    bills.forEach(b => {
                        const items = b.items.map(i => i.name + " ($" + i.price.toFixed(2) + ")").join(", ");
                        table.innerHTML += `<tr>
                            <td>${b.billId}</td>
                            <td>${b.accountNumber}</td>
                            <td>${b.billDate}</td>
                            <td>$${b.totalAmount.toFixed(2)}</td>
                            <td>${b.discount.toFixed(2)}%</td>
                            <td>$${b.finalAmount.toFixed(2)}</td>
                            <td>${items}</td>
                        </tr>`;
                    });
                });
        }
        /*** Logout Function ***/
        function logout() {
            if (confirm("Are you sure you want to logout?")) {
                // Use window.location.href to call the LogoutServlet
                window.location.href = "<%=request.getContextPath()%>/LogoutServlet";
            }
        }
    </script>
</head>
<body>

<header>Cashier Dashboard</header>

<nav>
    <button onclick="showSection('customer-section')">Customer Details</button>
    <button onclick="showSection('all-customers-section')">All Customers</button>
    <button onclick="showSection('all-bills-section'); loadAllBills()">All Bills</button>
    <button onclick="showSection('help-section')">Help</button>
    <form action="LogoutServlet" method="post" style="display:inline;">
        <button type="submit">Logout</button>
    </form>
</nav>

<main>
    <!-- Single Customer Section -->
    <section id="customer-section" class="active">
        <h2>View Customer Account & Calculate Bill</h2>
        <label>Account Number:</label>
        <input type="text" id="searchAccountNo" required>
        <button type="button" class="submit-btn" onclick="searchCustomer()">Search</button>

        <div id="customerResult"></div>

        <!-- Bill Section -->
        <div class="calc-section">
            <h3>Calculate & Print Bill</h3>
            <label>Select Item:</label>
            <select id="itemSelect" onchange="addItemToBill()">
                <option value="">--Select Item--</option>
            </select>

            <div id="selectedItems"></div>

            <label>Discount (%):</label>
            <input type="number" id="discount" step="0.01" oninput="calculateFinalAmount()"><br><br>

            <span>Total Amount: $<span id="totalAmount">0.00</span></span><br>
            <span>Final Amount after Discount: $<span id="finalAmount">0.00</span></span><br><br>

            <button type="button" class="submit-btn" onclick="calculateAndPrint()">Calculate & Print Bill</button>
        </div>

        <div id="billContent" style="display:none;">
            <h3>Bill for <span id="customerNameBill"></span></h3>
            <p>Account Number: <span id="customerAccountBill"></span></p>
            <div id="billItemsList"></div>
            <p>Total Amount: $<span id="billTotal">0</span></p>
            <p>Discount: <span id="billDiscount">0</span>%</p>
            <p>Final Amount: $<span id="billFinal">0</span></p>
        </div>
    </section>

    <!-- All Customers Section -->
    <section id="all-customers-section">
        <h2>All Customers</h2>
        <%
            CustomerDAO customerDAO = new CustomerDAO();
            List<Customer> allCustomers = customerDAO.getAllCustomers();
            if (allCustomers != null && !allCustomers.isEmpty()) {
        %>
        <table>
            <tr>
                <th>Account No</th>
                <th>Name</th>
                <th>Address</th>
                <th>Telephone</th>
                <th>Email</th>
                <th>Status</th>
            </tr>
            <%
                for (Customer c : allCustomers) {
            %>
            <tr>
                <td><%= c.getAccountNo() %></td>
                <td><%= c.getCustName() %></td>
                <td><%= c.getAddress() %></td>
                <td><%= c.getTelephone() %></td>
                <td><%= c.getEmail() %></td>
                <td><%= c.getStatus() %></td>
            </tr>
            <%
                }
            %>
        </table>
        <% } else { %>
        <p class="error-message">No customers found.</p>
        <% } %>
    </section>

    <!-- All Bills Section -->
    <section id="all-bills-section">
        <h2>All Bills</h2>
        <table id="billsTable">
            <tr>
                <th>Bill ID</th>
                <th>Account Number</th>
                <th>Date</th>
                <th>Total</th>
                <th>Discount</th>
                <th>Final Amount</th>
                <th>Items</th>
            </tr>
        </table>
    </section>

    <!-- Help Section -->
    <section id="help-section">
        <h2>Help</h2>
        <ul>
            <li>Search customer account details by Account Number</li>
            <li>Select items to generate bill</li>
            <li>Apply discount if any</li>
            <li>Print bill with shop and customer details</li>
            <li>View all previous bills</li>
            <li>Contact admin for system issues</li>
        </ul>
    </section>
</main>

</body>
</html>
