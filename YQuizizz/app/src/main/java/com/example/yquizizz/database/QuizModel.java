package com.example.yquizizz.database;

import androidx.annotation.NonNull;

public class QuizModel {

    private String topic;
    private String difficulty;
    private String question;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;

    public QuizModel(String rawData) {
        String[] processed = rawData.split(",");

        this.topic = processed[1];
        this.difficulty = processed[2];
        this.question = processed[3];
        this.answer1 = processed[4];
        this.answer2 = processed[5];
        this.answer3 = processed[6];
        this.answer4 = processed[7];

    }

    @NonNull
    @Override
    public String toString() {
        return topic + " " + difficulty + " " + question + " " + answer1 + " " + answer2 + " " + answer3 + " " + answer4;
    }

    public String getTopic() {
        return topic;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer1() {
        return answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

}
