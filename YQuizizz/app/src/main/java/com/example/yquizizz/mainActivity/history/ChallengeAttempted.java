package com.example.yquizizz.mainActivity.history;

import java.util.ArrayList;

public class ChallengeAttempted {

    private String topic;
    private String difficulty;
    private ArrayList<AttemptedQuiz> quizList;
    private Integer result = 5;

    public ChallengeAttempted(String topic, String difficulty) {
        this.topic = topic;
        this.difficulty = difficulty;
        quizList = new ArrayList<>();
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public void setQuizList(ArrayList<AttemptedQuiz> qList) {
        quizList = qList;
    }

    public String getTopic() {
        return topic;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public Integer getResult() {
        return result;
    }
}
