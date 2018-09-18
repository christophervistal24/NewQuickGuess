package com.example.forest.quickguessv2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dragankrstic.autotypetextview.AutoTypeTextView;
import com.example.forest.quickguessv2.Helpers.FirstLaunchHelper;
import com.example.forest.quickguessv2.Utilities.LaunchUtil;
import com.example.forest.quickguessv2.Utilities.SoundUtil;
import com.example.forest.quickguessv2.Utilities.TypeFaceUtil;
import com.huanhailiuxin.coolviewpager.CoolViewPager;

public class WelcomeActivity extends AppCompatActivity {

    private CoolViewPager viewPager;
    private LinearLayout dotsLayout;
    private int[] layouts;
    private Button  btnSkip , btnNext;
    private FirstLaunchHelper firstLaunchHelper;
    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firstLaunchHelper = new FirstLaunchHelper(this);
        if(!firstLaunchHelper.isFirstTimeLaunch())
        {
            launchHomeScreen();
            finish();
        }

        if  (Build.VERSION.SDK_INT >= 21)
        {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_welcome);
        TypeFaceUtil.initDimboFont(this);
        viewPager = findViewById(R.id.view_pager);
        dotsLayout = findViewById(R.id.layoutDots);
        btnSkip = findViewById(R.id.btn_skip);
        btnNext = findViewById(R.id.btn_next);
        handler = new Handler();
        // layouts of all welcome sliders
        // add few more layouts if you want
        layouts = new int[]{
                R.layout.welcome_slide_1,
                R.layout.welcome_slide2,
                R.layout.welcome_slide3,
                R.layout.welcome_slide4,
                R.layout.welcome_slide_5,
                R.layout.welcome_slide_6
        };

        // adding bottom dots
        addBottomDots(0);

        // making notification bar transparent
        changeStatusBarColor();

        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(this);
        viewPager.setScrollMode(CoolViewPager.ScrollMode.VERTICAL);
//        viewPager.setAutoScroll(true,6000);
//        viewPager.setAutoScrollDirection(CoolViewPager.AutoScrollDirection.FORWARD);
//        viewPager.setInfiniteLoop(true);
        viewPager.setScrollDuration(true,1000);
        viewPager.setDrawEdgeEffect(true);
        viewPager.setAdapter(myViewPagerAdapter);

        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchHomeScreen();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched
                int current = getItem(+1);
                if (current < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
                    launchHomeScreen();
                }
            }
        });
    }

    private void addBottomDots(int currentPage) {
/*        if (currentPage >= 6 || currentPage <= 0)
        {
            currentPage = 0;
        }*/
        TextView[] dots = new TextView[layouts.length];
        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);
        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i)
    {
        return viewPager.getCurrentItem() + i;
    }


    private void launchHomeScreen()
    {
        firstLaunchHelper.setFirstTimeLaunch(false);
        startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
        finish();
    }

    //  viewpager change listener
    CoolViewPager.OnPageChangeListener viewPagerPageChangeListener = new CoolViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }


        @Override
        public void onPageSelected(int position) {
            Log.d("position",String.valueOf(position));
            addBottomDots(position);
            SoundUtil.songLoad(getApplicationContext(),R.raw.click)
                    .start();
            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.length - 1) {
                // last page. make button text to GOT IT
                btnNext.setText(getString(R.string.start));
                btnSkip.setVisibility(View.GONE);
            } else {
                // still pages are left
                btnNext.setText(getString(R.string.next));
                btnSkip.setVisibility(View.VISIBLE);
            }

            switch (position)
            {
                case 1:
                    ImageView wolfy2 = findViewById(R.id.wolfy2);
                    final AutoTypeTextView message2 = findViewById(R.id.message2);
                    LaunchUtil.animateWolfy(wolfy2,50,800);
                    message2.setVisibility(View.VISIBLE);
                    message2.setTypingSpeed(40);
                    message2.setTextAutoTyping("Last night I celebrate my Birthday in my house, lots of my friends had been there," +
                            " one of my friend give me a bottle of wine.");

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            message2.setTypingSpeed(40);
                            message2.setTextAutoTyping("");
                        }
                    }, 3000);

                    handler.postDelayed(new Runnable() {
                        @Override
                    public void run() {
                    message2.setTypingSpeed(40);
                    message2.setTextAutoTyping("we decided to drink it, then suddenly I felt dizzy, when I" +
                            " woked up all of my friends was gone, and there's a letter beside me");
                }
            }, 10000);
                    break;

                case 2:
                    AutoTypeTextView message3 = findViewById(R.id.message3);
                    message3.setTypingSpeed(80);
                    message3.setTextAutoTyping("Life of your friends is on my hands, if you want to save them, come and play with me Bwahahaha..");
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }


    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;
        private Activity activity;
        MyViewPagerAdapter(Activity activity) {
            this.activity = activity;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(layouts[position], container, false);
            ImageView wolfy = view.findViewById(R.id.wolfy);
            AutoTypeTextView message =  view.findViewById(R.id.message);
            if (position == 0)
            {
                LaunchUtil.animateWolfy(wolfy,50,800);
                message.setVisibility(View.VISIBLE);
                message.setTypingSpeed(80);
                message.setTextAutoTyping("Hello! my name is Wolfy can you help me?");
            }
            container.addView(view);
            TypeFaceUtil.initDimboFont(activity);
            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }


    }
}

