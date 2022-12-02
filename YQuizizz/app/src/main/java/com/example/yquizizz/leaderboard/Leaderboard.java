package com.example.yquizizz.leaderboard;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yquizizz.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Leaderboard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Leaderboard extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;

    private RecyclerView lessThanThreeRankView;

    private ArrayList<Guest> guestList;


    public Leaderboard() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Leaderboard.
     */
    // TODO: Rename and change types and number of parameters
    public static Leaderboard newInstance(String param1, String param2) {
        Leaderboard fragment = new Leaderboard();
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
        view = inflater.inflate(R.layout.fragment_leaderboard, container, false);

        try {
            lessThanThreeRankView = view.findViewById(R.id.lessThanThreeGuestRank);

            guestList = new ArrayList<>();
            guestList.add(new Guest("Yumiko", 4, 500, 1, "123"));
            guestList.add(new Guest("Hehe", 5, 500, 1, "123"));
            guestList.add(new Guest("HiHI", 6, 500, 1, "123"));
            guestList.add(new Guest("Sleep", 7, 500, 1, "123"));
            guestList.add(new Guest("Wake up", 8, 500, 1, "123"));
            guestList.add(new Guest("Wake up", 9, 500, 1, "123"));
            guestList.add(new Guest("Wake up", 10, 500, 1, "123"));
            guestList.add(new Guest("Wake up", 11, 500, 1, "123"));
            guestList.add(new Guest("Wake up", 12, 500, 1, "123"));

            GuestRankViewAdapter adapter = new GuestRankViewAdapter();
            adapter.setGuestList(guestList);

            lessThanThreeRankView.setAdapter(adapter);
            lessThanThreeRankView.setLayoutManager(new LinearLayoutManager(getContext()));

        } catch (Exception e) {
            System.out.println(e);
        }


        return view;
    }
}