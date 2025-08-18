package com.pahanaedu.billing.util;

import com.pahanaedu.billing.model.Admin;
import com.pahanaedu.billing.model.Cashier;

public class UserFactory {

    // Factory method to create users based on role
    public static Object createUser(String role, String username, String password, String name, String email) {
        if (role == null) return null;

        switch (role.toLowerCase()) {
            case "admin":
                Admin admin = new Admin();
                admin.setUsername(username);
                admin.setPassword(password);
                admin.setName(name);
                admin.setEmail(email);
                return admin;

            case "cashier":
                Cashier cashier = new Cashier();
                cashier.setUsername(username);
                cashier.setPassword(password);
                cashier.setName(name);
                cashier.setEmail(email);
                cashier.setStatus("active");
                return cashier;

            default:
                return null;
        }
    }
}
