package com.example.forest.quickguess.Utilities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.forest.quickguess.AnswerQuestion;
import com.example.forest.quickguess.FunFacts;
import com.example.forest.quickguess.MenuFragment;
import com.example.forest.quickguess.QuestionResult;
import com.example.forest.quickguess.R;


public class FragmentUtil extends Fragment{
    public static boolean  sDisableFragmentAnimations = false;
    public AppCompatActivity activity;


    public FragmentUtil()
    {

    }

    public  void startMenuFragment(Context context)
    {
        activity = (AppCompatActivity) context;
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MenuFragment menuFragment = new MenuFragment();
        fragmentTransaction.add(R.id.fragment_one,menuFragment);
        fragmentTransaction.commit();
    }


    public void startFunFactsFragment(Context context)
    {
      if (context instanceof AnswerQuestion)
      {
          activity = (AppCompatActivity) context;
          AnswerQuestion answerQuestion = ((AnswerQuestion)context);
          FragmentManager fragmentManager = answerQuestion.getSupportFragmentManager();
          FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
          FunFacts funFacts = new FunFacts();
          fragmentTransaction.replace(R.id.fragment_fun_facts, funFacts);
          fragmentTransaction.addToBackStack(null);
          fragmentTransaction.commit();
      }
    }

    public void disposeBackStack()
    {
        int count = activity.getSupportFragmentManager().getBackStackEntryCount();
        if (count != 0)
        {
            activity.getSupportFragmentManager().popBackStack();
        }
    }




}
