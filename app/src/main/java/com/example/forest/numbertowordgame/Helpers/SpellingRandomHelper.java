package com.example.forest.numbertowordgame.Helpers;

import com.example.forest.numbertowordgame.Models.Grammar;
import com.example.forest.numbertowordgame.Models.Spelling;

import java.util.List;
import java.util.Random;

public class SpellingRandomHelper {
    public static List<Spelling> questions(List<Spelling> arr, int n)
    {
        Random r = new Random();

        for(int i = n-1; i > 0; i--)
        {
            int j = r.nextInt(i);

            Spelling temp = arr.get(i);
            arr.set(i, arr.get(j));
            arr.set(j, temp);
        }

        return arr;
    }

    //randomize the choices
    public static List<String> choices(List<String> arr)
    {
        Random r = new Random();

        for(int i = arr.size()-1; i > 0; i--)
        {
            int j = r.nextInt(i);

            String temp = arr.get(i);
            arr.set(i, arr.get(j));
            arr.set(j, temp);
        }

        return arr;
    }
}
