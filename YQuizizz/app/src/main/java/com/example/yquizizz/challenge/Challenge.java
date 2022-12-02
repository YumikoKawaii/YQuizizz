package com.example.yquizizz.challenge;

import java.util.ArrayList;

public class Challenge {

    private ArrayList<Quiz> quizList;
    private String topic;
    private Integer exp;

    public Challenge(ArrayList<Quiz> quizList, String topic, Integer exp) {
        this.quizList = quizList;
        this.topic = topic;
        this.exp = exp;
    }

    public ArrayList<Quiz> getQuizList() {
        return quizList;
    }

    public String getTopic() {
        return topic;
    }

    public Integer getExp() {
        return exp;
    }
}
