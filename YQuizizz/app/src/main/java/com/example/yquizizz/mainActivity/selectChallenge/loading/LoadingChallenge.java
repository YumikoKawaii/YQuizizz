package com.example.yquizizz.mainActivity.selectChallenge.loading;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yquizizz.R;
import com.example.yquizizz.challenge.Challenge;
import com.example.yquizizz.challenge.Quiz;
import com.example.yquizizz.database.QuizDataController;
import com.example.yquizizz.database.QuizModel;
import com.example.yquizizz.mainActivity.selectChallenge.attemptChallenge.AttemptChallenge;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LoadingChallenge extends Fragment {

    private static final long COUNTDOWN = 5000;

    private CountDownTimer countDownTimer;
    private long timeLeft;
    private TextView timeLeftText;

    private boolean isRunning = false;

    private AttemptChallenge attemptChallenge;

    private TextView topicChosen;
    private TextView difficultyChosen;

    public LoadingChallenge() {
        // Required empty public constructor
    }

    public static LoadingChallenge newInstance() {
        LoadingChallenge fragment = new LoadingChallenge();
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
        View view = inflater.inflate(R.layout.fragment_loading_challenge, container, false);
        timeLeftText = view.findViewById(R.id.countdownTimer);
        topicChosen = (TextView) view.findViewById(R.id.topicChosen);
        difficultyChosen = (TextView) view.findViewById(R.id.difficultyChosen);

        Bundle data = this.getArguments();
        startTimer();

        validateData();

        ArrayList<Quiz> quizData = getData(data.getString("topic"), data.getString("difficulty"));
        Challenge challenge = new Challenge(quizData);

        Bundle challengeData = new Bundle();
        challengeData.putSerializable("Challenge Data", challenge);
        challengeData.putString("topic", data.getString("topic"));
        challengeData.putString("difficulty", data.getString("difficulty"));

        attemptChallenge = new AttemptChallenge();
        attemptChallenge.setArguments(challengeData);

        topicChosen.setText(data.getString("topic"));
        difficultyChosen.setText(data.getString("difficulty"));

        return view;
    }

    public void cancelTimer() {
        if (isRunning) {
            countDownTimer.cancel();
        }
        if (attemptChallenge != null) {

            attemptChallenge.cancelAllTimer();
        }

    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(COUNTDOWN, 1000) {
            @Override
            public void onTick(long l) {
                timeLeft = l;
                updateCountdownText();
                isRunning = true;
            }

            @Override
            public void onFinish() {
                isRunning = false;

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.body, attemptChallenge, "findThisFragment")
                        .commit();

            }
        }.start();
    }

    private void updateCountdownText() {
        int time = (int) timeLeft / 1000;
        timeLeftText.setText(Integer.toString(time));
    }

    private ArrayList<Quiz> getData(String topic, String difficulty) {
        QuizDataController quizDataController = new QuizDataController(getContext());
        ArrayList<Quiz> result =  quizDataController.getTopicData(topic, difficulty);
        return result;
    }

    private void validateData() {
        QuizDataController quizDataController = new QuizDataController(getContext());
        if (quizDataController.getSizeOfData() != 240) {
            System.out.println("Error");
            quizDataController.resetDatabase();
            ArrayList<String> dataSet = getDataSet();
            for (String i : dataSet) quizDataController.addOne(new QuizModel(i));
        }
    }

    private ArrayList<String> getDataSet() {

        InputStream inputStream = getContext().getResources().openRawResource(R.raw.quizdata);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        ArrayList<String> dataSet = new ArrayList<>();
        String str;
        try {
            while ((str = bufferedReader.readLine()) != null) {
                dataSet.add(str);
            }
            bufferedReader.close();

        } catch (Exception e) {
            System.out.println(e);
        }

        return dataSet;
    }

}