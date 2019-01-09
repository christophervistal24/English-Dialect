package com.example.forest.numbertowordgame.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "grammar")
public class Grammar {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String question;
    public String choice_a;
    public String choice_b;
    public String choice_c;
    public String choice_d;
    public String correct_answer;
    public String level;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getChoice_a() {
        return choice_a;
    }

    public void setChoice_a(String choice_a) {
        this.choice_a = choice_a;
    }

    public String getChoice_b() {
        return choice_b;
    }

    public void setChoice_b(String choice_b) {
        this.choice_b = choice_b;
    }

    public String getChoice_c() {
        return choice_c;
    }

    public void setChoice_c(String choice_c) {
        this.choice_c = choice_c;
    }

    public String getChoice_d() {
        return choice_d;
    }

    public void setChoice_d(String choice_d) {
        this.choice_d = choice_d;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
