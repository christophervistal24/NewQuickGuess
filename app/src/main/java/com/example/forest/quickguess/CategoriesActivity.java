package com.example.forest.quickguess;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.forest.quickguess.Adapters.SlideAdapter;
import com.example.forest.quickguess.DB.DB;
import com.example.forest.quickguess.Helpers.SharedPreferenceHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoriesActivity extends AppCompatActivity {

    @BindView(R.id.viewpager) ViewPager viewPager;
    SlideAdapter myadapter;
    MediaPlayer Sfx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        ButterKnife.bind(this);
        myadapter = new SlideAdapter(this);
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
        viewPager.setAdapter(myadapter);
    }

    @Override
    protected void onResume() {
        viewPager.setAdapter(myadapter);
        SharedPreferenceHelper.PREF_FILE = "user_played";
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
