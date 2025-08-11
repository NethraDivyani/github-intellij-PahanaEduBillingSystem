package com.pahanaedu.billing.servlet;

import com.pahanaedu.billing.util.DBConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;

@WebServlet("/GetAllCustomersServlet")
public class GetAllCustomersServlet extends HttpServlet {

    private static final Gson gson = new Gson();

    private class Customer {
        String accountNumber, name, address, telephone, email;
        Customer(String a, String n, String ad, String t, String e) {
            this.accountNumber = a;
            this.name = n;
            this.address = ad;
            this.telephone = t;
            this.email = e;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Customer> customers = new ArrayList<>();

        String sql = "SELECT account_no, cust_name, address, telephone, email FROM customer";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                customers.add(new Customer(
                        rs.getString("account_no"),
                        rs.getString("cust_name"),
                        rs.getString("address"),
                        rs.getString("telephone"),
                        rs.getString("email")
                ));
            }

            String json = new Gson().toJson(customers);

            resp.setContentType("application/json");
            resp.getWriter().write(json);

        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
            resp.getWriter().write("[]");
        }
    }
}
