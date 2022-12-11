package com.example.yquizizz.mainActivity.support;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.yquizizz.R;
import com.example.yquizizz.mainActivity.support.rateQuestion.rateQuestion;
import com.example.yquizizz.mainActivity.support.submitQuestion.submitQuestion;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class SupportTeam extends Fragment {

    private View fragView;
    private BottomNavigationView topNavBar;

    public static final int id = 4;

    public SupportTeam() {
        // Required empty public constructor
    }

    public static SupportTeam newInstance() {
        SupportTeam fragment = new SupportTeam();
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
        fragView = inflater.inflate(R.layout.fragment_support_team, container, false);
        topNavBar = (BottomNavigationView) fragView.findViewById(R.id.operationSelector);

        replaceFragment(new submitQuestion());
        topNavBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                item.setChecked(true);
                switch (id) {
                    case R.id.sendQuestionToDev:
                        replaceFragment(new submitQuestion());
                        break;
                    case R.id.reviewQuestion:
                        replaceFragment(new rateQuestion());
                        break;
                }

                return true;
            }
        });

        return fragView;
    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.supportTeamBody, fragment);
        fragmentTransaction.commit();
    }
}