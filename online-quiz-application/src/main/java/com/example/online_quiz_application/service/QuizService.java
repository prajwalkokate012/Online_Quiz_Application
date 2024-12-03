package com.example.online_quiz_application.service;

import com.example.online_quiz_application.dao.QuizDAO;
import com.example.online_quiz_application.dao.QuestionDAO;
import com.example.online_quiz_application.model.Quiz;
import com.example.online_quiz_application.model.Question;

import java.sql.SQLException;
import java.util.List;

public class QuizService {

    private QuizDAO quizDAO;
    private QuestionDAO questionDAO;

    // Constructor to initialize the QuizDAO and QuestionDAO
    public QuizService() {
        this.quizDAO = new QuizDAO();
        this.questionDAO = new QuestionDAO();
    }

    // Create a new quiz
    public int createQuiz(Quiz quiz) throws SQLException {
        // Save quiz and return its generated ID
        return quizDAO.createQuiz(quiz);
    }

    // Add questions to a quiz
    public boolean addQuestionsToQuiz(int quizId, List<Question> questions) throws SQLException {
        // Associate each question with the quiz and save it
        for (Question question : questions) {
            question.setQuizId(quizId);
            boolean result = questionDAO.createQuestion(question);
            if (!result) {
                return false; // If any question fails to insert, return false
            }
        }
        return true; // If all questions are inserted successfully
    }

    // Get a quiz by its ID
    public Quiz getQuizById(int quizId) throws SQLException {
        Quiz quiz = quizDAO.getQuizById(quizId);
        if (quiz != null) {
            List<Question> questions = questionDAO.getQuestionsByQuizId(quizId);
            quiz.setQuestions(questions); // Set the questions for the quiz
        }
        return quiz; // Return the quiz with its questions
    }

    // Get all quizzes
    public List<Quiz> getAllQuizzes() throws SQLException {
        return quizDAO.getAllQuizzes();
    }

    // Update a quiz
    public boolean updateQuiz(Quiz quiz) throws SQLException {
        return quizDAO.updateQuiz(quiz); // Update quiz details in the database
    }

    // Delete a quiz by its ID
    public boolean deleteQuiz(int quizId) throws SQLException {
        return quizDAO.deleteQuiz(quizId); // Delete the quiz and associated questions
    }
}
