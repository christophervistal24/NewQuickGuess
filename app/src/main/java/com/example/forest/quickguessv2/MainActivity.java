package com.example.forest.quickguessv2;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.forest.quickguessv2.DB.Categories.QuestionCategoryRepositories;
import com.example.forest.quickguessv2.DB.DB;
import com.example.forest.quickguessv2.DB.Life.LifeRepositories;
import com.example.forest.quickguessv2.DB.User.User;
import com.example.forest.quickguessv2.DB.User.UserRepositories;
import com.example.forest.quickguessv2.Helpers.InputHelpers;
import com.example.forest.quickguessv2.Helpers.WindowHelper;
import com.example.forest.quickguessv2.Utilities.TypeFaceUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.username) EditText username;
    @BindView(R.id.welcomeLayout) RelativeLayout welcomeLayout;
    QuestionCategoryRepositories questionCategoryRepositories;
    public  LifeRepositories lifeRepositories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TypeFaceUtil.initFont(this);
        WindowHelper.hideNavigationBar(this);
        ButterKnife.bind(this);
        questionCategoryRepositories = new QuestionCategoryRepositories(this);
        lifeRepositories = new LifeRepositories(this);
        isAlreadyRegistered();
        categories();

    }



    @Override
    protected void onResume() {
        isAlreadyRegistered();
        super.onResume();
        WindowHelper.hideNavigationBar(this);
    }


    @OnClick(R.id.createUser)
    public void create() {
        String player = username.getText().toString();
        if  (player.isEmpty()  || InputHelpers.isProperUsername(player))
        {
            username.setError("Please provide a proper username");
        } else {
            UserRepositories.createUser(this,new User(player));
            lifeRepositories.setLifeToUser(5);
            username.setText(null);
        }
        isAlreadyRegistered();
    }

    private void displayMenu()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MenuFragment menuFragment = new MenuFragment();
        fragmentTransaction.add(R.id.fragment_one,menuFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void isAlreadyRegistered()
    {
         int isExists = UserRepositories.isAlreadyRegister(this);
         if (isExists >= 1){
             welcomeLayout.setVisibility(View.GONE);
             displayMenu();
         } else {
             welcomeLayout.setVisibility(View.VISIBLE);
         }
    }


    @Override
    protected void onDestroy() {
        DB.getInstance(this).destroyInstance();
        super.onDestroy();
    }

    private void categories()
    {
        questionCategoryRepositories.addCategory("people","All about");
        questionCategoryRepositories.addCategory("plants","All about");
        questionCategoryRepositories.addCategory("animals","All about");
        questionCategoryRepositories.addCategory("geography","All about");
        questionCategoryRepositories.addCategory("sports","All about");
        questionCategoryRepositories.addCategory("music","All about");
        questionCategoryRepositories.addCategory("technology","All about");
        questionCategoryRepositories.addCategory("entertainment","All about");
        questionCategoryRepositories.addCategory("entertainment","All about");
        questionCategoryRepositories.addCategory("entertainment","All about");
    }
}
