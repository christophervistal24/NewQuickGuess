package com.example.forest.quickguess;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.forest.quickguess.DB.Categories.QuestionCategoryRepositories;
import com.example.forest.quickguess.DB.DB;
import com.example.forest.quickguess.DB.Life.LifeRepositories;
import com.example.forest.quickguess.DB.Points.PointsRepositories;
import com.example.forest.quickguess.DB.Questions.QuestionRepositories;
import com.example.forest.quickguess.DB.Questions.Questions;
import com.example.forest.quickguess.DB.User.UserRepositories;
import com.example.forest.quickguess.DB.UserStatus.UserStatus;
import com.example.forest.quickguess.DB.UserStatus.UserStatusRepositories;
import com.example.forest.quickguess.Models.FunFactsContent;
import com.example.forest.quickguess.QuestionInterface.QuestionInterface;
import com.example.forest.quickguess.Service.backgroundMusicService;
import com.example.forest.quickguess.Utilities.AnswerUtil;
import com.example.forest.quickguess.Utilities.DifficultLevelUtil;
import com.example.forest.quickguess.Utilities.EncryptUtil;
import com.example.forest.quickguess.Utilities.FragmentUtil;
import com.example.forest.quickguess.Utilities.GameOverUtil;
import com.example.forest.quickguess.Utilities.SoundUtil;
import com.example.forest.quickguess.Utilities.TimerUtil;
import com.example.forest.quickguess.Utilities.UserUtil;


