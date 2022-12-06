package com.example.yquizizz.mainActivity.history;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yquizizz.R;

import java.util.ArrayList;

public class History extends Fragment {

    private RecyclerView historyDisplay;

    public History() {
        // Required empty public constructor
    }

    public static History newInstance(String param1, String param2) {
        History fragment = new History();
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
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        historyDisplay = view.findViewById(R.id.historyDisplay);

        ArrayList<ChallengeAttempted> challengeAttemptedList = new ArrayList<>();

        challengeAttemptedList.add(new ChallengeAttempted("Physics", "Hard"));
        challengeAttemptedList.add(new ChallengeAttempted("Art", "Easy"));
        challengeAttemptedList.add(new ChallengeAttempted("Chemistry", "Nightmare"));
        challengeAttemptedList.add(new ChallengeAttempted("History", "Normal"));
        challengeAttemptedList.add(new ChallengeAttempted("Geography", "Hard"));
        challengeAttemptedList.add(new ChallengeAttempted("General Knowledge", "Normal"));

        ChallengeAttemptedDisplayAdapter adapter = new ChallengeAttemptedDisplayAdapter();
        adapter.setChallengeAttemptedList(challengeAttemptedList);

        historyDisplay.setAdapter(adapter);
        historyDisplay.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
}