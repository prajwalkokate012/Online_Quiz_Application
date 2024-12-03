package com.example.online_quiz_application.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {

    // Hash the password
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    // Verify the password
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
