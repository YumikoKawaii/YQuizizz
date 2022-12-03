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

import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectDifficulty#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectDifficulty extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View fragView;
    private RecyclerView difficultySelector;

    public SelectDifficulty() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelectDifficulty.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectDifficulty newInstance(String param1, String param2) {
        SelectDifficulty fragment = new SelectDifficulty();
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
        fragView = inflater.inflate(R.layout.fragment_select_difficulty, container, false);

        DifficultyViewAdapter difficulty_adapter = new DifficultyViewAdapter();
        difficulty_adapter.setContext(fragView.getContext());
        difficulty_adapter.setDifficultyList(Arrays.asList(fragView.getResources().getStringArray(R.array.difficulty)));

        difficulty_adapter.setItemClickListener(new DifficultyViewAdapter.ItemClickListener() {
            @Override
            public void onClickItemListener(String difficulty) {

                System.out.println(difficulty);
                openPlayScreen();
            }
        });

        difficultySelector = (RecyclerView) fragView.findViewById(R.id.difficultySelector);
        difficultySelector.setAdapter(difficulty_adapter);
        difficultySelector.setLayoutManager(new LinearLayoutManager(getContext()));

        return fragView;
    }

    private void openPlayScreen() {
        Intent intent = new Intent(getContext(), PlayActivity.class);
        startActivity(intent);
    }

}