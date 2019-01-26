package com.example.forest.quickguess;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationManagerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.forest.quickguess.DB.DB;
import com.example.forest.quickguess.DB.User.UserRepositories;
import com.example.forest.quickguess.DB.UserStatus.UserStatusRepositories;
import com.example.forest.quickguess.Helpers.LayoutHelper;
import com.example.forest.quickguess.Helpers.LifeRevive;
import com.example.forest.quickguess.Helpers.RedirectHelper;
import com.example.forest.quickguess.Helpers.SharedPreferenceHelper;
import com.example.forest.quickguess.Service.backgroundMusicService;
import com.example.forest.quickguess.Utilities.BGMusicUtil;
import com.example.forest.quickguess.Utilities.GameOverUtil;
import com.example.forest.quickguess.Utilities.SoundUtil;
import com.example.forest.quickguess.Utilities.UserUtil;
import com.example.forest.quickguess.Watcher.HomeWatcher;

import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment implements View.OnClickListener {

    private Unbinder unbinder;
    @BindView(R.id.userLife) TextView userLife;
    @BindView(R.id.userPoints) TextView userPoints;
    @BindView(R.id.userName) TextView userName;
    @BindView(R.id.btnCategories) ImageButton btnCategories;

    LifeRevive lifeRevive;
    YoYo.YoYoString animation;

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
        getActivity().bindService(new Intent(getActivity(), backgroundMusicService.class),
                Scon, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService()
    {
        if(mIsBound)
        {
            getActivity().unbindService(Scon);
            mIsBound = false;
        }
    }



    @Override
    public void onPause() {
        super.onPause();

        PowerManager pm = (PowerManager)getActivity().getSystemService(Context.POWER_SERVICE);
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


    public MenuFragment() {
        // Required empty public constructor
    }




    @Override
    public void onResume() {
        RelativeLayout welcomeLayout = (Objects.requireNonNull(getActivity())).findViewById(R.id.welcomeLayout);

        // hide the welcome layout or the instructions section of the app
        welcomeLayout.setVisibility(View.GONE);

        //checking if the user has a token for requesting in API
        if (UserUtil.isUserHasAToken(getContext())) {
            UserStatusRepositories.sendStatus(getContext(),DB.getInstance(getContext()).userDao().getUsername());
        }

        //if the user has internet connection and not finish to create an account
        //register section will appear
        if (UserUtil.isUserNotFinishToCreateAnAccount(getActivity())) {
            LayoutHelper.showLayout(welcomeLayout);
        }

        //initialize points of user
        this.initializeUserPoints();

        //initialize life for user
        this.initializeUserLife();

        //display and check if the user is game over if so display an timer for life revival
        this.lifeRevive.watchLifeRevive(userLife);

        super.onResume();
    }

    private void initializeUsername()
    {
        userName.setText(UserUtil.isUserGuestOrNot(getContext()));
    }

    private void initializeUserLife()
    {
        userLife.setText(String.valueOf(UserRepositories.getLifeOfUser(((MainActivity)getActivity()).lifeRepositories)));
    }

    private void initializeUserPoints()
    {
        userPoints.setText(String.valueOf((UserUtil.intiUserPoints(getContext()))));
    }





    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        unbinder = ButterKnife.bind(this,view);

        lifeRevive = new LifeRevive(getContext(),((MainActivity)getActivity()).lifeRepositories);

        //background music for user base on it's preference
        this.checkStateOfBGMusic();

        this.initializeUserPoints();

        this.initializeUserLife();

        this.initializeUsername();

        //watcher for checking if the app need to display timer or not
        this.lifeRevive.watchLifeRevive(userLife);

       return view;
    }






    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //when the view of activity is created play this animation
        animation = YoYo.with(Techniques.Shake)
                .duration(2000)
                .repeat(-1)
                .playOn(btnCategories);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }





    @Override
    @OnClick({R.id.btnCategories,R.id.btnAbout,R.id.btnRanks,R.id.btnSettings})
    public void onClick(View view) {
        String classname = null;
        switch (view.getId())
        {
            case R.id.btnCategories:
                classname = "CategoriesActivity";
                disposeFragments();
                break;

            case R.id.btnRanks:
                classname = "RanksActivity";
                SoundUtil.playMusic(getContext(),R.raw.click);
                disposeFragments();
                break;

            case R.id.btnSettings:
                classname = "SettingsActivity";
                SoundUtil.playMusic(getContext(),R.raw.click);
                disposeFragments();
                break;


            case R.id.btnAbout:
                classname = "AboutActivity";
                SoundUtil.playMusic(getContext(),R.raw.click);
                disposeFragments();
            break;
        }

        redirection(classname);
    }

    //redirect user to intended activity,
    // base on what the user choose on the menu
    private void redirection(String classname) {
        try {
            Class <?> Cref = Class .forName("com.example.forest.quickguess."+classname);
            new RedirectHelper(getActivity(),Cref);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //destroy some instance
    @Override
    public void onDestroyView() {
        animation.stop();
        animation = null;
        unbinder.unbind();
        DB.getInstance(getActivity().getApplicationContext()).destroyInstance();
        super.onDestroyView();
    }

    //clear all backstack of fragment
    private void disposeFragments()
    {
        FragmentManager fm = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

    //checking if the user want to play the background music of the app
    private void checkStateOfBGMusic() {

        if (BGMusicUtil.isUserWantToPlayBGMusic(getContext()))
        {
            doBindService();
            Intent music = new Intent();
            music.setClass(getContext(), backgroundMusicService.class);
            getActivity().startService(music);

            mHomeWatcher = new HomeWatcher(getContext());
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

}
