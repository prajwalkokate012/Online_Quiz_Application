package com.example.online_quiz_application.service;

import com.example.online_quiz_application.dao.LeaderboardDAO;
import com.example.online_quiz_application.model.LeaderboardEntry;

import java.sql.SQLException;
import java.util.List;

public class LeaderboardService {

    private LeaderboardDAO leaderboardDAO;

    // Constructor to initialize the LeaderboardDAO
    public LeaderboardService() {
        this.leaderboardDAO = new LeaderboardDAO();
    }

    // Add a new score entry to the leaderboard
    public boolean addScore(int quizId, String username, int score) throws SQLException {
        LeaderboardEntry entry = new LeaderboardEntry();
        entry.setQuizId(quizId);
        entry.setUsername(username);
        entry.setScore(score);
        return leaderboardDAO.addLeaderboardEntry(entry);
    }

    // Get the leaderboard for a specific quiz
    public List<LeaderboardEntry> getLeaderboardForQuiz(int quizId) throws SQLException {
        return leaderboardDAO.getLeaderboardForQuiz(quizId);
    }

    // Get the overall leaderboard (for all quizzes)
    public List<LeaderboardEntry> getOverallLeaderboard() throws SQLException {
        return leaderboardDAO.getOverallLeaderboard();
    }

    // Get the top N scorers for a specific quiz
    public List<LeaderboardEntry> getTopNForQuiz(int quizId, int n) throws SQLException {
        return leaderboardDAO.getTopNForQuiz(quizId, n);
    }

    // Get the top N overall scorers (from all quizzes)
    public List<LeaderboardEntry> getTopNOverall(int n) throws SQLException {
        return leaderboardDAO.getTopNOverall(n);
    }
}
