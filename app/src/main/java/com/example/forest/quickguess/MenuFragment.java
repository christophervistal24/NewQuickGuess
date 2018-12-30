package com.example.forest.quickguess;



import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.forest.quickguess.APIsInterface.APISendStatus;
import com.example.forest.quickguess.APIsInterface.APIUserHistory;
import com.example.forest.quickguess.DB.DB;

import com.example.forest.quickguess.DB.Life.LifeRepositories;
import com.example.forest.quickguess.DB.User.User;
import com.example.forest.quickguess.DB.User.UserRepositories;
import com.example.forest.quickguess.DB.UserStatus.UserStatus;
import com.example.forest.quickguess.DB.UserStatus.UserStatusRepositories;
import com.example.forest.quickguess.Helpers.Connectivity;
import com.example.forest.quickguess.Helpers.LayoutHelper;
import com.example.forest.quickguess.Helpers.RedirectHelper;
import com.example.forest.quickguess.Helpers.SharedPreferenceHelper;
import com.example.forest.quickguess.Services.WebService.UserHistoryRequest;
import com.example.forest.quickguess.Services.WebService.UserHistoryResponse;
import com.example.forest.quickguess.Services.WebService.UserHistoryService;
import com.example.forest.quickguess.Services.WebService.UserStatusRequest;
import com.example.forest.quickguess.Services.WebService.UserStatusResponse;
import com.example.forest.quickguess.Services.WebService.UserStatusService;
import com.example.forest.quickguess.Utilities.GameOverUtil;
import com.example.forest.quickguess.Utilities.SoundUtil;

import com.google.gson.Gson;


import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment implements View.OnClickListener {

    private Unbinder unbinder;
    @BindView(R.id.userLife) TextView userLife;
    @BindView(R.id.userPoints) TextView userPoints;
    @BindView(R.id.userName) TextView userName;
    @BindView(R.id.categories) LinearLayout categories;


    private int user_life;
    private int user_points;

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
        initUserLifeAndPoints();
        if (Connectivity.isConnectedWifi(getContext()) && !UserRepositories.isUserAlreadyRegister(getActivity())) {
            LayoutHelper.showLayout(welcomeLayout);
        }
        super.onResume();
    }

    private void initUserLifeAndPoints() {

        String username = DB.getInstance(getContext()).userDao().getUsername();
        if  (username != null)
        {
            String capitalizeName = username.substring(0,1).toUpperCase() + username.substring(1);
            userName.setText(capitalizeName);
        } else {
            userName.setText(R.string.user_guest);
        }
        userPoints.setText(getString(R.string.points).concat(" : " + String.valueOf((user_points))));
        userLife.setText(getString(R.string.life).concat(" : " + String.valueOf(user_life)));

    }

    private void lifeRevive() { //check time for life revive
        if (((MainActivity)getActivity()).lifeRepositories.getUserLife() <= 0)
        {
            long gameOverTime = GameOverUtil.userGameOverTime(getContext());
            long currentTimeWithGameOver = System.currentTimeMillis() - gameOverTime;
            if (currentTimeWithGameOver >= FIVE_MINUTES) { // 5 minutes ago
                UserRepositories.defaultLifetoUser(((MainActivity)getActivity()).lifeRepositories);
            } else {
                long remainingTime = FIVE_MINUTES - currentTimeWithGameOver;
                startTimerForLife(remainingTime);
            }
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        SharedPreferenceHelper.PREF_FILE ="user_token";
        if (SharedPreferenceHelper.getSharedPreferenceString(getContext(),"token",null) != null) {
            UserStatusRepositories.sendStatus(getContext(),DB.getInstance(getContext()).userDao().getUsername());
        }
        SharedPreferenceHelper.PREF_FILE = "points";
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        unbinder = ButterKnife.bind(this,view);
        rebaseLifeAndPoints();
        lifeRevive();
        user_life = UserRepositories.getLifeOfUser(((MainActivity)getActivity()).lifeRepositories);
        initUserPoints();
        initUserLifeAndPoints();
       return view;
    }

    private void rebaseLifeAndPoints() {
        userLife.setText(null);
        userPoints.setText(null);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initUserPoints();
        lifeRevive();
    }

    private void initUserPoints() {
        //get the current points of the user
        int extraPoints = DB.getInstance(getContext()).pointsDao().getUserPoints();
        user_points = (DB.getInstance(getContext()).userStatusDao().countAllForPoints() * 100) + extraPoints;
    }

    @Override
    @OnClick({R.id.btnCategories,R.id.btnSettings,R.id.btnRanks,R.id.btnAbout})
    public void onClick(View view) {
        String classname = null;
        switch (view.getId())
        {
            case R.id.btnCategories:
                classname = "CategoriesActivity";
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

            case R.id.btnSettings:
                classname = "SettingsActivity";
                SoundUtil.songLoad(getContext(),R.raw.click)
                        .start();
                disposeFragments();
                break;

            case R.id.btnAbout:
                classname = "AboutActivity";
                SoundUtil.songLoad(getContext(),R.raw.click)
                        .start();
                disposeFragments();
                break;
       }
        redirection(classname);
    }

    @Override
    public void onDestroyView() {
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


    private void startTimerForLife(long counter) //timer for life revive
    {
        if (!isCounterRunning) {
            countDownTimer = new CountDownTimer(counter,1000) {
                @Override
                public void onTick(long mill) {
                    userLife.setText(String.format("%s%s", getString(R.string.time_left), String.format(Locale.getDefault(), "%02d:%02d",
                            TimeUnit.MILLISECONDS.toMinutes(mill),
                            TimeUnit.MILLISECONDS.toSeconds(mill) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(mill))
                            ))
                    );
                }

                @Override
                public void onFinish() {
                    //add user life value 5
                    UserRepositories.defaultLifetoUser(((MainActivity)getActivity()).lifeRepositories);
                    userLife.setText("Life : ".concat(String.valueOf(UserRepositories.getLifeOfUser(((MainActivity)getActivity()).lifeRepositories))));
                    countDownTimer.cancel();
                    isCounterRunning = false;
                    //set
                }
            }.start();
        }
        isCounterRunning = true;
    }




    private void redirection(String classname) {
        try {
            Class <?> Cref = Class .forName("com.example.forest.quickguess."+classname);
            new RedirectHelper(getActivity(),Cref);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {
            if(countDownTimer != null){
                countDownTimer.cancel();
            }
        super.onStop();
    }

}
