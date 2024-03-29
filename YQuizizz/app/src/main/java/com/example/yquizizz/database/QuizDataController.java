package com.example.yquizizz.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.yquizizz.challenge.Quiz;

import java.util.ArrayList;

public class QuizDataController extends SQLiteOpenHelper {

    private static final String QUIZ_DATA = "QUIZ_DATA";
    private static final String ID = "ID";
    private static final String TOPIC = "TOPIC";
    private static final String DIFFICULTY = "DIFFICULTY";
    private static final String QUESTION = "QUESTION";
    private static final String ANSWER_1 = "ANSWER_1";
    private static final String ANSWER_2 = "ANSWER_2";
    private static final String ANSWER_3 = "ANSWER_3";
    private static final String ANSWER_4 = "ANSWER_4";

    public QuizDataController(@Nullable Context context) {
        super(context, "quizData.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String createTableStatement = "CREATE TABLE " + QUIZ_DATA + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TOPIC + " TEXT, " + DIFFICULTY + " TEXT, " + QUESTION + " TEXT, " + ANSWER_1 + " TEXT, " + ANSWER_2 + " TEXT, " + ANSWER_3 + " TEXT, " + ANSWER_4 + " TEXT)";
        database.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addOne(QuizModel quiz) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(TOPIC, quiz.getTopic());
        cv.put(DIFFICULTY, quiz.getDifficulty());
        cv.put(QUESTION, quiz.getQuestion());
        cv.put(ANSWER_1, quiz.getAnswer1());
        cv.put(ANSWER_2, quiz.getAnswer2());
        cv.put(ANSWER_3, quiz.getAnswer3());
        cv.put(ANSWER_4, quiz.getAnswer4());

        long l = database.insert(QUIZ_DATA, null, cv);

        database.close();

        return l != -1;
    }

    public ArrayList<Quiz> getTopicData(String topic, String difficulty) {

        SQLiteDatabase database = this.getReadableDatabase();

        ArrayList<Quiz> result = new ArrayList<>();

        String query;

        if (!topic.equals("General Knowledge")) {
            query = "SELECT * FROM " + QUIZ_DATA + " WHERE " + TOPIC + " = \"" + topic + "\"";

            switch (difficulty) {
                case "Easy":
                    query = query + " AND " + DIFFICULTY + " = \"Easy\"";
                    break;
                case "Normal":
                    query = query + " AND ( " + DIFFICULTY + " = \"Easy\" OR " + DIFFICULTY + " = \"Normal\" )";
                    break;
                case "Hard":
                    query = query + " AND ( " + DIFFICULTY + " = \"Normal\" OR " + DIFFICULTY + " = \"Hard\" )";
                    break;
                default:
                    query = query + " AND ( " + DIFFICULTY + " = \"Hard\" OR " + DIFFICULTY + " = \"Nightmare\" )";
                    break;
            }
        } else {
            query = "SELECT * FROM " + QUIZ_DATA + " WHERE ";

            switch (difficulty) {
                case "Easy":
                    query = query + DIFFICULTY + " = \"Easy\"";
                    break;
                case "Normal":
                    query = query + DIFFICULTY + " = \"Easy\" OR " + DIFFICULTY + " = \"Normal\"";
                    break;
                case "Hard":
                    query = query + DIFFICULTY + " = \"Normal\" OR " + DIFFICULTY + " = \"Hard\"";
                    break;
                default:
                    query = query + DIFFICULTY + " = \"Hard\" OR " + DIFFICULTY + " = \"Nightmare\"";
                    break;
            }

        }

        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {

            do {
                String id = cursor.getString(0);
                String to = cursor.getString(1);
                String di = cursor.getString(2);
                String question = cursor.getString(3);
                ArrayList<String> answerList = new ArrayList<>();
                for (int i = 4; i < 8; i++) {
                    answerList.add(cursor.getString(i));
                }

                result.add(new Quiz(id, to, di, question, answerList));
            } while (cursor.moveToNext());

        } else {
            System.out.println("Data is not available!");
        }

        database.close();

        return result;
    }

    public void resetDatabase() {
        SQLiteDatabase database = this.getWritableDatabase();

        database.execSQL("DELETE FROM " + QUIZ_DATA);

        database.close();
    }


    public int getSizeOfData() {
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT COUNT(ID) FROM " + QUIZ_DATA, null);

        int result = 0;
        if (cursor.moveToFirst()) {
            result = cursor.getInt(0);
        }

        cursor.close();
        database.close();

        return result;
    }

}
