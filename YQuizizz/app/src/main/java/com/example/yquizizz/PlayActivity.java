package com.example.yquizizz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.yquizizz.playActivity.loading.LoadingChallenge;

public class PlayActivity extends AppCompatActivity {

    private LoadingChallenge loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        loading = new LoadingChallenge();
        replaceFragment(loading);
    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.play_body, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        loading.cancelTimer();
        super.onBackPressed();
    }
}