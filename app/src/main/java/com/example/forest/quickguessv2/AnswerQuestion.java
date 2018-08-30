package com.example.forest.quickguessv2;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
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
import com.example.forest.quickguessv2.Helpers.SharedPreferenceHelper;
import com.example.forest.quickguessv2.QuestionInterface.QuestionInterface;
import com.example.forest.quickguessv2.Utilities.BackgroundUtil;
import com.example.forest.quickguessv2.Utilities.FragmentUtil;
import com.example.forest.quickguessv2.Utilities.GameBundleUitl;
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

    private static final long counter = 21000;
    public CountDownTimer countDownTimer;
    private int userPoints = 0;
    public LifeRepositories lifeRepositories;
    PointsRepositories pointsRepositories;

    Handler handler;
    Runnable openFunFactsFragment;


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
        bundle = new Bundle();
    }


    public void getQuestion()
    {

        try {
            startTimer(counter);
            //get one questions
            q = questionRepositories.selectQuestion();
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
            Toast.makeText(this, "Congrats", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    @OnCheckedChanged({R.id.choice_a, R.id.choice_b,R.id.choice_c,R.id.choice_d})
    public void onRadioButtonCheckChanged(CompoundButton button, boolean checked) {
        if(checked) {
            String userAnswer;
            userAnswer = button.getText().toString();
            getAnswer(userAnswer, q.getCorrect_answer());
            cancelTimer();
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
                    isCounterRunning = false;
                    getAnswer("No answer", q.getCorrect_answer());
                }
            };
            countDownTimer.start();
        } else {
            cancelTimer();
            countDownTimer.start();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        cancelTimer();
        if  (getSupportFragmentManager().getBackStackEntryCount() != 0)
        {
           fragmentUtil.disposeAllBackStack();
        }
    }

    @Override
    public void correct() {
        UserStatus userStatus = new UserStatus();
        userStatus.setQuestion_id(q.getId());
        // 1 - is eqaul to correct
        userStatus.setQuestion_result(1);
        //add user answered question
        DB.getInstance(this).userStatusDao().addUserStatus(userStatus);
        UserRepositories.isUserHasPoints(getApplicationContext(),userPoints++,pointsRepositories);
        result();
    }


    @Override
    public void wrong() {
        int decreaseCurrentLife = (lifeRepositories.getUserLife()) - 1;
        lifeRepositories.setLifeToUser(decreaseCurrentLife);
        GameoverRepositories.isGameOver(lifeRepositories);
        result();
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
        fragmentUtil.startResultFragment(this,bundle);
    }



    private void radioChangeBackground(String correct_answer)
    {
        BackgroundUtil.radioBackgrounds(getApplicationContext(),RGroup,correct_answer);
    }

    private void cancelTimer()
    {
        if  (countDownTimer != null)
        {
            countDownTimer.cancel();
        }
    }

    @Override
    protected void onDestroy() {
       if (openFunFactsFragment != null)
       {
           handler.removeCallbacks(openFunFactsFragment);
       }
        cancelTimer();
        DB.getInstance(this).destroyInstance();
        super.onDestroy();
    }

    private void gotoFunFactsFragment()
    {
        fragmentUtil.startFunFactsFragment(this);
    }

    protected void onPreExecute() {
        handler = new Handler();
        openFunFactsFragment = new Runnable() {
            public void run() {
                gotoFunFactsFragment();
                //rebase
                life.setText(String.valueOf(lifeRepositories.getUserLife()));
                userPoints = 0;
            }
        };
        handler.postDelayed(openFunFactsFragment,800);
    }
}
