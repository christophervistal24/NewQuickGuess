package com.example.forest.quickguess;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Typeface;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.forest.quickguess.APIsInterface.APISendStatus;
import com.example.forest.quickguess.APIsInterface.APIUserHistory;
import com.example.forest.quickguess.APIsInterface.ApiLogin;
import com.example.forest.quickguess.DB.DB;
import com.example.forest.quickguess.DB.Life.LifeRepositories;
import com.example.forest.quickguess.DB.Points.Points;
import com.example.forest.quickguess.DB.User.User;
import com.example.forest.quickguess.DB.UserStatus.UserStatus;
import com.example.forest.quickguess.Helpers.Connectivity;
import com.example.forest.quickguess.Helpers.SharedPreferenceHelper;
import com.example.forest.quickguess.Helpers.WindowHelper;
import com.example.forest.quickguess.Models.Status;
import com.example.forest.quickguess.Service.backgroundMusicService;
import com.example.forest.quickguess.Services.WebService.UserHistoryRequest;
import com.example.forest.quickguess.Services.WebService.UserHistoryResponse;
import com.example.forest.quickguess.Services.WebService.UserHistoryService;
import com.example.forest.quickguess.Services.WebService.UserLoginRequest;
import com.example.forest.quickguess.Services.WebService.UserLoginResponse;
import com.example.forest.quickguess.Services.WebService.UserLoginService;
import com.example.forest.quickguess.Services.WebService.UserStatusRequest;
import com.example.forest.quickguess.Services.WebService.UserStatusResponse;
import com.example.forest.quickguess.Services.WebService.UserStatusService;
import com.example.forest.quickguess.Utilities.BGMusicUtil;
import com.example.forest.quickguess.Utilities.GameOverUtil;
import com.example.forest.quickguess.Utilities.UserUtil;
import com.example.forest.quickguess.Watcher.HomeWatcher;
import com.google.gson.Gson;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SettingsActivity extends AppCompatActivity implements SwitchDialog.SwitchDialogListener {

    private static final String FILENAME = "game_over_time";
    @BindView(R.id.switchAccount) Button switchAccount;
    @BindView(R.id.bgMusicOff) Switch bgMusic;
    @BindView(R.id.soundFxOff) Switch soundFX;
    private String userUsername;
    private String userPassword;


    AlertDialog dialog;
    String playerUsername;
    LifeRepositories lifeRepositories;

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
        if (BGMusicUtil.isUserWantToPlayBGMusic(this))
        {
            if (mServ != null) {
                mServ.resumeMusic();
            }
        }

        //check user preference for background music
        this.checkUserBGValue();


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
        setContentView(R.layout.activity_settings);
        //init butterknife
        ButterKnife.bind(this);

        //hide title and navigation
        WindowHelper.hideNavigationBar(this);


        lifeRepositories = new LifeRepositories(getApplicationContext());
        //check the user if logged in this is for user switch account
        this.checkIfLoggedIn();

        //check user preference for background music
        this.checkUserBGValue();
        //event for backgroud music switch toggle
        this.bgMusicEvent();


    }


    private void bgMusicEvent() {

        bgMusic.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                BGMusicUtil.setBGMusicToOn(getApplicationContext());

                bindAndPlayMusic();
            } else {
                doUnbindService();
                BGMusicUtil.setBGMusicToOff(getApplicationContext());
                if (backgroundMusicService.mPlayer != null) {
                    if (backgroundMusicService.mPlayer.isPlaying()) {
                        backgroundMusicService.mPlayer.pause();
                    }
                }

            }
        });
    }

    private void checkUserBGValue() {
        boolean state = BGMusicUtil.isUserWantToPlayBGMusic(this);
        Typeface face = Typeface.createFromAsset(getAssets(),"fonts/Dimbo_Regular.ttf");

        bgMusic.setTypeface(face);
        bgMusic.setChecked(state);
        if (bgMusic.isChecked()) {
            bindAndPlayMusic();
            BGMusicUtil.setBGMusicToOn(this);
        } else {
            doUnbindService();
            if (backgroundMusicService.mPlayer != null) {
                if (backgroundMusicService.mPlayer.isPlaying()) {
                    backgroundMusicService.mPlayer.pause();
                    BGMusicUtil.setBGMusicToOff(this);
                }
            }
        }
    }


    private void checkIfLoggedIn()
    {
        switchAccount.setOnClickListener(view -> {
            playerUsername = DB.getInstance(getApplicationContext()).userDao().getUsername();
            if  (playerUsername != null && Connectivity.isConnectedFast(getApplicationContext())) {
                openSwitchAccountDialog();
            } else {
                TastyToast.makeText(getApplicationContext(),"The switch account is only available if you finish to create an account \n" +
                        "and have access to an internet connection", TastyToast.LENGTH_LONG, TastyToast.INFO);
            }
        });

    }

    private void openSwitchAccountDialog() {
        SwitchDialog switchDialog = new SwitchDialog();
        switchDialog.show(getSupportFragmentManager(),"Switch dialog");
    }

    @Override
    public void applyText(String username, String password) {
            //API Request for login
            userUsername = username;
            userPassword = password;
            //check if there's a user status belongs to the user in the database
            List<UserStatus> userStatus = DB.getInstance(getApplicationContext()).userStatusDao().getAllStatusOfUser();
            String userToken = UserUtil.getUserToken(this);
            if (!userStatus.isEmpty() && userToken != null)
            {
                //send the user status
                sendUserStatus();
            } else {
                //send user history
                sendUserHistory();
            }
    }


    private void loginUser(String username , String password)
    {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Processing . . .");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        Retrofit refrofit = UserLoginService.RetrofitInstance(getApplicationContext());
        ApiLogin services = refrofit.create(ApiLogin.class);

        UserLoginRequest userLoginRequest = new UserLoginRequest(username,password);

        Call<UserLoginResponse> call = services.login(userLoginRequest);

        call.enqueue(new Callback<UserLoginResponse>() {
            @Override
            public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {
                UserLoginResponse userLoginResponse = response.body();
                if  (response.isSuccessful())
                {

                    //set new id to shared preference user_id
                    UserUtil.setUserIdForSharePref(getApplicationContext(),userLoginResponse.getId());

                    //deleting all the status of the user after it send to the API
                    DB.getInstance(getApplicationContext()).userStatusDao().deleteAllFromUserStatus();

                    //insert new user status
                    for(Status s: userLoginResponse.getStat())
                    {
                        UserStatus userStatus = new UserStatus();
                        userStatus.creator(s.getQuestion_id(), Integer.parseInt(s.getUser_id()),s.getQuestion_result(),s.getCategory_id(),s.getClass_id());
                        DB.getInstance(getApplicationContext()).userStatusDao().addUserStatus(userStatus);
                    }

                    //update the current username
                    DB.getInstance(getApplicationContext()).userDao().updateUser(userLoginResponse.getName());

                    //check if there's a user history response
                    if (userLoginResponse.getUserHistory() != null)
                    {
                        lifeRepositories.setLifeToUser(userLoginResponse.getUserHistory().getPrev_user_life());
                        GameOverUtil.saveTime(getApplicationContext(),Long.valueOf(userLoginResponse.getUserHistory().getGame_over_time()));
                    }

                    //update user points
                    updateUserPoints(userLoginResponse.getReset_stat());

                    //update the user token
                    UserUtil.setUserToken(getApplicationContext(),userLoginResponse.getToken());


                    //display message
                    TastyToast.makeText(getApplicationContext(),"Successfully logged in your account", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                    progressDialog.dismiss();
                } else { // there's is something wrong display the dialog switch account again
                    progressDialog.dismiss();
                    TastyToast.makeText(getApplicationContext(),"Please check your username or password", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    openSwitchAccountDialog();
                }
            }



            @Override
            public void onFailure(Call<UserLoginResponse> call, Throwable t) {
            }
        });
    }

    private void updateUserPoints(int user_points) {
        //delete the old points
        DB.getInstance(getApplicationContext()).pointsDao().delete();

        //set up new points
        Points points = new Points();
        points.setPoints(user_points * 100);
        DB.getInstance(getApplicationContext()).pointsDao().insert(points);
    }

    private void sendUserHistory() {

        String username = DB.getInstance(getApplicationContext()).userDao().getUsername();
        int userLife = lifeRepositories.getUserLife();
        long gameOverTime = GameOverUtil.userGameOverTime(getApplicationContext());


        Retrofit retrofit = UserHistoryService.RetrofitInstance(getApplicationContext());
        APIUserHistory services = retrofit.create(APIUserHistory.class);

        UserHistoryRequest userHistoryRequest = new UserHistoryRequest(username,userLife,String.valueOf(gameOverTime),UserUtil.getUserToken(this));

        Call<UserHistoryResponse> call = services.insertUserHistory(userHistoryRequest);
        call.enqueue(new Callback<UserHistoryResponse>() {
            @Override
            public void onResponse(Call<UserHistoryResponse> call, Response<UserHistoryResponse> response) {
                if (response.body().getCode() == 201 &&  response.isSuccessful())
                {
                    GameOverUtil.deleteGameOverTime(getApplicationContext());
                    loginUser(userUsername,userPassword);
                }
            }

            @Override
            public void onFailure(Call<UserHistoryResponse> call, Throwable t) {
                Toast.makeText(SettingsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void sendUserStatus() {

        List<UserStatus> status = DB.getInstance(getApplicationContext()).userStatusDao().getAllStatusOfUser();
        String username = DB.getInstance(getApplicationContext()).userDao().getUsername();

        Gson gson = new Gson();

        Retrofit refrofit = UserStatusService.RetrofitInstance(getApplicationContext());
        APISendStatus services = refrofit.create(APISendStatus.class);

        UserStatusRequest userStatusRequest = new UserStatusRequest(username,gson.toJson(status),UserUtil.getUserToken(this));

        Call<UserStatusResponse> call = services.sendUserStatus(userStatusRequest);

        call.enqueue(new Callback<UserStatusResponse>() {
            @Override
            public void onResponse(Call<UserStatusResponse> call, Response<UserStatusResponse> response) {
                int statusCode = response.body().getCode();
                //if the user has a token send the user status of user
                if (UserUtil.isUserHasAToken(getApplicationContext())
                        && statusCode == 201)
                {
                    sendUserHistory();
                }

            }

            @Override
            public void onFailure(Call<UserStatusResponse> call, Throwable t) {

            }
        });
    }




    /*
     * Attach background music to service
     * */
    private void bindAndPlayMusic() {
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
    }



}
