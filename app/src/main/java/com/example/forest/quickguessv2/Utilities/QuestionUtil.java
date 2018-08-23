package com.example.forest.quickguessv2.Utilities;

import com.example.forest.quickguessv2.DB.Questions.Questions;

import java.util.List;
import java.util.Random;

public class QuestionUtil {

    //randomize the questions
    public static List<Questions> questions(List<Questions> arr, int n)
    {
        Random r = new Random();

        for(int i = n-1; i > 0; i--)
        {
            int j = r.nextInt(i);

            Questions temp = arr.get(i);
            arr.set(i, arr.get(j));
            arr.set(j, temp);
        }

        return arr;
    }

    //randomize the choices
    public static String[] choices(String arr[] , int n)
    {
        Random r = new Random();

        for(int i = n-1; i > 0; i--)
        {
            int j = r.nextInt(i);

            String temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }

        return arr;
    }
}
