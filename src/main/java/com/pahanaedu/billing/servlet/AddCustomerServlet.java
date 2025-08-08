package com.pahanaedu.billing.servlet;

import com.pahanaedu.billing.dao.AdminDAO;
import com.pahanaedu.billing.model.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/AddCustomerServlet")
public class AddCustomerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String accountNo = request.getParameter("accountNo");
        String custName = request.getParameter("custName");
        String address = request.getParameter("address");
        String telephone = request.getParameter("telephone");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String status = request.getParameter("status");

        if (accountNo == null || accountNo.trim().isEmpty() ||
                custName == null || custName.trim().isEmpty() ||
                address == null || address.trim().isEmpty() ||
                telephone == null || telephone.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {

            response.setContentType("text/plain");
            response.getWriter().write("failure");
            return;
        }

        if (status == null || status.trim().isEmpty()) {
            status = "active";
        }

        Customer customer = new Customer();
        customer.setAccountNo(accountNo.trim());
        customer.setCustName(custName.trim());
        customer.setAddress(address.trim());
        customer.setTelephone(telephone.trim());
        customer.setEmail(email.trim());
        customer.setPassword(password.trim());
        customer.setStatus(status.trim());

        AdminDAO dao = new AdminDAO();
        boolean added = dao.addCustomer(customer);

        response.setContentType("text/plain");
        if (added) {
            response.getWriter().write("success");
        } else {
            response.getWriter().write("failure");
        }
    }
}
