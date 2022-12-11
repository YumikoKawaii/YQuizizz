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
import com.example.yquizizz.database.UserController;
import com.example.yquizizz.handling.NoInternet;
import com.example.yquizizz.mainActivity.aboutUs.AboutUs;
import com.example.yquizizz.mainActivity.history.History;
import com.example.yquizizz.mainActivity.home.Home;
import com.example.yquizizz.mainActivity.leaderboard.Leaderboard;
import com.example.yquizizz.mainActivity.privacy.Privacy;
import com.example.yquizizz.mainActivity.selectChallenge.SelectChallenge;
import com.example.yquizizz.mainActivity.selectChallenge.loading.LoadingChallenge;
import com.example.yquizizz.mainActivity.selectChallenge.selectDifficulty.SelectDifficulty;
import com.example.yquizizz.mainActivity.selectChallenge.selectTopic.SelectTopic;
import com.example.yquizizz.mainActivity.selectChallenge.summary.Summary;
import com.example.yquizizz.mainActivity.setting.Setting;
import com.example.yquizizz.mainActivity.support.SupportTeam;
import com.example.yquizizz.mainActivity.support.submitQuestion.submitQuestion;
import com.example.yquizizz.utils.SystemData;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.Stack;

public class MainActivity extends AppCompatActivity implements submitQuestion.returnHomeFromSubmitQuestion, LoadingChallenge.addToTrace, SelectDifficulty.addToTrace, Summary.addToTrace {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    private SelectChallenge selectChallenge;

    private Stack<Integer> screenTrace = new Stack<>();

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

                System.out.println(screenTrace);
                if (!item.isChecked()) {

                    cancelAllTimer();

                    switch (item.getItemId()) {
                        case R.id.dashboard:
                            replaceFragmentWithBackStack(new Home());
                            screenTrace.push(Home.id);
                            break;
                        case R.id.selectChallenge:
                            selectChallenge = new SelectChallenge();
                            replaceFragmentWithBackStack(selectChallenge);
                            screenTrace.push(SelectChallenge.id);
                            break;
                        case R.id.leaderboard:
                            if (SystemData.checkConnection(getBaseContext())) {
                                replaceFragmentWithBackStack(new Leaderboard());
                            } else {
                                replaceFragmentWithBackStack(new NoInternet());
                            }
                            screenTrace.push(Leaderboard.id);
                            break;
                        case R.id.support:
                            if (SystemData.checkConnection(getBaseContext())) {
                                replaceFragmentWithBackStack(new SupportTeam());
                            } else {
                                replaceFragmentWithoutBackstack(new NoInternet());
                            }
                            screenTrace.push(SupportTeam.id);
                            break;
                        case R.id.history:
                            replaceFragmentWithBackStack(new History());
                            screenTrace.push(History.id);
                            break;
                    }
                }

                return true;
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                cancelAllTimer();

                if (drawerLayout.isDrawerOpen(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);

                switch (item.getItemId()) {

                    case R.id.home:
                        if (screenTrace.peek() != 1)
                            bottomNavigationView.setSelectedItemId(R.id.dashboard);
                        showBottomNav();
                        break;

                    case R.id.setting:
                        replaceFragmentWithoutBackstack(new Setting());
                        hiddeBottomNav();
                        break;

                    case R.id.submitIdea:
                        if (SystemData.checkConnection(getBaseContext())) {
                            sendFeedback();
                        } else {
                            replaceFragmentWithoutBackstack(new NoInternet());
                        }
                        break;

                    case R.id.aboutUs:
                        replaceFragmentWithoutBackstack(new AboutUs());
                        hiddeBottomNav();
                        break;

                    case R.id.privacyPolicy:
                        replaceFragmentWithoutBackstack(new Privacy());
                        hiddeBottomNav();
                        break;

                    case R.id.logOut:
                        deleteData();
                        openLoginActivity();
                        break;

                }

                return false;
            }
        });

        screenTrace.push(SelectChallenge.id);
        selectChallenge = new SelectChallenge();
        replaceFragmentWithoutBackstack(selectChallenge);
        bottomNavigationView.getMenu().getItem(1).setChecked(true);
        System.out.println(screenTrace);
    }

    @Override
    public void onBackPressed() {

        cancelAllTimer();

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }

        if (screenTrace.size() > 1) {

            System.out.println(screenTrace);
            screenTrace.pop();

            switch (screenTrace.peek()) {
                case Home.id:
                    bottomNavigationView.getMenu().getItem(0).setChecked(true);
                    break;
                case SelectChallenge.id:
                    bottomNavigationView.getMenu().getItem(1).setChecked(true);
                    break;
                case Leaderboard.id:
                    bottomNavigationView.getMenu().getItem(2).setChecked(true);
                    break;
                case SupportTeam.id:
                    bottomNavigationView.getMenu().getItem(3).setChecked(true);
                    break;
                case History.id:
                    bottomNavigationView.getMenu().getItem(4).setChecked(true);
                    break;
            }

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

    private void replaceFragmentWithBackStack(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_bottom, R.anim.exit_to_top, R.anim.enter_from_left, R.anim.exit_to_right);
        fragmentTransaction.replace(R.id.body, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void replaceFragmentWithoutBackstack(Fragment fragment) {

        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_from_bottom, R.anim.exit_to_top)
                .replace(R.id.body, fragment)
                .commit();

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

    @Override
    public void addSelectDifficulty(boolean bool) {
        if (bool) screenTrace.add(SelectChallenge.id);
    }

    @Override
    public void addLoad(boolean bool) {
        if (bool) screenTrace.add(SelectChallenge.id);
    }

    @Override
    public void addSummary(boolean bool) {
        if (bool) screenTrace.add(SelectChallenge.id);
    }
}