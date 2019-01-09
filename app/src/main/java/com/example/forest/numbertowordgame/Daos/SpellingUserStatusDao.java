package com.example.forest.numbertowordgame.Daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;


import com.example.forest.numbertowordgame.Models.SpellingUserStatus;

@Dao
public interface SpellingUserStatusDao {
    @Insert
    void insertUserStatus(SpellingUserStatus...SpellingUserStatus);

    @Query("SELECT COUNT(*) FROM spelling_user_status WHERE user_id=:user_id AND level ='beginner'")
    int countAllAnsweredInBeginner(int user_id);

    @Query("SELECT COUNT(*) FROM spelling_user_status WHERE user_id=:user_id AND level ='advance'")
    int countAllAnsweredInAdvance(int user_id);

    @Query("SELECT COUNT(*) FROM spelling_user_status WHERE user_id=:user_id AND level ='expert'")
    int countAllAnsweredInExpert(int user_id);
}
