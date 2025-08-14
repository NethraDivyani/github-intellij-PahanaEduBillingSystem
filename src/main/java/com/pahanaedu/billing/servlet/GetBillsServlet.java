package com.pahanaedu.billing.servlet;

import com.pahanaedu.billing.util.DBConnection;
import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/GetBillsServlet")
public class GetBillsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Map<String, Object>> bills = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM bill ORDER BY billDate DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int billId = rs.getInt("billId");
                Map<String, Object> bill = new HashMap<>();
                bill.put("billId", billId);
                bill.put("accountNumber", rs.getString("accountNumber"));
                bill.put("totalAmount", rs.getDouble("totalAmount"));
                bill.put("discount", rs.getDouble("discount"));
                bill.put("finalAmount", rs.getDouble("finalAmount"));
                bill.put("billDate", rs.getTimestamp("billDate").toString());

                // Get items for this bill
                String sqlItems = "SELECT itemName, itemPrice FROM bill_item WHERE billId=?";
                PreparedStatement psItems = conn.prepareStatement(sqlItems);
                psItems.setInt(1, billId);
                ResultSet rsItems = psItems.executeQuery();

                List<Map<String, Object>> items = new ArrayList<>();
                while (rsItems.next()) {
                    Map<String, Object> item = new HashMap<>();
                    item.put("name", rsItems.getString("itemName"));
                    item.put("price", rsItems.getDouble("itemPrice"));
                    items.add(item);
                }
                bill.put("items", items);

                bills.add(bill);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        resp.setContentType("application/json");
        resp.getWriter().write(new Gson().toJson(bills));
    }
}
