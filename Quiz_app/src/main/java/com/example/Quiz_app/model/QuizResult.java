package com.example.Quiz_app.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "quiz_results")
public class QuizResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String quizTopic;
    private int score;
    private int totalQuestions;
    private double percentage;

    private LocalDateTime dateTaken;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public QuizResult() {}

    public QuizResult(String quizTopic, int score, int totalQuestions, double percentage, User user) {
        this.quizTopic = quizTopic;
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.percentage = percentage;
        this.user = user;
        this.dateTaken = LocalDateTime.now();
    }

    // Getters & Setters
    public Long getId() { return id; }
    public String getQuizTopic() { return quizTopic; }
    public int getScore() { return score; }
    public int getTotalQuestions() { return totalQuestions; }
    public double getPercentage() { return percentage; }
    public LocalDateTime getDateTaken() { return dateTaken; }
    public User getUser() { return user; }

    public void setQuizTopic(String quizTopic) { this.quizTopic = quizTopic; }
    public void setScore(int score) { this.score = score; }
    public void setTotalQuestions(int totalQuestions) { this.totalQuestions = totalQuestions; }
    public void setPercentage(double percentage) { this.percentage = percentage; }
    public void setDateTaken(LocalDateTime dateTaken) { this.dateTaken = dateTaken; }
    public void setUser(User user) { this.user = user; }
}
