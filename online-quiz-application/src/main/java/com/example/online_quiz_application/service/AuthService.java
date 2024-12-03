package com.example.online_quiz_application.service;

import com.example.online_quiz_application.dao.UserDAO;
import com.example.online_quiz_application.model.User;
import com.example.online_quiz_application.util.PasswordUtils;

import java.sql.SQLException;

public class AuthService {

    private UserDAO userDAO;

    public AuthService() {
        this.userDAO = new UserDAO();
    }

    // User registration method
    public boolean registerUser(String username, String password) throws SQLException {
        if (userDAO.getUserByUsername(username) != null) {
            return false;  // Username already exists
        }

        // Hash the password before storing it
        String hashedPassword = PasswordUtils.hashPassword(password);
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(hashedPassword);

        return userDAO.register(newUser); // Save the new user in the database
    }

    // User login method
    public boolean loginUser(String username, String password) throws SQLException {
        User user = userDAO.getUserByUsername(username);
        if (user != null && PasswordUtils.checkPassword(password, user.getPassword())) {
            return true; // Successful login
        }
        return false; // Invalid username or password
    }

    // Check if the user exists by username
    public boolean isUserExist(String username) throws SQLException {
        return userDAO.getUserByUsername(username) != null;
    }
}
