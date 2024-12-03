package com.example.online_quiz_application.model;

public class LeaderboardEntry {
    private int quizId;
    private String username;
    private int score;

    // Getters and setters
    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    // Override toString for easier display
    @Override
    public String toString() {
        return "LeaderboardEntry{" +
                "quizId=" + quizId +
                ", username='" + username + '\'' +
                ", score=" + score +
                '}';
    }
}
