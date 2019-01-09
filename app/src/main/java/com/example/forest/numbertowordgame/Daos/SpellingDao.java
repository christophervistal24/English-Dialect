package com.example.forest.numbertowordgame.Daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.forest.numbertowordgame.Models.Pronouce;
import com.example.forest.numbertowordgame.Models.Spelling;

import java.util.List;

@Dao
public interface SpellingDao {

    @Insert
    void insertAllSpellingQuestion(Spelling ...spellings);

    @Query("SELECT COUNT(*) FROM spelling")
    int countSpellingQuestions();

    @Query("SELECT * FROM spelling WHERE level = :level AND id NOT IN (SELECT spelling.id FROM spelling INNER JOIN spelling_user_status ON spelling.id = spelling_user_status.question_id WHERE spelling_user_status.user_id = :user_id)")
    List<Spelling> getAllSpellingQuestionByLevel(String level, int user_id);


    @Query("SELECT COUNT(*) FROM spelling WHERE level='beginner'")
    int countAllBeginnerQuestion();

    @Query("SELECT COUNT(*) FROM spelling WHERE level='advance'")
    int countAllAdvanceQuestion();

    @Query("SELECT COUNT(*) FROM spelling WHERE level='expert'")
    int countAllExpertQuestion();

    @Query("SELECT COUNT(*) as correct_answer FROM spelling INNER JOIN spelling_user_status ON spelling.id = spelling_user_status.question_id WHERE spelling_user_status.user_id = :user_id")
    int countAllCorrect(int user_id);

    @Query("SELECT COUNT(*) FROM spelling")
    int noOfQuestions();
}
