package com.example.yquizizz.loginActivity.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yquizizz.MainActivity;
import com.example.yquizizz.R;
import com.example.yquizizz.loginActivity.signup.Signup;
import com.example.yquizizz.user.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Login extends Fragment {

    private static final Integer day = 86400000;

    private EditText userEmailGetter;
    private EditText passwordGetter;

    private TextView emailCautions;
    private TextView passwordCautions;

    private AppCompatButton loginBtn;
    private AppCompatButton signupBtn;

    private CheckBox rememberMe;

    private Context context;

    private static final String url = "http://10.0.2.2:3000/login";

    public Login() {
        // Required empty public constructor
    }

    public static Login newInstance() {
        Login fragment = new Login();
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        context = view.getContext();

        userEmailGetter = (EditText) view.findViewById(R.id.loginUserEmail);
        passwordGetter = (EditText) view.findViewById(R.id.loginPassword);

        emailCautions = (TextView) view.findViewById(R.id.loginUserEmailCaution);
        passwordCautions = (TextView) view.findViewById(R.id.loginPasswordCaution);

        rememberMe = (CheckBox) view.findViewById(R.id.rememberMe);

        loginBtn = (AppCompatButton) view.findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = userEmailGetter.getText().toString();
                String password = passwordGetter.getText().toString();

                if (validateEmailClient(email)) {
                    login(email, password);
                }

            }
        });


        signupBtn = (AppCompatButton) view.findViewById(R.id.switchToSignup);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.loginBody, new Signup(), "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
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

    private void openMainActivity() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
    }

    private void login(String userEmail, String userPassword) {
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
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
                        JSONObject res = new JSONObject(response.body().string());

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                try {
                                    handlingResCode(Integer.parseInt(res.getString("resCode")), res);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                    } catch (Exception e) {
                        System.out.println(e);
                    }

                }
            }
        });
    }

    private void handlingResCode(int resCode, JSONObject res) {
        switch (resCode) {
            case 1:
                emailCautions.setText(context.getText(R.string.cautions_email_not_exist));
                emailCautions.setVisibility(View.VISIBLE);
                passwordCautions.setVisibility(View.INVISIBLE);
                break;
            case 2:
                emailCautions.setVisibility(View.INVISIBLE);
                passwordCautions.setText(context.getText(R.string.cautions_password_wrong));
                passwordCautions.setVisibility(View.VISIBLE);
                break;
            case 3:
                if (rememberMe.isChecked()) {
                    createSession(getContext());
                }
                try {
                    handlingResponse(res.getJSONObject("data"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                openMainActivity();
                break;
        }
    }

    private void handlingResponse(JSONObject userInfo) {
        try {

            String userEmailFromServer = userInfo.getString("email");
            String usernameFromServer = userInfo.getString("username");
            Integer userCurrentExpFromServer = Integer.parseInt(userInfo.getString("currentExp"));
            Integer userLevelFromServer = Integer.parseInt(userInfo.getString("currentLevel"));
            User user = new User(userEmailFromServer, usernameFromServer, userCurrentExpFromServer, userLevelFromServer, context);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void createSession(Context context) {

        try {
            FileOutputStream fileOutputStream = context.openFileOutput(User.session, Context.MODE_PRIVATE);
            Long time = System.currentTimeMillis() + 10L *day;
            fileOutputStream.write(time.toString().getBytes());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}