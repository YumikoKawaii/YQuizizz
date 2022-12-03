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
import com.example.yquizizz.user.User;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View fragView;
    private Context context;

    private AppCompatImageButton changeAvatar;
    private AppCompatImageButton changeName;
    private TextView username;
    private TextView level;
    private TextView pointText;
    private ProgressBar pointBar;
    private CircleImageView userAvatar;


    private User user;

    private AppCompatButton playSolo;
    private AppCompatButton playPvP;

    private static final String userData = "userData.txt";


    public Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */
    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragView = inflater.inflate(R.layout.fragment_home, container, false);
        context = fragView.getContext();
        user = new User(context);

        try {

            userAvatar = (CircleImageView) fragView.findViewById(R.id.userAvatar);
            username = (TextView) fragView.findViewById(R.id.username);
            level = (TextView) fragView.findViewById(R.id.level);
            pointText = (TextView) fragView.findViewById(R.id.pointText);
            pointBar = (ProgressBar) fragView.findViewById(R.id.pointBar);

            user.setDisplay(username, level, pointText, pointBar);

            playSolo = (AppCompatButton) fragView.findViewById(R.id.playSolo);
            playSolo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment nextFrag = new SelectChallenge();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.body, nextFrag, "findThisFragment")
                            .addToBackStack(null)
                            .commit();
                }
            });

            changeName = (AppCompatImageButton) fragView.findViewById(R.id.ediNameButton);

            changeName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openChangeNameDialog(Gravity.CENTER);
                }
            });

        } catch (Exception e) {
            System.out.println(e);
        }

        return fragView;
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

        dialog.setCancelable(true);

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
                        user.updateUsername(newUsername);
                        user.saveData(context);
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

        String fixedName = name.trim().replaceAll(" +", " ");;

        if (!name.equals(fixedName)) return 2;

        for (Character i : fixedName.toCharArray())
            if (!(Character.isAlphabetic(i) || Character.isDigit(i) || i == ' ')) return 3;

        return 0;
    }
}