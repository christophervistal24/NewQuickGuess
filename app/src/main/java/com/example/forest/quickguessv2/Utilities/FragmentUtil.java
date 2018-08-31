package com.example.forest.quickguessv2.Utilities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.forest.quickguessv2.AnswerQuestion;
import com.example.forest.quickguessv2.FunFacts;
import com.example.forest.quickguessv2.MenuFragment;
import com.example.forest.quickguessv2.QuestionResult;
import com.example.forest.quickguessv2.QuitFragment;
import com.example.forest.quickguessv2.R;


public class FragmentUtil extends Fragment{

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
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void startQuitFragment()
    {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        QuitFragment QuitFragment = new QuitFragment();
        fragmentTransaction.add(R.id.fragment_quit,QuitFragment);
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
          fragmentTransaction.add(R.id.fragment_fun_facts, funFacts);
          fragmentTransaction.addToBackStack(null);
          fragmentTransaction.commit();
      }
    }

    public void startResultFragment(Context context,Bundle bundle)
    {

        if (context instanceof AnswerQuestion)
        {
            activity = (AppCompatActivity) context;
            AnswerQuestion answerQuestion = ((AnswerQuestion)context);
            FragmentManager fragmentManager = answerQuestion.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            QuestionResult questionResult = new QuestionResult();
            fragmentTransaction.add(R.id.questionResult,questionResult);
            questionResult.setArguments(bundle);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    public boolean disposeBackStack()
    {
        int count = activity.getSupportFragmentManager().getBackStackEntryCount();
        if (count != 0)
        {
            activity.getSupportFragmentManager().popBackStack();
            return true;
        }
        return false;
    }

}
