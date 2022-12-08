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

public class Summary extends Fragment {

    private TextView prise;
    private ProgressBar attemptSummary;
    private TextView userLevel;
    private ProgressBar userCurrentPoint;
    private TextView pointSummary;
    private TextView bonusPoint;
    private AppCompatButton newChallenge;
    private AppCompatButton shareResult;

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

        Bundle resultData = this.getArguments();



        return view;
    }
}