package com.example.yquizizz.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.yquizizz.systemLink.SystemLink;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HistoryController extends SQLiteOpenHelper {

    private static final String HISTORY = "HISTORY";
    private static final String ID = "ID";
    private static final String TOPIC = "TOPIC";
    private static final String DIFFICULTY = "DIFFICULTY";
    private static final String TOTAL_POINT = "TOTAL_POINT";
    private static final String NUMBER_OF_QUIZ = "NUMBER_OF_QUIZ";
    private static final String NUMBER_OF_RIGHT_ANSWER = "NUMBER_OF_RIGHT_ANSWER";
    private static final String USER_POINT = "USER_POINT";
    private static final String DATE_ATTEMPT = "DATE_ATTEMPT";

    public HistoryController(@Nullable Context context) {
        super(context, "history.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String createTable = "CREATE TABLE " + HISTORY + " ( " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TOPIC + " TEXT, " + DIFFICULTY + " TEXT, " + TOTAL_POINT + " INTEGER, " + NUMBER_OF_QUIZ + " INTEGER, " + NUMBER_OF_RIGHT_ANSWER + " INTEGER, " + USER_POINT + " INTEGER, " + DATE_ATTEMPT + " TEXT)";

        database.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addOne(HistoryModel model, String userEmail) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(TOPIC, model.getTopic());
        cv.put(DIFFICULTY, model.getDifficulty());
        cv.put(TOTAL_POINT, model.getTotalPoint());
        cv.put(NUMBER_OF_QUIZ, model.getNumberOfQuiz());
        cv.put(NUMBER_OF_RIGHT_ANSWER, model.getNumberOfRightAnswer());
        cv.put(USER_POINT, model.getUserPoint());
        cv.put(DATE_ATTEMPT, model.getDateAttempt());

        long l = database.insert(HISTORY, null, cv);

        database.close();

        uploadToServer(model, userEmail);

        return l != -1;

    }

    public ArrayList<HistoryModel> findAll() {

        ArrayList<HistoryModel> result = new ArrayList<>();

        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM " + HISTORY + " ORDER BY " + ID + " DESC";

        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {

                String topic = cursor.getString(1);
                String difficulty = cursor.getString(2);
                Integer totalPoint = cursor.getInt(3);
                Integer numberOfQuiz = cursor.getInt(4);
                Integer numberOfRightAnswer = cursor.getInt(5);
                Integer userPoint = cursor.getInt(6);
                String dateAttempt = cursor.getString(7);

                HistoryModel model = new HistoryModel(topic, difficulty, totalPoint, numberOfQuiz, numberOfRightAnswer, userPoint, dateAttempt);

                result.add(model);

            } while (cursor.moveToNext());
        } else {
            System.out.println("Empty!");
        }

        cursor.close();
        database.close();

        return result;
    }

    public void deleteHistory() {

        SQLiteDatabase database = this.getWritableDatabase();

        String query = "DELETE FROM " + HISTORY;

        database.execSQL(query);

        database.close();

    }

    private Integer getIndex(){
        SQLiteDatabase database = this.getReadableDatabase();

        String query = "SELECT COUNT(" + ID + ") FROM " + HISTORY;

        Cursor cursor = database.rawQuery(query, null);

        cursor.moveToFirst();
        Integer result = cursor.getInt(0);

        cursor.close();
        database.close();

        return result;
    }

    public void fetchDataFromServer(ArrayList<HistoryModel> dataSet) {

        SQLiteDatabase database = this.getWritableDatabase();

        for (int i = 0;i < dataSet.size();i++) {

            ContentValues cv = new ContentValues();
            cv.put(TOPIC, dataSet.get(i).getTopic());
            cv.put(DIFFICULTY, dataSet.get(i).getDifficulty());
            cv.put(TOTAL_POINT, dataSet.get(i).getTotalPoint());
            cv.put(NUMBER_OF_QUIZ, dataSet.get(i).getNumberOfQuiz());
            cv.put(NUMBER_OF_RIGHT_ANSWER, dataSet.get(i).getNumberOfRightAnswer());
            cv.put(USER_POINT, dataSet.get(i).getUserPoint());
            cv.put(DATE_ATTEMPT, dataSet.get(i).getDateAttempt());

            database.insert(HISTORY, null, cv);

        }

    }

    private void uploadToServer(HistoryModel model, String userEmail) {

        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("email", userEmail)
                .add("index", getIndex().toString())
                .add("topic", model.getTopic())
                .add("difficulty", model.getDifficulty())
                .add("totalPoint", model.getTotalPoint().toString())
                .add("numberOfQuiz", model.getNumberOfQuiz().toString())
                .add("numberOfRightAnswer", model.getNumberOfRightAnswer().toString())
                .add("userPoint", model.getUserPoint().toString())
                .add("dateAttempt", model.getDateAttempt())
                .build();

        Request request = new Request.Builder()
                .url(SystemLink.uploadHistory)
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
