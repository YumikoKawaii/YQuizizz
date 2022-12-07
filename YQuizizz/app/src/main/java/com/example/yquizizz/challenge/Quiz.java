package com.example.yquizizz.challenge;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
    private ArrayList<Integer> shuffle = new ArrayList<>();
    private Integer rightIndex;

    public Quiz(String id, String topic, String difficulty, String question, ArrayList<String> answerL) {
        this.topic = topic;
        this.difficulty = difficulty;
        this.question = question;
        rightAnswer = answerL.get(0);
        this.answerList = answerL;
        Collections.shuffle(answerList);
        for (int i = 0;i < 4;i++)
        {
            shuffle.add(answerL.indexOf(answerList.get(i)));
        }
        rightIndex = answerList.indexOf(rightAnswer);
    }

    @NonNull
    @Override
    public String toString() {
        return topic + "," + difficulty + "," + question;
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

    public ArrayList<Integer> getShuffle() {
        return this.shuffle;
    }

    public Integer getRightIndex() {
        return this.rightIndex;
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
