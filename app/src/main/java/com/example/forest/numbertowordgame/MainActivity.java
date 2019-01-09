package com.example.forest.numbertowordgame;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.forest.numbertowordgame.DB.DB;
import com.example.forest.numbertowordgame.Helpers.RedirectHelper;
import com.example.forest.numbertowordgame.Helpers.SharedPref;
import com.example.forest.numbertowordgame.Repositories.Grammar.GrammarRepository;
import com.example.forest.numbertowordgame.Repositories.Pronouce.PronouceRepository;
import com.example.forest.numbertowordgame.Repositories.Spelling.SpellingRepository;
import com.example.forest.numbertowordgame.Repositories.Users.UserRepository;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.sdsmdg.tastytoast.TastyToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.username)
    EditText username;

    @BindView(R.id.password)
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        SharedPref.PREF_FILE ="user";

        if (DB.getInstance(getApplicationContext()).grammarDao().countGrammarQuestions() == 0)
        {
            insertAllGrammarQuestions();
        }

        if (DB.getInstance(getApplicationContext()).pronounceDao().countPronounceQuestions() == 0)
        {
            insertAllPronouceQuestion();
        }

        if (DB.getInstance(getApplicationContext()).spellingDao().countSpellingQuestions() == 0)
        {
            insertAllSpelling();
        }

    }

    @OnClick(R.id.register)
    public void goToRegisterActivity()
    {
        new RedirectHelper(MainActivity.this,RegisterActivity.class);
    }

    @OnClick(R.id.login)
    public void checkUserCredentials()
    {
        String userUsername = username.getText().toString();
        String userPassword = password.getText().toString();
        boolean isCredentialsCorrect = UserRepository.isUserAlreadyExists(userUsername , getApplicationContext())
                    && UserRepository.isPasswordCorrect(userUsername , userPassword , getApplicationContext());
        if (isCredentialsCorrect)
        {
            SharedPref.PREF_FILE = "user";
            SharedPref.setSharedPreferenceString(getApplicationContext(),"username",null);
            SharedPref.setSharedPreferenceString(getApplicationContext(),"username",username.getText().toString());
            new RedirectHelper(MainActivity.this,CategoryActivity.class);
            username.setText("");
            password.setText("");
        } else {
            TastyToast.makeText(getApplicationContext(), "Please check your username or password", TastyToast.LENGTH_LONG, TastyToast.ERROR);
        }
    }

    public void insertAllGrammarQuestions()
    {
        /*BEGINNER*/
        GrammarRepository.questionCreator("He wants to get a better ______ and earn more money.","employ","job","work","employment","job","beginner",getApplicationContext());
        GrammarRepository.questionCreator("Managers set objectives, and decide _____ their organization can achieve them.","what","how","which","because","how","beginner",getApplicationContext());
        GrammarRepository.questionCreator("Obviously, objectives occasionally _____ be modified or changed.","have to","must to","shouldn't","ought","have to","beginner",getApplicationContext());
        GrammarRepository.questionCreator("A defect can be caused _____ negligence by one of the members of a team.","by","to","at","in","by","beginner",getApplicationContext());
        GrammarRepository.questionCreator("I _____ the piano since the age of five.","played","am playing","play","have played","have played","beginner",getApplicationContext());
        GrammarRepository.questionCreator("_____ is my best hobby.","Eat","Eating","Ate","Is eating","Eating","beginner",getApplicationContext());

        /*EXPERT*/
        GrammarRepository.questionCreator("Based on the forecast, the tornado ______ in the remote area.","has hit","will hit","hits","hit","will hit","expert",getApplicationContext());
        GrammarRepository.questionCreator("Tomorrow _____ another day.","is","will be","is going to be","has","is","expert",getApplicationContext());

        /*ADVANCE*/
        GrammarRepository.questionCreator("The earthquake ______ last night.","stroke","strucked","struck","strick","strucked","advance",getApplicationContext());
        GrammarRepository.questionCreator("While I _____, I heard a loud noise under my bed.","awake","awaken","was awake","is awake","was awake","advance",getApplicationContext());
    }

    public void insertAllPronouceQuestion()
    {
        PronouceRepository.questionCreator("add_odd","add","beginner",getApplicationContext());
        PronouceRepository.questionCreator("after_offer","after","beginner",getApplicationContext());
        PronouceRepository.questionCreator("wire_why","wire","advance",getApplicationContext());
        PronouceRepository.questionCreator("zero_xerox","zero","advance",getApplicationContext());
        PronouceRepository.questionCreator("zone_soon","zoon","expert",getApplicationContext());
    }

    public void insertAllSpelling()
    {
        SpellingRepository.questionCreator("add_odd","add","odd","add","beginner",getApplicationContext());
        SpellingRepository.questionCreator("after_offer","after","offer","after","beginner",getApplicationContext());
        SpellingRepository.questionCreator("wire_why","wire","why","wire","advance",getApplicationContext());
        SpellingRepository.questionCreator("zero_xerox","zero","xerox","zero","advance",getApplicationContext());
        SpellingRepository.questionCreator("zone_soon","zone","soon","zone","expert",getApplicationContext());
    }



    @Override
    protected void onResume() {

        super.onResume();
    }
}
