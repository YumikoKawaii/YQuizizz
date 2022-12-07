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
import android.widget.TextView;

import com.example.yquizizz.R;
import com.example.yquizizz.challenge.Quiz;
import com.example.yquizizz.systemLink.SystemLink;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class submitQuestion extends Fragment {

    private Quiz quiz;

    private AutoCompleteTextView categorySelector;
    private List<String> categoryList;
    private AutoCompleteTextView difficultySelector;
    private List<String> difficultyList;

    private EditText question;
    private EditText rightAnswer, wrongAnswer1, wrongAnswer2, wrongAnswer3;
    private String category = "", difficulty = "";
    private AppCompatButton btnSubmitQuestion;

    private TextView questionCautions;
    private TextView rightAnsCautions;
    private TextView wrongAns1Cautions;
    private TextView wrongAns2Cautions;
    private TextView wrongAns3Cautions;
    private TextView categoryCautions;
    private TextView difficultyCautions;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_submit_question, container, false);

        questionCautions = view.findViewById(R.id.questionCautions);
        rightAnsCautions = view.findViewById(R.id.rightAnswerCautions);
        wrongAns1Cautions = view.findViewById(R.id.wrongAns1Cautions);
        wrongAns2Cautions = view.findViewById(R.id.wrongAns2Cautions);
        wrongAns3Cautions = view.findViewById(R.id.wrongAns3Cautions);
        categoryCautions = view.findViewById(R.id.categoryCautions);
        difficultyCautions = view.findViewById(R.id.difficultyCautions);


        try {
            categoryList = Arrays.asList(view.getResources().getStringArray(R.array.topicList));
            ArrayAdapter<String> category_adapter = new ArrayAdapter<>(getContext(), R.layout.select_topic_item, categoryList);
            categorySelector = view.findViewById(R.id.userCategory);
            categorySelector.setAdapter(category_adapter);
            categorySelector.setOnItemClickListener((adapterView, view12, i, l) -> category = categorySelector.getText().toString());

            difficultyList = Arrays.asList(view.getResources().getStringArray(R.array.difficulty));
            ArrayAdapter<String> difficulty_adapter = new ArrayAdapter<>(getContext(), R.layout.select_topic_item, difficultyList);
            difficultySelector = view.findViewById(R.id.userDifficulty);
            difficultySelector.setAdapter(difficulty_adapter);
            difficultySelector.setOnItemClickListener((adapterView, view1, i, l) -> difficulty = difficultySelector.getText().toString());
        } catch (Exception e) {
            System.out.println("Failed to load data!");
        }

        try {

            question = view.findViewById(R.id.userQuestion);
            rightAnswer = view.findViewById(R.id.userRightAnswer);
            wrongAnswer1 = view.findViewById(R.id.userWrongAnswer1);
            wrongAnswer2 = view.findViewById(R.id.userWrongAnswer2);
            wrongAnswer3 = view.findViewById(R.id.userWrongAnswer3);
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            btnSubmitQuestion = view.findViewById(R.id.submitQuestion);
            btnSubmitQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String userQuestion = question.getText().toString();
                    ArrayList<String> answerList = new ArrayList<>();
                    answerList.add(rightAnswer.getText().toString());
                    answerList.add(wrongAnswer1.getText().toString());
                    answerList.add(wrongAnswer2.getText().toString());
                    answerList.add(wrongAnswer3.getText().toString());
                    System.out.println(validateQuestion(userQuestion, answerList));
                    uploadToServer(quiz);
                }
            });
        } catch (Exception e) {
            System.out.println(e);
        }

        return view;
    }

    private boolean uploadToServer(Quiz ques) {
        /*OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("email", userEmail)
                .add("password", userPassword)
                .build();

        Request request = new Request.Builder()
                .url(SystemLink.login)
                .post(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONObject res = new JSONObject(response.body().string());

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


                            }
                        });

                    } catch (Exception e) {
                        System.out.println(e);
                    }

                }
            }
        }); */


        return true;
    }

    private boolean validateQuestion(String question, ArrayList<String> answerList) {

        boolean accept = true;

        if (question.length() == 0) {
            questionCautions.setVisibility(View.VISIBLE);
            accept = false;
        } else {
            questionCautions.setVisibility(View.INVISIBLE);
        }

        if (answerList.get(0).length() == 0) {
            rightAnsCautions.setVisibility(View.VISIBLE);
            accept = false;
        } else {
            rightAnsCautions.setVisibility(View.INVISIBLE);
        }

        if (answerList.get(1).length() == 0) {
            wrongAns1Cautions.setVisibility(View.VISIBLE);
            accept = false;
        } else {
            wrongAns1Cautions.setVisibility(View.INVISIBLE);
        }

        if (answerList.get(2).length() == 0) {
            wrongAns2Cautions.setVisibility(View.VISIBLE);
            accept = false;
        } else {
            wrongAns2Cautions.setVisibility(View.INVISIBLE);
        }

        if (answerList.get(3).length() == 0) {
            wrongAns3Cautions.setVisibility(View.VISIBLE);
            accept = false;
        } else {
            wrongAns3Cautions.setVisibility(View.INVISIBLE);
        }

        if (category.length() == 0) {
            categoryCautions.setVisibility(View.VISIBLE);
            accept = false;
        } else {
            categoryCautions.setVisibility(View.INVISIBLE);
        }

        if (difficulty.length() == 0) {
            difficultyCautions.setVisibility(View.VISIBLE);
            accept = false;
        } else {
            difficultyCautions.setVisibility(View.INVISIBLE);
        }

        return accept;
    }

}