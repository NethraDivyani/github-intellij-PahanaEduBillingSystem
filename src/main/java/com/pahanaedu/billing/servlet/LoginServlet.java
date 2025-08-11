package com.pahanaedu.billing.servlet;

import com.pahanaedu.billing.dao.AdminDAO;
import com.pahanaedu.billing.model.Admin;
import com.pahanaedu.billing.dao.CashierDAO;
import com.pahanaedu.billing.model.Cashier;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    private final AdminDAO adminDAO = new AdminDAO();


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        boolean authenticated = false;

        if ("admin".equalsIgnoreCase(role)) {
            AdminDAO adminDAO = new AdminDAO();
            Admin admin = adminDAO.authenticate(username, password);
            if (admin != null) {
                authenticated = true;
                request.getSession().setAttribute("user", admin);
                request.getSession().setAttribute("role", "admin");
                response.sendRedirect("Admin.jsp"); // your admin dashboard
            }
        } else if ("cashier".equalsIgnoreCase(role)) {
            // Similar code for cashier authentication using CashierDAO
        } else if ("customer".equalsIgnoreCase(role)) {
            // Similar code for customer authentication using CustomerDAO
        }

        if (!authenticated) {
            request.setAttribute("loginError", "Invalid username/password/role");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}



