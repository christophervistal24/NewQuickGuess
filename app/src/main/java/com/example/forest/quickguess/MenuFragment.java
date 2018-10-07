package com.example.forest.quickguess;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.forest.quickguess.DB.DB;
import com.example.forest.quickguess.DB.Points.PointsRepositories;
import com.example.forest.quickguess.DB.User.UserRepositories;
import com.example.forest.quickguess.Helpers.RedirectHelper;
import com.example.forest.quickguess.Helpers.SharedPreferenceHelper;
import com.example.forest.quickguess.Helpers.WindowHelper;
import com.example.forest.quickguess.Utilities.GameOverUtil;
import com.example.forest.quickguess.Utilities.IOnBackPressed;
import com.example.forest.quickguess.Utilities.SoundUtil;

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
    @BindView(R.id.btnCategories) ImageButton btnCategories;

    private int user_life;
    private int user_points;
    YoYo.YoYoString animation;

    private static final long FIVE_MINUTES = 5 * 60 * 1000;
    CountDownTimer countDownTimer;
    private boolean isCounterRunning = false;


    public MenuFragment() {
        // Required empty public constructor
    }




    @Override
    public void onResume() {
        RelativeLayout welcomeLayout = (Objects.requireNonNull(getActivity())).findViewById(R.id.welcomeLayout);
        welcomeLayout.setVisibility(View.GONE);
        initUserPoints();
        lifeRevive();
        userPoints.setText(String.valueOf(user_points));
        userLife.setText(String.valueOf(user_life));
        ((MainActivity)getActivity()).pointsRepositories.sendPoints();
        super.onResume();
    }

    private void lifeRevive() {
        if (((MainActivity)getActivity()).lifeRepositories.getUserLife() <= 0)
        {
            long gameOverTime = GameOverUtil.userGameOverTime(getContext());
            long fiveAgo = System.currentTimeMillis() - gameOverTime;
            if (fiveAgo >= FIVE_MINUTES) {
                UserRepositories.defaultLifetoUser(((MainActivity)getActivity()).lifeRepositories);
            } else {
                long remainingTime = FIVE_MINUTES - fiveAgo;
                startTimerForLife(remainingTime);
            }
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SharedPreferenceHelper.PREF_FILE = "points";
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        WindowHelper.hideNavigationBar(getActivity());
        unbinder = ButterKnife.bind(this,view);
        userLife.setText(null);
        userPoints.setText(null);
        lifeRevive();
        userLife.setTypeface(Typeface.createFromAsset(getContext().getAssets(),  "fonts/Dimbo_Regular.ttf"));
        userPoints.setTypeface(Typeface.createFromAsset(getContext().getAssets(),  "fonts/Dimbo_Regular.ttf"));
        user_life = UserRepositories.getLifeOfUser(((MainActivity)getActivity()).lifeRepositories);
        initUserPoints();
        userLife.setText(String.valueOf(user_life));
        userPoints.setText(String.valueOf(user_points));
       return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initUserPoints();
        lifeRevive();
        animation = YoYo.with(Techniques.Shake)
                .duration(2000)
                .repeat(-1)
                .playOn(btnCategories);
        super.onViewCreated(view, savedInstanceState);
    }

    private void initUserPoints() {
        user_points = DB.getInstance(getContext()).userStatusDao().countAllForPoints() * 100;
    }



    @Override
    @OnClick({R.id.btnCategories,R.id.btnAbout,R.id.btnRanks})
    public void onClick(View view) {
        String classname = null;
        switch (view.getId())
        {
            case R.id.btnCategories:
                classname = "CategoriesActivity";
                disposeFragments();
                break;

            case R.id.btnAbout:
                classname = "AboutActivity";
                SoundUtil.songLoad(getContext(),R.raw.click)
                    .start();
                disposeFragments();
                break;

            case R.id.btnRanks:
                classname = "RanksActivity";
                SoundUtil.songLoad(getContext(),R.raw.click)
                        .start();
                disposeFragments();
                break;
        }
        try {
            Class <?> Cref = Class .forName("com.example.forest.quickguess."+classname);
            new RedirectHelper(getActivity(),Cref);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDestroyView() {
        animation.stop();
        animation = null;
        DB.getInstance(getActivity().getApplicationContext()).destroyInstance();
        unbinder.unbind();
        super.onDestroyView();
    }

    private void disposeFragments()
    {
        FragmentManager fm = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

    private void startTimerForLife(long counter)
    {
        if (!isCounterRunning) {
            countDownTimer = new CountDownTimer(counter,1000) {
                @Override
                public void onTick(long mill) {
                    userLife.setText(String.format(Locale.getDefault(),"%02d:%02d",
                            TimeUnit.MILLISECONDS.toMinutes(mill),
                            TimeUnit.MILLISECONDS.toSeconds(mill) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(mill))
                            )
                    );
                }

                @Override
                public void onFinish() {
                    countDownTimer.cancel();
                    isCounterRunning = false;
                }
            }.start();
        }
        isCounterRunning = true;
    }


    @Override
    public void onStop() {
            if(countDownTimer != null){
                countDownTimer.cancel();
            }
        super.onStop();
    }
}
