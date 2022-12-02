package com.example.yquizizz.loading;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yquizizz.R;
import com.example.yquizizz.attemptChallenge.AttemptChallenge;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoadingChallenge#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoadingChallenge extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final long COUNTDOWN = 5000;

    private CountDownTimer countDownTimer;
    private long timeLeft;
    private TextView timeLeftText;

    public LoadingChallenge() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment loading_challenge.
     */
    // TODO: Rename and change types and number of parameters
    public static LoadingChallenge newInstance(String param1, String param2) {
        LoadingChallenge fragment = new LoadingChallenge();
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
        View view = inflater.inflate(R.layout.fragment_loading_challenge, container, false);

        timeLeftText = view.findViewById(R.id.countdownTimer);
        startTimer();

        return view;
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(COUNTDOWN, 1000) {
            @Override
            public void onTick(long l) {
                timeLeft = l;
                updateCountdownText();
            }

            @Override
            public void onFinish() {
                Fragment nextFrag = new AttemptChallenge();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.play_body, nextFrag, "findThisFragment")
                        .commit();
            }
        }.start();
    }

    private void updateCountdownText() {
        Integer time = Math.toIntExact(timeLeft / 1000);
        timeLeftText.setText(time.toString());
    }

}