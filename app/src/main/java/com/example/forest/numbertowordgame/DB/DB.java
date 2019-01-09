package com.example.forest.numbertowordgame.DB;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.forest.numbertowordgame.Daos.GrammarDao;
import com.example.forest.numbertowordgame.Daos.PointsDao;
import com.example.forest.numbertowordgame.Daos.PronouceUserStatusDao;
import com.example.forest.numbertowordgame.Daos.PronounceDao;
import com.example.forest.numbertowordgame.Daos.SpellingDao;
import com.example.forest.numbertowordgame.Daos.SpellingUserStatusDao;
import com.example.forest.numbertowordgame.Daos.UserDao;
import com.example.forest.numbertowordgame.Daos.GrammarUserStatusDao;
import com.example.forest.numbertowordgame.Models.Grammar;
import com.example.forest.numbertowordgame.Models.Points;
import com.example.forest.numbertowordgame.Models.Pronouce;
import com.example.forest.numbertowordgame.Models.PronouceUserStatus;
import com.example.forest.numbertowordgame.Models.Spelling;
import com.example.forest.numbertowordgame.Models.SpellingUserStatus;
import com.example.forest.numbertowordgame.Models.UserStatus;
import com.example.forest.numbertowordgame.Models.Users;

@Database(entities = {
        Users.class,Points.class,Grammar.class,UserStatus.class,Pronouce.class,PronouceUserStatus.class,
        Spelling.class,SpellingUserStatus.class,
},version = 1)
public abstract class DB extends RoomDatabase
{
    private static DB appDatabase;
    private Context context;
    public abstract UserDao userDao();
    public abstract PointsDao pointsDao();
    public abstract GrammarDao grammarDao();
    public abstract GrammarUserStatusDao userStatusDao();
    public abstract PronounceDao pronounceDao();
    public abstract PronouceUserStatusDao pronouceUserStatusDao();
    public abstract SpellingDao spellingDao();
    public abstract SpellingUserStatusDao spellingUserStatusDao();



    public synchronized  static DB getInstance(Context context){
        if(appDatabase == null){
            appDatabase = Room.databaseBuilder(context, DB.class, "ed_game")
                    .allowMainThreadQueries()
                    .build();
        }
        return appDatabase;
    }

    public void destroyInstance() {
        appDatabase = null;
    }

}
