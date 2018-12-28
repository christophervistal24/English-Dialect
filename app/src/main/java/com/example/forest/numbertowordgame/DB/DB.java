package com.example.forest.numbertowordgame.DB;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.forest.numbertowordgame.Daos.UserDao;
import com.example.forest.numbertowordgame.Models.User;

@Database(entities = {User.class},version = 1)
public abstract class DB extends RoomDatabase
{
    private static DB appDatabase;
    private Context context;
    public abstract UserDao userDao();


    public synchronized  static DB getInstance(Context context){
        if(appDatabase == null){
            appDatabase = Room.databaseBuilder(context.getApplicationContext(), DB.class, "number_to_word")
                    .allowMainThreadQueries()
                    .build();
        }
        return appDatabase;
    }

    public void destroyInstance() {
        appDatabase = null;
    }

}
