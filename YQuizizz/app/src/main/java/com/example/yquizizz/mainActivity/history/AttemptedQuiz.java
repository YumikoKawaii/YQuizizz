package com.example.yquizizz.mainActivity.history;

import com.example.yquizizz.challenge.Quiz;

import java.util.ArrayList;

public class AttemptedQuiz {
    private Integer id;
    private Integer index;
    private ArrayList<Integer> shuffle;
    private Integer rightAnswer;
    private Integer chosenAnswer;

    public AttemptedQuiz(Integer index, Quiz quiz) {
        this.index = index;
        this.shuffle = quiz.getShuffle();
        this.rightAnswer = quiz.getRightIndex();
    }

    public void setChosenAnswer(Integer chosenAnswer) {
        this.chosenAnswer = chosenAnswer;
    }
}
