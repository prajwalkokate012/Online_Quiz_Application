package com.example.online_quiz_application.model;

import java.util.List;

public class Quiz {
    private int id;
    private String title;
    private String description;
    private List<Question> questions;  // List to store questions for this quiz
    private String category;  // Added category field

    // Default constructor
    public Quiz() {}

    // Constructor with parameters
    public Quiz(int id, String title, String description, String category) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;  // Initialize category
    }

    // Getters and setters for other fields

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter and setter for category
    public String getCategory() {
        return category;  // Returns the category of the quiz
    }

    public void setCategory(String category) {
        this.category = category;  // Sets the category of the quiz
    }

    // Getter and setter for questions
    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
