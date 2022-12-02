package com.example.yquizizz.attemptChallenge;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yquizizz.R;
import com.example.yquizizz.challenge.Challenge;
import com.example.yquizizz.challenge.Quiz;
import com.example.yquizizz.summary.Summary;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AttemptChallenge#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AttemptChallenge extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final long TIME_PER_CHALLENGE = 120000;
    private CountDownTimer totalCountDownTimer;
    private TextView timeLeftText;

    private static final long TIME_SWITCH_QUIZ = 1000;
    private CountDownTimer switchCountDownTimer;

    private TextView question;
    private ArrayList<AppCompatButton> answerList;
    private Challenge challenge;
    private Quiz quiz;

    private int index = 1;
    private TextView indexTextView;

    private int remainingLife = 3;
    private TextView remainingLifeTextView;

    private Context context;

    public AttemptChallenge() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AttempChallenge.
     */
    // TODO: Rename and change types and number of parameters
    public static AttemptChallenge newInstance(String param1, String param2) {
        AttemptChallenge fragment = new AttemptChallenge();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attempt_challenge, container, false);

        context = view.getContext();

        timeLeftText = view.findViewById(R.id.remainingTimeOfChallenge);
        startTotalTimer();

        indexTextView = view.findViewById(R.id.currentQuestionIndex);
        indexTextView.setText(String.format("%d/10",index));

        answerList = new ArrayList<>();


        question = view.findViewById(R.id.questionDisplay);
        answerList.add(view.findViewById(R.id.answer1));
        answerList.add(view.findViewById(R.id.answer2));
        answerList.add(view.findViewById(R.id.answer3));
        answerList.add(view.findViewById(R.id.answer4));
        remainingLifeTextView = view.findViewById(R.id.remainingLifeOfChallenge);

        for (AppCompatButton i : answerList) i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quiz.setSummaryAnswer(answerList, i, view.getContext());
                index++;
                indexTextView.setText(String.format("%d/10",index));
                if (quiz.isRightAnswer(i.getText().toString())) {

                } else {
                    remainingLife--;
                }
                setEnableAnswer(false);
                startSwitchTimer();
                remainingLifeTextView.setText(Integer.toString(remainingLife));
            }
        });

        ArrayList<String> list = new ArrayList<>(Arrays.asList("Yumiko", "Yumiko Sturluson", "Madoka", "Hinata"));
        quiz = new Quiz("General Knowledge", "Normal", "Who is the strongest?", list);

        quiz.setDisplay(question, answerList, view.getContext());
        return view;
    }

    private void startTotalTimer() {
        totalCountDownTimer = new CountDownTimer(TIME_PER_CHALLENGE, 1000) {
            @Override
            public void onTick(long l) {
                int m = (int) l / 60000;
                int s = (int) (l / 1000) % 60;
                timeLeftText.setText(String.format("%s:%s", m < 9 ? ("0" + Integer.toString(m)) : Integer.toString(m), s < 9 ? ("0" + Integer.toString(s)) : Integer.toString(s)));
            }

            @Override
            public void onFinish() {
                Fragment nextFrag = new Summary();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.play_body, nextFrag, "findThisFragment")
                        .commit();
            }
        }.start();
    }

    private void pauseTotalTimer() {
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
            }
        }.start();
    }

    private void setEnableAnswer(boolean b) {
        for (AppCompatButton i : answerList) i.setEnabled(b);
    }
}