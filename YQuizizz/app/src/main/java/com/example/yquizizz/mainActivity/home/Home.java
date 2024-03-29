package com.example.yquizizz.mainActivity.home;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.yquizizz.R;
import com.example.yquizizz.mainActivity.selectChallenge.SelectChallenge;
import com.example.yquizizz.mainActivity.selectChallenge.loading.LoadingChallenge;
import com.example.yquizizz.user.User;
import com.example.yquizizz.utils.SystemData;

import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home extends Fragment {

    private Context context;

    private AppCompatImageButton changeAvatar;
    private AppCompatImageButton changeName;
    private TextView username;
    private TextView level;
    private TextView pointText;
    private ProgressBar pointBar;
    private CircleImageView userAvatar;
    private TextView userTotalPoint;

    private User user;

    private AppCompatButton playRandom;
    private AppCompatButton playPvP;

    private LoadingChallenge loading;

    public static final int id = 1;

    public Home() {
        // Required empty public constructor
    }

    public static Home newInstance() {
        Home fragment = new Home();
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

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        context = view.getContext();
        user = new User(context);

        userAvatar = view.findViewById(R.id.userAvatar);
        username = view.findViewById(R.id.username);
        level = view.findViewById(R.id.level);
        pointText = view.findViewById(R.id.pointText);
        pointBar = view.findViewById(R.id.pointBar);
        userTotalPoint = view.findViewById(R.id.totalPoint);

        user.setHomeDisplay(username, level, pointText, pointBar, userTotalPoint);

        playRandom = view.findViewById(R.id.playSolo);
        playRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Random random = new Random();

                String topic = SystemData.topicList.get(random.nextInt((SystemData.topicList.size() - 1)));
                String difficulty = SystemData.difficultyList.get(random.nextInt((SystemData.difficultyList.size() - 1)));

                System.out.println(topic + " " + difficulty);

                Bundle bundle = new Bundle();
                bundle.putString("topic", topic);
                bundle.putString("difficulty", difficulty);

                loading = new LoadingChallenge();
                loading.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_bottom, R.anim.exit_to_top, R.anim.enter_from_left, R.anim.exit_to_right)
                        .replace(R.id.body, loading, "findThisFragment")
                        .commit();
            }
        });

        changeName = (AppCompatImageButton) view.findViewById(R.id.ediNameButton);

        changeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openChangeNameDialog(Gravity.CENTER);
            }
        });

        playPvP = (AppCompatButton) view.findViewById(R.id.playPvP);
        playPvP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        return view;
    }

    private void openChangeNameDialog(int gravity) {

        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.change_name_popup_dialog);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        dialog.setCancelable(false);

        EditText newUsernameInput = (EditText) dialog.findViewById(R.id.userNewName);
        AppCompatImageButton cancel = (AppCompatImageButton) dialog.findViewById(R.id.exitEditNameDialog);
        AppCompatButton confirm = (AppCompatButton) dialog.findViewById(R.id.confirmChangeName);
        TextView cautions = (TextView) dialog.findViewById(R.id.cautions);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newUsername = newUsernameInput.getText().toString();
                switch (validateName(newUsername)) {
                    case 0:
                        user.updateUsername(newUsername, context);
                        username.setText(newUsername);
                        dialog.dismiss();
                        break;
                    case 1:
                        cautions.setText(view.getResources().getString(R.string.cautions_name_null));
                        break;
                    case 2:
                        cautions.setText(view.getResources().getString(R.string.cautions_name_spaces));
                        break;
                    case 3:
                        cautions.setText(view.getResources().getString(R.string.cautions_name_default));
                        break;
                }
            }
        });

        dialog.show();

    }

    private int validateName(String name) {

        if (name.length() == 0) return 1;

        String fixedName = name.trim().replaceAll(" +", " ");

        if (!name.equals(fixedName)) return 2;

        for (Character i : fixedName.toCharArray())
            if (!(Character.isAlphabetic(i) || Character.isDigit(i) || i == ' ')) return 3;

        return 0;
    }

    public void cancelTimer() {
        if (loading != null) {
            loading.cancelTimer();
        }
    }

}