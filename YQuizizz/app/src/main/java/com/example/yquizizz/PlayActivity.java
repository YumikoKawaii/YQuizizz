package com.example.yquizizz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.yquizizz.mainActivity.selectChallenge.selectDifficulty.SelectDifficulty;
import com.example.yquizizz.playActivity.loading.LoadingChallenge;

public class PlayActivity extends AppCompatActivity{

    private LoadingChallenge loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        Intent intent = getIntent();
        Bundle data = new Bundle();
        data.putString("topic", intent.getStringExtra("topic"));
        data.putString("difficulty", intent.getStringExtra("difficulty"));
        loading = new LoadingChallenge();
        loading.setArguments(data);
        replaceFragment(loading, data);
    }

    private void replaceFragment(Fragment fragment, Bundle data) {

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