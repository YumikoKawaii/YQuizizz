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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link submitQuestion#newInstance} factory method to
 * create an instance of this fragment.
 */
public class submitQuestion extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment submitQuestion.
     */
    // TODO: Rename and change types and number of parameters
    public static submitQuestion newInstance(String param1, String param2) {
        submitQuestion fragment = new submitQuestion();
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