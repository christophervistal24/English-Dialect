package com.example.forest.numbertowordgame;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.forest.numbertowordgame.Classes.NumberToWordConversion;
import com.example.forest.numbertowordgame.DB.DB;
import com.example.forest.numbertowordgame.Models.Points;
import com.example.forest.numbertowordgame.Repositories.Points.PointsRepository;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class PlayActivity extends AppCompatActivity {

    @BindView(R.id.number) TextView generatedNumber;
    @BindView(R.id.numberShadow) TextView generatedNumberShadow;
    @BindView(R.id.userAnswer) EditText userAnswer;
    @BindView(R.id.timer) TextView timer;
    @BindView(R.id.correct) TextView correct;
    @BindView(R.id.wrong) TextView wrong;

    private int min , max , randomNumber;
    private boolean isCounterRunning = false;
    private static int counter;
    CountDownTimer countDownTimer;
    private int questionResult;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);
        getIntentParameters();
        startTimer();
        generateQuestion();
        initUserPoints();
        addMobInit();
    }

    private void addMobInit() {
        MobileAds.initialize(this, getString(R.string.google_ad_mod_id));
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-2034994780703506~2365933413/450");
        mAdView = findViewById(R.id.adView);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    //user want to answer new question
    @OnClick(R.id.btnNextNumber)
    public void userClickNext()
    {
        countDownTimer.cancel();
        checkAnswer();
        isCounterRunning = false;
        countDownTimer.start();
        //clear the edittext and generate new number
        userAnswer.setText("");
        userAnswer.requestFocus();
        generateQuestion();
    }


    //get the minimum and maximum range depending on it's category
    private void getIntentParameters()
    {
        Intent myIntent = getIntent();
        //get the parameter that in intent
        min = myIntent.getIntExtra("min",1);
        max = myIntent.getIntExtra("max",20);
        counter = myIntent.getIntExtra("counter",10000);
    }

    //check the user answer
    private void checkAnswer()
    {
        //remove all white space & convert to lowercase any characters for two string and compare
        String answerOfUser = userAnswer.getText().toString()
                                         .replaceAll("\\s|-","").toLowerCase();
        String correctAnswer = NumberToWordConversion.convert(randomNumber)
                                                    .replaceAll("\\s","").toLowerCase();
        Points points = new Points();
        if (answerOfUser.equals(correctAnswer))
        {
            points.setResult(1);
        } else {
            points.setResult(0);
        }
        DB.getInstance(getApplicationContext()).pointsDao().insertResult(points);
        initUserPoints();
    }

    private void initUserPoints() {
        int allCorrect = DB.getInstance(getApplicationContext()).pointsDao().getAllCorrect();
        int allWrong = DB.getInstance(getApplicationContext()).pointsDao().getAllWrong();
        correct.setText(this.getString(R.string.correct).concat(" " + String.valueOf(allCorrect)));
        wrong.setText(this.getString(R.string.wrong).concat(" " + String.valueOf(allWrong)));
    }


    private void generateQuestion()
    {
       //generate random number from min to max this will depend on the category
       //that the user choose
       randomNumber = new Random().nextInt((max - min) + 1);
       generatedNumber.setText(String.valueOf(randomNumber));
       generatedNumberShadow.setText(String.valueOf(randomNumber));
    }



    private  void startTimer() {
        if (!isCounterRunning) {
            isCounterRunning  = true;
            countDownTimer = new CountDownTimer(PlayActivity.counter, 1000) {
                @Override
                public void onTick(long timeRemaining) {
                    int remainingTime = (int) (TimeUnit.MILLISECONDS.toSeconds(timeRemaining) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeRemaining)));
                    timer.setText(String.valueOf(remainingTime));
                }

                @Override
                public void onFinish() {
                    checkAnswer();
                    generateQuestion();
                    userAnswer.setText(null);
                    userAnswer.requestFocus();
                    isCounterRunning = false;
                    countDownTimer.start();
                }
            }.start();
        } else {
            countDownTimer.cancel();
            countDownTimer.start();
        }
    }


    @Override
    protected void onResume() {
        countDownTimer.cancel();
        countDownTimer.start();
        userAnswer.setText("");
        generateQuestion();
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        countDownTimer.cancel();
        isCounterRunning = false;
        super.onBackPressed();
    }
}
