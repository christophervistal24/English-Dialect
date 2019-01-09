package com.example.forest.numbertowordgame;

import android.content.res.Resources;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.forest.numbertowordgame.DB.DB;
import com.example.forest.numbertowordgame.Helpers.SharedPref;
import com.example.forest.numbertowordgame.Models.Pronouce;
import com.example.forest.numbertowordgame.Models.PronouceUserStatus;
import com.example.forest.numbertowordgame.Repositories.Pronouce.PronouceRepository;
import com.example.forest.numbertowordgame.Repositories.Users.UserRepository;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PronounceActivity extends AppCompatActivity {

    @BindView(R.id.playerName)
    TextView playerName;

    @BindView(R.id.correct) TextView correct;
    @BindView(R.id.wrong) TextView wrong;
    @BindView(R.id.noOfQuestion) TextView noOfQuestion;
    @BindView(R.id.userAnswer) EditText userAnswer;


    @BindView(R.id.home) Button home;
    @BindView(R.id.playVoice) Button playVoice;

    @BindView(R.id.level)
    Spinner spinner;
    String username , level;
    private static int userWrong = 0;
    private static int user_id;
    private Pronouce randomizedQuestion;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pronounce);
        ButterKnife.bind(this);
        playerInit();
        prepareQuestion();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (getIndex(spinner,PronouceRepository.level) >= i) {
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
        int correctAnswers = DB.getInstance(getApplicationContext()).pronounceDao().countAllCorrect(user_id);
        int no_of_questions = DB.getInstance(getApplicationContext()).pronounceDao().noOfQuestions();
        noOfQuestion.setText(correctAnswers + " / " + String.valueOf(no_of_questions));
        correct.setText("Correct : " + String.valueOf(correctAnswers));
        userWrong = SharedPref.getSharedPreferenceInt(getApplicationContext(),username.concat("_pronouce_wrongs"),0);
        wrong.setText("Wrong : " + String.valueOf(userWrong));
    }



    private void prepareQuestion() {
        noOfQuestionsInit();
        List<Pronouce> pronouceList = PronouceRepository.getLevelForUser(getApplicationContext(),user_id);
        randomizedQuestion = pronouceList.get(0);
        String filename = randomizedQuestion.getFilename();
        Resources res = getApplicationContext().getResources();
        int soundId = res.getIdentifier(filename, "raw", getApplicationContext().getPackageName());
        mediaPlayer = MediaPlayer.create(getApplicationContext(),soundId);
    }

    @OnClick(R.id.playVoice)
    public void startVoice(){
        mediaPlayer.start();
    }

    @OnClick(R.id.check)
    public void checkAnswer()
    {
        checkAnswer(userAnswer.getText().toString(),randomizedQuestion.getCorrect_answer());
        userAnswer.setText("");
        userAnswer.requestFocus();
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
        playerName.setText(username + " : " + PronouceRepository.level);
    }






    private void checkAnswer(String userAnswer, String correct_answer) {
        if (userAnswer.toLowerCase().equals(correct_answer.toLowerCase()))
        {
            PronouceUserStatus pronouceUserStatus = new PronouceUserStatus();
            pronouceUserStatus.setUser_id(user_id);
            pronouceUserStatus.setQuestion_id(randomizedQuestion.getId());
            pronouceUserStatus.setLevel(randomizedQuestion.getLevel());
            DB.getInstance(getApplicationContext()).pronouceUserStatusDao().insertUserStatus(pronouceUserStatus);
            TastyToast.makeText(getApplicationContext(),"Correct",TastyToast.LENGTH_SHORT,TastyToast.SUCCESS);
        } else {
            userWrong++;
            SharedPref.PREF_FILE = "user";
            SharedPref.setSharedPreferenceInt(getApplicationContext(),username.concat("_pronouce_wrongs"),userWrong);
            TastyToast.makeText(getApplicationContext(),"Your wrong : " + String.valueOf(userWrong),TastyToast.LENGTH_SHORT,TastyToast.ERROR);
        }
        prepareQuestion();
        playerInit();
        setValuesForSpinner();
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
        spinner.setSelection(getIndex(spinner,PronouceRepository.level));
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
