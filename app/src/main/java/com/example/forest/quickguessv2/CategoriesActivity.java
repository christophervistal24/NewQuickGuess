package com.example.forest.quickguessv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.forest.quickguessv2.DB.Categories.QuestionCategory;
import com.example.forest.quickguessv2.DB.MyAppDB;
import com.example.forest.quickguessv2.DB.Questions.Questions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CategoriesActivity extends AppCompatActivity {

    @BindView(R.id.categoriesLayout)LinearLayout categoriesLayout;
    private List<QuestionCategory> questionCategory;
    int countQuestions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        ButterKnife.bind(this);

        questionCategory = MyAppDB.getInstance(this).categoriesQuestionDao().getAllCategories();
        insertQuestions();
        Log.d("number of questions" , String.valueOf(countQuestions));
    }

    @OnClick({R.id.people,R.id.plants,R.id.animals,R.id.geography,R.id.sports,R.id.music,R.id.technology,R.id.entertainment})
    public void onClick(View view) {
        Intent intent;
       switch (view.getId())
        {
            case R.id.people:
                gotoActivity("bg_people",this,AnswerQuestion.class);
                break;

            case R.id.plants:
                gotoActivity("bg_plants",this,AnswerQuestion.class);
                break;

            case R.id.animals:
                gotoActivity("bg_animals",this,AnswerQuestion.class);
                break;

            case R.id.geography:
                gotoActivity("bg_geography",this,AnswerQuestion.class);
                break;

            case R.id.sports:
                gotoActivity("bg_sports",this,AnswerQuestion.class);
                break;

            case R.id.music:
                gotoActivity("bg_music",this,AnswerQuestion.class);
                break;

            case R.id.technology:
                gotoActivity("bg_technology",this,AnswerQuestion.class);
                break;

            case R.id.entertainment:
                gotoActivity("bg_entertainment",this,AnswerQuestion.class);
                break;


        }
    }

    private void gotoActivity(String category_name, CategoriesActivity categoriesActivity, Class<AnswerQuestion> categoriesActivityClass) {
        Intent intent = new Intent(categoriesActivity,categoriesActivityClass);
        intent.putExtra("category_name",category_name);
        startActivity(intent);
    }

    private void addQuestion(String question, String a, String b, String c , String d , String correct , String fun_facts , String fun_facts_image, int category_id)
    {
        Questions questions = new Questions();
        questions.setQuestion(question);
        questions.setChoice_a(a);
        questions.setChoice_b(b);
        questions.setChoice_c(c);
        questions.setChoice_d(d);
        questions.setCorrect_answer(a);
        questions.setFun_facts(fun_facts);
        questions.setFun_facts_image(fun_facts_image);
        questions.setCategory_id(category_id);
        MyAppDB.getInstance(this).questionsDao().insert(questions);
    }


    private void insertQuestions()
    {
        countQuestions = MyAppDB.getInstance(this).questionsDao().countQuestion();
        if  (countQuestions == 0){
            addQuestion("What is","sample","sample2","sample3","sample4","sample","This is a sample","default",1);
            addQuestion("What is2","sample","sample2","sample3","sample4","sample","This is a sample","default",1);
            addQuestion("What is3","sample","sample2","sample3","sample4","sample","This is a sample","default",1);
            addQuestion("What is4","sample","sample2","sample3","sample4","sample","This is a sample","default",1);
            addQuestion("What is5","sample","sample2","sample3","sample4","sample","This is a sample","default",1);
            addQuestion("What is6","sample","sample2","sample3","sample4","sample","This is a sample","default",1);
            addQuestion("What is7","sample","sample2","sample3","sample4","sample","This is a sample","default",1);
        }
    }
}
