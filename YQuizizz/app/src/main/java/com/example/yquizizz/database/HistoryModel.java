package com.example.yquizizz.database;

public class HistoryModel {

    private String topic;
    private String difficulty;
    private Integer totalPoint;
    private Integer numberOfQuiz;
    private Integer numberOfRightAnswer;
    private Integer userPoint;
    private String dateAttempt;

    public HistoryModel(String topic, String difficulty, Integer totalPoint, Integer numberOfQuiz, Integer numberOfRightAnswer, Integer userPoint, String dateAttempt) {
        this.topic = topic;
        this.difficulty = difficulty;
        this.totalPoint = totalPoint;
        this.numberOfQuiz = numberOfQuiz;
        this.numberOfRightAnswer = numberOfRightAnswer;
        this.userPoint = userPoint;
        this.dateAttempt = dateAttempt;
    }

    public String getTopic() {
        return topic;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public Integer getTotalPoint() {
        return totalPoint;
    }

    public Integer getNumberOfQuiz() {
        return numberOfQuiz;
    }

    public Integer getNumberOfRightAnswer() {
        return numberOfRightAnswer;
    }

    public Integer getUserPoint() {
        return userPoint;
    }

    public String getDateAttempt() {
        return dateAttempt;
    }
}
