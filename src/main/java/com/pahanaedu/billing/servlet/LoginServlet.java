package com.pahanaedu.billing.servlet;

import com.pahanaedu.billing.dao.AdminDAO;
import com.pahanaedu.billing.model.Admin;
// Similarly import CashierDAO/CustomerDAO and model if you have them

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    private AdminDAO adminDAO = new AdminDAO();
    // private CashierDAO cashierDAO = new CashierDAO();
    // private CustomerDAO customerDAO = new CustomerDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password"); // plain password from form
        String role = request.getParameter("role");

        if (username == null || password == null || role == null ||
                username.isEmpty() || password.isEmpty() || role.isEmpty()) {
            request.setAttribute("loginError", "All fields are required.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        switch (role.toLowerCase()) {
            case "admin":
                Admin admin = adminDAO.authenticate(username, password);
                if (admin != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("user", admin);
                    session.setAttribute("role", "admin");
                    response.sendRedirect("Admin.jsp");
                } else {
                    request.setAttribute("loginError", "Invalid username or password for Admin.");
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
                break;

            case "cashier":
                // Implement similar authenticate method in CashierDAO
                // Cashier cashier = cashierDAO.authenticate(username, password);
                // if (cashier != null) { ... }
                // else { ... }
                // For now, just dummy reject:
                request.setAttribute("loginError", "Cashier login not implemented yet.");
                request.getRequestDispatcher("index.jsp").forward(request, response);
                break;

            case "customer":
                // Implement similar authenticate in CustomerDAO
                // Customer customer = customerDAO.authenticate(username, password);
                // if (customer != null) { ... }
                // else { ... }
                request.setAttribute("loginError", "Customer login not implemented yet.");
                request.getRequestDispatcher("index.jsp").forward(request, response);
                break;

            default:
                request.setAttribute("loginError", "Invalid role selected.");
                request.getRequestDispatcher("index.jsp").forward(request, response);
                break;
        }
    }
}
