package com.pahanaedu.billing.servlet;

import com.pahanaedu.billing.dao.AdminDAO;
import com.pahanaedu.billing.model.Admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    private final AdminDAO adminDAO = new AdminDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        if (username == null || password == null || role == null ||
                username.isEmpty() || password.isEmpty() || role.isEmpty()) {
            request.setAttribute("loginError", "All fields are required.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        HttpSession session = request.getSession();
        boolean isAuthenticated = false;

        switch (role.toLowerCase()) {
            case "admin":
                Admin admin = adminDAO.authenticate(username, password);
                if (admin != null) {
                    session.setAttribute("user", admin);
                    session.setAttribute("role", "admin");
                    session.setAttribute("loginSuccess", "Welcome, " + admin.getName() + "!");
                    response.sendRedirect("Admin.jsp");
                    isAuthenticated = true;
                    return; // <-- Stop further processing after redirect
                }
                break;

            case "cashier":
                // cashier login logic here
                break;

            case "customer":
                // customer login logic here
                break;
        }

        if (!isAuthenticated) {
            request.setAttribute("loginError", "Invalid username, password, or role.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}
