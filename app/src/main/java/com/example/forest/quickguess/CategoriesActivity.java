package com.example.forest.quickguess;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.forest.quickguess.Adapters.CategoryAdapter;
import com.example.forest.quickguess.Adapters.FunFactsAdapter;
import com.example.forest.quickguess.Adapters.SlideAdapter;
import com.example.forest.quickguess.DB.Categories.QuestionCategory;
import com.example.forest.quickguess.DB.DB;
import com.example.forest.quickguess.Helpers.SharedPreferenceHelper;
import com.example.forest.quickguess.RecyclerView.Categories;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoriesActivity extends AppCompatActivity {

//    @BindView(R.id.viewpager) ViewPager viewPager;
    //SlideAdapter myadapter;

    private RecyclerView categoryRecyclerView;
    private RecyclerView.Adapter adapter;
    private List<com.example.forest.quickguess.RecyclerView.Categories> categoriesList;
    MediaPlayer Sfx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        ButterKnife.bind(this);
        categoryRecyclerView = findViewById(R.id.categoryRecyclerView);
        categoryRecyclerView.setHasFixedSize(true);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        displayAllCategories();


       /* myadapter = new SlideAdapter(this);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {}

            @Override
            public void onPageSelected(int i) {
                Sfx = MediaPlayer.create(getApplicationContext(),R.raw.click);
                Sfx.start();
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
        viewPager.setAdapter(myadapter);*/
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
//        viewPager.setAdapter(myadapter);
        /*SharedPreferenceHelper.PREF_FILE = "user_played";
        String category = SharedPreferenceHelper.getSharedPreferenceString(getApplicationContext(),"category",null);
        if (category != null)
        {
            int position = 0;
            try {
                position = DB.getInstance(getApplicationContext())
                            .categoriesQuestionDao()
                            .getCategoryIdByName(category.toLowerCase());
            } catch (Exception e) {
                e.printStackTrace();
            }
            viewPager.setCurrentItem(position-1);
        }*/
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
