package com.example.yquizizz.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UserController extends SQLiteOpenHelper {

    private static final String USER = "USER";
    private static final String EMAIL = "EMAIL";
    private static final String USERNAME = "USERNAME";
    private static final String EXP = "EXP";
    private static final String LEVEL = "LEVEL";
    private static final String SESSION = "SESSION";

    public UserController(@Nullable Context context) {
        super(context, "user.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        String createTableStatement = "CREATE TABLE " + USER + " ( " + EMAIL + " TEXT, " + USERNAME + " TEXT, " + EXP + " INTEGER, " + LEVEL + " INTEGER, " + SESSION + " TEXT )";

        database.execSQL(createTableStatement);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean insertUser(UserModel userModel) {

        if (isAvailable()) deleteUserData();
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(EMAIL, userModel.getEmail());
        cv.put(USERNAME, userModel.getUsername());
        cv.put(EXP, userModel.getExp());
        cv.put(LEVEL, userModel.getLevel());
        cv.put(SESSION, userModel.getSession());

        database.insert(USER, null, cv);
        database.close();

        return true;
    }

    private boolean isAvailable() {

        SQLiteDatabase database = this.getReadableDatabase();

        String query = "SELECT COUNT(" + EMAIL + ") FROM " + USER;

        Cursor cursor = database.rawQuery(query, null);

        boolean result = !cursor.moveToFirst();

        cursor.close();
        database.close();

        return result;

    }

    public void deleteUserData() {

        SQLiteDatabase database = this.getWritableDatabase();

        database.execSQL("DELETE FROM " + USER);

        database.close();
    }

    public UserModel getUserData() {

        SQLiteDatabase database = this.getReadableDatabase();

        String query = "SELECT " + EMAIL + ", " + USERNAME + ", " + EXP + ", " + LEVEL + " FROM " + USER;

        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        UserModel model = new UserModel(cursor.getString(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3));

        cursor.close();
        database.close();

        return model;

    }

    public void updateUserData(UserModel model) {

        SQLiteDatabase database = this.getWritableDatabase();

        String username = model.getUsername();
        Integer exp = model.getExp();
        Integer level = model.getLevel();

        String query = "UPDATE " + USER + " SET " + USERNAME + " = \"" + username + "\", " + EXP + " = " + exp.toString() + ", " + LEVEL + " = " + level.toString();

        database.execSQL(query);
        database.close();
    }

    public String getSession() {

        SQLiteDatabase database = this.getReadableDatabase();

        String query = "SELECT " + SESSION + " FROM " + USER;

        Cursor cursor = database.rawQuery(query, null);

        String result = "0";
        if (cursor.moveToFirst()) {
            result = cursor.getString(0);
        }

        cursor.close();
        database.close();

        return result;
    }

}
