package com.example.yquizizz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.yquizizz.database.HistoryController;
import com.example.yquizizz.database.HistoryModel;
import com.example.yquizizz.database.QuizDataController;
import com.example.yquizizz.database.QuizModel;
import com.example.yquizizz.database.UserController;
import com.example.yquizizz.handling.NoInternet;
import com.example.yquizizz.mainActivity.aboutUs.AboutUs;
import com.example.yquizizz.mainActivity.history.History;
import com.example.yquizizz.mainActivity.home.Home;
import com.example.yquizizz.mainActivity.leaderboard.Leaderboard;
import com.example.yquizizz.mainActivity.privacy.Privacy;
import com.example.yquizizz.mainActivity.selectChallenge.SelectChallenge;
import com.example.yquizizz.mainActivity.setting.Setting;
import com.example.yquizizz.mainActivity.submitIdea.SubmitIdea;
import com.example.yquizizz.mainActivity.support.SupportTeam;
import com.example.yquizizz.mainActivity.support.submitQuestion.submitQuestion;
import com.example.yquizizz.systemLink.SystemData;
import com.example.yquizizz.user.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements submitQuestion.returnHomeFromSubmitQuestion {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    private SelectChallenge selectChallenge;

    private int currentScreen = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.template);
        navigationView = findViewById(R.id.navigation_view);
        toolbar = findViewById(R.id.toolbar);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else
                    drawerLayout.openDrawer(GravityCompat.START);

            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (!item.isChecked()) {

                    cancelAllTimer();
                    switch (item.getItemId()) {
                        case R.id.dashboard:
                            replaceFragment(new Home());
                            currentScreen = 1;
                            break;
                        case R.id.selectChallenge:
                            selectChallenge = new SelectChallenge();
                            replaceFragment(selectChallenge);
                            currentScreen = 2;
                            break;
                        case R.id.leaderboard:
                            if (SystemData.checkConnection(getBaseContext())) {
                                replaceFragment(new Leaderboard());
                            } else {
                                replaceFragment(new NoInternet());
                            }
                            currentScreen = 3;
                            break;
                        case R.id.support:
                            if (SystemData.checkConnection(getBaseContext())) {
                                replaceFragment(new SupportTeam());
                            } else {
                                replaceFragment(new NoInternet());
                            }
                            currentScreen = 4;
                            break;
                        case R.id.history:
                            replaceFragment(new History());
                            currentScreen = 5;
                            break;
                    }
                }

                return true;
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (!item.isChecked()) {

                    cancelAllTimer();
                    if (drawerLayout.isDrawerOpen(GravityCompat.START))
                        drawerLayout.closeDrawer(GravityCompat.START);

                    switch (item.getItemId()) {

                        case R.id.home:
                            if (bottomNavigationView.getMenu().getItem(0).isChecked() && currentScreen != 1) replaceFragment(new Home());
                            else if (currentScreen != 1) bottomNavigationView.setSelectedItemId(R.id.dashboard);
                            showBottomNav();
                            break;

                        case R.id.setting:
                            replaceFragment(new Setting());
                            hiddeBottomNav();
                            currentScreen = 6;
                            break;

                        case R.id.submitIdea:
                            if (SystemData.checkConnection(getBaseContext())) {
                                sendFeedback();
                            } else {
                                replaceFragment(new NoInternet());
                            }
                            break;

                        case R.id.aboutUs:
                            replaceFragment(new AboutUs());
                            hiddeBottomNav();
                            currentScreen = 7;
                            break;

                        case R.id.privacyPolicy:
                            replaceFragment(new Privacy());
                            hiddeBottomNav();
                            currentScreen = 8;
                            break;

                        case R.id.logOut:
                            deleteData();
                            openLoginActivity();
                            break;


                    }
                }

                return false;
            }
        });

        replaceFragment(new SelectChallenge());
        bottomNavigationView.setSelectedItemId(R.id.selectChallenge);

    }

    @Override
    public void onBackPressed() {

        cancelAllTimer();

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }

        super.onBackPressed();
    }

    private void hiddeBottomNav() {
        bottomNavigationView.clearAnimation();
        bottomNavigationView.animate().translationY(bottomNavigationView.getHeight()).setDuration(300);
    }

    private void showBottomNav() {
        bottomNavigationView.clearAnimation();
        bottomNavigationView.animate().translationY(0).setDuration(300);
    }


    private void cancelAllTimer() {
        if (selectChallenge != null) {
            selectChallenge.cancelAllTimer();
        }
    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_bottom, R.anim.exit_to_top);
        fragmentTransaction.replace(R.id.body, fragment);
        fragmentTransaction.commit();
    }

    private void openLoginActivity() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    private void deleteData() {
        UserController controller = new UserController(getBaseContext());
        controller.deleteUserData();

        HistoryController historyController = new HistoryController(getBaseContext());
        historyController.deleteHistory();

    }

    @Override
    public void returnHome(boolean home) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (home) {
                    bottomNavigationView.setSelectedItemId(R.id.dashboard);
                }
            }
        });
    }

    private void sendFeedback() {

        String[] receiver = new String[1];
        receiver[0] = SystemData.RECEIVER;

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, receiver);
        intent.putExtra(Intent.EXTRA_SUBJECT, SystemData.EMAIL_SUBJECT);
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent, "Choose an email client"));

    }
}