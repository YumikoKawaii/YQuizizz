package com.example.yquizizz.challenge;

import java.io.Serializable;

public class Result implements Serializable {

    private String topic;
    private String difficulty;
    private Integer totalPointOfChallenge;
    private Integer numberOfQuiz;
    private Integer numberOfRightAnswer;
    private Integer userPoint;
    private String dateAttempted;

    public Result(String topic, String difficulty, Integer totalPointOfChallenge, Integer numberOfQuiz) {
        this.topic = topic;
        this.difficulty = difficulty;
        this.totalPointOfChallenge = totalPointOfChallenge;
        this.numberOfQuiz = numberOfQuiz;
        this.userPoint = 0;
        this.numberOfRightAnswer = 0;
    }

    public void setNumberOfRightAnswer(Integer numberOfRightAnswer) {
        this.numberOfRightAnswer = numberOfRightAnswer;
    }

    public void setDateAttempted(String dateAttempted) {
        this.dateAttempted = dateAttempted;
    }

    public void updateUserPoint(Integer p) {
        this.userPoint += p;
    }

    public Integer getUserPoint() {
        return this.userPoint;
    }

    public Integer getNumberOfRightAnswer() {
        return this.numberOfRightAnswer;
    }

    public void updateNumberOfRightAnswer() {
        this.numberOfRightAnswer++;
    }
}
