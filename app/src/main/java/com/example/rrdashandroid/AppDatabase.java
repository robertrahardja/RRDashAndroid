package com.example.rrdashandroid;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.rrdashandroid.Objects.Tray;

@Database(entities = {Tray.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static  AppDatabase db;

    public abstract TrayDAO trayDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (db == null) {
            db = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "database-name").build();
        }
        return db;
    }
}