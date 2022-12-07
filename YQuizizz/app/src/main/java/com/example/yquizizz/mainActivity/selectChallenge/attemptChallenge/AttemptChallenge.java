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
import com.example.yquizizz.mainActivity.selectChallenge.summary.Summary;

import java.util.ArrayList;
import java.util.Arrays;

public class AttemptChallenge extends Fragment {

    private long TIME_PER_CHALLENGE = 10000;
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
        attemptProgress.setProgress(index*10);

        timeLeftText = view.findViewById(R.id.remainingTimeOfChallenge);
        startTotalTimer();

        indexTextView = view.findViewById(R.id.currentQuestionIndex);
        indexTextView.setText(String.format("%d/10", index));

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
                    quiz.setSummaryAnswer(answerList, i, view.getContext());
                    index++;

                    if (!quiz.isRightAnswer(i.getText().toString())) remainingLife--;

                    if (remainingLife == 0 || index == 10) {
                        openSummary();
                    } else {
                        totalCountDownTimer.cancel();

                        startSwitchTimer();
                        remainingLifeTextView.setText(Integer.toString(remainingLife));
                    }
                }
            });

        /*ArrayList<String> list = new ArrayList<>(Arrays.asList("Yumiko", "Yumiko Sturluson", "Madoka", "Hinata"));
        quiz = new Quiz("General Knowledge", "Normal", "Who is the strongest?", list);

        quiz.setDisplay(question, answerList, view.getContext());*/
        return view;
    }

    public void cancelAllTimer() {
        System.out.println("Prepare to shutdown");
        if (isTotalTimerRunning) totalCountDownTimer.cancel();
        if (isSwitchTimerRunning) switchCountDownTimer.cancel();
    }

    private void startTotalTimer() {

        totalCountDownTimer = new CountDownTimer(TIME_PER_CHALLENGE, 1000) {
            @Override
            public void onTick(long l) {

                System.out.println(l);
                System.out.println("Continue");

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
                attemptProgress.setProgress(index*10);
            }
        }.start();

        setEnableAnswer(false);
        isSwitchTimerRunning = true;
    }

    private void openSummary() {
        cancelAllTimer();
        Fragment summary = new Summary();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.body, summary, "findThisFragment")
                .commit();
    }

    private void setEnableAnswer(boolean b) {
        for (AppCompatButton i : answerList) i.setEnabled(b);
    }
}