package com.pahanaedu.billing.servlet;

import com.pahanaedu.billing.model.Item;
import com.pahanaedu.billing.util.DBConnection;
import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/GetItemsServlet")
public class GetItemsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Item> items = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM item ORDER BY itemId ASC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Item item = new Item();
                item.setItemId(rs.getInt("itemId"));
                item.setName(rs.getString("name"));
                item.setDescription(rs.getString("description"));
                item.setPrice(rs.getDouble("price"));
                item.setQuantityAvailable(rs.getInt("quantityAvailable"));
                item.setCategory(rs.getString("category"));

                items.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        resp.setContentType("application/json");
        resp.getWriter().write(new Gson().toJson(items));
    }
}
