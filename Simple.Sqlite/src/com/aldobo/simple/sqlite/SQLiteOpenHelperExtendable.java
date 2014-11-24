package com.aldobo.simple.sqlite;


import android.database.sqlite.SQLiteDatabase;

public interface SQLiteOpenHelperExtendable {

    public void onCreate(SQLiteDatabase db);

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);
}
