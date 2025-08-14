package com.pahanaedu.billing.servlet;

import com.google.gson.Gson;
import com.pahanaedu.billing.dao.CustomerDAO;
import com.pahanaedu.billing.model.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/GetCustomerServlet")
public class GetCustomerServlet extends HttpServlet {

    private static final Gson gson = new Gson();

    // Inner class to return only safe customer data
    private class CustomerResponse {
        String accountNumber, name, address, telephone, email, status;

        CustomerResponse(String accountNumber, String name, String address,
                         String telephone, String email, String status) {
            this.accountNumber = accountNumber;
            this.name = name;
            this.address = address;
            this.telephone = telephone;
            this.email = email;
            this.status = status;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accountNo = req.getParameter("accountNo");
        CustomerResponse customerResp = null;

        try {
            CustomerDAO dao = new CustomerDAO();
            Customer c = dao.getCustomerByAccountNo(accountNo);

            if (c != null) {
                customerResp = new CustomerResponse(
                        c.getAccountNo(),
                        c.getCustName(),
                        c.getAddress(),
                        c.getTelephone(),
                        c.getEmail(),
                        c.getStatus()
                );
            }

            String json = gson.toJson(customerResp);
            resp.setContentType("application/json");
            resp.getWriter().write(json);

        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
            resp.getWriter().write("{}");
        }
    }
}
