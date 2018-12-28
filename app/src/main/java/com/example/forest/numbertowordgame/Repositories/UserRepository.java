package com.example.forest.numbertowordgame.Repositories;

import android.content.Context;

import com.example.forest.numbertowordgame.DB.DB;
import com.example.forest.numbertowordgame.Models.User;

public class UserRepository {

    private Context context;

    public UserRepository()
    {

    }

    public static void createUser(String playerName,Context context)
    {
        User user = new User();
        user.setUsername(playerName);
        DB.getInstance(context).userDao().insertuser(user);
    }
}
