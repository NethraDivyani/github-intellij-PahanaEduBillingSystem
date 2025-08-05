package com.pahanaedu.billing.servlet;

import com.pahanaedu.billing.dao.AdminDAO;
import com.pahanaedu.billing.model.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/AddCustomerServlet")
public class AddCustomerServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Read parameters
        String accountNo = request.getParameter("accountNo");
        String custName = request.getParameter("custName");
        String address = request.getParameter("address");
        String telephone = request.getParameter("telephone");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String status = request.getParameter("status");

        // Create Customer object
        Customer customer = new Customer();
        customer.setAccountNo(accountNo);
        customer.setCustName(custName);
        customer.setAddress(address);
        customer.setTelephone(telephone);
        customer.setEmail(email);
        customer.setPassword(password);
        customer.setStatus(status);

        // Add customer using AdminDAO
        boolean isAdded = AdminDAO.addCustomer(customer);
        response.setContentType("text/plain");
        if (isAdded) {
            response.getWriter().write("success");
        } else {
            response.getWriter().write("failure");
        }



    }
}
