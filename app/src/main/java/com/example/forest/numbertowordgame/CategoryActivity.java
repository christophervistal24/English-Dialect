package com.example.forest.numbertowordgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.forest.numbertowordgame.Helpers.RedirectHelper;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.btnEasy,R.id.btnModerate,R.id.btnDifficult})
    public void selectCategory(View v)
    {
        Intent myIntent = null;
        switch (v.getId())
        {
            case R.id.btnEasy :
                    myIntent = new Intent(CategoryActivity.this, PlayActivity.class);
                    myIntent.putExtra("min",1);
                    myIntent.putExtra("max",100);
                    myIntent.putExtra("counter",11000);
                break;

            case R.id.btnModerate :
                    myIntent = new Intent(CategoryActivity.this, PlayActivity.class);
                    myIntent.putExtra("min",100);
                    myIntent.putExtra("max",10000);
                    myIntent.putExtra("counter",21000);
                break;

            case R.id.btnDifficult :
                    myIntent = new Intent(CategoryActivity.this, PlayActivity.class);
                    myIntent.putExtra("min",10000);
                    myIntent.putExtra("max",1000000);
                    myIntent.putExtra("counter",31000);
                break;
        }
        CategoryActivity.this.startActivity(myIntent);
    }

}
