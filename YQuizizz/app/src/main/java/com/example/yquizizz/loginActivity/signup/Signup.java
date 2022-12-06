package com.example.yquizizz.loginActivity.signup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yquizizz.MainActivity;
import com.example.yquizizz.R;
import com.example.yquizizz.loginActivity.login.Login;
import com.example.yquizizz.user.User;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Signup extends Fragment {

    private EditText usernameGetter;
    private EditText userEmailGetter;
    private EditText userPasswordGetter;
    private EditText userPasswordConfirm;

    private TextView usernameCautions;
    private TextView emailCautions;
    private TextView passwordCautions;
    private TextView confirmPasswordCautions;

    private static final String url = "http://10.0.2.2:3000/register";

    private AppCompatButton registerBtn;
    private AppCompatButton signInBtn;

    private Context context;


    public Signup() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Signup newInstance(String param1, String param2) {
        Signup fragment = new Signup();
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
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        context = view.getContext();
        usernameGetter = (EditText) view.findViewById(R.id.registerUsername);
        userEmailGetter = (EditText) view.findViewById(R.id.userEmail);
        userPasswordGetter = (EditText) view.findViewById(R.id.registerUserPassword);
        userPasswordConfirm = (EditText) view.findViewById(R.id.userPasswordConfirm);

        emailCautions = (TextView) view.findViewById(R.id.registerEmailCautions);
        passwordCautions = (TextView) view.findViewById(R.id.registerPasswordCaution);
        confirmPasswordCautions = (TextView) view.findViewById(R.id.registerConfirmPassword);
        usernameCautions = (TextView) view.findViewById(R.id.registerUsernameCautions);

        registerBtn = (AppCompatButton) view.findViewById(R.id.register);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean accept = true;
                String username = usernameGetter.getText().toString();
                String userPassword = userPasswordGetter.getText().toString();
                String userEmail = userEmailGetter.getText().toString();
                if (validatePassword(userPassword)) {
                    accept = confirmPassword(userPassword, userPasswordConfirm.getText().toString());
                }
                accept = validateEmailClient(userEmail);
                accept = validateUsername(username);
                if (accept) {
                    registerAccount(username, userEmail, userPassword);
                }
            }
        });

        signInBtn = (AppCompatButton) view.findViewById(R.id.switchToSignIn);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.loginBody, new Login(), "findThisFragment")
                        .commit();
            }
        });

        return view;
    }

    private boolean validateUsername(String username) {

        if (username.length() == 0) {
            usernameCautions.setText(context.getText(R.string.cautions_username_null));
            usernameCautions.setVisibility(View.VISIBLE);
            return false;
        } else {
            usernameCautions.setVisibility(View.INVISIBLE);
        }
        return true;
    }

    private int countSpecificDigit(String string, Character digit) {
        int count = 0;
        for (int i = 0; i < string.length(); i++)
            if (string.charAt(i) == digit) count++;
        return count;
    }

    private boolean validateEmailClient(String email) {

        if (countSpecificDigit(email, '@') != 1) {
            emailCautions.setText(context.getText(R.string.cautions_email_invalid));
            emailCautions.setVisibility(View.VISIBLE);
            return false;
        } else {
            emailCautions.setVisibility(View.INVISIBLE);
        }

        return true;
    }

    private void emailIsUsed() {
        emailCautions.setText(context.getText(R.string.cautions_email_already_use));
        emailCautions.setVisibility(View.VISIBLE);
    }

    private boolean validatePassword(String password) {

        boolean result = true;

        if (password.length() == 0) {
            passwordCautions.setText(context.getText(R.string.cautions_password_null));
            result = false;
        } else if (password.contains(" ")) {
            passwordCautions.setText(context.getText(R.string.cautions_password_spaces));
            result = false;
        } else if (password.length() < 6) {
            passwordCautions.setText(context.getText(R.string.cautions_password_short));
            result = false;
        } else if (password.length() > 15) {
            passwordCautions.setText(context.getText(R.string.cautions_password_long));
            result = false;
        }

        if (!result) {
            passwordCautions.setVisibility(View.VISIBLE);
            confirmPasswordCautions.setVisibility(View.INVISIBLE);
        } else {
            passwordCautions.setText(context.getText(R.string.good_password));
        }

        return result;
    }

    private boolean confirmPassword(String password, String confirm) {

        if (!password.equals(confirm)) {
            confirmPasswordCautions.setText(context.getText(R.string.cautions_password_confirm));
            confirmPasswordCautions.setVisibility(View.VISIBLE);
            return false;
        }
        confirmPasswordCautions.setVisibility(View.INVISIBLE);
        return true;
    }

    private void openMainActivity() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
    }

    private void registerAccount(String username, String userEmail, String userPassword){

        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("username", username)
                .add("email", userEmail)
                .add("password", userPassword)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        int statusCode = Integer.parseInt(response.body().string());

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                analyzeResponseCode(statusCode, userEmail, username);
                            }
                        });
                    } catch (Exception e) {
                        System.out.println(e);
                    }

                }
            }
        });
    }

    private void analyzeResponseCode(int code, String userEmail, String username) {
        switch (code) {
            case 200:
                System.out.println(1);
                User user = new User(context);
                user = new User(userEmail, username, context);
                openMainActivity();
                System.out.println(1);
                break;
            case 406:
                emailIsUsed();
                break;
        }
    }

}