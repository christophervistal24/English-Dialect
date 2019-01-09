package com.example.forest.numbertowordgame.Daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.example.forest.numbertowordgame.Models.Points;
import com.example.forest.numbertowordgame.Models.Users;

@Dao
public interface PointsDao {

    @Insert
    void insertResult(Points ...points);

    @Query("SELECT COUNT(*) FROM points WHERE result = 1")
    int getAllCorrect();

    @Query("SELECT COUNT(*) FROM points WHERE result = 0")
    int getAllWrong();
}
