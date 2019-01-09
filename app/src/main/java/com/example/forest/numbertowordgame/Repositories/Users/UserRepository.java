package com.example.forest.numbertowordgame.Repositories.Users;

import android.content.Context;

import com.example.forest.numbertowordgame.DB.DB;
import com.example.forest.numbertowordgame.Models.Users;

public class UserRepository {

    private Context context;


    public UserRepository()
    {

    }

    public static void createUser(String firstname , String lastname , String birthday , String password , String username , Context context)
    {
        Users users = new Users();
        users.setUsername(username);
        users.setFirstname(firstname);
        users.setLastname(lastname);
        users.setPassword(password);
        users.setBirthday(birthday);
        DB.getInstance(context).userDao().insertUser(users);
    }



    public static boolean isUserAlreadyExists(String username , Context context)
    {
        return DB.getInstance(context).userDao().checkuser(username);
    }

    public static boolean isPasswordCorrect(String userUsername , String userPassword , Context context)
    {
        String password =  DB.getInstance(context).userDao().checkPassword(userUsername);
        return password.equals(userPassword);

    }


    public static int getIdByName(String username , Context context) {
        return DB.getInstance(context).userDao().getIdByName(username);
    }



}
