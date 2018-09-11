package com.example.forest.quickguessv2;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.forest.quickguessv2.DB.DB;
import com.example.forest.quickguessv2.DB.GameOver.GameoverRepositories;
import com.example.forest.quickguessv2.DB.Life.LifeRepositories;
import com.example.forest.quickguessv2.DB.Points.PointsRepositories;
import com.example.forest.quickguessv2.DB.Questions.QuestionRepositories;
import com.example.forest.quickguessv2.DB.Questions.Questions;
import com.example.forest.quickguessv2.DB.User.UserRepositories;
import com.example.forest.quickguessv2.DB.UserStatus.UserStatus;
import com.example.forest.quickguessv2.Helpers.RedirectHelper;
import com.example.forest.quickguessv2.Helpers.SharedPreferenceHelper;
import com.example.forest.quickguessv2.QuestionInterface.QuestionInterface;
import com.example.forest.quickguessv2.Service.MyService;
import com.example.forest.quickguessv2.Utilities.BackgroundUtil;
import com.example.forest.quickguessv2.Utilities.FragmentUtil;
import com.example.forest.quickguessv2.Utilities.GameBundleUitl;
import com.example.forest.quickguessv2.Utilities.SoundUtil;
import com.example.forest.quickguessv2.Utilities.TypeFaceUtil;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

public class AnswerQuestion extends AppCompatActivity  implements QuestionInterface{

    @BindView(R.id.background) ImageView background;
    @BindView(R.id.question) TextView question;
    @BindView(R.id.choice_a) RadioButton choice_a;
    @BindView(R.id.choice_b) RadioButton choice_b;
    @BindView(R.id.choice_c) RadioButton choice_c;
    @BindView(R.id.choice_d) RadioButton choice_d;
    @BindView(R.id.timer) TextView timer;
    @BindView(R.id.RGroup) RadioGroup RGroup;
    @BindView(R.id.life) TextView life;
    @BindView(R.id.userPoints) TextView points;

    private static final long counter = 5000;
    public CountDownTimer countDownTimer;
    protected int userPoints = 0;
    public LifeRepositories lifeRepositories;
    private PointsRepositories pointsRepositories;
    private int updatedUserPoints = 0;

    Handler handler;
    Runnable openFunFactsFragment;
    MediaPlayer clockTick;

