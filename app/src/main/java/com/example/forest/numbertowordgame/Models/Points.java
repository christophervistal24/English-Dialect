package com.example.forest.numbertowordgame.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "points")
public class Points {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int result;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

}
