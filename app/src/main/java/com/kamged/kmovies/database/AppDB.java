package com.kamged.kmovies.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.kamged.kmovies.models.Movie;
import com.kamged.kmovies.services.I_MovieDao;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class AppDB extends RoomDatabase {
    public abstract I_MovieDao movieDao();

    private static AppDB _instance;
    private static final Object LOCK = new Object();

    public static AppDB getInstance(Context context) {
        if(_instance == null) {
            synchronized (LOCK) {
                _instance = Room.databaseBuilder(context.getApplicationContext(), AppDB.class, "kmovies_db.db").build();
            }
        }

        return _instance;
    }
}
