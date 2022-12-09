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

import com.example.yquizizz.database.QuizDataController;
import com.example.yquizizz.database.QuizModel;
import com.example.yquizizz.mainActivity.aboutUs.AboutUs;
import com.example.yquizizz.mainActivity.history.History;
import com.example.yquizizz.mainActivity.home.Home;
import com.example.yquizizz.mainActivity.leaderboard.Leaderboard;
import com.example.yquizizz.mainActivity.selectChallenge.SelectChallenge;
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

public class MainActivity extends AppCompatActivity implements SubmitIdea.setToHome{

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

                drawerLayout.closeDrawer((GravityCompat.START));
                setCheckableBottomNav();

                if (!item.isChecked()) {
                    uncheckAllItemNavDrawer();
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

                setCheckableNavDrawer();
                if (!item.isChecked()) {

                    uncheckAllItemBottomNav();

                    cancelAllTimer();
                    if (drawerLayout.isDrawerOpen(GravityCompat.START))
                        drawerLayout.closeDrawer(GravityCompat.START);
                    switch (item.getItemId()) {

                        case R.id.logOut:
                            deleteData();
                            openLoginActivity();
                            break;

                        case R.id.aboutUs:
                            replaceFragment(new AboutUs());
                            break;

                        case R.id.submitIdea:
                            replaceFragment(new SubmitIdea());
                            break;

                    }
                }

                return true;
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

    private void deleteData() {
        File dir = getFilesDir();
        File userData = new File(dir, User.userData);
        File session = new File(dir, User.session);
        userData.delete();
        session.delete();
    }

    private void openLoginActivity() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    private void uncheckAllItemBottomNav() {
        for (int i = 0;i < bottomNavigationView.getMenu().size();i++) {
            bottomNavigationView.getMenu().getItem(i).setCheckable(false).setChecked(false);
        }
    }

    private void setCheckableBottomNav() {
        for (int i = 0;i < bottomNavigationView.getMenu().size();i++) {
            bottomNavigationView.getMenu().getItem(i).setCheckable(true);
        }
    }

    private void uncheckAllItemNavDrawer() {
        for (int i = 0;i < navigationView.getMenu().size();i++) {
            navigationView.getMenu().getItem(i).setCheckable(false).setChecked(false);
        }
    }

    private void setCheckableNavDrawer() {
        for (int i = 0;i < navigationView.getMenu().size();i++) {
            navigationView.getMenu().getItem(i).setCheckable(true);
        }
    }

    @Override
    public void isGoToHome(boolean home) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (home) bottomNavigationView.setSelectedItemId(R.id.dashboard);
            }
        });

    }
}