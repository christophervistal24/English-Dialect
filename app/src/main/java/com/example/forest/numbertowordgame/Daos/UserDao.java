package com.example.forest.numbertowordgame.Daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.forest.numbertowordgame.Models.Users;

@Dao
public interface UserDao {

    @Insert
    void insertUser(Users ...users);

    @Query("SELECT COUNT(*)  FROM users WHERE username=:username")
    boolean checkuser(String username);

    @Query("SELECT password FROM users WHERE username = :username")
    String checkPassword(String username);

    @Query("SELECT id FROM users WHERE username = :username")
    int getIdByName(String username);
}
