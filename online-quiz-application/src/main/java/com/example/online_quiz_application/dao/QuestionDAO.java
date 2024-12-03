package com.example.online_quiz_application.dao;

import java.sql.*;
import java.util.*;

import com.example.online_quiz_application.model.Question;
import com.example.online_quiz_application.util.DBConnection;

public class QuestionDAO {

    // Create a new question
    public boolean createQuestion(Question question) {
        String sql = "INSERT INTO questions (quiz_id, question_text, option_a, option_b, option_c, option_d, correct_answer) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, question.getQuizId());
            preparedStatement.setString(2, question.getQuestionText());
            preparedStatement.setString(3, question.getOptionA());
            preparedStatement.setString(4, question.getOptionB());
            preparedStatement.setString(5, question.getOptionC());
            preparedStatement.setString(6, question.getOptionD());
            preparedStatement.setString(7, question.getCorrectAnswer());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;  // Return true if the question was successfully added
        } catch (SQLException e) {
            System.out.println("Error creating question: " + e.getMessage());
            return false;  // Return false if there was an error
        }
    }

    // Update an existing question
    public boolean updateQuestion(Question question) {
        String sql = "UPDATE questions SET question_text = ?, option_a = ?, option_b = ?, option_c = ?, option_d = ?, correct_answer = ? " +
                     "WHERE quiz_id = ? AND question_text = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, question.getQuestionText());
            preparedStatement.setString(2, question.getOptionA());
            preparedStatement.setString(3, question.getOptionB());
            preparedStatement.setString(4, question.getOptionC());
            preparedStatement.setString(5, question.getOptionD());
            preparedStatement.setString(6, question.getCorrectAnswer());
            preparedStatement.setInt(7, question.getQuizId());
            preparedStatement.setString(8, question.getQuestionText());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;  // Return true if update is successful
        } catch (SQLException e) {
            System.out.println("Error updating question: " + e.getMessage());
            return false;  // Return false if there is an error
        }
    }

    // Delete a question
    public boolean deleteQuestion(int quizId, String questionText) {
        String sql = "DELETE FROM questions WHERE quiz_id = ? AND question_text = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, quizId);
            preparedStatement.setString(2, questionText);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;  // Return true if delete is successful
        } catch (SQLException e) {
            System.out.println("Error deleting question: " + e.getMessage());
            return false;  // Return false if there is an error
        }
    }

    // Get questions by quiz ID
    public List<Question> getQuestionsByQuizId(int quizId) {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT * FROM questions WHERE quiz_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, quizId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Question question = new Question();
                question.setQuizId(resultSet.getInt("quiz_id"));
                question.setQuestionText(resultSet.getString("question_text"));
                question.setOptionA(resultSet.getString("option_a"));
                question.setOptionB(resultSet.getString("option_b"));
                question.setOptionC(resultSet.getString("option_c"));
                question.setOptionD(resultSet.getString("option_d"));
                question.setCorrectAnswer(resultSet.getString("correct_answer"));
                questions.add(question);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving questions: " + e.getMessage());
        }

        return questions;  // Return the list of questions
    }

    // Get a question by its ID
    public Question getQuestionById(int questionId) {
        String sql = "SELECT * FROM questions WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, questionId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Question question = new Question();
                question.setQuizId(resultSet.getInt("quiz_id"));
                question.setQuestionText(resultSet.getString("question_text"));
                question.setOptionA(resultSet.getString("option_a"));
                question.setOptionB(resultSet.getString("option_b"));
                question.setOptionC(resultSet.getString("option_c"));
                question.setOptionD(resultSet.getString("option_d"));
                question.setCorrectAnswer(resultSet.getString("correct_answer"));
                return question;
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving question: " + e.getMessage());
        }

        return null;  // Return null if no question found
    }

    // Get all questions in the database
    public List<Question> getAllQuestions() {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT * FROM questions";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Question question = new Question();
                question.setQuizId(resultSet.getInt("quiz_id"));
                question.setQuestionText(resultSet.getString("question_text"));
                question.setOptionA(resultSet.getString("option_a"));
                question.setOptionB(resultSet.getString("option_b"));
                question.setOptionC(resultSet.getString("option_c"));
                question.setOptionD(resultSet.getString("option_d"));
                question.setCorrectAnswer(resultSet.getString("correct_answer"));
                questions.add(question);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving questions: " + e.getMessage());
        }

        return questions;  // Return the list of all questions
    }
}
