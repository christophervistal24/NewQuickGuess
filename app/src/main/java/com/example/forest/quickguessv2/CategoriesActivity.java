package com.example.forest.quickguessv2;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.forest.quickguessv2.Adapters.SlideAdapter;
import com.example.forest.quickguessv2.DB.DB;
import com.example.forest.quickguessv2.Helpers.RedirectHelper;
import com.example.forest.quickguessv2.Helpers.SharedPreferenceHelper;
import com.example.forest.quickguessv2.Service.MyService;
import com.example.forest.quickguessv2.Utilities.SoundUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
            int position = DB.getInstance(getApplicationContext()).categoriesQuestionDao().getCategoryIdByName(category.toLowerCase());
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
