package com.example.forest.quickguessv2;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.forest.quickguessv2.DBModule.Questions;
import com.example.forest.quickguessv2.Helpers.FontHelper;
import com.example.forest.quickguessv2.Helpers.RandomizeHelper;
import com.example.forest.quickguessv2.QuestionInterface.QuestionInterface;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class AnswerQuestion extends AppCompatActivity  implements QuestionInterface{

    @BindView(R.id.background) ImageView background;
    @BindView(R.id.question) TextView question;
    @BindView(R.id.choice_a) RadioButton choice_a;
    @BindView(R.id.choice_b) RadioButton choice_b;
    @BindView(R.id.choice_c) RadioButton choice_c;
    @BindView(R.id.choice_d) RadioButton choice_d;
    @BindView(R.id.timer) TextView timer;
    private static final long counter = 6000;
    CountDownTimer countDownTimer;
    boolean isCounterRunning = false;
    private Questions q;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_question);
        ButterKnife.bind(this);
        Typeface fontHelper = new FontHelper().dimboFont(this);
        Intent i = getIntent();
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
        List<Questions> question1 = RandomizeHelper.questions(questions, questions.size());
        q = question1.get(0);
        question.setText(q.getQuestion());
        choice_a.setText(q.getChoice_a());
        choice_b.setText(q.getChoice_b());
        choice_c.setText(q.getChoice_c());
        choice_d.setText(q.getChoice_d());
    }

    @OnCheckedChanged({R.id.choice_a, R.id.choice_b,R.id.choice_c,R.id.choice_d})
    public void onRadioButtonCheckChanged(CompoundButton button, boolean checked) {
        if(checked) {
            switch (button.getId()) {
                case R.id.choice_a:
                        getAnswer((String) choice_a.getText(),q.getCorrect_answer());
                    break;
                case R.id.choice_b:
                    getAnswer((String) choice_b.getText(),q.getCorrect_answer());
                    break;

                case R.id.choice_c:
                    getAnswer((String) choice_c.getText(),q.getCorrect_answer());
                    break;

                case R.id.choice_d:
                    getAnswer((String) choice_d.getText(),q.getCorrect_answer());
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
                }
            };
            countDownTimer.start();
        } else {
            countDownTimer.cancel();
            countDownTimer.start();
        }
    }


    @Override
    public void correct() {

    }

    @Override
    public void wrong() {

    }

    @Override
    public void getAnswer(String answer, String correct_answer) {
        if (answer.equalsIgnoreCase(correct_answer))
        {
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            correct();
        } else {
            Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show();
            wrong();
        }
    }
}
