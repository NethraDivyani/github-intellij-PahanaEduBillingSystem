<%@ page import="com.pahanaedu.billing.model.Customer" %>
<%@ page import="com.pahanaedu.billing.dao.CustomerDAO" %>

<%
    String accountNo = request.getParameter("accountNo");
    Customer customer = null;

    if (accountNo != null && !accountNo.trim().isEmpty()) {
        CustomerDAO customerDAO = new CustomerDAO();
        customer = customerDAO.getCustomerByAccountNo(accountNo.trim());
    }
%>

<!-- Customer details display -->
<h3>Customer Account Details</h3>

<%
    if (customer != null) {
%>
<table border="1" cellpadding="5">
    <tr><td>Account Number</td><td><%= customer.getAccountNo() %></td></tr>
    <tr><td>Name</td><td><%= customer.getCustName() %></td></tr>
    <tr><td>Address</td><td><%= customer.getAddress() %></td></tr>
    <tr><td>Telephone</td><td><%= customer.getTelephone() %></td></tr>
    <tr><td>Email</td><td><%= customer.getEmail() %></td></tr>
    <tr><td>Status</td><td><%= customer.getStatus() %></td></tr>
</table>
<%
} else {
%>
<p>Please enter a valid Account Number and submit to see customer details.</p>
<%
    }
%>
