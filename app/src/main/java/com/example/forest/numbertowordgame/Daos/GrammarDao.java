package com.example.forest.numbertowordgame.Daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.forest.numbertowordgame.Models.Grammar;
import com.example.forest.numbertowordgame.Models.Pronouce;

import java.util.List;

@Dao
public interface GrammarDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertGrammarQuestion(Grammar ...grammars);

    @Query("SELECT COUNT(*) FROM grammar")
    int countGrammarQuestions();

//    @Query("SELECT * FROM grammar WHERE level=:level")
//    List<Grammar> getAllGrammarQuestionByLevel(String level);
//
//    @Query("SELECT * FROM questions WHERE category_id = :category_id AND classication_id = :classification_id AND id NOT " +
//            "IN (SELECT question_id FROM user_status)")
    @Query("SELECT * FROM grammar WHERE level = :level AND id NOT IN (SELECT grammar.id FROM grammar INNER JOIN user_status ON grammar.id = user_status.question_id WHERE user_status.user_id = :user_id)")
    List<Grammar> getAllGrammarQuestionByLevel(String level, int user_id);


    @Query("SELECT COUNT(*) as correct_answer FROM grammar INNER JOIN user_status ON grammar.id = user_status.question_id WHERE user_status.user_id = :user_id")
    int countAllCorrect(int user_id);

    @Query("SELECT COUNT(*) FROM grammar")
    int noOfQuestions();

    @Query("SELECT COUNT(*) FROM grammar WHERE level='beginner'")
    int countAllBeginnerQuestion();

    @Query("SELECT COUNT(*) FROM grammar WHERE level='advance'")
    int countAllAdvanceQuestion();

    @Query("SELECT COUNT(*) FROM grammar WHERE level='expert'")
    int countAllExpertQuestion();

}

