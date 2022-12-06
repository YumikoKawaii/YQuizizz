package com.example.yquizizz.mainActivity.selectChallenge;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yquizizz.R;
import com.example.yquizizz.mainActivity.selectChallenge.selectTopic.SelectTopic;

public class SelectChallenge extends Fragment {



    public SelectChallenge() {
    }

    public static SelectChallenge newInstance(String param1, String param2) {
        SelectChallenge fragment = new SelectChallenge();
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