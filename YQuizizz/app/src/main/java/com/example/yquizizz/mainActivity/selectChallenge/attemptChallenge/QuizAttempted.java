package com.example.yquizizz.mainActivity.selectChallenge.attemptChallenge;

import android.content.Context;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

import com.example.yquizizz.challenge.Quiz;

import java.util.ArrayList;

public class QuizAttempted {

    private String question;
    private ArrayList<String> answerList;
    private String rightAnswer;
    private String answerChosen;

    public QuizAttempted(Quiz quiz, String answerChosen) {
        this.question = quiz.getQuestion();
        this.answerList = quiz.getAnswerList();
        this.rightAnswer = quiz.getRightAnswer();
    }

    public void setAttemptedDisplay (TextView q, ArrayList<AppCompatButton> answers, Context context){
    }
}
