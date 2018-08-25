package com.example.forest.quickguessv2.DB.Questions;

import android.content.Context;

import com.example.forest.quickguessv2.DB.DB;

public class QuestionRepositories {

    public Context context;
    public QuestionRepositories(Context context)
    {
        this.context = context;
    }

    public void questionCreator(String question, String a, String b, String c , String d , String correct , String fun_facts , String fun_facts_image, int category_id)
    {
       if (checkQuestions() == 0)
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
           DB.getInstance(context).questionsDao().insert(questions);
       }
    }

    private int checkQuestions()
    {
        return DB.getInstance(context).questionsDao().countQuestion();
    }
}
