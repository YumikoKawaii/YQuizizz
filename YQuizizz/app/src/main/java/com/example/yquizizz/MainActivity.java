package com.example.yquizizz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.example.yquizizz.daily.DailyChallenge;
import com.example.yquizizz.home.Home;
import com.example.yquizizz.leaderboard.Leaderboard;
import com.example.yquizizz.selectChallenge.SelectChallenge;
import com.example.yquizizz.support.SupportTeam;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.io.FileOutputStream;

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
                item.setChecked(false);
                System.out.println(1);
                return false;
            }
        });

        replaceFragment(new Home(), getResources().getColor(R.color.blue_nav_1));

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                item.setChecked(true);
                drawerLayout.closeDrawer((GravityCompat.START));
                switch (id) {
                    case R.id.dashboard:
                        replaceFragment(new Home(), getResources().getColor(R.color.blue_nav_1));
                        break;
                    case R.id.selectChallenge:
                        replaceFragment(new SelectChallenge(), getResources().getColor(R.color.green_nav_2));
                        break;
                    case R.id.leaderboard:
                        replaceFragment(new Leaderboard(), getResources().getColor(R.color.pink_1));
                        break;
                    case R.id.support:
                        replaceFragment(new SupportTeam(), getResources().getColor(R.color.purple_2));
                        break;
                    case R.id.daily:
                        replaceFragment(new DailyChallenge(), getResources().getColor(R.color.blue_6));
                        break;
                }

                return true;
            }
        });

    }

    private void replaceFragment(Fragment fragment, int color) {

        toolbar.setBackgroundColor(color);
        bottomNavigationView.setBackgroundColor(color);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.body, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }

        super.onBackPressed();
    }
}