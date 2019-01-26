package com.example.forest.quickguess;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.forest.quickguess.Adapters.SlideAdapter;
import com.example.forest.quickguess.DB.DB;
import com.example.forest.quickguess.DB.Life.LifeRepositories;
import com.example.forest.quickguess.DB.User.UserRepositories;
import com.example.forest.quickguess.Helpers.LifeRevive;
import com.example.forest.quickguess.Helpers.SharedPreferenceHelper;
import com.example.forest.quickguess.Service.backgroundMusicService;
import com.example.forest.quickguess.Utilities.BGMusicUtil;
import com.example.forest.quickguess.Utilities.GameOverUtil;
import com.example.forest.quickguess.Utilities.SoundUtil;
import com.example.forest.quickguess.Utilities.UserUtil;
import com.example.forest.quickguess.Watcher.HomeWatcher;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoriesActivity extends AppCompatActivity {

    @BindView(R.id.viewpager) ViewPager viewPager;
    SlideAdapter myadapter;


    LifeRepositories lifeRepositories;
    LifeRevive lifeRevive;



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
        setContentView(R.layout.activity_categories);
        ButterKnife.bind(this);

        this.lifeRepositories  = new LifeRepositories(getApplicationContext());
        this.lifeRevive         = new LifeRevive(this,lifeRepositories);
        this.myadapter          = new SlideAdapter(this);

        this.checkStateOfBGMusic();

        this.viewPageSetListenerAndAdapter();
    }



    @Override
    protected void onResume() {
        //checking if the time that user game over is two minutes ago
        if (lifeRevive.isUserGameOver() && lifeRevive.isTwoMinutesAgo()) {
            UserRepositories.defaultLifetoUser(lifeRepositories);
            GameOverUtil.deleteGameOverTime(this);
        }

        //set adapter to view pager
        this.viewPager.setAdapter(myadapter);

        //get and set user last category played
        this.setUserLastCategoryPlayed();

        //checking if the background music in on if so play the background music
        if (mServ != null) {
            mServ.resumeMusic();
        }
        super.onResume();
    }

    private void setUserLastCategoryPlayed() {
        String category = UserUtil.getSelectedCategoryOfUser(this);
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
    }

    @Override
    protected void onDestroy() {

        //unbind the service
        doUnbindService();

        //create new intent for music
        Intent music = new Intent();

        //set music for service
        music.setClass(this,backgroundMusicService.class);

        //stop the service for background music
        stopService(music);

        //destroy the Room persistence library instance
        DB.getInstance(this).destroyInstance();

        super.onDestroy();
    }

    private void checkStateOfBGMusic() {
        if (BGMusicUtil.isUserWantToPlayBGMusic(this))
        {
            doBindService();
            Intent music = new Intent();
            music.setClass(getApplicationContext(), backgroundMusicService.class);
            startService(music);

            mHomeWatcher = new HomeWatcher(getApplicationContext());
            mHomeWatcher.setOnHomePressedListener(new HomeWatcher.OnHomePressedListener() {
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
            mHomeWatcher.startWatch();
        } else {
            doUnbindService();
            if (backgroundMusicService.mPlayer != null) {
                if (backgroundMusicService.mPlayer.isPlaying()) {
                    backgroundMusicService.mPlayer.pause();
                }
            }
        }
    }

    private void viewPageSetListenerAndAdapter() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {}

            @Override
            public void onPageSelected(int i) {
                SoundUtil.playMusic(getApplicationContext(),R.raw.click);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
        viewPager.setAdapter(myadapter);
    }

}
