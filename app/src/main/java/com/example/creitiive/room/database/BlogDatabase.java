package com.example.creitiive.room.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.creitiive.room.dao.BlogDao;
import com.example.creitiive.room.entity.BlogEntity;

@Database(entities = BlogEntity.class, exportSchema = false, version = 1)
public abstract class BlogDatabase extends RoomDatabase {
    private static final String DB_NAME = "blog_db";
    private static BlogDatabase instance;

    public static synchronized BlogDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), BlogDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract BlogDao blogDao();
}

