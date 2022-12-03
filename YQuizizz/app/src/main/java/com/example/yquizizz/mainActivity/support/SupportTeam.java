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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SupportTeam#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SupportTeam extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View fragView;
    private BottomNavigationView topNavBar;

    public SupportTeam() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment supportTeam.
     */
    // TODO: Rename and change types and number of parameters
    public static SupportTeam newInstance(String param1, String param2) {
        SupportTeam fragment = new SupportTeam();
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