package com.example.yquizizz.user;

import android.content.Context;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.yquizizz.database.UserController;
import com.example.yquizizz.database.UserModel;
import com.example.yquizizz.utils.SystemData;
import com.example.yquizizz.utils.SystemLink;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class User {

    private String email;
    private String username;
    private Integer exp;
    private Integer level;

    //Read data from storage
    public User(Context context) {

        UserController controller = new UserController(context);
        UserModel model = controller.getUserData();

        this.email = model.getEmail();
        this.username = model.getUsername();
        this.exp = model.getExp();
        this.level = model.getLevel();

    }

    //Data from Server
    public User(String email, String username, Integer exp, Integer level) {
        this.email = email;
        this.username = username;
        this.exp = exp;
        this.level = level;
    }


    public void saveData(Context context) {

        UserController controller = new UserController(context);

        controller.updateUserData(new UserModel(this.email, this.username, this.exp, this.level));

        if (SystemData.checkConnection(context) && !email.equals("")) uploadUserData();
    }

    private Integer getExpToNextLevel(){
        return 400 + (this.level/10)*100;
    }

    public Integer getCurrentProgress() {

        Double temp = exp.doubleValue()/getExpToNextLevel().doubleValue()*100;
        return temp.intValue();
    }

    public String getPointProgressText() {

        return String.format("%d/%d",exp, getExpToNextLevel());

    }

    private Integer getUserTotalExp() {
        Integer total = 0;
        Integer base = 400;
        for (int i = 1;i < level;i++)
        {
            if (i%10 == 0) base += 100;
            total += base;
        }
        total += exp;
        return total;
    }

    public void updateUsername(String newUsername, Context context) {
        this.username = newUsername;
        saveData(context);
    }

    public String getEmail() {
        return this.email;
    }

    public void setLeaderboardDisplay(TextView userName, TextView rankView, TextView exp, TextView levelView, Integer rank) {
        userName.setText(this.username);
        if (email.equals("")) rankView.setText("???");
        else rankView.setText(rank.toString());
        exp.setText(getUserTotalExp().toString());
        levelView.setText(level.toString());
    }

    public void setHomeDisplay(TextView username, TextView level, TextView pointText, ProgressBar pointBar, TextView totalPoint) {
        username.setText(this.username);
        level.setText(this.level.toString());
        pointText.setText(getPointProgressText());
        pointBar.setProgress(getCurrentProgress());
        totalPoint.setText(getUserTotalExp().toString());
    }

    public void updateExp(Integer incomeExp, Context context) {

        exp += incomeExp;
        if (exp > getExpToNextLevel()) {
            exp -= getExpToNextLevel();
            level++;
        }

        saveData(context);

    }

    public Integer getExp() {
        return this.exp;
    }

    public Integer getLevel() {
        return this.level;
    }

    private void uploadUserData() {
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("email", email)
                .add("username", username)
                .add("currentExp", exp.toString())
                .add("currentLevel", level.toString())
                .build();

        Request request = new Request.Builder()
                .url(SystemLink.uploadUserData)
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
                        System.out.println("Upload Completed!");

                    } catch (Exception e) {
                        System.out.println(e);
                    }

                }
            }
        });
    }

}
