package com.example.yquizizz.mainActivity.support.submitQuestion;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.example.yquizizz.R;
import com.example.yquizizz.challenge.Quiz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class submitQuestion extends Fragment {

    private View fragView;
    private Quiz quiz;

    private AutoCompleteTextView categorySelector;
    private List<String> categoryList;
    private AutoCompleteTextView difficultySelector;
    private List<String> difficultyList;

    private EditText question;
    private EditText rightAnswer, wrongAnswer1, wrongAnswer2, wrongAnswer3;
    private String category = "", difficulty = "";
    private AppCompatButton btnSubmitQuestion;

    public submitQuestion() {
        // Required empty public constructor
    }

    public static submitQuestion newInstance() {
        submitQuestion fragment = new submitQuestion();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private boolean uploadToServer(Quiz ques)
    {
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragView = inflater.inflate(R.layout.fragment_submit_question, container, false);

        try {
            categoryList = Arrays.asList(fragView.getResources().getStringArray(R.array.topicList));
            ArrayAdapter<String> category_adapter = new ArrayAdapter<>(getContext(), R.layout.select_topic_item, categoryList);
            categorySelector = (AutoCompleteTextView) fragView.findViewById(R.id.userCategory);
            categorySelector.setAdapter(category_adapter);
            categorySelector.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    category = categorySelector.getText().toString();
                }
            });

            difficultyList = Arrays.asList(fragView.getResources().getStringArray(R.array.difficulty));
            ArrayAdapter<String> difficulty_adapter = new ArrayAdapter<>(getContext(), R.layout.select_topic_item, difficultyList);
            difficultySelector = (AutoCompleteTextView) fragView.findViewById(R.id.userDifficulty);
            difficultySelector.setAdapter(difficulty_adapter);
            difficultySelector.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    difficulty = difficultySelector.getText().toString();
                }
            });
        } catch (Exception e) {
            System.out.println("Failed to load data!");
        }

        try {

            question = (EditText) fragView.findViewById(R.id.userQuestion);
            rightAnswer = (EditText) fragView.findViewById(R.id.userRightAnswer);
            wrongAnswer1 = (EditText) fragView.findViewById(R.id.userWrongAnswer1);
            wrongAnswer2 = (EditText) fragView.findViewById(R.id.userWrongAnswer2);
            wrongAnswer3 = (EditText) fragView.findViewById(R.id.userWrongAnswer3);
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            btnSubmitQuestion = (AppCompatButton) fragView.findViewById(R.id.submitQuestion);
            btnSubmitQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String userQuestion = question.getText().toString();
                    ArrayList<String> answerList = new ArrayList<>();
                    answerList.add(rightAnswer.getText().toString());
                    answerList.add(wrongAnswer1.getText().toString());
                    answerList.add(wrongAnswer2.getText().toString());
                    answerList.add(wrongAnswer3.getText().toString());
                    quiz = new Quiz(category, difficulty, userQuestion, answerList);
                    uploadToServer(quiz);
                }
            });
        } catch (Exception e) {
            System.out.println(e);
        }

        return fragView;
    }
}