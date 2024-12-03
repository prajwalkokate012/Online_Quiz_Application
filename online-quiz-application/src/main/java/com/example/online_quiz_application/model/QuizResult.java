package com.example.online_quiz_application.model;

public class QuizResult {
    private int id;
    private int userId;
    private int quizId;
    private int score;
    private String quizName;

    // Default no-argument constructor
    public QuizResult() {
    }

    // Parameterized constructor
    public QuizResult(int userId, int quizId, int score) {
        this.userId = userId;
        this.quizId = quizId;
        this.score = score;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int string) {
        this.quizId = string;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

	public String getQuizName() {
		return quizName;
	}

	public void setQuizName(String quizName) {
		this.quizName = quizName;
	}
}
