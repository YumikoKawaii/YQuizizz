package com.example.yquizizz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.yquizizz.database.UserController;
import com.example.yquizizz.loginActivity.login.Login;
import com.example.yquizizz.user.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (!checkSessionExpired()) {
            replaceFragment(new Login());
        } else {
            openMainActivity();
        }

    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.loginBody, fragment);
        fragmentTransaction.commit();
    }

    private boolean checkSessionExpired() {

        UserController controller = new UserController(getBaseContext());

        String session = controller.getSession();
        System.out.println(session);
        Long time = Long.parseLong(session);
        return time > System.currentTimeMillis();

    }

    private void openMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

}