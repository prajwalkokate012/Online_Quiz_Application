package com.example.online_quiz_application.dao;

import java.sql.*;
import java.util.*;

import com.example.online_quiz_application.model.Quiz;

public class QuizDAO {

    private Connection connection;

    // Constructor to initialize the database connection
    public QuizDAO() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quiz_app", "root", "root");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Create a new quiz and return its ID
    public int createQuiz(Quiz quiz) {
        String query = "INSERT INTO quizzes (title, description, category) VALUES (?, ?, ?)";
        int quizId = -1; // Default ID to indicate failure

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, quiz.getTitle());
            statement.setString(2, quiz.getDescription());
            statement.setString(3, quiz.getCategory());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet keys = statement.getGeneratedKeys();
                if (keys.next()) {
                    quizId = keys.getInt(1); // Get the generated ID
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quizId; // Return the quiz ID or -1 if creation failed
    }

    // Get a quiz by its ID
    public Quiz getQuizById(int id) {
        String query = "SELECT * FROM quizzes WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String category = resultSet.getString("category");
                return new Quiz(id, title, description, category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get all quizzes
    public List<Quiz> getAllQuizzes() {
        List<Quiz> quizzes = new ArrayList<>();
        String query = "SELECT * FROM quizzes";

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String category = resultSet.getString("category");
                quizzes.add(new Quiz(id, title, description, category));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quizzes;
    }

    // Update a quiz
    public boolean updateQuiz(Quiz quiz) {
        String query = "UPDATE quizzes SET title = ?, description = ?, category = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, quiz.getTitle());
            statement.setString(2, quiz.getDescription());
            statement.setString(3, quiz.getCategory());
            statement.setInt(4, quiz.getId());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0; // Return true if the quiz was successfully updated
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

 // Delete a quiz by its ID
    public boolean deleteQuiz(int quizId) {
        String deleteLeaderboardQuery = "DELETE FROM leaderboard WHERE quiz_id = ?";
        String deleteQuizQuery = "DELETE FROM quizzes WHERE id = ?";

        try (
            PreparedStatement deleteLeaderboardStmt = connection.prepareStatement(deleteLeaderboardQuery);
            PreparedStatement deleteQuizStmt = connection.prepareStatement(deleteQuizQuery)
        ) {
            // Step 1: Delete entries from leaderboard table
            deleteLeaderboardStmt.setInt(1, quizId);
            deleteLeaderboardStmt.executeUpdate();

            // Step 2: Delete the quiz
            deleteQuizStmt.setInt(1, quizId);
            int rowsAffected = deleteQuizStmt.executeUpdate();

            return rowsAffected > 0; // Return true if the quiz was successfully deleted
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // Get quizzes by category
    public List<Quiz> getQuizzesByCategory(String category) {
        List<Quiz> quizzes = new ArrayList<>();
        String query = "SELECT * FROM quizzes WHERE category = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, category);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                quizzes.add(new Quiz(id, title, description, category));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quizzes;
    }

    // Get quizzes sorted by title
    public List<Quiz> getQuizzesSortedByTitle() {
        List<Quiz> quizzes = new ArrayList<>();
        String query = "SELECT * FROM quizzes ORDER BY title ASC";

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String category = resultSet.getString("category");
                quizzes.add(new Quiz(id, title, description, category));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quizzes;
    }
}
