package com.pahanaedu.billing.servlet;

import com.pahanaedu.billing.util.DBConnection;
import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Map;

@WebServlet("/SaveBillServlet")
public class SaveBillServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        Gson gson = new Gson();
        Map<String, Object> billData = gson.fromJson(reader, Map.class);

        String accountNumber = (String) billData.get("accountNumber");
        double totalAmount = ((Number) billData.get("totalAmount")).doubleValue();
        double discount = ((Number) billData.get("discount")).doubleValue();
        double finalAmount = ((Number) billData.get("finalAmount")).doubleValue();
        List<Map<String, Object>> items = (List<Map<String, Object>>) billData.get("items");

        try (Connection conn = DBConnection.getConnection()) {
            // Insert into bill table
            String sqlBill = "INSERT INTO bill(accountNumber, totalAmount, discount, finalAmount) VALUES (?, ?, ?, ?)";
            PreparedStatement psBill = conn.prepareStatement(sqlBill, Statement.RETURN_GENERATED_KEYS);
            psBill.setString(1, accountNumber);
            psBill.setDouble(2, totalAmount);
            psBill.setDouble(3, discount);
            psBill.setDouble(4, finalAmount);
            psBill.executeUpdate();

            ResultSet rs = psBill.getGeneratedKeys();
            int billId = 0;
            if (rs.next()) billId = rs.getInt(1);

            // Insert bill items
            String sqlItem = "INSERT INTO bill_item(billId, itemName, itemPrice) VALUES (?, ?, ?)";
            PreparedStatement psItem = conn.prepareStatement(sqlItem);
            for (Map<String, Object> item : items) {
                psItem.setInt(1, billId);
                psItem.setString(2, (String) item.get("name"));
                psItem.setDouble(3, ((Number) item.get("price")).doubleValue());
                psItem.addBatch();
            }
            psItem.executeBatch();

            resp.setContentType("application/json");
            resp.getWriter().write("{\"status\":\"success\"}");
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("{\"status\":\"error\"}");
        }
    }
}
