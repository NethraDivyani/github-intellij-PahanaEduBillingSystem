package com.pahanaedu.billing.servlet;

import com.pahanaedu.billing.dao.AdminDAO;
import com.pahanaedu.billing.model.Admin;
import com.pahanaedu.billing.model.Cashier;
import com.pahanaedu.billing.model.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String role = request.getParameter("role");
        boolean success = false;

        if ("cashier".equalsIgnoreCase(role)) {
            Cashier cashier = new Cashier();
            cashier.setUsername(request.getParameter("username"));
            cashier.setPassword(request.getParameter("password"));
            cashier.setName(request.getParameter("name"));
            cashier.setEmail(request.getParameter("email"));
            cashier.setStatus("active");

            success = AdminDAO.addCashier(cashier);

        } else if ("admin".equalsIgnoreCase(role)) {
            Admin admin = new Admin();
            admin.setUsername(request.getParameter("username"));
            admin.setPassword(request.getParameter("password"));
            admin.setName(request.getParameter("name"));
            admin.setEmail(request.getParameter("email"));

            AdminDAO dao = new AdminDAO();
            success = dao.addAdmin(admin);
        }

        if (success) {
            request.getSession().setAttribute("loginSuccess", "Registration successful! Please log in.");
            response.sendRedirect("index.jsp");
        } else {
            request.setAttribute("loginError", "Registration failed. Please try again.");
            request.getRequestDispatcher("Register.jsp").forward(request, response);
        }
    }
}
