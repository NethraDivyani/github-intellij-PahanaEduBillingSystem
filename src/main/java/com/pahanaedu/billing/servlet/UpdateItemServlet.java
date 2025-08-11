package com.pahanaedu.billing.servlet;

import com.pahanaedu.billing.util.DBConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/UpdateItemServlet")
public class UpdateItemServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("itemId");
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String priceStr = req.getParameter("price");
        String quantityStr = req.getParameter("quantityAvailable");
        String category = req.getParameter("category");

        int itemId = 0;
        double price = 0;
        int quantity = 0;

        try {
            itemId = Integer.parseInt(idStr);
            price = Double.parseDouble(priceStr);
            quantity = Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            resp.getWriter().write("error");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE item SET name=?, description=?, price=?, quantity_available=?, category=? WHERE itemId=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, description);
            ps.setDouble(3, price);
            ps.setInt(4, quantity);
            ps.setString(5, category);
            ps.setInt(6, itemId);

            int row = ps.executeUpdate();
            if (row > 0) {
                resp.getWriter().write("success");
            } else {
                resp.getWriter().write("error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("error");
        }
    }
}
