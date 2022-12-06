package com.example.yquizizz.mainActivity.selectChallenge.selectDifficulty;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yquizizz.PlayActivity;
import com.example.yquizizz.R;

import java.util.Arrays;

public class SelectDifficulty extends Fragment {

    private View fragView;
    private RecyclerView difficultySelector;

    public SelectDifficulty() {
        // Required empty public constructor
    }

    public static SelectDifficulty newInstance(String param1, String param2) {
        SelectDifficulty fragment = new SelectDifficulty();
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
        fragView = inflater.inflate(R.layout.fragment_select_difficulty, container, false);

        Bundle bundle = this.getArguments();
        String topic = bundle.getString("topic");

        DifficultyViewAdapter difficulty_adapter = new DifficultyViewAdapter();
        difficulty_adapter.setContext(fragView.getContext());
        difficulty_adapter.setDifficultyList(Arrays.asList(fragView.getResources().getStringArray(R.array.difficulty)));

        difficulty_adapter.setItemClickListener(new DifficultyViewAdapter.ItemClickListener() {
            @Override
            public void onClickItemListener(String difficulty) {
                openPlayScreen(topic, difficulty);
            }
        });

        difficultySelector = (RecyclerView) fragView.findViewById(R.id.difficultySelector);
        difficultySelector.setAdapter(difficulty_adapter);
        difficultySelector.setLayoutManager(new LinearLayoutManager(getContext()));

        return fragView;
    }

    private void openPlayScreen(String topic, String difficulty) {
        Intent intent = new Intent(getContext(), PlayActivity.class);
        intent.putExtra("topic", topic);
        intent.putExtra("difficulty", difficulty);
        startActivity(intent);
    }

}