package com.example.forest.numbertowordgame.Repositories.Spelling;

import android.content.Context;

import com.example.forest.numbertowordgame.DB.DB;
import com.example.forest.numbertowordgame.Models.Pronouce;
import com.example.forest.numbertowordgame.Models.Spelling;
import com.example.forest.numbertowordgame.Repositories.Spelling.SpellingRepository;

import java.util.List;

public class SpellingRepository {
    public static String level = "beginner";
    public static void questionCreator(String file_name , String choice_a , String choice_b ,String correct_answer ,String level, Context context)
    {
        Spelling spelling = new Spelling();
        spelling.setFileName(file_name);
        spelling.setChoice_a(choice_a);
        spelling.setChoice_b(choice_b);
        spelling.setCorrect_answer(correct_answer);
        spelling.setLevel(level);
        DB.getInstance(context).spellingDao().insertAllSpellingQuestion(spelling);
    }

    public static List<Spelling> getLevelForUser(Context context, int user_id) {
        int grammarBeginner = DB.getInstance(context).spellingDao().countAllBeginnerQuestion();
        int grammarAdvance = DB.getInstance(context).spellingDao().countAllAdvanceQuestion();
        int beginnerAnswer = DB.getInstance(context).spellingUserStatusDao().countAllAnsweredInBeginner(user_id);
        int advanceAnswer = DB.getInstance(context).spellingUserStatusDao().countAllAnsweredInAdvance(user_id);

        if (grammarBeginner == beginnerAnswer ) {
            SpellingRepository.level = "advance";
        } else {
            SpellingRepository.level = "beginner";
        }

        if (grammarAdvance == advanceAnswer) {
            SpellingRepository.level = "expert";
        }

        return  DB.getInstance(context).spellingDao().getAllSpellingQuestionByLevel(SpellingRepository.level,user_id);


    }
}
