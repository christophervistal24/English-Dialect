package com.example.forest.numbertowordgame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.forest.numbertowordgame.DB.DB;
import com.example.forest.numbertowordgame.Helpers.RandomHelper;
import com.example.forest.numbertowordgame.Helpers.RedirectHelper;
import com.example.forest.numbertowordgame.Helpers.SharedPref;
import com.example.forest.numbertowordgame.Models.Grammar;
import com.example.forest.numbertowordgame.Models.UserStatus;
import com.example.forest.numbertowordgame.Repositories.Grammar.GrammarRepository;
import com.example.forest.numbertowordgame.Repositories.Users.UserRepository;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GrammarActivity extends AppCompatActivity {

    @BindView(R.id.playerName) TextView playerName;

    @BindView(R.id.correct) TextView correct;
    @BindView(R.id.wrong) TextView wrong;

    @BindView(R.id.noOfQuestion) TextView noOfQuestion;
    @BindView(R.id.question) TextView question;
    @BindView(R.id.choiceA) Button choiceA;
    @BindView(R.id.choiceB) Button choiceB;
    @BindView(R.id.choiceC) Button choiceC;
    @BindView(R.id.choiceD) Button choiceD;

    @BindView(R.id.home) Button home;

    @BindView(R.id.level) Spinner spinner;
    String username , userAnswer , level;
    private static int userWrong = 0;
    private static int user_id;
    private Grammar randomizedQuestion;
    List<String> randomizedChoices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grammar);
        ButterKnife.bind(this);
        playerInit();
        prepareQuestion();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (getIndex(spinner,GrammarRepository.level) >= i) {
                        prepareQuestion();
                } else {
                    setValuesForSpinner();
                    TastyToast.makeText(GrammarActivity.this, "You can't select this level", TastyToast.LENGTH_SHORT,TastyToast.INFO);
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
        setValuesForSpinner();
    }

    private void noOfQuestionsInit() {
        int correctAnswers = DB.getInstance(getApplicationContext()).grammarDao().countAllCorrect(user_id);
        int no_of_questions = DB.getInstance(getApplicationContext()).grammarDao().noOfQuestions();
        noOfQuestion.setText(correctAnswers + " / " + String.valueOf(no_of_questions));
        correct.setText("Correct : " + String.valueOf(correctAnswers));
        int wrongs = SharedPref.getSharedPreferenceInt(getApplicationContext(),username.concat("_grammar_wrongs"),0);
        wrong.setText("Wrong : " + String.valueOf(wrongs));
    }


    private void prepareQuestion() {
        noOfQuestionsInit();
        List<Grammar> grammar = GrammarRepository.getLevelForUser(getApplicationContext(),user_id);
        //get all questions

        //randomize all questions
        grammar = RandomHelper.questions(grammar,grammar.size());
        //pick only one
        randomizedQuestion = grammar.get(0);
        //randomize choices
        randomizedChoices = RandomHelper.choices(Arrays.asList(randomizedQuestion.getChoice_a(),randomizedQuestion.getChoice_b(),randomizedQuestion.getChoice_c(),randomizedQuestion.getChoice_d()));
        //set up
        setQuestionAndChoices();
    }

    private void setQuestionAndChoices() {
        question.setText(randomizedQuestion.getQuestion());
        choiceA.setText(randomizedChoices.get(0));
        choiceB.setText(randomizedChoices.get(1));
        choiceC.setText(randomizedChoices.get(2));
        choiceD.setText(randomizedChoices.get(3));
    }


    @Override
    protected void onResume() {
        playerInit();
        prepareQuestion();
        setValuesForSpinner();
        super.onResume();
    }

    private void playerInit() {
        SharedPref.PREF_FILE = "user";
        username = SharedPref.getSharedPreferenceString(getApplicationContext(), "username", null);
        user_id = UserRepository.getIdByName(username,getApplicationContext());
        playerName.setText(username + " : " + GrammarRepository.level);
    }


    @OnClick({R.id.choiceA,R.id.choiceB,R.id.choiceC,R.id.choiceD})
    public void checkUserSelectedAnswer(View v)
    {
        userAnswer = null;
            switch (v.getId())
            {
                case R.id.choiceA:
                    userAnswer = choiceA.getText().toString();
                    break;

                case R.id.choiceB:
                    userAnswer = choiceB.getText().toString();
                    break;

                case R.id.choiceC:
                    userAnswer = choiceC.getText().toString();
                    break;

                case R.id.choiceD:
                    userAnswer = choiceD.getText().toString();
                    break;

        }
        checkAnswer(userAnswer,randomizedQuestion.getCorrect_answer());
    }

    private void checkAnswer(String userAnswer, String correct_answer) {
        if (userAnswer.toLowerCase().equals(correct_answer.toLowerCase()))
        {
            UserStatus userStatus = new UserStatus();
            userStatus.setUser_id(user_id);
            userStatus.setQuestion_id(randomizedQuestion.getId());
            userStatus.setLevel(randomizedQuestion.getLevel());
            DB.getInstance(getApplicationContext()).userStatusDao().insertUserStatus(userStatus);
            TastyToast.makeText(getApplicationContext(),"Correct",TastyToast.LENGTH_SHORT,TastyToast.SUCCESS);
        } else {
            userWrong++;
            SharedPref.PREF_FILE = "user";
            SharedPref.setSharedPreferenceInt(getApplicationContext(),username.concat("_grammar_wrongs"),userWrong);
            TastyToast.makeText(getApplicationContext(),"Your wrong : " + String.valueOf(userWrong),TastyToast.LENGTH_SHORT,TastyToast.ERROR);
        }
        prepareQuestion();
        setValuesForSpinner();
        playerInit();
    }

    private void setValuesForSpinner()
    {
    // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.levels, android.R.layout.simple_spinner_item);
    // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    // Apply the adapter to the spinner
            spinner.setAdapter(adapter);
            spinner.setSelection(getIndex(spinner,GrammarRepository.level));
    }

    //private method of your class
    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }



    @OnClick(R.id.home)
    public void gotoHome() { finish(); }







}
