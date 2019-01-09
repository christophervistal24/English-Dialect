package com.example.forest.numbertowordgame.Repositories.Pronouce;

import android.content.Context;
import android.util.Log;

import com.example.forest.numbertowordgame.DB.DB;
import com.example.forest.numbertowordgame.Models.Grammar;
import com.example.forest.numbertowordgame.Models.Pronouce;
import com.example.forest.numbertowordgame.Repositories.Grammar.GrammarRepository;

import java.util.List;

public class PronouceRepository {
    public static String level = "beginner";

    public static void questionCreator(String file_name , String correct_answer ,String level, Context context)
    {
        Pronouce pronouce = new Pronouce();
        pronouce.setFilename(file_name);
        pronouce.setCorrect_answer(correct_answer);
        pronouce.setLevel(level);
        DB.getInstance(context).pronounceDao().insertPronouceQuestion(pronouce);
    }

    public static List<Pronouce> getLevelForUser(Context context , int user_id)
    {
        int grammarBeginner = DB.getInstance(context).pronounceDao().countAllBeginnerQuestion();
        int grammarAdvance = DB.getInstance(context).pronounceDao().countAllAdvanceQuestion();
        int beginnerAnswer = DB.getInstance(context).pronouceUserStatusDao().countAllAnsweredInBeginner(user_id);
        int advanceAnswer = DB.getInstance(context).pronouceUserStatusDao().countAllAnsweredInAdvance(user_id);

        if (grammarBeginner == beginnerAnswer ) {
            PronouceRepository.level = "advance";
        } else {
            PronouceRepository.level = "beginner";
        }

        if (grammarAdvance == advanceAnswer) {
            PronouceRepository.level = "expert";
        }

        return  DB.getInstance(context).pronounceDao().getAllPronouceQuestionByLevel(PronouceRepository.level,user_id);
    }

}
