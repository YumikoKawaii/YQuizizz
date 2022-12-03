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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link rateQuestion#newInstance} factory method to
 * create an instance of this fragment.
 */
public class rateQuestion extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View fragView;
    private Quiz quiz;

    private AppCompatButton dislike;
    private AppCompatButton like;
    private AppCompatButton nextQuestion;

    private DisplayQuiz displayQuiz;

    public rateQuestion() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment rateQuestion.
     */
    // TODO: Rename and change types and number of parameters
    public static rateQuestion newInstance(String param1, String param2) {
        rateQuestion fragment = new rateQuestion();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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