    boolean isCounterRunning = false;
    private Questions q;
    Bundle bundle;
    FragmentUtil fragmentUtil;
    QuestionRepositories questionRepositories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_question);
        ButterKnife.bind(this);
        TypeFaceUtil.initFont(this);
        classInstantiate();
        categoryBackground();
        getQuestion();
        life.setText(String.valueOf(lifeRepositories.getUserLife()));
        points.setText(String.valueOf(UserRepositories.isUserHasPoints(getApplicationContext(),userPoints,pointsRepositories)));
    }

    private void categoryBackground() {
        Intent i = getIntent();
        background.setImageResource(BackgroundUtil.setBackground(getApplicationContext(),i));
    }

    private void classInstantiate() {
        lifeRepositories = new LifeRepositories(this);
        pointsRepositories = new PointsRepositories(this);
        questionRepositories = new QuestionRepositories(this);
        fragmentUtil = new FragmentUtil();
        clockTick = SoundUtil.songLoad(getApplicationContext(),R.raw.clock_tick);
        clockTick.start();
        bundle = new Bundle();
        stopService(new Intent(this, MyService.class));
    }


    public void getQuestion()
    {
        try {
            startTimer(counter);
            SoundUtil.songLoad(getApplicationContext(),R.raw.clock_tick)
                    .start();
            SharedPreferenceHelper.PREF_FILE = "user_played";
            String selected_category = SharedPreferenceHelper
                    .getSharedPreferenceString(getApplicationContext(),"category",null)
                    .toLowerCase();
            int category_id = DB.getInstance(getApplicationContext()).categoriesQuestionDao().getCategoryIdByName(selected_category);
            //get one questions
            q = questionRepositories.selectQuestion(category_id);
            List<String> choices = Arrays.asList(q.getChoice_a(), q.getChoice_b(), q.getChoice_c(), q.getChoice_d());
            List<String> randomizeChoices = questionRepositories.randomizeChoices(choices);

            //set
            question.setText(q.getQuestion());
            choice_a.setText(randomizeChoices.get(0));
            choice_b.setText(randomizeChoices.get(1));
            choice_c.setText(randomizeChoices.get(2));
            choice_d.setText(randomizeChoices.get(3));

        } catch (NullPointerException e)
        {
            finish();
        }

    }

    @OnCheckedChanged({R.id.choice_a, R.id.choice_b,R.id.choice_c,R.id.choice_d})
    public void onRadioButtonCheckChanged(CompoundButton button, boolean checked) {
        if(checked) {
            getAnswer(button.getText().toString(), q.getCorrect_answer());
            clockTick.stop();
            countDownTimer.cancel();
            RGroup.setEnabled(false);
        }
    }


    private  void startTimer(final long counter) {
        if (!isCounterRunning) {
            isCounterRunning  = true;
            countDownTimer = new CountDownTimer(counter, 1000) {
                @Override
                public void onTick(long timeRemaining) {
                    timer.setText(
                            String.valueOf(TimeUnit.MILLISECONDS.toSeconds(timeRemaining) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeRemaining)))
                    );
                }

                @Override
                public void onFinish() {
                    clockTick.stop();
                    isCounterRunning = false;
                    getAnswer("No answer", q.getCorrect_answer());
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
        tellFragments();
        super.onBackPressed();
    }

    @Override
    public void correct() {
        UserStatus userStatus = new UserStatus();
        userStatus.setQuestion_id(q.getId());
        // 1 - is eqaul to correct
        userStatus.setQuestion_result(1);
        //add user answered question
        DB.getInstance(getApplicationContext()).userStatusDao().addUserStatus(userStatus);
        updatedUserPoints = UserRepositories.isUserHasPoints(getApplicationContext(),userPoints+1,pointsRepositories);
        points.setText(String.valueOf(updatedUserPoints));
        result();
    }


    @Override
    public void wrong() {
        int decreaseCurrentLife = (lifeRepositories.getUserLife()) - 1;
        lifeRepositories.setLifeToUser(decreaseCurrentLife);
        updatedUserPoints = UserRepositories.isUserHasPoints(getApplicationContext(),userPoints,pointsRepositories);
        points.setText(String.valueOf(updatedUserPoints));
        GameoverRepositories.isGameOver(lifeRepositories);
        result();
    }

    @Override
    protected void onResume() {
        clockTick.stop();
        clockTick.start();
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
    public void getAnswer(String answer, String correct_answer) {
        SharedPreferenceHelper.PREF_FILE = "question";
        SharedPreferenceHelper.setSharedPreferenceString(this,"title",correct_answer);
        SharedPreferenceHelper.setSharedPreferenceString(this,"question_content", q.getFun_facts());

        //apply some background changes
        radioChangeBackground(correct_answer);
        if (answer.equalsIgnoreCase(correct_answer))
        {
            GameBundleUitl.setQuestionResult(bundle,"result","check");
            correct();
        } else {
            GameBundleUitl.setQuestionResult(bundle,"result","wrong");
            wrong();
        }
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
    protected void onPause() {
        super.onPause();
        if  (this.isFinishing())
        {
            clockTick.stop();
        }

    }

    private void radioChangeBackground(String correct_answer)
    {
        BackgroundUtil.radioBackgrounds(getApplicationContext(),RGroup,correct_answer);
    }



    public void removeCallback()
    {
        if (openFunFactsFragment != null)
        {
            handler.removeCallbacks(openFunFactsFragment);
        }
    }
    @Override
    protected void onDestroy() {
        countDownTimer.cancel();
        DB.getInstance(getApplicationContext()).destroyInstance();
        super.onDestroy();
    }

    private void gotoFunFactsFragment(){ fragmentUtil.startFunFactsFragment(this); }

    protected void onPreExecute() {
        handler = new Handler();
        openFunFactsFragment = new Runnable() {
            public void run() {

                gotoFunFactsFragment();
                //rebase
                life.setText(String.valueOf(lifeRepositories.getUserLife()));
//                initUserPoints()
                userPoints = 0;
                countDownTimer.cancel();
                isCounterRunning = true;
            }
        };
        handler.postDelayed(openFunFactsFragment,200);
    }

    private void tellFragments() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for(Fragment f : fragments){
            if(f != null && f instanceof FunFacts)
                ((FunFacts)f).onBackPressed();
        }
    }
}
