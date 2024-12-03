
# Online Quiz Application

The **Online Quiz Application** is a comprehensive system designed to manage and participate in quizzes. It features user management, question management, leaderboard tracking, and secure authentication.

---

## Features

- **User Management**: Register, login, and manage user profiles.
- **Quiz Creation**: Add, edit, and delete quizzes and questions.
- **Quiz Participation**: Users can take quizzes and view their results.
- **Leaderboard**: Tracks user performance and ranks participants.
- **Secure Authentication**: Implements robust user authentication and password encryption.
- **Database Integration**: Efficient data management using a relational database.
- **Utility Classes**: Includes tools for password hashing and other security utilities.

---

## Project Structure

### Key Directories and Files
- **`src/main/java/com/example/online_quiz_application/`**
  - **`dao/`**: Data Access Objects for interacting with the database.
    - `LeaderboardDAO.java`
    - `QuestionDAO.java`
    - `QuizDAO.java`
    - `UserDAO.java`
  - **`model/`**: Java classes representing the data models.
    - `LeaderboardEntry.java`
    - `Question.java`
    - `Quiz.java`
    - `User.java`
  - **`service/`**: Business logic and service layer.
    - `AuthService.java`
    - `QuizService.java`
    - `LeaderboardService.java`
  - **`util/`**: Utility classes for common tasks.
    - `DBConnection.java`
    - `PasswordUtils.java`
    - `SecurityUtils.java`
  - **`App.java`**: The main application entry point.

- **`src/main/resources/application.properties`**: Configuration file for database and application settings.

- **`pom.xml`**: Maven build file for managing dependencies.

---

## Prerequisites

- **Java Development Kit (JDK)**: Version 11 or above.
- **Apache Maven**: For project build and dependency management.
- **Database**: MySQL or equivalent relational database.
- **IDE**: Recommended - Eclipse, IntelliJ IDEA.

---

## Installation and Setup

1. **Clone the repository**:
   ```bash
   git clone https://github.com/prajwalkokate012/Online_Quiz_Application.git
   cd online-quiz-application
   ```

2. **Configure the database**:
   - Update the `application.properties` file with your database credentials.

3. **Build the project**:
   ```bash
   mvn clean install
   ```

4. **Run the application**:
   ```bash
   mvn spring-boot:run
   ```

5. **Access the application**:
   Open your browser and navigate to `http://localhost:8080`.

---

## Usage

- **Admin Panel**: Create and manage quizzes and questions.
- **Users**: Register, participate in quizzes, and view leaderboard rankings.
- **Security**: Implements robust authentication to ensure data integrity.

---

## Technologies Used

- **Java**: Backend language.
- **Spring Boot**: Framework for backend development.
- **MySQL**: Relational database for data storage.
- **Maven**: Build tool for managing dependencies.
- **JUnit**: Testing framework.

---

## Future Enhancements

- Add more question types (e.g., multiple-select, match the following).
- Enhance UI/UX for a smoother user experience.
- Add real-time quiz hosting features.

---

## Contributing

Contributions are welcome! Please fork this repository and create a pull request with your changes.

---


## Contact

For any queries, contact prajwalkokate012@gmail.com.
