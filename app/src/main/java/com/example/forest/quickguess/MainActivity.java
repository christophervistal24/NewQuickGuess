package com.example.forest.quickguess;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.example.forest.quickguess.APIsInterface.APIRegister;
import com.example.forest.quickguess.DB.DB;
import com.example.forest.quickguess.DB.Life.LifeRepositories;
import com.example.forest.quickguess.DB.Points.PointsRepositories;
import com.example.forest.quickguess.DB.User.User;
import com.example.forest.quickguess.DB.User.UserRepositories;
import com.example.forest.quickguess.Helpers.Connectivity;
import com.example.forest.quickguess.Helpers.InputHelpers;
import com.example.forest.quickguess.Helpers.LayoutHelper;
import com.example.forest.quickguess.Helpers.SharedPreferenceHelper;
import com.example.forest.quickguess.Helpers.WindowHelper;
import com.example.forest.quickguess.Service.backgroundMusicService;
import com.example.forest.quickguess.Services.WebService.UserRegisterRequest;
import com.example.forest.quickguess.Services.WebService.UserRegisterResponse;
import com.example.forest.quickguess.Services.WebService.UserRegisterService;
import com.example.forest.quickguess.Utilities.BGMusicUtil;
import com.example.forest.quickguess.Utilities.FragmentUtil;
import com.example.forest.quickguess.Utilities.SoundUtil;
import com.example.forest.quickguess.Watcher.HomeWatcher;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hotchemi.android.rate.AppRate;
import hotchemi.android.rate.OnClickButtonListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.username) EditText username;
    @BindView(R.id.userPin) EditText userPin;
    @BindView(R.id.welcomeLayout) RelativeLayout welcomeLayout;
    @BindView(R.id.mainLayout)  RelativeLayout mainLayout;


    private static final int MAX_LENGTH = 8;
    private static final int MIN_LENGTH = 4;

    public LifeRepositories lifeRepositories;
    FragmentUtil fragmentUtil;
    public PointsRepositories pointsRepositories;
    private ProgressDialog progressDialog;


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
        setContentView(R.layout.activity_main);
        //remove all backstacks
        this.disposeAllBackstack();
        //check the user preference for background music of the app
        this.checkStateOfBGMusic();
        this.initializeRequireClass();
        this.checkUser();


    }

    private void checkStateOfBGMusic() {

        //check if the bg music in user preferences is on
        //start to play the background music
        //otherwise unbind and pause.
        if (BGMusicUtil.isUserWantToPlayBGMusic(this))
        {
            doBindService();
            Intent music = new Intent();
            music.setClass(this, backgroundMusicService.class);
            startService(music);

            mHomeWatcher = new HomeWatcher(this);
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


    /*
    * Class initializations */
    private void initializeRequireClass() {
        WindowHelper.hideNavigationBar(this);
        ButterKnife.bind(this);
        this.lifeRepositories = new LifeRepositories(this);
        this.fragmentUtil = new FragmentUtil();
        this.pointsRepositories = new PointsRepositories(getApplicationContext());
    }





    //if the user is in home page and tries to exit the app display the rate dialog
    @Override
    public void onBackPressed() {
        AppRate.with(getApplicationContext())
                .setInstallDays(2)
                .setLaunchTimes(1)
                .setRemindInterval(2)
                .setOnClickButtonListener(new OnClickButtonListener() {
                    @Override
                    public void onClickButton(int which) {
                        if  (which != -1)
                        {
                           Fragment fragmentManager = getSupportFragmentManager().findFragmentById(R.id.fragment_one);
                            if  (fragmentManager != null)
                            {
                                fragmentUtil.disposeBackStack();
                            }
                            System.exit(0);
                        }
                    }
                })
                .monitor();
        AppRate.showRateDialogIfMeetsConditions(this);
        AppRate.with(getApplicationContext()).showRateDialog(this);
    }


    @Override
    protected void onResume() {
        checkUser();
        if (mServ != null) {
            mServ.resumeMusic();
        }
        this.disposeAllBackstack();
        WindowHelper.hideNavigationBar(this);
        super.onResume();
    }


    @OnClick(R.id.createUser)
    public void create() {
        final String playerUsername = username.getText().toString();
        final String playerPin = userPin.getText().toString();

        if  ( playerUsername.isEmpty()  || InputHelpers.isProperUsername(playerUsername) )
        {
            SoundUtil.songLoad(getApplicationContext(),R.raw.error)
                    .start();
            username.setError("Please provide a proper name");

        } else if ( playerPin.length() > MAX_LENGTH  || playerPin.length() < MIN_LENGTH ) {
            SoundUtil.songLoad(getApplicationContext(),R.raw.error)
                    .start();
            userPin.setError("Pin must be minimum of 4 characters");
        }  else {
            signUp(playerUsername,playerPin);
        }

    }

    private void signUp(final String userName , final String userPin)
    {

        //Declare progressDialog before so you can use .hide() later!
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("LOADING . . .");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        Retrofit refrofit = UserRegisterService.RetrofitInstance(getApplicationContext());
        APIRegister services = refrofit.create(APIRegister.class);
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest(userName,userPin);
        Call<UserRegisterResponse> UserRegisterResponseCall = services.register(userRegisterRequest);
        UserRegisterResponseCall.enqueue(new Callback<UserRegisterResponse>() {
            @Override
            public void onResponse(Call<UserRegisterResponse> call, Response<UserRegisterResponse> response) {
                if  (response.code() != 422)
                {
                    //store to database
                    UserRepositories.createUser(getApplicationContext(),new User(userName));

                    //check if the user already play
                    SharedPreferenceHelper.PREF_FILE = "game_over_time";
                    if (SharedPreferenceHelper.getSharedPreferenceLong(getApplicationContext(), "time", 0) == 0) {
                        if (UserRepositories.getLifeOfUser(lifeRepositories) <= 0 ) // if the user has no remaning life
                        {
                            UserRepositories.defaultLifetoUser(lifeRepositories);
                        }
                    }
                    //set the user id of the user
                    SharedPreferenceHelper.PREF_FILE="user";
                    SharedPreferenceHelper.setSharedPreferenceInt(getApplicationContext(),"user_id",response.body().getId());
                    checkUser();
                    //set the JWT token for user
                    SharedPreferenceHelper.PREF_FILE = "user_token";
                    SharedPreferenceHelper.setSharedPreferenceString(getApplicationContext(),"token",response.body().getToken());
                } else {
                    username.setError("Username already exists");
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<UserRegisterResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }


    private void checkUser()
    {
        if (Connectivity.isConnectedWifi(getApplicationContext()) && !UserRepositories.isUserAlreadyRegister(this)) {
            LayoutHelper.showLayout(welcomeLayout);
        } else {
            LayoutHelper.hideLayout(welcomeLayout);
            fragmentUtil.startMenuFragment(this);
        }
    }

    private void disposeAllBackstack()
    {
        for (Fragment fragment:getSupportFragmentManager().getFragments()) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }


    @Override
    protected void onDestroy() {
        doUnbindService();
        Intent music = new Intent();
        music.setClass(this,backgroundMusicService.class);
        stopService(music);
        DB.getInstance(getApplicationContext()).destroyInstance();
        super.onDestroy();
    }


}
