package com.pahanaedu.billing.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Invalidate session if it exists
        if (request.getSession(false) != null) {
            request.getSession().invalidate();
        }
        // Redirect to main page after logout
        response.sendRedirect("index.jsp");
    }
}
