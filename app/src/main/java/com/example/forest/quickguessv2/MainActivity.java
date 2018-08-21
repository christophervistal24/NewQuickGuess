package com.example.forest.quickguessv2;


import android.arch.persistence.room.Room;
import android.graphics.Typeface;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.forest.quickguessv2.DBModule.MyAppDB;
import com.example.forest.quickguessv2.DBModule.User;
import com.example.forest.quickguessv2.Helpers.InputHelpers;
import com.example.forest.quickguessv2.Helpers.SharedPreferenceHelper;
import com.example.forest.quickguessv2.Helpers.WindowHelper;

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
        ButterKnife.bind(this);
        SharedPreferenceHelper.PREF_FILE = "user";
        Typeface dimboFont = Typeface.createFromAsset(getAssets(),"fonts/Dimbo Regular.ttf");
        username.setTypeface(dimboFont);
        myAppDB = Room.databaseBuilder(getApplicationContext(),MyAppDB.class,"quickguess")
                .allowMainThreadQueries()
                .build();
        sUsername = SharedPreferenceHelper.getSharedPreferenceString(this,"username",null);
        isAlreadyRegistered(sUsername);

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
}
