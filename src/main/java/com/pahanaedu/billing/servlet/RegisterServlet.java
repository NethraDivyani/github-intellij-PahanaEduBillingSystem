package com.pahanaedu.billing.servlet;

import com.pahanaedu.billing.dao.AdminDAO;
import com.pahanaedu.billing.model.Admin;
import com.pahanaedu.billing.model.Cashier;
import com.pahanaedu.billing.util.UserFactory;

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
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String name = request.getParameter("name");

        boolean success = false;
        AdminDAO dao = new AdminDAO(); // create a DAO instance

        // 1. Validate duplicate email (DB check)
        if (dao.emailExists(email)) { // call via instance
            request.setAttribute("loginError", "This email is already registered. Please use another one.");
            request.getRequestDispatcher("Register.jsp").forward(request, response);
            return;
        }

        // 2. Create user using factory
        Object user = UserFactory.createUser(role, username, password, name, email);

        // 3. Save user using DAO
        if (user instanceof Admin) {
            success = dao.addAdmin((Admin) user); // instance method
        } else if (user instanceof Cashier) {
            success = dao.addCashier((Cashier) user); // instance method
        }

        // 4. Handle result
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
