package com.example.yquizizz.daily;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yquizizz.R;
import com.example.yquizizz.challenge.Challenge;
import com.example.yquizizz.challenge.ChallengeAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DailyChallenge#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DailyChallenge extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;

    private ArrayList<Challenge> challengeList = new ArrayList<>();;
    private RecyclerView dailyChallenge;

    public DailyChallenge() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DailyChallenge.
     */
    // TODO: Rename and change types and number of parameters
    public static DailyChallenge newInstance(String param1, String param2) {
        DailyChallenge fragment = new DailyChallenge();
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
        view = inflater.inflate(R.layout.fragment_daily_challenge, container, false);

        try {
            dailyChallenge = view.findViewById(R.id.dailyChallenge);

            challengeList.add(new Challenge(null, "Art",100));
            challengeList.add(new Challenge(null, "Computer Science",200));
            challengeList.add(new Challenge(null, "Robotics",300));
            challengeList.add(new Challenge(null, "Mathematics",400));
            challengeList.add(new Challenge(null, "Physics",500));
            challengeList.add(new Challenge(null, "Biology",500));
            challengeList.add(new Challenge(null, "Geography",500));
            challengeList.add(new Challenge(null, "History",500));
            challengeList.add(new Challenge(null, "Electrical Engineering",500));

            ChallengeAdapter adapter = new ChallengeAdapter();
            adapter.setChallengeList(challengeList);
            adapter.setContext(view.getContext());

            dailyChallenge.setAdapter(adapter);
            dailyChallenge.setLayoutManager(new LinearLayoutManager(getContext()));

        } catch (Exception e) {
            System.out.println(e);
        }


        return view;
    }
}