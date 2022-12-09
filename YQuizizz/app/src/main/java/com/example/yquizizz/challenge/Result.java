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
    private long remainingTime;
    private Integer bonusPoint;

    public Result(String topic, String difficulty, Integer totalPointOfChallenge, Integer numberOfQuiz) {
        this.topic = topic;
        this.difficulty = difficulty;
        this.totalPointOfChallenge = totalPointOfChallenge;
        this.numberOfQuiz = numberOfQuiz;
        this.userPoint = 0;
        this.numberOfRightAnswer = 0;
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

    public Double getPercentPointEarned() {
        return (double) (userPoint.doubleValue() / totalPointOfChallenge.doubleValue())*100;
    }

    public Integer getNumberOfQuiz() {
        return this.numberOfQuiz;
    }

    public void setRemainingTime(long l) {
        this.remainingTime = l;
        this.bonusPoint = (int) (l/10000)*this.numberOfRightAnswer;
    }

    public Integer getBonusPoint() {
        return this.bonusPoint;
    }

    public String getDateAttempted() {
        return this.dateAttempted;
    }

    public String getTopic() {
        return this.topic;
    }

    public String getDifficulty() {
        return this.difficulty;
    }

    public Integer getTotalPointOfChallenge() {
        return totalPointOfChallenge;
    }
}
