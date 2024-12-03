package com.example.online_quiz_application;

import com.example.online_quiz_application.dao.*;
import com.example.online_quiz_application.model.*;
import com.example.online_quiz_application.util.PasswordUtils;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserDAO userDAO = new UserDAO();
        QuizDAO quizDAO = new QuizDAO();
        QuestionDAO questionDAO = new QuestionDAO();
        LeaderboardDAO leaderboardDAO = new LeaderboardDAO();

        User loggedInUser = null;

        while (true) {
            if (loggedInUser == null) {
                // User Authentication Menu
                System.out.println("\nOnline Quiz Application");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                try {
                    switch (choice) {
                    case 1:
                        // Registration
                        System.out.print("Enter username: ");
                        String username = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String password = scanner.nextLine();

                        System.out.print("Enter role (user/admin): ");
                        String role = scanner.nextLine().toLowerCase();

                        // Validate role
                        if (!role.equals("user") && !role.equals("admin")) {
                            System.out.println("Invalid role. Defaulting to 'user'.");
                            role = "user";
                        }

                        User user = new User();
                        user.setUsername(username);
                        user.setPassword(PasswordUtils.hashPassword(password));
                        user.setRole(role); // Assign the chosen role

                        if (userDAO.register(user)) {
                            System.out.println("Registration successful! Role assigned: " + role);
                        } else {
                            System.out.println("Registration failed.");
                        }
                        break;

                        case 2:
                            // Login
                            System.out.print("Enter username: ");
                            String loginUsername = scanner.nextLine();
                            System.out.print("Enter password: ");
                            String loginPassword = scanner.nextLine();

                            User loginUser = userDAO.getUserByUsername(loginUsername);
                            if (loginUser != null && PasswordUtils.checkPassword(loginPassword, loginUser.getPassword())) {
                                loggedInUser = loginUser;
                                System.out.println("Login successful! Welcome, " + loggedInUser.getUsername());
                            } else {
                                System.out.println("Invalid credentials. Please try again.");
                            }
                            break;

                        case 3:
                            // Exit
                            System.out.println("Exiting application. Goodbye!");
                            System.exit(0);

                        default:
                            System.out.println("Invalid choice.");
                    }
                } catch (Exception e) {
                    System.out.println("An error occurred: " + e.getMessage());
                }
            } else {
                // Logged-in User Menu
                System.out.println("\nWelcome, " + loggedInUser.getUsername());

                // Admin or User menu
                if ("admin".equals(loggedInUser.getRole())) {
                    // Admin-specific menu
                    System.out.println("1. Create Quiz");
                    System.out.println("2. Edit Quiz");
                    System.out.println("3. Delete Quiz");
                    System.out.println("4. View Users");
                    System.out.println("5. Logout");
                    System.out.print("Enter your choice: ");
                } else {
                    // User-specific menu
                    System.out.println("1. Take Quiz");
                    System.out.println("2. View Leaderboard");
                    System.out.println("3. View My Past Quizzes");
                    System.out.println("4. Logout");
                    System.out.print("Enter your choice: ");
                }

                int loggedInChoice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                try {
                    switch (loggedInChoice) {
                        case 1:
                            if ("admin".equals(loggedInUser.getRole())) {
                            	System.out.print("Enter quiz title: ");
                                String quizTitle = scanner.nextLine();
                                Quiz quiz = new Quiz();
                                quiz.setTitle(quizTitle);

                                int quizId = quizDAO.createQuiz(quiz);
                                System.out.println("Quiz created successfully with ID: " + quizId);

                                System.out.print("How many questions to add? ");
                                int questionCount = scanner.nextInt();
                                scanner.nextLine(); // Consume newline

                                for (int i = 1; i <= questionCount; i++) {
                                    System.out.println("Enter details for Question " + i);
                                    Question question = new Question();
                                    question.setQuizId(quizId);

                                    System.out.print("Question text: ");
                                    question.setQuestionText(scanner.nextLine());

                                    System.out.print("Option A: ");
                                    question.setOptionA(scanner.nextLine());
                                    System.out.print("Option B: ");
                                    question.setOptionB(scanner.nextLine());
                                    System.out.print("Option C: ");
                                    question.setOptionC(scanner.nextLine());
                                    System.out.print("Option D: ");
                                    question.setOptionD(scanner.nextLine());

                                    System.out.print("Correct answer (A/B/C/D): ");
                                    question.setCorrectAnswer(scanner.nextLine().toUpperCase());

                                    questionDAO.createQuestion(question);
                                }
                                System.out.println("Quiz setup completed!");

                            } else {
                                // User: Take quiz logic here
                                List<Quiz> quizzes = quizDAO.getAllQuizzes();
                                if (quizzes.isEmpty()) {
                                    System.out.println("No quizzes available.");
                                } else {
                                    System.out.println("Available Quizzes:");
                                    quizzes.forEach(q -> System.out.println(q.getId() + ": " + q.getTitle()));
                                    System.out.print("Enter quiz ID to start: ");
                                    int quizId = scanner.nextInt();
                                    scanner.nextLine(); // Consume newline
                                    List<Question> questions = questionDAO.getQuestionsByQuizId(quizId);
                                    if (questions.isEmpty()) {
                                        System.out.println("No questions found for this quiz.");
                                    } else {
                                        int score = 0;
                                        for (Question question : questions) {
                                            System.out.println(question.getQuestionText());
                                            System.out.println("A: " + question.getOptionA());
                                            System.out.println("B: " + question.getOptionB());
                                            System.out.println("C: " + question.getOptionC());
                                            System.out.println("D: " + question.getOptionD());
                                            System.out.print("Enter your answer: ");
                                            String answer = scanner.nextLine();

                                            if (answer.equalsIgnoreCase(question.getCorrectAnswer())) {
                                                score++;
                                            } else {
                                                System.out.println("Incorrect! Correct answer: " + question.getCorrectAnswer());
                                            }
                                        }
                                        System.out.println("Quiz completed! Your score: " + score);
                                    }
                                }
                            }
                            break;

                        case 2:
                        	if ("admin".equals(loggedInUser.getRole())) {
                                // Admin: Edit Quiz
                                System.out.print("Enter quiz ID to edit: ");
                                int quizId = scanner.nextInt();
                                scanner.nextLine();

                                Quiz quiz = quizDAO.getQuizById(quizId);
                                if (quiz != null) {
                                    System.out.println("Current Title: " + quiz.getTitle());
                                    System.out.print("Enter new title: ");
                                    quiz.setTitle(scanner.nextLine());
                                    quizDAO.updateQuiz(quiz);
                                    System.out.println("Quiz updated successfully!");
                                } else {
                                    System.out.println("Quiz not found!");
                                }
                            } else {
                                // User: View Leaderboard
                                System.out.println("Leaderboard:");
                                List<LeaderboardEntry> leaderboard = leaderboardDAO.getOverallLeaderboard();
                                leaderboard.forEach(entry ->
                                        System.out.println(entry.getUsername() + " - " + entry.getScore()));
                            }
                            break;


                        case 3:
                        	if ("admin".equals(loggedInUser.getRole())) {
                        	    try {
                        	        System.out.print("Enter quiz ID to delete: ");
                        	        int quizId = scanner.nextInt();
                        	        scanner.nextLine();

                        	        if (quizDAO.deleteQuiz(quizId)) {
                        	            System.out.println("Quiz deleted successfully!");
                        	        } else {
                        	            System.out.println("Failed to delete quiz. Ensure the quiz ID exists.");
                        	        }
                        	    } catch (InputMismatchException e) {
                        	        System.out.println("Invalid input! Please enter a valid quiz ID.");
                        	        scanner.nextLine(); // Clear invalid input
                        	    }
                        	} else {
                        	    System.out.println("Your Past Quizzes:");
                        	    List<QuizResult> results = leaderboardDAO.getUserResults(loggedInUser.getId());
                        	    if (results.isEmpty()) {
                        	        System.out.println("No quizzes found.");
                        	    } else {
                        	        results.forEach(result ->
                        	                System.out.println("Quiz ID: " + result.getQuizId() +
                        	                        ", Score: " + result.getScore()));
                        	    }
                        	}



                        case 4:
                            // Logout
                            System.out.println("Logging out. Goodbye, " + loggedInUser.getUsername());
                            loggedInUser = null;
                            break;

                        default:
                            System.out.println("Invalid choice.");
                    }
                } catch (Exception e) {
                    System.out.println("An error occurred: " + e.getMessage());
                }
            }
        }
    }
}
