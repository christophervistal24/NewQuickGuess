package com.example.forest.quickguessv2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.forest.quickguessv2.DBModule.Questions;
import com.example.forest.quickguessv2.Helpers.FontHelper;
import com.example.forest.quickguessv2.Helpers.SharedPreferenceHelper;
import com.example.forest.quickguessv2.Utilities.QuestionUtil;
import com.example.forest.quickguessv2.QuestionInterface.QuestionInterface;

import java.util.List;
import java.util.Objects;
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

    private static final long counter = 6000;
    CountDownTimer countDownTimer;


    boolean isCounterRunning = false;
    private Questions q;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_question);
        ButterKnife.bind(this);
        Typeface fontHelper = new FontHelper().dimboFont(this);
        Intent i = getIntent();
        bundle = new Bundle();
        String category = i.getStringExtra("category_name");
        background.setImageResource(getResources().getIdentifier(category,"drawable",getPackageName()));
        question.setTypeface(fontHelper);
        choice_a.setTypeface(fontHelper);
        choice_b.setTypeface(fontHelper);
        choice_c.setTypeface(fontHelper);
        choice_d.setTypeface(fontHelper);
        timer.setTypeface(fontHelper);
        getAllQuestions();
    }


    private void getAllQuestions()
    {
        startTimer(counter);
        List<Questions>  questions = MainActivity.myAppDB.questionsDao().getQuestionsByCategory(1);
        List<Questions> question1 = QuestionUtil.questions(questions, questions.size());
        q = question1.get(0);
        String[] randomizeChoices = QuestionUtil.choices(new String[]{q.getChoice_a(), q.getChoice_b(), q.getChoice_b(), q.getChoice_d()}, 3);
        question.setText(q.getQuestion());
        choice_a.setText(randomizeChoices[0]);
        choice_b.setText(randomizeChoices[1]);
        choice_c.setText(randomizeChoices[2]);
        choice_d.setText(randomizeChoices[3]);
    }

    @OnCheckedChanged({R.id.choice_a, R.id.choice_b,R.id.choice_c,R.id.choice_d})
    public void onRadioButtonCheckChanged(CompoundButton button, boolean checked) {
        if(checked) {
            switch (button.getId()) {
                case R.id.choice_a:
                     getAnswer((String) choice_a.getText(),q.getCorrect_answer());
                     countDownTimer.cancel();
                    break;
                case R.id.choice_b:
                    getAnswer((String) choice_b.getText(),q.getCorrect_answer());
                    countDownTimer.cancel();
                    break;

                case R.id.choice_c:
                    getAnswer((String) choice_c.getText(),q.getCorrect_answer());
                    countDownTimer.cancel();
                    break;

                case R.id.choice_d:
                    getAnswer((String) choice_d.getText(),q.getCorrect_answer());
                    countDownTimer.cancel();
                    break;
            }
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
            countDownTimer.cancel();
            countDownTimer.start();
        }
    }

    @Override
    public void onBackPressed() {
        countDownTimer.cancel();
        super.onBackPressed();
    }

    @Override
    public void correct() {
        result();
    }

    @Override
    public void wrong() {
        result();
    }

    @Override
    public void getAnswer(String answer, String correct_answer) {
        isA(correct_answer);
        isB(correct_answer);
        isC(correct_answer);
        isD(correct_answer);
        SharedPreferenceHelper.PREF_FILE = "question";
        SharedPreferenceHelper.setSharedPreferenceString(this,"title",correct_answer);
        SharedPreferenceHelper.setSharedPreferenceString(this,"question_content",q.getFun_facts());
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

    private void isA(String correct_answer)
    {
        if (choice_a.getText().toString().equalsIgnoreCase(correct_answer))
        {
            choice_a.setBackground(ContextCompat.getDrawable(this, R.drawable.btn_correct));
        }
        else {
            choice_a.setBackground(ContextCompat.getDrawable(this, R.drawable.btn_incorrect));
        }
    }
    private void isB(String correct_answer)
    {
        if (choice_b.getText().toString().equalsIgnoreCase(correct_answer))
        {
            choice_b.setBackground(ContextCompat.getDrawable(this, R.drawable.btn_correct));
        } else {
            choice_b.setBackground(ContextCompat.getDrawable(this, R.drawable.btn_incorrect));
        }
    }
    private void isC(String correct_answer)
    {
        if (choice_c.getText().toString().equalsIgnoreCase(correct_answer))
        {
            choice_c.setBackground(ContextCompat.getDrawable(this, R.drawable.btn_correct));
        } else {
            choice_c.setBackground(ContextCompat.getDrawable(this, R.drawable.btn_incorrect));
        }
    }
    private void isD(String correct_answer)
    {
        if (choice_d.getText().toString().equalsIgnoreCase(correct_answer))
        {
            choice_d.setBackground(ContextCompat.getDrawable(this, R.drawable.btn_correct));
        } else {
            choice_d.setBackground(ContextCompat.getDrawable(this, R.drawable.btn_incorrect));
        }
    }
}
