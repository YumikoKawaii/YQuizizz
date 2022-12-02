package com.example.yquizizz.challenge;

import android.content.Context;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.res.ResourcesCompat;

import com.example.yquizizz.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Quiz {

    private String id;
    private String topic;
    private String difficulty;
    private String question;
    private String rightAnswer;
    private ArrayList<String> answerList;

    public Quiz(String topic, String difficulty, String question, ArrayList<String> answerList) {
        this.topic = topic;
        this.difficulty = difficulty;
        this.question = question;
        rightAnswer = answerList.get(0);
        Collections.shuffle(answerList);
        this.answerList = answerList;
    }

    public boolean isRightAnswer(String answer)
    {
        return answer.equals(rightAnswer);
    }

    public void setSummaryAnswer(ArrayList<AppCompatButton> answers, AppCompatButton answerChosen, Context context) {

        answerChosen.setBackground(context.getResources().getDrawable(R.drawable.attempt_wrong_answer));
        for (AppCompatButton i : answers) if (isRightAnswer(i.getText().toString())) {
            i.setBackground(context.getResources().getDrawable(R.drawable.attempt_right_answer));
            return;
        }

    }

    public void setDisplay(TextView q, ArrayList<AppCompatButton> answers, Context context) {
        q.setText(this.question);
        for (int i = 0;i < answers.size();i++) {
            answers.get(i).setBackground(context.getResources().getDrawable(R.drawable.attempt_answer_bg));
            answers.get(i).setText(answerList.get(i));
        }
    }

    public String getQuestion() {
        return question;
    }

    public ArrayList<String> getAnswerList() {
        return answerList;
    }

    public String getRightAnswer() {
        return this.rightAnswer;
    }
}
