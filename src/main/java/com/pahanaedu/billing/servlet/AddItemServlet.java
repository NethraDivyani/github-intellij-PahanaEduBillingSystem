package com.pahanaedu.billing.servlet;

import com.pahanaedu.billing.util.DBConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/AddItemServlet")
public class AddItemServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/plain;charset=UTF-8");

        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String priceStr = req.getParameter("price");
        String quantityStr = req.getParameter("quantityAvailable");
        String category = req.getParameter("category");

        double price = 0;
        int quantity = 0;

        try {
            price = Double.parseDouble(priceStr);
            quantity = Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            resp.getWriter().write("error: invalid number format");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {

            String sql = "INSERT INTO item (name, description, price, quantityAvailable, category) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, description);
            ps.setDouble(3, price);
            ps.setInt(4, quantity);
            ps.setString(5, category);

            int row = ps.executeUpdate();
            if (row > 0) {
                resp.getWriter().write("success");
            } else {
                resp.getWriter().write("error: failed to insert");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("error: " + e.getMessage());
        }
    }
}
