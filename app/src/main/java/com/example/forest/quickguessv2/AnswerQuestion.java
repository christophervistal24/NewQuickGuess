package com.example.forest.quickguessv2;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.forest.quickguessv2.DB.DB;
import com.example.forest.quickguessv2.DB.Points.Points;
import com.example.forest.quickguessv2.DB.Questions.Questions;
import com.example.forest.quickguessv2.DB.UserStatus.UserStatus;
import com.example.forest.quickguessv2.Helpers.SharedPreferenceHelper;
import com.example.forest.quickguessv2.Utilities.PointsUtil;
import com.example.forest.quickguessv2.Utilities.QuestionUtil;
import com.example.forest.quickguessv2.QuestionInterface.QuestionInterface;
import com.example.forest.quickguessv2.Utilities.TypeFaceUtil;

import java.util.ArrayList;
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

    private static final long counter = 21000;
    public static CountDownTimer countDownTimer;
    private int userPoints = 0;


    public ArrayList<RadioButton> listOfRadioButtons;
    int count = 0;
    boolean isCounterRunning = false;
    private Questions q;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_question);
        TypeFaceUtil.initFont(this);
        ButterKnife.bind(this);
        Intent i = getIntent();
        bundle = new Bundle();
        count = RGroup.getChildCount();
        String category = i.getStringExtra("category_name");
        background.setImageResource(getResources().getIdentifier(category,"drawable",getPackageName()));
        getAllQuestions();
    }


    public void getAllQuestions()
    {
        startTimer(counter);
        List<Questions>  questions = DB.getInstance(this).questionsDao().getQuestionsByCategory(1);
        if  (questions.size() != 0)
        {
            List<Questions> question1 = QuestionUtil.questions(questions, questions.size());
            q = question1.get(0);
            String[] randomizeChoices = QuestionUtil.choices(new String[]{q.getChoice_a(), q.getChoice_b(), q.getChoice_c(), q.getChoice_d()}, 3);
            question.setText(q.getQuestion());
            choice_a.setText(randomizeChoices[0]);
            choice_b.setText(randomizeChoices[1]);
            choice_c.setText(randomizeChoices[2]);
            choice_d.setText(randomizeChoices[3]);
        } else {
            Toast.makeText(this, "Congratualations!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @OnCheckedChanged({R.id.choice_a, R.id.choice_b,R.id.choice_c,R.id.choice_d})
    public void onRadioButtonCheckChanged(CompoundButton button, boolean checked) {
        if(checked) {
            String userAnswer;
            userAnswer = button.getText().toString();
            getAnswer(userAnswer,q.getCorrect_answer());
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
                    getAnswer("No answer",q.getCorrect_answer());
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
        countDownTimer.cancel();
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
            super.onBackPressed();
    }

    @Override
    public void correct() {
        UserStatus userStatus = new UserStatus();
        userStatus.setQuestion_id(q.getId());
        // 1 - is eqaul to correct
        userStatus.setQuestion_result(1);
        //add user answered question
        DB.getInstance(this).userStatusDao().addUserStatus(userStatus);
        result();
        isUserHasPoints();
    }

    private void isUserHasPoints()
    {
        userPoints +=1;
        Points points = new Points();
        points.setId(1);
        points.setPoints(PointsUtil.plusExtra(userPoints));
        try {
            DB.getInstance(this).pointsDao().insert(points);
         } catch (SQLiteConstraintException exception) {
            DB.getInstance(this).pointsDao().update(points);
         }
    }

    @Override
    public void wrong() {
        result();
    }

    @Override
    public void getAnswer(String answer, String correct_answer) {

        SharedPreferenceHelper.PREF_FILE = "question";
        SharedPreferenceHelper.setSharedPreferenceString(this,"title",correct_answer);
        SharedPreferenceHelper.setSharedPreferenceString(this,"question_content",q.getFun_facts());

        //apply some background changes
        radioChangeBackground(correct_answer);
        if (answer.equalsIgnoreCase(correct_answer))
        {
            bundle.putString("result","check");
            correct();
        } else {
            bundle.putString("result","wrong");
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



    private void radioChangeBackground(String correct_answer)
    {
        listOfRadioButtons = new ArrayList<>();
        for(int i =0; i<count; i++)
        {
            View o = RGroup.getChildAt(i);
            if (o instanceof RadioButton) {
                listOfRadioButtons.add((RadioButton)o);
                if (listOfRadioButtons.get(i).getText().equals(correct_answer))
                {
                    listOfRadioButtons.get(i).setBackground(ContextCompat.getDrawable(this, R.drawable.btn_correct));
                } else {
                    listOfRadioButtons.get(i).setBackground(ContextCompat.getDrawable(this, R.drawable.btn_incorrect));
                }
        }

        }
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
        cancelTimer();
        DB.getInstance(this).destroyInstance();
        super.onDestroy();
    }
}
