package com.omkar.jobaiassistant.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "question_answers")
public class QuestionAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String rawQuestion;

    @Column(columnDefinition = "TEXT")
    private String normalizedQuestion;

    @Column(columnDefinition = "TEXT")
    private String answerText;

    @Enumerated(EnumType.STRING)
    private QuestionType questionType;

    private Integer usageCount = 0;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public QuestionAnswer() {
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.usageCount = 0;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getRawQuestion() {
        return rawQuestion;
    }

    public String getNormalizedQuestion() {
        return normalizedQuestion;
    }

    public String getAnswerText() {
        return answerText;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public Integer getUsageCount() {
        return usageCount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public User getUser() {
        return user;
    }

    public void setRawQuestion(String rawQuestion) {
        this.rawQuestion = rawQuestion;
    }

    public void setNormalizedQuestion(String normalizedQuestion) {
        this.normalizedQuestion = normalizedQuestion;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public void setUsageCount(Integer usageCount) {
        this.usageCount = usageCount;
    }

    public void setUser(User user) {
        this.user = user;
    }
}