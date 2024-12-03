package com.example.online_quiz_application.util;

import org.mindrot.jbcrypt.BCrypt;

public class SecurityUtils {

    // Hashes the password using BCrypt
    public static String hashPassword(String password) {
        // Generate a salt and hash the password
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(password, salt);
    }

    // Verifies if the provided password matches the hashed password
    public static boolean verifyPassword(String password, String storedHash) {
        // Compare the provided password with the stored hash
        return BCrypt.checkpw(password, storedHash);
    }
}
