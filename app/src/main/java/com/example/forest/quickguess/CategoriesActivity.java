package com.example.forest.quickguess;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.forest.quickguess.Adapters.CategoryAdapter;
import com.example.forest.quickguess.DB.Categories.QuestionCategory;
import com.example.forest.quickguess.DB.DB;
import com.example.forest.quickguess.DB.Life.LifeRepositories;
import com.example.forest.quickguess.DB.User.UserRepositories;
import com.example.forest.quickguess.RecyclerView.Categories;
import com.example.forest.quickguess.Utilities.GameOverUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class CategoriesActivity extends AppCompatActivity {


    private RecyclerView categoryRecyclerView;
    private RecyclerView.Adapter adapter;
    private List<com.example.forest.quickguess.RecyclerView.Categories> categoriesList;
    MediaPlayer Sfx;
    private static final long FIVE_MINUTES = 5 * 60 * 1000;
    LifeRepositories lifeRepositories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        ButterKnife.bind(this);
        lifeRepositories  = new LifeRepositories(getApplicationContext());
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(CategoriesActivity.this, 1);
        categoryRecyclerView = findViewById(R.id.categoryRecyclerView);
        categoryRecyclerView.setHasFixedSize(true);
        categoryRecyclerView.setLayoutManager(mGridLayoutManager);
        displayAllCategories();
    }

    private void displayAllCategories() {
        categoriesList = new ArrayList<>();
        List<QuestionCategory> categories = DB.getInstance(getApplicationContext()).categoriesQuestionDao().getAllCategories();
        for(QuestionCategory category : categories)
        {
            Categories categoryRetrieved = new Categories(category.getId(),category.getCategory(),category.getCategory_description());
            categoriesList.add(categoryRetrieved);
        }
         adapter = new CategoryAdapter(categoriesList,getApplicationContext());
        categoryRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        if  (lifeRepositories.getUserLife() <= 0)
        {
            long gameOverTime = GameOverUtil.userGameOverTime(getApplicationContext());
            long fiveAgo = System.currentTimeMillis() - gameOverTime;
            if (fiveAgo >= FIVE_MINUTES) {
                UserRepositories.defaultLifetoUser(lifeRepositories);
            }
        }
        if (Sfx != null)
        {
            Sfx.release();
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        DB.getInstance(this).destroyInstance();
        super.onDestroy();
    }

}
