package com.pahanaedu.billing.model;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasher {
    public static void main(String[] args) {
        String plainPassword = "admin123"; // Replace with your desired password
        String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
        System.out.println("Hashed Password: " + hashedPassword);
    }
}