import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnswerQuestion extends AppCompatActivity  implements QuestionInterface{

    @BindView(R.id.question) TextView question;
    @BindView(R.id.choice_a) Button choice_a;
    @BindView(R.id.choice_b) Button choice_b;
    @BindView(R.id.choice_c) Button choice_c;
    @BindView(R.id.choice_d) Button choice_d;
    @BindView(R.id.timer) TextView timer;
    @BindView(R.id.life) TextView life;
    @BindView(R.id.userPoints) TextView points;
    @BindView(R.id.timerLayout) RelativeLayout timerLayout;
    @BindView(R.id.fragment_game_over) FrameLayout gameOver;
    @BindView(R.id.questionLayout) LinearLayout questionLayout;
    @BindView(R.id.answerQuestionLayout) RelativeLayout answerQuestionLayout;
    @BindView(R.id.layout) ScrollView layout;

    //default timer
    private static long counter = 16000;


    public CountDownTimer countDownTimer;
    protected int userPoints = 0;
    public LifeRepositories lifeRepositories;
    private PointsRepositories pointsRepositories;
    private final String[] button_ids = {"choice_a","choice_b","choice_c","choice_d"};
    private List<String> choices;


    boolean isCounterRunning = false;
    protected Questions selectedQuestion;
    Bundle bundle;
    FragmentUtil fragmentUtil;
    QuestionRepositories questionRepositories;
    public MediaPlayer clockTick;

    Vibrator vibrator;
    private boolean isAnswerQuestionInstance = false;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_question);
        ButterKnife.bind(this);

        //stop the background music service
        this.doUnbindService();

        //stop the backgorund music
        this.stopBackgroundMusic();

        //Instantiate the require class for this activity
        this.classInstantiate();

        //set vibrator
        this.vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        //get question
        this.getQuestion();

        //telling that this category is answer question activity
        //so that the finish activity will not appear
        this.isAnswerQuestionInstance = true;



        life.setText(String.valueOf(lifeRepositories.getUserLife()));
        pointsRepositories.initUserPoints(points);

    }

    private void stopBackgroundMusic() {
        if (backgroundMusicService.mPlayer != null) {
            if (backgroundMusicService.mPlayer.isPlaying()) {
                backgroundMusicService.mPlayer.pause();
            }
        }
    }


    private void classInstantiate() {
        this.lifeRepositories     = new LifeRepositories(getApplicationContext());
        this.pointsRepositories   = new PointsRepositories(getApplicationContext());
        this.questionRepositories = new QuestionRepositories(getApplicationContext());
        this.fragmentUtil          = new FragmentUtil();
        this.bundle                 = new Bundle();
        this.clockTick = soundTick(R.raw.clock_tick);
    }

    public MediaPlayer soundTick(int uri)
    {
        return MediaPlayer.create(this,uri);
    }

    public void getQuestion()
    {
        try {
            if  (isAnswerQuestionInstance) //check if activity is AnswerQuestion Class
            {  //set count down depending
                // on the difficulty level that user choose
                AnswerQuestion.counter = DifficultLevelUtil
                                        .getTimerForDifficulty(UserUtil.getSelectedDifficultyOfUser(this));
                this.startTimer();
            }

            //get one questions
            selectedQuestion = this.prepareQuestion();

            //prepare choices and randomize.
            choices = this.prepareChoices();

            //set and decrypt
            //we need to decrypt the data cause it's encrypted in the database
            try {
                this.setQuestionAndChoices();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (NullPointerException e) { // if there's no question to retrieved
            finish();
        }

    }

    //fill the question textbox and choices button
    private void setQuestionAndChoices() throws Exception {
        question.setText(EncryptUtil.decryptMethod(selectedQuestion.getQuestion()));
        this.setChoiceForQuestion();
    }

    //prepare a choices depending on question
    //and also randomize
    @NonNull
    private List<String> prepareChoices() {
        return questionRepositories.randomizeChoices(Arrays.asList(selectedQuestion.getChoice_a(), selectedQuestion.getChoice_b(), selectedQuestion.getChoice_c(), selectedQuestion.getChoice_d()));
    }

    //get one question from the list
    private Questions prepareQuestion() {
      return questionRepositories.selectQuestion(QuestionCategoryRepositories.getUserSelectedCategory(this));
    }

    //set text for each choices button
    private void setChoiceForQuestion() throws Exception {
        int index = 0;
        for (String button_id : button_ids) {
            Button button = (Button) findViewById(getResources()
                    .getIdentifier(button_id, "id", this.getPackageName()));
            button.setText(EncryptUtil.decryptMethod(choices.get(index)));
            index++;
        }
    }



    //onclick listener for choices
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
            getAnswer(userAnswer, EncryptUtil.decryptMethod(selectedQuestion.getCorrect_answer()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        clockTick.release();
        countDownTimer.cancel();
    }


    private  void startTimer() {
        if (!isCounterRunning) {
            isCounterRunning  = true;
            //start the sound sfx for timer
            clockTick.start();

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
                        getAnswer("No answer", EncryptUtil.decryptMethod(selectedQuestion.getCorrect_answer()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
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
    public void getAnswer(String answer, String correct_answer) throws Exception {
        //set content for fun facts
        new FunFactsContent(getApplicationContext(),correct_answer, selectedQuestion.getFun_facts(), selectedQuestion.getFun_facts_image());

        this.hideTimer();
        this.hideQuestionLayout();

        if (AnswerUtil.checkAnswer(answer, bundle, correct_answer)) {
            this.correct();
        } else {
            this.wrong();
        }
    }


    @Override
    public void correct() {

        //play the correct sfx
        SoundUtil.songLoad(getApplicationContext(),R.raw.check).start();

        // 1 - is eqaul to correct

        //set new user status
        UserStatus userStatus = new UserStatus();
        userStatus.creator(selectedQuestion.getId(),UserUtil.getUserIdInSharedPref(this),1, selectedQuestion.getCategory_id(), selectedQuestion.getClass_id());

        //add the new user status
        DB.getInstance(getApplicationContext()).userStatusDao().addUserStatus(userStatus);

        //if there's a internet connection and user has token
        //make an API request to save the user status
        this.sendUserStatusToApi();

        //init new points
        this.pointsRepositories.initUserPoints(points);
        this.result();
    }

    //check if the user has a token if so then request user status to API
    private void sendUserStatusToApi() {
        if (UserUtil.isUserHasAToken(this)) {
            UserStatusRepositories.sendStatus(this,UserRepositories.username(this));
        }
    }


    @Override
    public void wrong() {
        //play the wrong sfx
        SoundUtil.songLoad(getApplicationContext(),R.raw.wrong).start();

        //decrease the user life
        lifeRepositories.setLifeToUser((lifeRepositories.getUserLife()) - 1);

        // 0- is equal to wrong

        //set new user status
        UserStatus userStatus = new UserStatus();
        userStatus.creator(selectedQuestion.getId(),UserUtil.getUserIdInSharedPref(this),0, selectedQuestion.getCategory_id(), selectedQuestion.getClass_id());

        //add the new user status
        DB.getInstance(getApplicationContext()).userStatusDao().addUserStatus(userStatus);

        //vibrate the user's phone
        vibrator.vibrate(500);

        this.result();
    }

    @Override
    public void result() {
        //open the question result fragment
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
            clockTick.isPlaying();
        } catch (IllegalStateException e) {
            clockTick = soundTick(R.raw.clock_tick);
            clockTick.start();
        }
        this.getQuestion();
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

    public void tellFragments() {
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


}
