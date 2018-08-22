package com.example.forest.quickguessv2;


import android.arch.persistence.room.Room;
import android.graphics.Typeface;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.forest.quickguessv2.DBModule.MyAppDB;
import com.example.forest.quickguessv2.DBModule.QuestionCategory;
import com.example.forest.quickguessv2.DBModule.User;
import com.example.forest.quickguessv2.Helpers.FontHelper;
import com.example.forest.quickguessv2.Helpers.InputHelpers;
import com.example.forest.quickguessv2.Helpers.SharedPreferenceHelper;
import com.example.forest.quickguessv2.Helpers.WindowHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.username) EditText username;
    @BindView(R.id.welcomeLayout) RelativeLayout welcomeLayout;
    public static MyAppDB myAppDB;
    String sUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WindowHelper.hideNavigationBar(this);
        Typeface fontHelper = new FontHelper().dimboFont(this);
        ButterKnife.bind(this);
        SharedPreferenceHelper.PREF_FILE = "user";

        myAppDB = Room.databaseBuilder(getApplicationContext(),MyAppDB.class,"quickguess")
                .allowMainThreadQueries()
                .build();
        username.setTypeface(fontHelper);
        sUsername = SharedPreferenceHelper.getSharedPreferenceString(this,"username",null);
        isAlreadyRegistered(sUsername);
        insertCategories();
    }

    @Override
    protected void onResume() {
        isAlreadyRegistered(sUsername);
        super.onResume();
        WindowHelper.hideNavigationBar(this);
    }


    @OnClick(R.id.createUser)
    public void create() {
        String player = username.getText().toString();
        if  (player.isEmpty()  || InputHelpers.isProperUsername(player))
        {
            username.setError("Please provide a proper username");
            return;
        }
        //insert to database
        User user = new User();
        user.setUsername(player);
        MainActivity.myAppDB.myDao().addUser(user);
        SharedPreferenceHelper.setSharedPreferenceString(this,"username",player);
        username.setText("");
        isAlreadyRegistered(player);
    }

    private void displayMenu()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MenuFragment menuFragment = new MenuFragment();
//        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_right,R.anim.enter_from_right,R.anim.exit_to_right);
        fragmentTransaction.add(R.id.fragment_one,menuFragment);
        fragmentTransaction.commit();
    }

    private void isAlreadyRegistered(String username)
    {
         if (MainActivity.myAppDB.myDao().isUserExists(username)){
             welcomeLayout.setVisibility(View.GONE);
             displayMenu();
         } else {
             welcomeLayout.setVisibility(View.VISIBLE);
         }
    }


    private void addCategory(String category , String category_description)
    {
            QuestionCategory questionCategory = new QuestionCategory();
            questionCategory.setCategory(category);
            questionCategory.setCategory_description(category_description);
            MainActivity.myAppDB.categoriesQuestionDao().insertAll(questionCategory);
    }

    private void insertCategories()
    {
        int countCategories = MainActivity.myAppDB.categoriesQuestionDao().countCategories();
        if  (countCategories == 0)
        {
            addCategory("people"," ");
            addCategory("plants"," ");
            addCategory("animals"," ");
            addCategory("geography"," ");
            addCategory("sports"," ");
            addCategory("music"," ");
            addCategory("technology"," ");
            addCategory("entertainment"," ");
        } else {
            return;
        }
        Toast.makeText(this, "No of categories" + String.valueOf(countCategories), Toast.LENGTH_SHORT).show();
    }

}
