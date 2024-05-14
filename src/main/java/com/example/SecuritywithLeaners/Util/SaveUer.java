package com.example.SecuritywithLeaners.Util;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Transactional
@Service
public class SaveUer {
    //Random Password Generator
    public static String generateRandomPassword(String username) {
        String passwordSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(64);

        // Add the username to the password set
        passwordSet += username;

        // Generate password characters randomly from the password set
        for (int i = 0; i < 64; i++) {
            int rand = random.nextInt(passwordSet.length());
            password.append(passwordSet.charAt(rand));
        }
        return password.toString();
    }
}
