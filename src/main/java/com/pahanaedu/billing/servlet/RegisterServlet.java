package com.pahanaedu.billing.servlet;

import com.pahanaedu.billing.dao.AdminDAO;
import com.pahanaedu.billing.model.Admin;
import com.pahanaedu.billing.model.Cashier;

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
        String email = request.getParameter("email");
        boolean success = false;

        // ✅ 1. Validate duplicate email (DB check)
        if (AdminDAO.emailExists(email)) {
            request.setAttribute("loginError", "This email is already registered. Please use another one.");
            request.getRequestDispatcher("Register.jsp").forward(request, response);
            return; // stop execution if email exists
        }

        // 2. Register user
        if ("cashier".equalsIgnoreCase(role)) {
            Cashier cashier = new Cashier();
            cashier.setUsername(request.getParameter("username"));
            cashier.setPassword(request.getParameter("password"));
            cashier.setName(request.getParameter("name"));
            cashier.setEmail(email);
            cashier.setStatus("active");

            success = AdminDAO.addCashier(cashier);

        } else if ("admin".equalsIgnoreCase(role)) {
            Admin admin = new Admin();
            admin.setUsername(request.getParameter("username"));
            admin.setPassword(request.getParameter("password"));
            admin.setName(request.getParameter("name"));
            admin.setEmail(email);

            AdminDAO dao = new AdminDAO();
            success = dao.addAdmin(admin);
        }

        // 3. Handle result
        if (success) {
            HttpSession session = request.getSession();
            session.setAttribute("loginSuccess", "Registration successful! Please log in.");
            response.sendRedirect("index.jsp");
        } else {
            request.setAttribute("loginError", "Registration failed. Please try again.");
            request.getRequestDispatcher("Register.jsp").forward(request, response);
        }
    }
}