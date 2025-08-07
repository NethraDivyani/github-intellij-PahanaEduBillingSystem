package com.pahanaedu.billing.servlet;

import com.pahanaedu.billing.model.Cashier;
import com.pahanaedu.billing.dao.AdminDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/AddCashierServlet")
public class AddCashierServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get form parameters
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        // Validate input (optional: you can add more complex validation)
        if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty() ||
                name == null || name.trim().isEmpty() ||
                email == null || email.trim().isEmpty()) {

            request.setAttribute("error", "All input fields are required.");
            request.getRequestDispatcher("addCashier.jsp").forward(request, response);
            return;
        }

        // Create Cashier object
        Cashier newCashier = new Cashier();
        newCashier.setUsername(username);
        newCashier.setPassword(password);  // raw password, will be hashed in DAO
        newCashier.setName(name);
        newCashier.setEmail(email);

        // Call DAO to add the cashier
        AdminDAO adminDAO = new AdminDAO();
        boolean added = adminDAO.addCashier(newCashier);

        if (added) {
            request.setAttribute("message", "Cashier added successfully.");
        } else {
            request.setAttribute("error", "Failed to add cashier. Username/email may already exist.");
        }

        // Forward to a result JSP page to show success or error messages
        request.getRequestDispatcher("addCashierResult.jsp").forward(request, response);
    }
}
