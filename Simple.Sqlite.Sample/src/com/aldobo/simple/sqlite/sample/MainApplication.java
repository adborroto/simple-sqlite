package com.aldobo.simple.sqlite.sample;


import android.app.Application;

import com.aldobo.simple.sqlite.SQLiteConfiguration;
import com.aldobo.simple.sqlite.SQLiteManager;
import com.aldobo.simple.sqlite.sample.models.*;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SQLiteConfiguration configuration = new SQLiteConfiguration.Builder()
                .setDatabaseName("my_database.db")
                .setDatabaseVersion(9)
                .registerTable(Person.class)
                .build();
        SQLiteManager.setConfiguration(configuration);

    }
}
