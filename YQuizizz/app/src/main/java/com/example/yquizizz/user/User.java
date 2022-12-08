package com.example.yquizizz.user;

import android.content.Context;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.dynamicanimation.animation.SpringAnimation;

import com.example.yquizizz.systemLink.SystemLink;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

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
    private Integer currentExp;
    private Integer level;

    public static final String userData = "userData.txt";
    public static final String session = "sessionInfo.txt";

    //Read data from storage
    public User(Context context) {

        File dir = context.getFilesDir();
        File file = new File(dir, userData);

        if (!validFile(context)) file.delete();

        if (file.isFile()) {
            try {
                FileInputStream fileInputStream = context.openFileInput(userData);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String rawData = bufferedReader.readLine();
                splitData(rawData);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println(e);
            }

        } else {
            initialNewUser();
            saveData(context);
            System.out.println(1);
        }
    }

    //Register new User
    public User(String email, String username, Context context) {
        this.email = email;
        this.username = username;
        this.currentExp = 0;
        this.level = 1;
        deleteData(context);
        saveData(context);
    }

    //User data from Server
    public User(String email, String username, Integer currentExp, Integer level, Context context) {
        this.email = email;
        this.username = username;
        this.currentExp = currentExp;
        this.level = level;
        System.out.println(convertDataToString());
        deleteData(context);
        saveData(context);
    }

    public void deleteData(Context context) {
        File dir = context.getFilesDir();
        File file = new File(dir, userData);
        file.delete();
    }

    public void saveData(Context context) {

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = context.openFileOutput(userData, Context.MODE_PRIVATE);
            fileOutputStream.write(convertDataToString().getBytes());
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        uploadUserData();
    }

    private void initialNewUser() {
        // using hash in the future
        this.email = "newplayer.lazy@gmail.com";
        this.username = "New Player";
        this.currentExp = 0;
        this.level = 1;
    }

    private void splitData(String rawData) {
        String[] data = rawData.split(",");
        this.email = data[0];
        this.username = data[1];
        this.currentExp = Integer.parseInt(data[2]);
        this.level = Integer.parseInt(data[3]);
    }

    private Integer getExpToNextLevel(){
        return 400 + (this.level/10)*100;
    }

    public Integer getCurrentProgress() {

        Double temp = currentExp.doubleValue()/getExpToNextLevel().doubleValue()*100;
        return temp.intValue();
    }

    public String getPointProgressText() {
        StringBuilder builder = new StringBuilder();
        builder.append(currentExp.toString()).append("/").append(getExpToNextLevel().toString());
        return builder.toString();
    }

    private Integer getUserTotalExp() {
        Integer total = 0;
        Integer base = 400;
        for (int i = 1;i < level;i++)
        {
            if (i%10 == 0) base += 100;
            total += base;
        }
        total += currentExp;
        return total;
    }

    public void updateUsername(String newUsername) {
        this.username = newUsername;
    }

    public String getEmail() {
        return this.email;
    }

    public void setLeaderboardDisplay(TextView userName, TextView rankView, TextView exp, TextView levelView, Integer rank) {
        userName.setText(this.username);
        rankView.setText(rank.toString());
        exp.setText(getUserTotalExp().toString());
        levelView.setText(level.toString());
    }

    public void setDisplay(TextView username, TextView level, TextView pointText, ProgressBar pointBar) {
        username.setText(this.username);
        level.setText(this.level.toString());
        pointText.setText(getPointProgressText());
        pointBar.setProgress(getCurrentProgress());
    }

    private boolean validFile(Context context) {

        try {
            FileInputStream fileInputStream = context.openFileInput(userData);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String rawData = bufferedReader.readLine();
            if (rawData.split(",").length != 4) return false;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e);
        }

        return true;
    }

    private String convertDataToString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.email).append(",");
        builder.append(this.username).append(",");
        builder.append(this.currentExp.toString()).append(",");
        builder.append(this.level.toString());

        return builder.toString();
    }

    public void updateExp(Integer exp, Context context) {

        currentExp += exp;
        if (currentExp > getExpToNextLevel()) {
            currentExp -= getExpToNextLevel();
            level++;
        }

        saveData(context);

    }

    public Integer getCurrentExp() {
        return this.currentExp;
    }

    public Integer getLevel() {
        return this.level;
    }

    private void uploadUserData() {
        System.out.println(12345567);
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("email", email)
                .add("username", username)
                .add("currentExp", currentExp.toString())
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
