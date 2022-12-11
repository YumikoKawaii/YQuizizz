package com.example.yquizizz.mainActivity.submitIdea;

import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yquizizz.R;
import com.example.yquizizz.utils.SystemData;

import java.util.Timer;
import java.util.TimerTask;

public class SubmitIdea extends Fragment {

    private EditText userIdea;
    private TextView ideaCaution;
    private AppCompatButton submitBtn;

    private Dialog dialog;

    public SubmitIdea() {
        // Required empty public constructor
    }

    public static SubmitIdea newInstance(String param1, String param2) {
        SubmitIdea fragment = new SubmitIdea();
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
        View view = inflater.inflate(R.layout.fragment_submit_idea, container, false);

        userIdea = view.findViewById(R.id.userIdea);
        submitBtn = view.findViewById(R.id.submitIdeaBtn);
        ideaCaution = view.findViewById(R.id.ideaCaution);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idea = userIdea.getText().toString();
                if (validateIdea(idea)) {
                    if (SystemData.checkConnection(getContext())) {
                        JavaMailAPI javaMailAPI = new JavaMailAPI(getContext(), SystemData.RECEIVER, SystemData.EMAIL_SUBJECT, idea);
                        javaMailAPI.execute();
                        showDialog();

                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                            }
                        }, 2000);

                    } else {
                        Toast.makeText(getContext(), "No Internet connection!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    ideaCaution.setVisibility(View.VISIBLE);
                }
            }
        });

        return view;
    }

    private void showDialog() {

        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.thank_for_an_idea_dialog);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);

        dialog.setCancelable(false);

        dialog.show();
    }

    private boolean validateIdea(String idea) {
        return idea.length() != 0;
    }

}