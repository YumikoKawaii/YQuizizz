package com.example.yquizizz.mainActivity.aboutUs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yquizizz.R;

public class AboutUs extends Fragment {

    public AboutUs() {
        // Required empty public constructor
    }

    public static AboutUs newInstance(String param1, String param2) {
        AboutUs fragment = new AboutUs();
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
        return inflater.inflate(R.layout.fragment_about_us, container, false);
    }
}