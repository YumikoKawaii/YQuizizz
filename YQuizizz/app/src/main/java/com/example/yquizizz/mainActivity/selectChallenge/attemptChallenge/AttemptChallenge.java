package com.example.yquizizz.mainActivity.selectChallenge.attemptChallenge;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.yquizizz.R;
import com.example.yquizizz.challenge.Challenge;
import com.example.yquizizz.challenge.Quiz;
import com.example.yquizizz.challenge.Result;
import com.example.yquizizz.mainActivity.selectChallenge.summary.Summary;
import com.example.yquizizz.systemLink.SystemData;

import java.util.ArrayList;
import java.util.Arrays;

public class AttemptChallenge extends Fragment {

    private long TIME_PER_CHALLENGE = 120000;
    private CountDownTimer totalCountDownTimer;
    private boolean isTotalTimerRunning = false;
    private TextView timeLeftText;

    private final long TIME_SWITCH_QUIZ = 1000;
    private CountDownTimer switchCountDownTimer;
    private boolean isSwitchTimerRunning = false;

    private TextView question;
    private ArrayList<AppCompatButton> answerList;
    private Challenge challenge;
    private Quiz quiz;

    private ProgressBar attemptProgress;

    private int index = 1;
    private TextView indexTextView;

    private int remainingLife = 3;
    private TextView remainingLifeTextView;

    private Result result;
    private Context context;

    public AttemptChallenge() {
        // Required empty public constructor
    }


    public static AttemptChallenge newInstance() {
        AttemptChallenge fragment = new AttemptChallenge();
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
        View view = inflater.inflate(R.layout.fragment_attempt_challenge, container, false);

        context = view.getContext();

        attemptProgress = view.findViewById(R.id.attemptProgress);

        timeLeftText = view.findViewById(R.id.remainingTimeOfChallenge);
        startTotalTimer();

        indexTextView = view.findViewById(R.id.currentQuestionIndex);

        answerList = new ArrayList<>();

        question = view.findViewById(R.id.questionDisplay);
        answerList.add(view.findViewById(R.id.answer1));
        answerList.add(view.findViewById(R.id.answer2));
        answerList.add(view.findViewById(R.id.answer3));
        answerList.add(view.findViewById(R.id.answer4));

        remainingLifeTextView = view.findViewById(R.id.remainingLifeOfChallenge);

        for (AppCompatButton i : answerList)
            i.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String ans = i.getText().toString();

                    if (quiz.isRightAnswer(ans)) {
                        result.updateUserPoint(quiz.getPoint());
                        result.updateNumberOfRightAnswer();
                    }

                    index++;
                    if (index > 5) {
                        result.setDateAttempted(SystemData.getCurrentDate());
                        openSummary();
                    } else {
                        quiz = challenge.getQuizByIndex(index - 1);
                        setViewDisplay(quiz);
                    }

                }
            });

        Bundle challengeData = this.getArguments();

        challenge = (Challenge) challengeData.getSerializable("Challenge Data");

        result = new Result(challengeData.getString("topic"), challengeData.getString("difficulty"), challenge.getTotalPointOfChallenge(), challenge.getNumberOfQuestion());

        quiz = challenge.getQuizByIndex(index - 1);
        setViewDisplay(quiz);
        return view;
    }

    public void cancelAllTimer() {
        if (isTotalTimerRunning) totalCountDownTimer.cancel();
        if (isSwitchTimerRunning) switchCountDownTimer.cancel();
    }

    private void startTotalTimer() {

        totalCountDownTimer = new CountDownTimer(TIME_PER_CHALLENGE, 1000) {
            @Override
            public void onTick(long l) {

                TIME_PER_CHALLENGE = l;
                int m = (int) l / 60000;
                int s = (int) (l / 1000) % 60;
                timeLeftText.setText(String.format("%s:%s", m <= 9 ? ("0" + Integer.toString(m)) : Integer.toString(m), s <= 9 ? ("0" + Integer.toString(s)) : Integer.toString(s)));
            }

            @Override
            public void onFinish() {
                isTotalTimerRunning = false;
                openSummary();
            }
        }.start();

        isTotalTimerRunning = true;

    }

    //temporary lock this feature
    private void startSwitchTimer() {
        switchCountDownTimer = new CountDownTimer(TIME_SWITCH_QUIZ, 1000) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                setEnableAnswer(true);
                quiz.setDisplay(question, answerList, context);
                indexTextView.setText(String.format("%d/10", index));
                startTotalTimer();
                isSwitchTimerRunning = false;
                attemptProgress.setProgress(index * 20);
            }
        }.start();

        setEnableAnswer(false);
        isSwitchTimerRunning = true;
    }

    private void openSummary() {
        cancelAllTimer();
        Fragment summary = new Summary();
        Bundle resultData = new Bundle();
        resultData.putSerializable("result", result);
        summary.setArguments(resultData);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.body, summary, "findThisFragment")
                .commit();
    }

    //temporary lock this feature
    private void setEnableAnswer(boolean b) {
        for (AppCompatButton i : answerList) i.setEnabled(b);
    }

    private void setViewDisplay(Quiz quiz) {

        indexTextView.setText(String.format("%d / 5", index));
        attemptProgress.setProgress(index * 20);

        question.setText(quiz.getQuestion());
        ArrayList<String> ans = quiz.getAnswerList();
        for (int i = 0; i < ans.size(); i++) {
            answerList.get(i).setText(ans.get(i));
        }
    }



}