package com.example.forest.numbertowordgame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.forest.numbertowordgame.Helpers.RedirectHelper;
import com.example.forest.numbertowordgame.Helpers.SharedPref;
import com.example.forest.numbertowordgame.Repositories.Users.UserRepository;
import com.wooplr.spotlight.SpotlightConfig;
import com.wooplr.spotlight.SpotlightView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CategoryActivity extends AppCompatActivity {

    @BindView(R.id.englishGrammar) Button englishGrammar;
    @BindView(R.id.spelling) Button spelling;
    @BindView(R.id.pronounce) Button pronounce;
    @BindView(R.id.userGreet) TextView userGreet;

    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);
        greetUser();
    }

    private void greetUser() {
        SharedPref.PREF_FILE = "user";
        username =  SharedPref.getSharedPreferenceString(getApplicationContext(),"username",null);
        userGreet.setText(getString(R.string.user_greet).concat(username));
    }


    @OnClick({R.id.englishGrammar,R.id.spelling,R.id.pronounce})
    public void selectCategory(View v) {
        Intent myIntent = null;
        switch (v.getId())
        {
            case R.id.englishGrammar :
                myIntent = new Intent(CategoryActivity.this, GrammarActivity.class);
                break;

            case R.id.spelling :
                myIntent = new Intent(CategoryActivity.this, SpellingActivity.class);
                break;

            case R.id.pronounce :
                myIntent = new Intent(CategoryActivity.this, PronounceActivity.class);
                break;
        }
        startActivity(myIntent);


    }

    @OnClick(R.id.logout)
    public void logout()
    {
        SharedPref.PREF_FILE = "user";
        SharedPref.setSharedPreferenceString(getApplicationContext(),"username",null);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (!username.isEmpty()) {
            new RedirectHelper(this,CategoryActivity.class);
        }
        super.onBackPressed();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }


}
