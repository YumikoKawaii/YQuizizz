package com.example.yquizizz.mainActivity.selectChallenge.summary;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.yquizizz.R;
import com.example.yquizizz.challenge.Result;
import com.example.yquizizz.user.User;

public class Summary extends Fragment {

    private TextView prise;
    private ProgressBar attemptSummary;
    private TextView percentOfPoint;
    private TextView numberOfRightAnswer;
    private TextView userLevel;
    private ProgressBar userCurrentPoint;
    private TextView pointProgressText;
    private TextView pointSummary;
    private TextView bonusPoint;
    private AppCompatButton newChallenge;
    private AppCompatButton shareResult;

    private User user;

    public Summary() {
        // Required empty public constructor
    }

    public static Summary newInstance() {
        Summary fragment = new Summary();
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
        View view = inflater.inflate(R.layout.fragment_summary, container, false);

        user = new User(view.getContext());

        Bundle resultData = this.getArguments();
        Result result = (Result) resultData.getSerializable("result");

        prise = view.findViewById(R.id.prise);

        switch (result.getNumberOfRightAnswer()) {
            case 0:
                prise.setText(R.string.zero_right_answer);
                break;
            case 1:
                prise.setText(R.string.one_right_answer);
                break;
            case 2:
                prise.setText(R.string.two_right_answer);
                break;
            case 3:
                prise.setText(R.string.three_right_answer);
                break;
            case 4:
                prise.setText(R.string.four_right_answer);
                break;
            case 5:
                prise.setText(R.string.five_right_answer);
                break;
        }

        attemptSummary = view.findViewById(R.id.attemptSummary);
        attemptSummary.setProgress(result.getPercentPointEarned().intValue());

        percentOfPoint = view.findViewById(R.id.percentOfPoint);
        percentOfPoint.setText(String.format("%.2f", result.getPercentPointEarned()));

        numberOfRightAnswer = view.findViewById(R.id.numberOfRightAnswer);
        numberOfRightAnswer.setText(String.format("%d/%d",result.getNumberOfRightAnswer(), result.getNumberOfQuiz()));

        user.updateExp(result.getUserPoint() + result.getBonusPoint(), view.getContext());

        userLevel = view.findViewById(R.id.level);
        userLevel.setText(user.getLevel().toString());

        userCurrentPoint = view.findViewById(R.id.pointBar);
        userCurrentPoint.setProgress(user.getCurrentProgress());

        pointProgressText = view.findViewById(R.id.pointProgressText);
        pointProgressText.setText(user.getPointProgressText());

        pointSummary = view.findViewById(R.id.pointSummary);
        pointSummary.setText(result.getUserPoint().toString());

        bonusPoint = view.findViewById(R.id.bonusPoint);
        bonusPoint.setText(result.getBonusPoint().toString());

        System.out.println(user.getCurrentExp());
        System.out.println(user.getLevel());
        return view;
    }
}