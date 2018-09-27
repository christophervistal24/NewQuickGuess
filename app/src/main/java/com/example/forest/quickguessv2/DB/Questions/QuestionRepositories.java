package com.example.forest.quickguessv2.DB.Questions;

import android.content.Context;
import android.widget.Toast;

import com.example.forest.quickguessv2.AnswerQuestion;
import com.example.forest.quickguessv2.DB.DB;
import com.example.forest.quickguessv2.FinishCategoryActivity;
import com.example.forest.quickguessv2.Helpers.RedirectHelper;
import com.example.forest.quickguessv2.Utilities.EncryptUtil;
import com.example.forest.quickguessv2.Utilities.RandomizeUtil;

import java.util.List;

public class QuestionRepositories {

    public Context context;
    private int class_id = 1;
    public QuestionRepositories(Context context)
    {
        this.context = context;
    }

    public void questionCreator(String question, String a, String b, String c , String d , String correct , String fun_facts , String fun_facts_image, int category_id,int class_id)
    {
        //encrypt
        try {
            Questions questions = new Questions();
            questions.setQuestion(EncryptUtil.encryptMethod(question));
            questions.setChoice_a(EncryptUtil.encryptMethod(a));
            questions.setChoice_b(EncryptUtil.encryptMethod(b));
            questions.setChoice_c(EncryptUtil.encryptMethod(c));
            questions.setChoice_d(EncryptUtil.encryptMethod(d));
            questions.setCorrect_answer(EncryptUtil.encryptMethod(correct));
            questions.setFun_facts(EncryptUtil.encryptMethod(fun_facts));
            questions.setFun_facts_image(EncryptUtil.encryptMethod(fun_facts_image));
            questions.setCategory_id(category_id);
            questions.setClass_id(class_id);
            DB.getInstance(context).questionsDao().insert(questions);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private List<Questions> getQuestions(int id,int class_id)
    {
        return DB.getInstance(context).questionsDao().getQuestionsByCategoryAndClass(id,class_id);
    }


  public Questions selectQuestion(int category_id)
  {
     int class_id = zigZagQuestionClassify(category_id);
     List<Questions> questionsList = RandomizeUtil.questions(getQuestions(category_id,class_id),getQuestions(category_id,class_id).size());
      if (questionsList.size() != 0 )
      {
          return questionsList.get(0);
      } else {
          if (context instanceof AnswerQuestion){
              ((AnswerQuestion) context).finish();
              ((AnswerQuestion) context).sample.release();
          }
          new RedirectHelper(context, FinishCategoryActivity.class);
      }
      return null;
  }

    private int zigZagQuestionClassify(int category_id) {
        if (checkEasyQuestions(category_id)) // if true proceed to moderate
        {
            class_id = 2;
            if (checkModerateQuestions(category_id)) { // if true proceed to difficult
                  class_id = 3;
              }
           }
        return class_id;
    }

    private boolean checkModerateQuestions(int category_id) {
        int countAllModerate = DB.getInstance(context).questionsDao().countAllByClassification(category_id,2);
        int getAnsweredByClass = DB.getInstance(context).userStatusDao().countAnsweredByClassAndCategoryId(category_id,2);
        return (countAllModerate - getAnsweredByClass) == 0;
    }

    private boolean checkEasyQuestions(int category_id) {
        int countAllEasy = DB.getInstance(context).questionsDao().countAllByClassification(category_id,1);
        int getAnsweredByClass = DB.getInstance(context).userStatusDao().countAnsweredByClassAndCategoryId(category_id,1);
        return (countAllEasy - getAnsweredByClass) == 0;
    }




    public List<String> randomizeChoices(List<String> choices)
  {
      return RandomizeUtil.choices(choices);
  }
  

}
