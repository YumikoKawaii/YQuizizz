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
import com.example.yquizizz.database.UserController;
import com.example.yquizizz.database.UserModel;
import com.example.yquizizz.loginActivity.login.Login;
import com.example.yquizizz.utils.SystemData;
import com.example.yquizizz.utils.SystemLink;

import java.io.IOException;
import java.util.regex.Pattern;

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

    private AppCompatButton registerBtn;
    private AppCompatButton signInBtn;

    private AppCompatButton playAsGuest;


    private Context context;


    public Signup() {
        // Required empty public constructor
    }

    public static Signup newInstance() {
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
        usernameGetter = view.findViewById(R.id.registerUsername);
        userEmailGetter = view.findViewById(R.id.userEmail);
        userPasswordGetter = view.findViewById(R.id.registerUserPassword);
        userPasswordConfirm = view.findViewById(R.id.userPasswordConfirm);

        emailCautions = view.findViewById(R.id.registerEmailCautions);
        passwordCautions = view.findViewById(R.id.registerPasswordCaution);
        confirmPasswordCautions = view.findViewById(R.id.registerConfirmPassword);
        usernameCautions = view.findViewById(R.id.registerUsernameCautions);

        registerBtn = view.findViewById(R.id.register);
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

        signInBtn = view.findViewById(R.id.switchToSignIn);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.loginBody, new Login(), "findThisFragment")
                        .commit();
            }
        });

        playAsGuest = view.findViewById(R.id.playAsGuest);
        playAsGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createGuestPlayer(context);
                openMainActivity();
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

    private boolean validateEmailClient(String email) {

        if (!patternMatches(email)) {
            emailCautions.setText(context.getText(R.string.cautions_email_invalid));
            emailCautions.setVisibility(View.VISIBLE);
            return false;
        } else {
            emailCautions.setVisibility(View.INVISIBLE);
        }

        return true;
    }

    public static boolean patternMatches(String email) {

        String pattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return Pattern.compile(pattern)
                .matcher(email)
                .matches();
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

    private void registerAccount(String username, String userEmail, String userPassword) {

        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("username", username)
                .add("email", userEmail)
                .add("password", userPassword)
                .build();

        Request request = new Request.Builder()
                .url(SystemLink.register)
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

                UserController controller = new UserController(context);

                UserModel model = new UserModel(userEmail, username, 0, 1);
                Long time = System.currentTimeMillis() + 10L * SystemData.day;
                model.setSession(time.toString());

                controller.insertUser(model);

                openMainActivity();
                break;
            case 406:
                emailIsUsed();
                break;
        }
    }

    private void createGuestPlayer(Context context) {

        UserController controller = new UserController(context);
        UserModel model = new UserModel("", "New Player", 0, 1);
        Long time = System.currentTimeMillis() + 10 * SystemData.day;
        model.setSession(time.toString());
        controller.insertUser(model);


    }

}