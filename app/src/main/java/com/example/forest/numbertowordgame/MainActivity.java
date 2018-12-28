package com.example.forest.numbertowordgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.forest.numbertowordgame.DB.DB;
import com.example.forest.numbertowordgame.Helpers.RedirectHelper;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btnstartToPlay,R.id.btnOptions})
    public void proceed(View v)
    {
        switch (v.getId())
        {
            case R.id.btnstartToPlay :
                new RedirectHelper(this, CategoryActivity.class);
                break;

            case R.id.btnOptions :
                new RedirectHelper(this,CategoryActivity.class);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        DB.getInstance(getApplicationContext()).destroyInstance();
        super.onDestroy();
    }

}
