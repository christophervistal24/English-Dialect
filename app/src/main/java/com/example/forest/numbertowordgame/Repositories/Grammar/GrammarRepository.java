package com.example.forest.numbertowordgame.Repositories.Grammar;

import android.content.Context;
import android.util.Log;

import com.example.forest.numbertowordgame.DB.DB;
import com.example.forest.numbertowordgame.Models.Grammar;

import java.util.List;

public class GrammarRepository {

    public static String level = "beginner";
    public static void questionCreator(String question , String choice_a , String choice_b , String choice_c , String choice_d
        ,String correct_answer , String level ,Context context)
    {
        Grammar grammar = new Grammar();
        grammar.setQuestion(question);
        grammar.setChoice_a(choice_a);
        grammar.setChoice_b(choice_b);
        grammar.setChoice_c(choice_c);
        grammar.setChoice_d(choice_d);
        grammar.setCorrect_answer(correct_answer);
        grammar.setLevel(level);
        DB.getInstance(context).grammarDao().insertGrammarQuestion(grammar);
    }


    public static List<Grammar> getLevelForUser(Context context , int user_id)
    {
        int grammarBeginner = DB.getInstance(context).grammarDao().countAllBeginnerQuestion();
        int grammarAdvance = DB.getInstance(context).grammarDao().countAllAdvanceQuestion();
        int beginnerAnswer = DB.getInstance(context).userStatusDao().countAllAnsweredInBeginner(user_id);
        int advanceAnswer = DB.getInstance(context).userStatusDao().countAllAnsweredInAdvance(user_id);



        if (grammarBeginner == beginnerAnswer ) {
            GrammarRepository.level = "advance";
        } else {
            GrammarRepository.level = "beginner";
        }

        if (grammarAdvance == advanceAnswer) {
            GrammarRepository.level = "expert";
        }


        return  DB.getInstance(context).grammarDao().getAllGrammarQuestionByLevel(GrammarRepository.level,user_id);
    }


    public static int getAllCorrectAnswer(int user_id, Context context)
    {
        return DB.getInstance(context).grammarDao().countAllCorrect(user_id);
    }




}
