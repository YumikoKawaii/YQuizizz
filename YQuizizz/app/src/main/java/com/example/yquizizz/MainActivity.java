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
import com.example.yquizizz.mainActivity.aboutUs.AboutUs;
import com.example.yquizizz.mainActivity.history.History;
import com.example.yquizizz.mainActivity.home.Home;
import com.example.yquizizz.mainActivity.leaderboard.Leaderboard;
import com.example.yquizizz.mainActivity.privacy.Privacy;
import com.example.yquizizz.mainActivity.selectChallenge.SelectChallenge;
import com.example.yquizizz.mainActivity.setting.Setting;
import com.example.yquizizz.mainActivity.submitIdea.SubmitIdea;
import com.example.yquizizz.mainActivity.support.SupportTeam;
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

public class MainActivity extends AppCompatActivity implements SubmitIdea.setToHome {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;

    private SelectChallenge selectChallenge;

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

                //drawerLayout.closeDrawer((GravityCompat.START));

                if (!item.isChecked()) {
                    //uncheckNavDrawer();
                    cancelAllTimer();
                    switch (item.getItemId()) {
                        case R.id.dashboard:
                            replaceFragment(new Home());
                            break;
                        case R.id.selectChallenge:
                            selectChallenge = new SelectChallenge();
                            replaceFragment(selectChallenge);
                            break;
                        case R.id.leaderboard:
                            replaceFragment(new Leaderboard());
                            break;
                        case R.id.support:
                            replaceFragment(new SupportTeam());
                            break;
                        case R.id.history:
                            replaceFragment(new History());
                            break;
                    }
                }

                return true;
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                bottomNavigationView.setVisibility(View.INVISIBLE);
                if (!item.isChecked()) {

                    cancelAllTimer();
                    if (drawerLayout.isDrawerOpen(GravityCompat.START))
                        drawerLayout.closeDrawer(GravityCompat.START);
                    switch (item.getItemId()) {

                        case R.id.home:
                            if (bottomNavigationView.getMenu().getItem(0).isChecked()) {
                                replaceFragment(new Home());
                            } else {
                                bottomNavigationView.setSelectedItemId(R.id.dashboard);
                            }
                            bottomNavigationView.setVisibility(View.VISIBLE);
                            break;


                        case R.id.setting:
                            replaceFragment(new Setting());
                            break;

                        case R.id.submitIdea:
                            replaceFragment(new SubmitIdea());
                            break;

                        case R.id.aboutUs:
                            replaceFragment(new AboutUs());
                            break;

                        case R.id.privacyPolicy:
                            replaceFragment(new Privacy());
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

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }

        if (bottomNavigationView.getVisibility() == View.INVISIBLE) {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        cancelAllTimer();

        super.onBackPressed();
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
    public void isGoToHome(boolean home) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (home) {
                    if (bottomNavigationView.getMenu().getItem(0).isChecked()) {
                        replaceFragment(new Home());
                    } else {
                        bottomNavigationView.setSelectedItemId(R.id.dashboard);
                    }
                    bottomNavigationView.setVisibility(View.VISIBLE);
                }
            }
        });

    }
}