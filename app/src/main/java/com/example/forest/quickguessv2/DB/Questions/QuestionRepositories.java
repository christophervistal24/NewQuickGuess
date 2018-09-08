package com.example.forest.quickguessv2.DB.Questions;

import android.content.Context;

import com.example.forest.quickguessv2.AnswerQuestion;
import com.example.forest.quickguessv2.DB.DB;
import com.example.forest.quickguessv2.FinishCategoryActivity;
import com.example.forest.quickguessv2.Helpers.RedirectHelper;
import com.example.forest.quickguessv2.Utilities.RandomizeUtil;

import java.util.List;

public class QuestionRepositories {

    public Context context;
    private int question_id;
    public QuestionRepositories(Context context)
    {
        this.context = context;
    }

    public void questionCreator(String question, String a, String b, String c , String d , String correct , String fun_facts , String fun_facts_image, int category_id)
    {
           Questions questions = new Questions();
           questions.setQuestion(question);
           questions.setChoice_a(a);
           questions.setChoice_b(b);
           questions.setChoice_c(c);
           questions.setChoice_d(d);
           questions.setCorrect_answer(correct);
           questions.setFun_facts(fun_facts);
           questions.setFun_facts_image(fun_facts_image);
           questions.setCategory_id(category_id);
           DB.getInstance(context).questionsDao().insert(questions);
    }

    private List<Questions> getQuestions(int id)
    {
        return DB.getInstance(context).questionsDao().getQuestionsByCategory(id);
    }

  /*  public static int countQuestions(Context context)
    {
        return DB.getInstance(context).questionsDao().countQuestion();
    }
*/

  public Questions selectQuestion(int category_id)
  {
      List<Questions> questionsList = RandomizeUtil.questions(getQuestions(category_id),getQuestions(category_id).size());
      if (questionsList.size() != 0)
      {
          return questionsList.get(0);
      } else {
          ((AnswerQuestion) context).finish();
          new RedirectHelper(context, FinishCategoryActivity.class);
      }
      return null;
  }

  public List<String> randomizeChoices(List<String> choices)
  {
      return RandomizeUtil.choices(choices);
  }

}
