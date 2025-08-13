package com.pahanaedu.billing.servlet;

import com.pahanaedu.billing.dao.ItemDAO;
import com.pahanaedu.billing.model.Item;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/EditItemServlet")
public class EditItemServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int itemId = Integer.parseInt(request.getParameter("itemId"));
            String itemName = request.getParameter("name");
            String itemDescription = request.getParameter("description");
            double itemPrice = Double.parseDouble(request.getParameter("price"));
            int itemQuantity = Integer.parseInt(request.getParameter("quantityAvailable"));
            String itemCategory = request.getParameter("category");

            Item item = new Item();
            item.setItemId(itemId);
            item.setName(itemName);
            item.setDescription(itemDescription);
            item.setPrice(itemPrice);
            item.setQuantityAvailable(itemQuantity);
            item.setCategory(itemCategory);

            ItemDAO itemDAO = new ItemDAO();
            boolean success = itemDAO.updateItem(item);

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
