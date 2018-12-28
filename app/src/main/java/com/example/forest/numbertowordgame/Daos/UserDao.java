package com.example.forest.numbertowordgame.Daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import com.example.forest.numbertowordgame.Models.User;

@Dao
public interface UserDao {

    @Insert
    void insertuser(User user);
}
