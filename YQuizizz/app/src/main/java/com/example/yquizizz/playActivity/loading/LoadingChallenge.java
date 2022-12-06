package com.example.yquizizz.playActivity.loading;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yquizizz.R;
import com.example.yquizizz.playActivity.attemptChallenge.AttemptChallenge;

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

    public static LoadingChallenge newInstance(String param1, String param2) {
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
        try {
            topicChosen.setText(data.getString("topic"));
            difficultyChosen.setText(data.getString("difficulty"));
        } catch (NullPointerException e) {
            throw new NullPointerException();
        }
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
                attemptChallenge = new AttemptChallenge();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.play_body, attemptChallenge, "findThisFragment")
                        .commit();
            }
        }.start();
    }

    private void updateCountdownText() {
        int time = (int) timeLeft / 1000;
        timeLeftText.setText(Integer.toString(time));
    }

}