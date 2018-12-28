package com.example.forest.numbertowordgame.Helpers;

import android.app.Activity;
import android.content.Intent;

import com.example.forest.numbertowordgame.CategoryActivity;
import com.example.forest.numbertowordgame.MainActivity;

import java.util.Map;

public class RedirectHelper {

    Intent intent;
    public RedirectHelper(Activity fromActivity, Class<?> toActivity)
    {
        fromActivity.startActivity(new Intent(fromActivity, toActivity));
    }

}
