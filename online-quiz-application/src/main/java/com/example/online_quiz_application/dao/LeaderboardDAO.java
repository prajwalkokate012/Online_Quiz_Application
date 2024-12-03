package com.example.online_quiz_application.dao;

import com.example.online_quiz_application.model.LeaderboardEntry;
import com.example.online_quiz_application.model.QuizResult;
import com.example.online_quiz_application.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LeaderboardDAO {

    // Add a new leaderboard entry
    public boolean addLeaderboardEntry(LeaderboardEntry entry) {
        String query = "INSERT INTO leaderboard (quiz_id, username, score) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, entry.getQuizId());
            stmt.setString(2, entry.getUsername());
            stmt.setInt(3, entry.getScore());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update or insert leaderboard entry
    public boolean updateLeaderboardEntry(LeaderboardEntry entry) {
        String checkQuery = "SELECT 1 FROM leaderboard WHERE quiz_id = ? AND username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
            checkStmt.setInt(1, entry.getQuizId());
            checkStmt.setString(2, entry.getUsername());
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next()) {
                    // Update existing entry
                    String updateQuery = "UPDATE leaderboard SET score = ? WHERE quiz_id = ? AND username = ?";
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                        updateStmt.setInt(1, entry.getScore());
                        updateStmt.setInt(2, entry.getQuizId());
                        updateStmt.setString(3, entry.getUsername());
                        return updateStmt.executeUpdate() > 0;
                    }
                } else {
                    // Insert a new entry
                    return addLeaderboardEntry(entry);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get leaderboard for a specific quiz
    public List<LeaderboardEntry> getLeaderboardForQuiz(int quizId) {
        List<LeaderboardEntry> leaderboard = new ArrayList<>();
        String query = "SELECT quiz_id, username, score FROM leaderboard WHERE quiz_id = ? ORDER BY score DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, quizId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    leaderboard.add(mapResultSetToEntry(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leaderboard;
    }

    // Get the overall leaderboard
    public List<LeaderboardEntry> getOverallLeaderboard() {
        List<LeaderboardEntry> leaderboard = new ArrayList<>();
        String query = "SELECT quiz_id, username, score FROM leaderboard ORDER BY score DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                leaderboard.add(mapResultSetToEntry(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leaderboard;
    }

    // Get leaderboard for a specific user
    public List<LeaderboardEntry> getLeaderboardForUser(String username) {
        List<LeaderboardEntry> leaderboard = new ArrayList<>();
        String query = "SELECT quiz_id, username, score FROM leaderboard WHERE username = ? ORDER BY quiz_id DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    leaderboard.add(mapResultSetToEntry(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leaderboard;
    }

    // Get the top N scorers for a specific quiz
    public List<LeaderboardEntry> getTopNForQuiz(int quizId, int n) {
        List<LeaderboardEntry> leaderboard = new ArrayList<>();
        String query = "SELECT quiz_id, username, score FROM leaderboard WHERE quiz_id = ? ORDER BY score DESC LIMIT ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, quizId);
            stmt.setInt(2, n);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    leaderboard.add(mapResultSetToEntry(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leaderboard;
    }

    // Get the top N overall scorers
    public List<LeaderboardEntry> getTopNOverall(int n) {
        List<LeaderboardEntry> leaderboard = new ArrayList<>();
        String query = "SELECT quiz_id, username, score FROM leaderboard ORDER BY score DESC LIMIT ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, n);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    leaderboard.add(mapResultSetToEntry(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leaderboard;
    }

    // Utility method to map a ResultSet row to a LeaderboardEntry object
    private LeaderboardEntry mapResultSetToEntry(ResultSet rs) throws SQLException {
        LeaderboardEntry entry = new LeaderboardEntry();
        entry.setQuizId(rs.getInt("quiz_id"));
        entry.setUsername(rs.getString("username"));
        entry.setScore(rs.getInt("score"));
        return entry;
    }

    public List<QuizResult> getUserResults(int userId) {
        List<QuizResult> results = new ArrayList<>();
        String query = "SELECT qr.quiz_id, qr.user_id, qr.score, q.title AS quiz_name " +
                       "FROM quiz_results qr " +
                       "JOIN quizzes q ON qr.quiz_id = q.id " +
                       "WHERE qr.user_id = ? " +
                       "ORDER BY qr.score DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    QuizResult result = new QuizResult();
                    result.setQuizId(rs.getInt("quiz_id"));
                    result.setUserId(rs.getInt("user_id"));
                    result.setScore(rs.getInt("score"));
                    result.setQuizName(rs.getString("quiz_name")); // Assuming `QuizResult` has this field
                    results.add(result);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }


}

