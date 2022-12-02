package com.example.yquizizz.user;

import android.content.Context;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class User {
    private String userID;
    private String username;
    private Integer currentExp;
    private Integer level;

    private static final String userData = "userData.txt";

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
    }

    private void initialNewUser() {
        // using hash in the future
        this.userID = "1";
        this.username = "New Player";
        this.currentExp = 0;
        this.level = 1;
    }

    private void splitData(String rawData) {
        String[] data = rawData.split(",");
        this.userID = data[0];
        this.username = data[1];
        this.currentExp = Integer.parseInt(data[2]);
        this.level = Integer.parseInt(data[3]);
    }



    private Integer getExpToNextLevel(){
        return 400 + (this.level/10)*100;
    }

    private Integer getCurrentProgress() {
        return currentExp/getExpToNextLevel();
    }

    private String getPointProgressText() {
        StringBuilder builder = new StringBuilder();
        builder.append(currentExp.toString()).append("/").append(getExpToNextLevel().toString());
        return builder.toString();
    }

    public void updateUsername(String newUsername) {
        this.username = newUsername;
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
        builder.append(userID).append(",");
        builder.append(username).append(",");
        builder.append(currentExp.toString()).append(",");
        builder.append(level.toString());

        return builder.toString();
    }

}
