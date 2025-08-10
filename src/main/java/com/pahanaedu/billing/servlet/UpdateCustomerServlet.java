package com.pahanaedu.billing.servlet;

import com.pahanaedu.billing.util.DBConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/UpdateCustomerServlet")
public class UpdateCustomerServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accountNo = req.getParameter("accountNo");
        String custName = req.getParameter("custName");
        String address = req.getParameter("address");
        String telephone = req.getParameter("telephone");
        String email = req.getParameter("email");

        String sql = "UPDATE customer SET cust_name = ?, address = ?, telephone = ?, email = ? WHERE account_no = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, custName);
            ps.setString(2, address);
            ps.setString(3, telephone);
            ps.setString(4, email);
            ps.setString(5, accountNo);

            int updated = ps.executeUpdate();

            if (updated > 0) {
                resp.getWriter().write("success");
            } else {
                resp.getWriter().write("fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("error");
        }
    }
}
