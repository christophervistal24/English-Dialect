package com.example.forest.numbertowordgame.Daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.forest.numbertowordgame.Models.Grammar;
import com.example.forest.numbertowordgame.Models.Pronouce;

import java.util.List;

@Dao
public interface PronounceDao {

    @Insert
    void insertPronouceQuestion(Pronouce ...pronouces);

    @Query("SELECT * FROM pronouce WHERE level = :level AND id NOT IN (SELECT pronouce.id FROM pronouce INNER JOIN pronouce_user_status ON pronouce.id = pronouce_user_status.question_id WHERE pronouce_user_status.user_id = :user_id)")
    List<Pronouce> getAllPronouceQuestionByLevel(String level, int user_id);

    @Query("SELECT COUNT(*) FROM pronouce")
    int countPronounceQuestions();

    @Query("SELECT COUNT(*) FROM pronouce WHERE level='beginner'")
    int countAllBeginnerQuestion();

    @Query("SELECT COUNT(*) FROM pronouce WHERE level='advance'")
    int countAllAdvanceQuestion();

    @Query("SELECT COUNT(*) FROM pronouce WHERE level='expert'")
    int countAllExpertQuestion();

    @Query("SELECT COUNT(*) as correct_answer FROM pronouce INNER JOIN pronouce_user_status ON pronouce.id = pronouce_user_status.question_id WHERE pronouce_user_status.user_id = :user_id")
    int countAllCorrect(int user_id);

    @Query("SELECT COUNT(*) FROM pronouce")
    int noOfQuestions();
}
