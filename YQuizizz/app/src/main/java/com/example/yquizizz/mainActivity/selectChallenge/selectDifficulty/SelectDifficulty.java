package com.example.yquizizz.mainActivity.selectChallenge.selectDifficulty;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yquizizz.PlayActivity;
import com.example.yquizizz.R;
import com.example.yquizizz.mainActivity.selectChallenge.loading.LoadingChallenge;

import java.util.Arrays;

public class SelectDifficulty extends Fragment {

    private View fragView;
    private RecyclerView difficultySelector;

    private LoadingChallenge loadingChallenge;

    public SelectDifficulty() {
        // Required empty public constructor
    }

    public static SelectDifficulty newInstance() {
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

                bundle.putString("difficulty", difficulty);

                loadingChallenge = new LoadingChallenge();
                loadingChallenge.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .replace(R.id.body, loadingChallenge, "findThisFragment")
                        .addToBackStack(null)
                        .commit();

            }
        });

        difficultySelector = (RecyclerView) fragView.findViewById(R.id.difficultySelector);
        difficultySelector.setAdapter(difficulty_adapter);
        difficultySelector.setLayoutManager(new LinearLayoutManager(getContext()));

        return fragView;
    }

    public void cancelAllTimer() {
        if (loadingChallenge != null) {
            loadingChallenge.cancelTimer();
        }
    }

    private void openPlayScreen(String topic, String difficulty) {
        Intent intent = new Intent(getContext(), PlayActivity.class);
        intent.putExtra("topic", topic);
        intent.putExtra("difficulty", difficulty);
        startActivity(intent);
    }

}