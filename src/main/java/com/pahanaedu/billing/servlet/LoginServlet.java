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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        System.out.println("Authenticating user: " + username + " with role: " + role);

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
                    session.setAttribute("loginSuccess", "Welcome, " + admin.getName() + "!");
                    response.sendRedirect("Admin.jsp");
                    return;
                }
                break;

            case "cashier":
                CashierDAO cashierDAO = new CashierDAO();
                Cashier cashier = null;
                try {
                    cashier = cashierDAO.authenticate(username, password);
                } catch (Exception e) {
                    e.printStackTrace();
                    request.setAttribute("loginError", "System error occurred. Please try again later.");
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                    return;
                }

                if (cashier != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("user", cashier);
                    session.setAttribute("role", "cashier");
                    session.setAttribute("loginSuccess", "Welcome, " + cashier.getName() + "!");
                    response.sendRedirect("Cashier.jsp");  // Your cashier dashboard page
                    return;
                }
                // Authentication failed
                request.setAttribute("loginError", "Invalid username, password, or role.");
                request.getRequestDispatcher("index.jsp").forward(request, response);
                
                break;

            case "customer":
                // TODO: Implement Customer login logic
                break;

            default:
                request.setAttribute("loginError", "Invalid role specified.");
                request.getRequestDispatcher("index.jsp").forward(request, response);
                return;
        }

        // If authentication failed
        request.setAttribute("loginError", "Invalid username, password, or role.");
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
