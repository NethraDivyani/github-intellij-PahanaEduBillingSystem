package com.pahanaedu.billing.servlet;

import com.pahanaedu.billing.dao.ItemDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/DeleteItemServlet")
public class DeleteItemServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int itemId = Integer.parseInt(request.getParameter("itemId"));

            ItemDAO itemDAO = new ItemDAO();
            boolean success = itemDAO.deleteItem(itemId);

            if(success) {
                response.getWriter().print("success");
            } else {
                response.getWriter().print("fail");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().print("error");
        }
    }
}
