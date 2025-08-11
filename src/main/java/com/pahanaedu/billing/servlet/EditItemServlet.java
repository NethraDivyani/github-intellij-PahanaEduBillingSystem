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
            String itemName = request.getParameter("itemName");
            String itemDescription = request.getParameter("itemDescription");
            double itemPrice = Double.parseDouble(request.getParameter("itemPrice"));
            int itemQuantity = Integer.parseInt(request.getParameter("itemQuantity"));
            String itemCategory = request.getParameter("itemCategory");

            // Create Item object and set all fields
            Item item = new Item();
            item.setItemId(itemId);
            item.setName(itemName);
            item.setDescription(itemDescription);
            item.setPrice(itemPrice);
            item.setQuantityAvailable(itemQuantity);
            item.setCategory(itemCategory);

            // Create ItemDAO instance and call updateItem (instance method)
            ItemDAO itemDAO = new ItemDAO();
            boolean success = itemDAO.updateItem(item);

            if (success) {
                response.sendRedirect("itemList.jsp?status=updated");
            } else {
                response.sendRedirect("editItem.jsp?status=failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("editItem.jsp?status=error");
        }
    }
}
