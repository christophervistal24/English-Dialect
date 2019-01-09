package com.example.forest.numbertowordgame;

import android.content.res.Resources;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.forest.numbertowordgame.DB.DB;
import com.example.forest.numbertowordgame.Helpers.SharedPref;
import com.example.forest.numbertowordgame.Helpers.SpellingRandomHelper;
import com.example.forest.numbertowordgame.Models.Pronouce;
import com.example.forest.numbertowordgame.Models.Spelling;
import com.example.forest.numbertowordgame.Models.SpellingUserStatus;
import com.example.forest.numbertowordgame.Models.UserStatus;
import com.example.forest.numbertowordgame.Repositories.Pronouce.PronouceRepository;
import com.example.forest.numbertowordgame.Repositories.Spelling.SpellingRepository;
import com.example.forest.numbertowordgame.Repositories.Users.UserRepository;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SpellingActivity extends AppCompatActivity {

    @BindView(R.id.playerName)
    TextView playerName;

    @BindView(R.id.correct) TextView correct;
    @BindView(R.id.wrong) TextView wrong;
    @BindView(R.id.noOfQuestion) TextView noOfQuestion;

    @BindView(R.id.home)
    Button home;
    @BindView(R.id.playVoice) Button playVoice;
    @BindView(R.id.choiceA) Button choiceA;
    @BindView(R.id.choiceB) Button choiceB;

    @BindView(R.id.level) Spinner spinner;
    String username , level , userAnswer;
    private static int userWrong = 0;
    private static int user_id;
    private Spelling randomizedQuestion;
    MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spelling);
        ButterKnife.bind(this);
        playerInit();
        prepareQuestion();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (getIndex(spinner,SpellingRepository.level) >= i) {
                    prepareQuestion();
                } else {
                    setValuesForSpinner();
                    TastyToast.makeText(getApplicationContext(), "You can't select this level", TastyToast.LENGTH_SHORT,TastyToast.INFO);
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
        setValuesForSpinner();
    }


    private void noOfQuestionsInit() {
        int correctAnswers = DB.getInstance(getApplicationContext()).spellingDao().countAllCorrect(user_id);
        int no_of_questions = DB.getInstance(getApplicationContext()).spellingDao().noOfQuestions();
        noOfQuestion.setText(correctAnswers + " / " + String.valueOf(no_of_questions));
        correct.setText("Correct : " + String.valueOf(correctAnswers));
        userWrong = SharedPref.getSharedPreferenceInt(getApplicationContext(),username.concat("_spelling_wrongs"),0);
        wrong.setText("Wrong : " + String.valueOf(userWrong));
    }


    private void prepareQuestion() {
        noOfQuestionsInit();
        List<Spelling> spellingList = SpellingRepository.getLevelForUser(getApplicationContext(),user_id);
        randomizedQuestion = spellingList.get(0);
        List<String> choices = SpellingRandomHelper
                               .choices(Arrays.asList(randomizedQuestion.getChoice_a(),randomizedQuestion.getChoice_b()));
        String filename = randomizedQuestion.getFileName();
        Resources res = getApplicationContext().getResources();
        int soundId = res.getIdentifier(filename, "raw", getApplicationContext().getPackageName());
        mediaPlayer = MediaPlayer.create(getApplicationContext(),soundId);
        choiceA.setText(choices.get(1));
        choiceB.setText(choices.get(0));
    }

    private void playerInit() {
        SharedPref.PREF_FILE = "user";
        username = SharedPref.getSharedPreferenceString(getApplicationContext(), "username", null);
        user_id = UserRepository.getIdByName(username,getApplicationContext());
        playerName.setText(username + " : " + SpellingRepository.level);
    }

    @OnClick(R.id.playVoice)
    public void startVoice(){
        mediaPlayer.start();
    }

    @Override
    protected void onResume() {
        playerInit();
        prepareQuestion();
        setValuesForSpinner();
        super.onResume();
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
        spinner.setSelection(getIndex(spinner,SpellingRepository.level));
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

    @OnClick({R.id.choiceA,R.id.choiceB})
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
        }
        checkAnswer(userAnswer,randomizedQuestion.getCorrect_answer());
    }

    private void checkAnswer(String userAnswer, String correct_answer) {
        if (userAnswer.toLowerCase().equals(correct_answer.toLowerCase()))
        {
            SpellingUserStatus spellingUserStatus = new SpellingUserStatus();
            spellingUserStatus.setUser_id(user_id);
            spellingUserStatus.setQuestion_id(randomizedQuestion.getId());
            spellingUserStatus.setLevel(randomizedQuestion.getLevel());
            DB.getInstance(getApplicationContext()).spellingUserStatusDao().insertUserStatus(spellingUserStatus);
            TastyToast.makeText(getApplicationContext(),"Correct",TastyToast.LENGTH_SHORT,TastyToast.SUCCESS);
        } else {
            userWrong++;
            SharedPref.PREF_FILE = "user";
            SharedPref.setSharedPreferenceInt(getApplicationContext(),username.concat("_spelling_wrongs"),userWrong);
            TastyToast.makeText(getApplicationContext(),"Your wrong : " + String.valueOf(userWrong),TastyToast.LENGTH_SHORT,TastyToast.ERROR);
        }
        prepareQuestion();
        setValuesForSpinner();
        playerInit();
    }


    @OnClick(R.id.home)
    public void gotoHome()
    {
        finish();
    }

}
