package com.example.forest.quickguessv2.Utilities;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.forest.quickguessv2.MenuFragment;
import com.example.forest.quickguessv2.R;


public class FragmentUtil extends Fragment{

    public AppCompatActivity activity;

    public FragmentUtil()
    {

    }

    public void startMenuFragment(Context context)
    {
        activity = (AppCompatActivity) context;
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MenuFragment menuFragment = new MenuFragment();
        fragmentTransaction.add(R.id.fragment_one,menuFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
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
