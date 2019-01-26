package com.example.forest.quickguess;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dragankrstic.autotypetextview.AutoTypeTextView;
import com.example.forest.quickguess.DB.Categories.QuestionCategoryRepositories;
import com.example.forest.quickguess.DB.DB;
import com.example.forest.quickguess.DB.Questions.QuestionRepositories;
import com.example.forest.quickguess.Helpers.FirstLaunchHelper;
import com.example.forest.quickguess.Helpers.WindowHelper;
import com.example.forest.quickguess.Service.backgroundMusicService;
import com.example.forest.quickguess.Utilities.LaunchUtil;
import com.example.forest.quickguess.Utilities.SoundUtil;
import com.example.forest.quickguess.Utilities.TypeFaceUtil;
import com.example.forest.quickguess.Watcher.HomeWatcher;
import com.huanhailiuxin.coolviewpager.CoolViewPager;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;


public class WelcomeActivity extends AppCompatActivity {

    private CoolViewPager viewPager;
    private LinearLayout dotsLayout;
    private int[] layouts;
    private Button  btnSkip , btnNext;
    private FirstLaunchHelper firstLaunchHelper;
    Handler handler;
    QuestionCategoryRepositories questionCategoryRepositories;
    QuestionRepositories questionRepositories;
    HomeWatcher mHomeWatcher;
    private boolean mIsBound = false;
    private backgroundMusicService mServ;
    private ServiceConnection Scon =new ServiceConnection(){

        public void onServiceConnected(ComponentName name, IBinder
                binder) {
            mServ = ((backgroundMusicService.ServiceBinder)binder).getService();
        }

        public void onServiceDisconnected(ComponentName name) {
            mServ = null;
        }
    };

    void doBindService(){
        bindService(new Intent(this,backgroundMusicService.class),
                Scon,Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService()
    {
        if(mIsBound)
        {
            unbindService(Scon);
            mIsBound = false;
        }
    }


    @Override
    protected void onDestroy() {
        doUnbindService();
        Intent music = new Intent();
        music.setClass(this,backgroundMusicService.class);
        stopService(music);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mServ != null) {
            mServ.resumeMusic();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        PowerManager pm = (PowerManager)
                getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = false;
        if (pm != null) {
            isScreenOn = pm.isScreenOn();
        }

        if (!isScreenOn) {
            if (mServ != null) {
                mServ.pauseMusic();
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowHelper.hideNavigationBar(this);
        setContentView(R.layout.activity_welcome);
        doBindService();
        Intent music = new Intent();
        music.setClass(this, backgroundMusicService.class);
        startService(music);

        this.questionCategoryRepositories = new QuestionCategoryRepositories(this);
        this.questionRepositories = new QuestionRepositories(getApplicationContext());
        this.firstLaunchHelper = new FirstLaunchHelper(this);

        if(!firstLaunchHelper.isFirstTimeLaunch())
        {
            this.launchHomeScreen();
            finish();
        }

        //for background music home watcher if the user click the home button this methods will
        //trigger
        this.mHomeWatcher = new HomeWatcher(this);
        this.mHomeWatcher.setOnHomePressedListener(new HomeWatcher.OnHomePressedListener() {
            @Override
            public void onHomePressed() {
                if (mServ != null) {
                    mServ.pauseMusic();
                }
            }
            @Override
            public void onHomeLongPressed() {
                if (mServ != null) {
                    mServ.pauseMusic();
                }
            }
        });
        //start to observe
        this.mHomeWatcher.startWatch();

        if  (Build.VERSION.SDK_INT >= 21)
        {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }


        setContentView(R.layout.activity_welcome);
        TypeFaceUtil.initDimboFont(this);
        this.viewPager = findViewById(R.id.view_pager);
        this.dotsLayout = findViewById(R.id.layoutDots);
        this.btnSkip = findViewById(R.id.btn_skip);
        this.btnNext = findViewById(R.id.btn_next);
        this.handler = new Handler();
        // layouts of all welcome sliders
        // add few more layouts if you want
        layouts = new int[]{
                R.layout.welcome_slide_1,
                R.layout.welcome_slide2,
                R.layout.welcome_slide3,
                R.layout.welcome_slide4,
                R.layout.welcome_slide_5,
                R.layout.welcome_slide_6,
                R.layout.welcome_slide_7,
                R.layout.welcome_slide_8,
                R.layout.welcome_slide_9
        };

        // adding bottom dots
        addBottomDots(0);

        // making notification bar transparent
        this.changeStatusBarColor();

        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(this);
        viewPager.setScrollMode(CoolViewPager.ScrollMode.VERTICAL);
//        viewPager.setAutoScroll(true,6000);
//        viewPager.setAutoScrollDirection(CoolViewPager.AutoScrollDirection.FORWARD);
//        viewPager.setInfiniteLoop(true);
        viewPager.setScrollDuration(true,1000);
        viewPager.setDrawEdgeEffect(true);
        viewPager.setAdapter(myViewPagerAdapter);

        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

       //set on click listener for btnSkip
        this.btnSkip.setOnClickListener((View v) -> {
            new insertQuestionAsync(WelcomeActivity.this).execute();
        });

        this.btnNext.setOnClickListener((View v) -> {
            // checking for last page
            // if last page home screen will be launched
            int current = getItem(+1);
            if (current < layouts.length) {
                // move to next screen
                viewPager.setCurrentItem(current);
            } else {
                new insertQuestionAsync(WelcomeActivity.this).execute();
            }

        });

    }

    private void addBottomDots(int currentPage) {
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

    private  class insertQuestionAsync extends AsyncTask<Void , Integer , Void> { //asyc task insert all questions
        private WeakReference<WelcomeActivity> activityReference;
        ProgressDialog progressDialog;
        // only retain a weak reference to the activity
        insertQuestionAsync(WelcomeActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(WelcomeActivity.this, "Setting up all for you", "Please wait . . . ", true, false);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            questionRepositories.insertAllQuestions();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) { // redirect if finish
            firstLaunchHelper.setFirstTimeLaunch(false);
            launchHomeScreen();
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

