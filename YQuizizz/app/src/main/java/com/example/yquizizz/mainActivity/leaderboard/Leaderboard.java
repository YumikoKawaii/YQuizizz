package com.example.yquizizz.mainActivity.leaderboard;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yquizizz.R;
import com.example.yquizizz.systemLink.SystemLink;
import com.example.yquizizz.user.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Leaderboard extends Fragment {

    private RecyclerView lessThanThreeRankView;


    private TextView firstRankName;
    private TextView firstRankExp;
    private TextView firstRankLevel;

    private TextView secondRankName;
    private TextView secondRankExp;
    private TextView secondRankLevel;

    private TextView thirdRankName;
    private TextView thirdRankExp;
    private TextView thirdRankLevel;

    private TextView userRank;
    private TextView userName;
    private TextView userExp;
    private TextView userLevel;

    private ArrayList<Guest> guestList;
    private ArrayList<Guest> topThree;

    private User user;

    public Leaderboard() {
        // Required empty public constructor
    }

    public static Leaderboard newInstance() {
        Leaderboard fragment = new Leaderboard();
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

        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);
        lessThanThreeRankView = view.findViewById(R.id.lessThanThreeGuestRank);
        guestList = new ArrayList<>();
        topThree = new ArrayList<>();

        firstRankName = view.findViewById(R.id.firstRankName);
        firstRankExp = view.findViewById(R.id.firstRankExp);
        firstRankLevel = view.findViewById(R.id.firstRankLevelDisplay);

        secondRankName = view.findViewById(R.id.secondRankName);
        secondRankExp = view.findViewById(R.id.secondRankExp);
        secondRankLevel = view.findViewById(R.id.secondRankLevelDisplay);

        thirdRankName = view.findViewById(R.id.thirdRankName);
        thirdRankExp = view.findViewById(R.id.thirdRankExp);
        thirdRankLevel = view.findViewById(R.id.thirdRankLevelDisplay);

        userName = view.findViewById(R.id.userName);
        userRank = view.findViewById(R.id.userRank);
        userExp = view.findViewById(R.id.userExp);
        userLevel = view.findViewById(R.id.userLevel);

        user = new User(view.getContext());

        getGuestDataFromServer();

        return view;
    }

    private void getGuestDataFromServer() {
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("email", user.getEmail())
                .build();

        Request request = new Request.Builder()
                .url(SystemLink.leaderboardInfo)
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {

                        JSONObject data = new JSONObject(response.body().string());
                        Integer rank = Integer.parseInt(data.getString("userRank"));
                        JSONArray guests = data.getJSONArray("data");

                        for (int i = 0; i < guests.length(); i++) {
                            JSONObject guest = guests.getJSONObject(i);

                            String username = guest.getString("username");
                            Integer userRank = i + 1;
                            Integer userCurrentExp = Integer.parseInt(guest.getString("currentExp"));
                            Integer userCurrentLevel = Integer.parseInt(guest.getString("currentLevel"));
                            if (i < 3) {
                                topThree.add(new Guest(username, userRank, userCurrentExp, userCurrentLevel));
                            } else {
                                guestList.add(new Guest(username, userRank, userCurrentExp, userCurrentLevel));
                            }

                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setTopThree();
                                setAdapter();
                                user.setLeaderboardDisplay(userName, userRank, userExp, userLevel, rank);
                            }
                        });


                    } catch (Exception e) {
                        System.out.println(e);
                    }

                }
            }
        });
    }

    private void setTopThree() {
        firstRankName.setText(topThree.get(0).getUsername());
        firstRankExp.setText(topThree.get(0).getUserTotalExp().toString());
        firstRankLevel.setText(String.format("Level   %d", topThree.get(0).getUserLevel()));
        if (topThree.size() > 1) {
            secondRankName.setText(topThree.get(1).getUsername());
            secondRankExp.setText(topThree.get(1).getUserTotalExp().toString());
            secondRankLevel.setText(String.format("Level   %d", topThree.get(1).getUserLevel()));
        }

        if (topThree.size() > 2) {
            thirdRankName.setText(topThree.get(2).getUsername());
            thirdRankExp.setText(topThree.get(2).getUserTotalExp().toString());
            thirdRankLevel.setText(String.format("Level   %d", topThree.get(2).getUserLevel()));
        }
    }

    private void setAdapter() {
        GuestRankViewAdapter adapter = new GuestRankViewAdapter();
        adapter.setGuestList(guestList);
        lessThanThreeRankView.setAdapter(adapter);
        lessThanThreeRankView.setLayoutManager(new LinearLayoutManager(getContext()));
    }


}