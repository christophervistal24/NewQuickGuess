package com.example.forest.quickguess;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.forest.quickguess.APIsInterface.APISendStatus;
import com.example.forest.quickguess.DB.Categories.QuestionCategoryRepositories;
import com.example.forest.quickguess.DB.DB;
import com.example.forest.quickguess.DB.Friends.FriendsRepositories;
import com.example.forest.quickguess.DB.Life.LifeRepositories;
import com.example.forest.quickguess.DB.Points.PointsRepositories;
import com.example.forest.quickguess.DB.Questions.QuestionRepositories;
import com.example.forest.quickguess.DB.Questions.Questions;
import com.example.forest.quickguess.DB.UserStatus.UserStatus;
import com.example.forest.quickguess.Helpers.SharedPreferenceHelper;
import com.example.forest.quickguess.Models.FunFactsContent;
import com.example.forest.quickguess.QuestionInterface.QuestionInterface;
import com.example.forest.quickguess.Services.WebService.UserStatusRequest;
import com.example.forest.quickguess.Services.WebService.UserStatusResponse;
import com.example.forest.quickguess.Services.WebService.UserStatusService;
import com.example.forest.quickguess.Utilities.AnswerUtil;
import com.example.forest.quickguess.Utilities.EncryptUtil;
import com.example.forest.quickguess.Utilities.FragmentUtil;
import com.example.forest.quickguess.Utilities.GameBundleUitl;
import com.example.forest.quickguess.Utilities.GameOverUtil;
import com.example.forest.quickguess.Utilities.SoundUtil;
import com.example.forest.quickguess.Utilities.TimerUtil;
import com.google.gson.Gson;
import com.marcouberti.autofitbutton.AutoFitButton;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AnswerQuestion extends AppCompatActivity  implements QuestionInterface{

    @BindView(R.id.question) TextView question;
    @BindView(R.id.choice_a) AutoFitButton choice_a;
    @BindView(R.id.choice_b) AutoFitButton choice_b;
    @BindView(R.id.choice_c) AutoFitButton choice_c;
    @BindView(R.id.choice_d) AutoFitButton choice_d;
    @BindView(R.id.timer) TextView timer;
    @BindView(R.id.life) TextView life;
    @BindView(R.id.userPoints) TextView points;
    @BindView(R.id.timerLayout) LinearLayout timerLayout;
    @BindView(R.id.fragment_game_over) FrameLayout gameOver;
    @BindView(R.id.questionLayout) LinearLayout questionLayout;
    @BindView(R.id.answerQuestionLayout) RelativeLayout answerQuestionLayout;



    private static final long counter = 21000;


    public CountDownTimer countDownTimer;
    protected int userPoints = 0;
    public LifeRepositories lifeRepositories;
    private PointsRepositories pointsRepositories;

    boolean isCounterRunning = false;
    protected Questions q;
    Bundle bundle;
    FragmentUtil fragmentUtil;
    QuestionRepositories questionRepositories;
    FriendsRepositories friendsRepositories;
    public MediaPlayer clockTick;

    Vibrator vibrator;
    private boolean isAnswerQuestionInstance = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_question);
        ButterKnife.bind(this);
        classInstantiate();
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        getQuestion();
        isAnswerQuestionInstance = true;
        life.setText(String.valueOf(lifeRepositories.getUserLife()));
        pointsRepositories.initUserPoints(points);
      }



    private void classInstantiate() {
        lifeRepositories     = new LifeRepositories(getApplicationContext());
        pointsRepositories   = new PointsRepositories(getApplicationContext());
        questionRepositories = new QuestionRepositories(getApplicationContext());
        fragmentUtil          = new FragmentUtil();
        bundle                 = new Bundle();
        clockTick = soundTick(R.raw.clock_tick);
    }

    public MediaPlayer soundTick(int uri)
    {
        return MediaPlayer.create(this,uri);
    }

    public void getQuestion()
    {
        try {
            if  (isAnswerQuestionInstance) //check if activity is AnswerQuestion Class
            {
                startTimer();
            }

            setFontSize(); //there's a bug in autofitbutton library so in order to fix we need to set an size programmatically

            //get one questions
            q = questionRepositories.selectQuestion(QuestionCategoryRepositories.getUserSelectedCategory(this));
            List<String> choices = Arrays.asList(q.getChoice_a(), q.getChoice_b(), q.getChoice_c(), q.getChoice_d());
            List<String> randomizeChoices = questionRepositories.randomizeChoices(choices);

            //set and decrypt data from the database
                try {
                    question.setText(EncryptUtil.decryptMethod(q.getQuestion()));
                    choice_a.setText(EncryptUtil.decryptMethod(randomizeChoices.get(0)));
                    choice_b.setText(EncryptUtil.decryptMethod(randomizeChoices.get(1)));
                    choice_c.setText(EncryptUtil.decryptMethod(randomizeChoices.get(2)));
                    choice_d.setText(EncryptUtil.decryptMethod(randomizeChoices.get(3)));
                } catch (Exception e) {
                    e.printStackTrace();
                }

        } catch (NullPointerException e) // if there's no question to retrieved
        {
            finish();
        }

    }

    private void setFontSize() {
        choice_a.setTextSize(20);
        choice_b.setTextSize(20);
        choice_c.setTextSize(20);
        choice_d.setTextSize(20);
    }


    @OnClick({R.id.choice_a, R.id.choice_b,R.id.choice_c,R.id.choice_d})
    public void click(View v) {
        String userAnswer = null;
        switch (v.getId())
        {
            case R.id.choice_a:
                userAnswer = choice_a.getText().toString();
                break;

            case R.id.choice_b:
                userAnswer = choice_b.getText().toString();
                break;

            case R.id.choice_c:
                userAnswer = choice_c.getText().toString();
                break;

            case R.id.choice_d:
                userAnswer = choice_d.getText().toString();
                break;
        }

        try {
            getAnswer(userAnswer, EncryptUtil.decryptMethod(q.getCorrect_answer()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        clockTick.release();
        countDownTimer.cancel();
   }


    private  void startTimer() {
        if (!isCounterRunning) {
            isCounterRunning  = true;
            clockTick.start(); // soundfx start
            countDownTimer = new CountDownTimer(AnswerQuestion.counter, 1000) {
                @Override
                public void onTick(long timeRemaining) {
                    int remainingTime = (int) (TimeUnit.MILLISECONDS.toSeconds(timeRemaining) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeRemaining)));
                    timer.setText(String.valueOf(remainingTime));
                    TimerUtil.changeTextColor(remainingTime,timer,clockTick); //changed timer textview color
                }

                @Override
                public void onFinish() {
                    isCounterRunning = false;
                    try {
                       getAnswer("No answer", EncryptUtil.decryptMethod(q.getCorrect_answer()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            countDownTimer.start();
        } else {
            countDownTimer.cancel();
            countDownTimer.start();
        }
    }



    @Override
    public void onBackPressed() {
        GameOverUtil.isGameOverOrNot(this,lifeRepositories,life);
        tellFragments();
        tellFragmentQuestionResult();
        countDownTimer.cancel();
        clockTick.release();
        super.onBackPressed();
    }

    @Override
    public void getAnswer(String answer, String correct_answer) {
        new FunFactsContent(getApplicationContext(),correct_answer,q.getFun_facts(),q.getFun_facts_image());
        hideTimer();
        hideQuestionLayout();
        if (AnswerUtil.checkAnswer(answer, bundle, correct_answer)) {
            correct();
        }
        else { wrong(); }
    }


    @Override
    public void correct() {
        SharedPreferenceHelper.PREF_FILE = "user";
        SoundUtil.songLoad(getApplicationContext(),R.raw.check).start();
        UserStatus userStatus = new UserStatus();
        userStatus.setQuestion_id(q.getId());
        userStatus.setUser_id(SharedPreferenceHelper.getSharedPreferenceInt(getApplicationContext(),"user_id",0));
        // 1 - is eqaul to correct
        userStatus.setQuestion_result(1);
        userStatus.setCategory_id(q.getCategory_id());
        userStatus.setClass_id(q.getClass_id());
        //add user answered question
        DB.getInstance(getApplicationContext()).userStatusDao().addUserStatus(userStatus);
        SharedPreferenceHelper.PREF_FILE="user_token";
        if (SharedPreferenceHelper.getSharedPreferenceString(getApplicationContext(),"token",null) != null)
        {
            sendStatus();
        }
        pointsRepositories.initUserPoints(points);
        result();
    }


    @Override
    public void wrong() {
        SoundUtil.songLoad(getApplicationContext(),R.raw.wrong).start();
        SharedPreferenceHelper.PREF_FILE = "user";
        int decreaseCurrentLife = (lifeRepositories.getUserLife()) - 1;
        lifeRepositories.setLifeToUser(decreaseCurrentLife);
//        pointsRepositories.sendPoints();
        pointsRepositories.initUserPoints(points);
        UserStatus userStatus = new UserStatus();
        userStatus.setQuestion_id(q.getId());
        // 0 - is eqaul to wrong
        userStatus.setUser_id(SharedPreferenceHelper.getSharedPreferenceInt(getApplicationContext(),"user_id",0));
        userStatus.setQuestion_result(0);
        userStatus.setCategory_id(q.getCategory_id());
        userStatus.setClass_id(q.getClass_id());
        //add user answered question
        DB.getInstance(getApplicationContext()).userStatusDao().addUserStatus(userStatus);
        vibrator.vibrate(500);
        result();
    }

    @Override
    public void result() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        QuestionResult questionResult = new QuestionResult();
        fragmentTransaction.add(R.id.questionResult,questionResult);
        questionResult.setArguments(bundle);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    protected void onResume() {
      try {
            pointsRepositories.initUserPoints(points);
            clockTick.isPlaying();
        } catch (IllegalStateException e)
        {
            clockTick = soundTick(R.raw.clock_tick);
            clockTick.start();
        }
        getQuestion();
        isAnswerQuestionInstance = true;
        super.onResume();
    }

    @Override
    protected void onStop() {
        countDownTimer.cancel();
        isCounterRunning = true;
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        clockTick.release();
    }

    protected void hideTimer()
    {
        timerLayout.setVisibility(View.GONE);
    }

    protected void displayTimer()
    {
        timerLayout.setVisibility(View.VISIBLE);
    }

    protected void hideQuestionLayout()
    {
        questionLayout.setVisibility(View.GONE);
    }

    public void displayQuestionLayout()
    {
        questionLayout.setVisibility(View.VISIBLE);
    }


    @Override
    protected void onDestroy() {
        if  (isCounterRunning)
        {
            countDownTimer.cancel();
        }
        DB.getInstance(getApplicationContext()).destroyInstance();
        super.onDestroy();
    }


    protected void gotoFunFacts() {
              //rebase
                life.setText(String.valueOf(lifeRepositories.getUserLife()));
                userPoints = 0;
                countDownTimer.cancel();
                isCounterRunning = true;
                fragmentUtil.startFunFactsFragment(this);
    }

    private void tellFragments() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for(Fragment f : fragments){
            if(f != null && f instanceof FunFacts)
                ((FunFacts)f).onBackPressed();
        }
    }

    private void tellFragmentQuestionResult() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for(Fragment f : fragments){
            if(f != null && f instanceof QuestionResult)
                ((QuestionResult)f).onBackPressed();
        }
    }


    protected void displayGameOverFragment()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        GameOverFragment gameOverFragment = new GameOverFragment();
        fragmentTransaction.add(R.id.fragment_game_over,gameOverFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @OnClick(R.id.skipQuestion)
    public void skipQuestion()
    {
        GameOverUtil.isGameOverOrNot(this,lifeRepositories,life);
        getQuestion();
    }

    @OnClick(R.id.exit)
    public void exitCategory()
    {
        finish();
    }


    private void sendStatus() //used if the user do not switch a account but want to save his / her status
    {
        List<UserStatus> a = DB.getInstance(getApplicationContext()).userStatusDao().getAllStatusOfUser();
        String username = DB.getInstance(getApplicationContext()).userDao().getUsername();
        SharedPreferenceHelper.PREF_FILE = "user_token";
        String token = SharedPreferenceHelper.getSharedPreferenceString(getApplicationContext(),"token",null);
        Gson gson = new Gson();
//        Log.d("sample",gson.toJson(a));
        Retrofit refrofit = UserStatusService.RetrofitInstance(getApplicationContext());
        APISendStatus services = refrofit.create(APISendStatus.class);
        UserStatusRequest userStatusRequest = new UserStatusRequest(username,gson.toJson(a), token);
        Call<UserStatusResponse> call = services.sendUserStatus(userStatusRequest);
        call.enqueue(new Callback<UserStatusResponse>() {
            @Override
            public void onResponse(Call<UserStatusResponse> call, Response<UserStatusResponse> response) {

            }

            @Override
            public void onFailure(Call<UserStatusResponse> call, Throwable t) {

            }
        });
    }


}
