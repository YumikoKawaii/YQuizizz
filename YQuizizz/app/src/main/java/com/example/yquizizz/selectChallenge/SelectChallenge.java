package com.example.yquizizz.selectChallenge;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yquizizz.Play;
import com.example.yquizizz.R;
import com.example.yquizizz.selectChallenge.selectDifficulty.DifficultyViewAdapter;
import com.example.yquizizz.selectChallenge.selectTopic.SelectTopic;
import com.example.yquizizz.selectChallenge.selectTopic.TopicViewAdapter;

import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectChallenge#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectChallenge extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SelectChallenge() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelectChallenge.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectChallenge newInstance(String param1, String param2) {
        SelectChallenge fragment = new SelectChallenge();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select_challenge, container, false);

        try {
            Fragment nextFrag = new SelectTopic();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.selectBody, nextFrag, "findThisFragment")
                    .commit();
        } catch (Exception e) {
            System.out.println(e);
        }

        return view;
    }

}