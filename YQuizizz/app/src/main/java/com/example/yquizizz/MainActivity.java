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

import com.example.yquizizz.mainActivity.history.History;
import com.example.yquizizz.mainActivity.home.Home;
import com.example.yquizizz.mainActivity.leaderboard.Leaderboard;
import com.example.yquizizz.mainActivity.selectChallenge.SelectChallenge;
import com.example.yquizizz.mainActivity.support.SupportTeam;
import com.example.yquizizz.user.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;

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

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.logOut:
                        deleteData();
                        openLoginActivity();
                        break;
                }

                return false;
            }
        });

        replaceFragment(new Home());

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                drawerLayout.closeDrawer((GravityCompat.START));
                switch (item.getItemId()) {
                    case R.id.dashboard:
                        replaceFragment(new Home());
                        break;
                    case R.id.selectChallenge:
                        replaceFragment(new SelectChallenge());
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

                return true;
            }
        });

    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }

        super.onBackPressed();
    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
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

}