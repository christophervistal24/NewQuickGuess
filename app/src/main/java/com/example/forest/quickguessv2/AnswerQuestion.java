package com.example.forest.quickguessv2;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.forest.quickguessv2.Adapters.SlideAdapter;
import com.example.forest.quickguessv2.DB.DB;
import com.example.forest.quickguessv2.DB.Friends.FriendsRepositories;
import com.example.forest.quickguessv2.DB.Life.LifeRepositories;
import com.example.forest.quickguessv2.DB.Points.PointsRepositories;
import com.example.forest.quickguessv2.DB.Questions.QuestionRepositories;
import com.example.forest.quickguessv2.DB.Questions.Questions;
import com.example.forest.quickguessv2.DB.User.UserRepositories;
import com.example.forest.quickguessv2.DB.UserStatus.UserStatus;
import com.example.forest.quickguessv2.Helpers.SharedPreferenceHelper;
import com.example.forest.quickguessv2.QuestionInterface.QuestionInterface;
import com.example.forest.quickguessv2.Utilities.BackgroundUtil;
import com.example.forest.quickguessv2.Utilities.EncryptUtil;
import com.example.forest.quickguessv2.Utilities.FragmentUtil;
import com.example.forest.quickguessv2.Utilities.GameBundleUitl;
import com.example.forest.quickguessv2.Utilities.SoundUtil;
import com.example.forest.quickguessv2.Utilities.TypeFaceUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class AnswerQuestion extends AppCompatActivity  implements QuestionInterface{

    @BindView(R.id.question) TextView question;
    @BindView(R.id.choice_a)Button choice_a;
    @BindView(R.id.choice_b) Button choice_b;
    @BindView(R.id.choice_c) Button choice_c;
    @BindView(R.id.choice_d) Button choice_d;
    @BindView(R.id.timer) TextView timer;
    @BindView(R.id.life) TextView life;
    @BindView(R.id.userPoints) TextView points;
    @BindView(R.id.timerLayout) LinearLayout timerLayout;
    @BindView(R.id.fragment_game_over) FrameLayout gameOver;
    @BindView(R.id.questionLayout) LinearLayout questionLayout;
    @BindView(R.id.answerQuestionLayout) RelativeLayout answerQuestionLayout;


    private static final long counter = 2000;
    public CountDownTimer countDownTimer;
    protected int userPoints = 0;
    public LifeRepositories lifeRepositories;
    private PointsRepositories pointsRepositories;
    private int updatedUserPoints = 0;

    boolean isCounterRunning = false;
    protected Questions q;
    Bundle bundle;
    FragmentUtil fragmentUtil;
    QuestionRepositories questionRepositories;
    FriendsRepositories friendsRepositories;
    public MediaPlayer clockTick;
    protected  SlideAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_question);
        ButterKnife.bind(this);
        TypeFaceUtil.initDimboFont(this);
        classInstantiate();
        getQuestion();
        life.setText(String.valueOf(lifeRepositories.getUserLife()));
        points.setText(String.valueOf(UserRepositories.isUserHasPoints(getApplicationContext(),userPoints,pointsRepositories)));
    }


    private void classInstantiate() {
        lifeRepositories = new LifeRepositories(getApplicationContext());
        pointsRepositories = new PointsRepositories(getApplicationContext());
        questionRepositories = new QuestionRepositories(getApplicationContext());
        friendsRepositories = new FriendsRepositories(getApplicationContext());
        fragmentUtil = new FragmentUtil();
        bundle = new Bundle();
        clockTick = soundTick(R.raw.clock_tick);
        adapter = new SlideAdapter(getApplicationContext());
    }

    public MediaPlayer soundTick(int uri)
    {
        return MediaPlayer.create(this,uri);
    }


    public void getQuestion()
    {

        try {
            startTimer(counter);
            SharedPreferenceHelper.PREF_FILE = "user_played";
            String selected_category = SharedPreferenceHelper
                    .getSharedPreferenceString(getApplicationContext(),"category",null)
                    .toLowerCase();
            int category_id = DB.getInstance(getApplicationContext()).categoriesQuestionDao().getCategoryIdByName(selected_category);
            int level_id = questionRepositories.questionClassier(category_id);
            BackgroundUtil.changeAnswerQuestionBG(answerQuestionLayout,level_id);
            BackgroundUtil.changeButtonsBackground(new Button[]{choice_a, choice_b, choice_c, choice_d},level_id);
            //get one questions
            q = questionRepositories.selectQuestion(category_id);
            List<String> choices = Arrays.asList(q.getChoice_a(), q.getChoice_b(), q.getChoice_c(), q.getChoice_d());
            List<String> randomizeChoices = questionRepositories.randomizeChoices(choices);
            //set and decrypt
                try {
                    question.setText(EncryptUtil.decryptMethod(q.getQuestion()));
                    choice_a.setText(EncryptUtil.decryptMethod(randomizeChoices.get(0)));
                    choice_b.setText(EncryptUtil.decryptMethod(randomizeChoices.get(1)));
                    choice_c.setText(EncryptUtil.decryptMethod(randomizeChoices.get(2)));
                    choice_d.setText(EncryptUtil.decryptMethod(randomizeChoices.get(3)));
                } catch (Exception e) {
                    e.printStackTrace();
                }

        } catch (NullPointerException e)
        {
            finish();
        }

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
            clockTick.release();
            try {
                getAnswer(userAnswer, EncryptUtil.decryptMethod(q.getCorrect_answer()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            countDownTimer.cancel();
   }


    private  void startTimer(final long counter) {
        if (!isCounterRunning) {
            isCounterRunning  = true;
            clockTick.start();
            countDownTimer = new CountDownTimer(counter, 1000) {
                @Override
                public void onTick(long timeRemaining) {
                    int remainingTime = (int) (TimeUnit.MILLISECONDS.toSeconds(timeRemaining) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeRemaining)));
                    timer.setText(String.valueOf(remainingTime));
                    if  (remainingTime <= 5)
                    {
                        timer.setTextColor(Color.parseColor("#d1395c"));
                        YoYo.with(Techniques.Pulse)
                                .duration(500)
                                .repeat(5)
                                .playOn(timer);
                    }  else {
                        timer.setTextColor(Color.parseColor("#707070"));
                    }

                    if  (remainingTime == 1)
                    {
                        clockTick.release();
                    }

                }

                @Override
                public void onFinish() {
                    try {
                        timer.setText("0");
                        isCounterRunning = false;
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
        countDownTimer.cancel();
        clockTick.release();
        tellFragments();
        super.onBackPressed();
    }

    @Override
    public void getAnswer(String answer, String correct_answer) {

        try {
            SharedPreferenceHelper.PREF_FILE = "question";
            SharedPreferenceHelper.setSharedPreferenceString(this,"title",correct_answer);
            SharedPreferenceHelper.setSharedPreferenceString(this,"question_content", EncryptUtil.decryptMethod(q.getFun_facts()));
            SharedPreferenceHelper.setSharedPreferenceString(this,"question_image", EncryptUtil.decryptMethod(q.getFun_facts_image()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        timerLayoutDisplayOrHide();
        questionLayoutDisplayOrHide();
        if (answer.trim().equalsIgnoreCase(correct_answer.trim()))
        {
            GameBundleUitl.setQuestionResult(bundle,"result","correct_icon");
            correct();
        } else {
            GameBundleUitl.setQuestionResult(bundle,"result","wrong_icon");
            wrong();
        }
    }


    @Override
    public void correct() {

        SoundUtil.songLoad(getApplicationContext(),R.raw.check).start();
        UserStatus userStatus = new UserStatus();
        userStatus.setQuestion_id(q.getId());
        // 1 - is eqaul to correct
        userStatus.setQuestion_result(1);
        userStatus.setCategory_id(q.getCategory_id());
        userStatus.setClass_id(q.getClass_id());
        //add user answered question
        DB.getInstance(getApplicationContext()).userStatusDao().addUserStatus(userStatus);
        updatedUserPoints = UserRepositories.isUserHasPoints(getApplicationContext(),++userPoints,pointsRepositories);
        points.setText(String.valueOf(updatedUserPoints));
        result();
    }


    @Override
    public void wrong() {
        SoundUtil.songLoad(getApplicationContext(),R.raw.wrong).start();
        int decreaseCurrentLife = (lifeRepositories.getUserLife()) - 1;
        lifeRepositories.setLifeToUser(decreaseCurrentLife);
        updatedUserPoints = UserRepositories.isUserHasPoints(getApplicationContext(),userPoints,pointsRepositories);
        points.setText(String.valueOf(updatedUserPoints));
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
            clockTick.isPlaying();
        } catch (IllegalStateException e)
        {
            clockTick = soundTick(R.raw.clock_tick);
            clockTick.start();
        }
        getQuestion();
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



    public void timerLayoutDisplayOrHide()
    {
        timerLayout.setVisibility(timerLayout.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }
    public void questionLayoutDisplayOrHide()
    {
        questionLayout.setVisibility(questionLayout.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }

 /*   public void questionLayoutDisplay()
    {
        questionLayout.setVisibility(View.VISIBLE);
    }
    public void questionLayoutHide()
    {
        questionLayout.setVisibility(View.GONE);
    }
*/
    @Override
    protected void onDestroy() {
        countDownTimer.cancel();
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

    protected void displayGameOverFragment()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        GameOverFragment gameOverFragment = new GameOverFragment();
        fragmentTransaction.add(R.id.fragment_game_over,gameOverFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


}
