package com.example.yquizizz.mainActivity.support.rateQuestion;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yquizizz.R;
import com.example.yquizizz.challenge.Quiz;

import java.util.ArrayList;

public class rateQuestion extends Fragment {

    private View fragView;
    private Quiz quiz;

    private AppCompatButton dislike;
    private AppCompatButton like;
    private AppCompatButton nextQuestion;

    private DisplayQuiz displayQuiz;

    public rateQuestion() {
        // Required empty public constructor
    }

    public static rateQuestion newInstance(String param1, String param2) {
        rateQuestion fragment = new rateQuestion();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private Quiz getQuizFromServer() {return null;}
    private void sendResultToServer(Quiz q, int value) {};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragView = inflater.inflate(R.layout.fragment_rate_question, container, false);

        try {
            dislike = (AppCompatButton) fragView.findViewById(R.id.dislikeQuestion);
            like = (AppCompatButton) fragView.findViewById(R.id.likeQuestion);
            nextQuestion = (AppCompatButton) fragView.findViewById(R.id.rateNextQuestion);

            dislike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendResultToServer(quiz, -1);
                }
            });

            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendResultToServer(quiz, 1);
                }
            });

            nextQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    quiz = getQuizFromServer();
                    displayQuiz.setDisplay(quiz);
                }
            });

        } catch (Exception e) {
            System.out.println("Cannot find button required!");
        }

        ArrayList<String> answerList = new ArrayList<>();
        answerList.add("Yumiko");
        answerList.add("Yumiko Sturluson");
        answerList.add("still Yumiko");
        answerList.add("Absolutely Yumiko");

        displayQuiz = new DisplayQuiz(fragView);
        displayQuiz.setDisplay(new Quiz("General Knowledge","Easy","Who deserved a Scholarship?", answerList));

        return fragView;
    }

    private class DisplayQuiz {

        private TextView question;
        private TextView rightAnswer;
        private TextView wrongAnswer1;
        private TextView wrongAnswer2;
        private TextView wrongAnswer3;

        public DisplayQuiz(View view) {
            try {
                question = (TextView) view.findViewById(R.id.rateQuestionDisplay);
                rightAnswer = (TextView) view.findViewById(R.id.rateQuestionRightAnswer);
                wrongAnswer1 = (TextView) view.findViewById(R.id.rateQuestionWrongAnswer1);
                wrongAnswer2 = (TextView) view.findViewById(R.id.rateQuestionWrongAnswer2);
                wrongAnswer3 = (TextView) view.findViewById(R.id.rateQuestionWrongAnswer3);
            } catch (Exception e) {
                System.out.println("Cannot find TextView required!");
            }
        }

        public void setDisplay(Quiz q)
        {
            try {
                question.setText(q.getQuestion());
                ArrayList<String> answerList = q.getAnswerList();
                rightAnswer.setText(answerList.get(0));
                wrongAnswer1.setText(answerList.get(1));
                wrongAnswer2.setText(answerList.get(2));
                wrongAnswer3.setText(answerList.get(3));
            } catch (Exception e) {
                System.out.println("Cannot set TextView required!");
            }
        }

    }
}