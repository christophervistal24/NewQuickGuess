package com.example.forest.quickguessv2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.forest.quickguessv2.Adapters.FunFactsAdapter;
import com.example.forest.quickguessv2.DB.DB;
import com.example.forest.quickguessv2.DB.Questions.Questions;
import com.example.forest.quickguessv2.Helpers.SharedPreferenceHelper;
import com.example.forest.quickguessv2.Helpers.WindowHelper;
import com.example.forest.quickguessv2.Utilities.TypeFaceUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class DisplayAllFunFacts extends AppCompatActivity {

    private RecyclerView questionRecyclerView;
    private RecyclerView.Adapter adapter;
    private List<com.example.forest.quickguessv2.RecyclerView.Questions> questionsItems;
    String category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_all_fun_facts);
        ButterKnife.bind(this);
        WindowHelper.hideNavigationBar(this);
        questionRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        questionRecyclerView.setHasFixedSize(true);
        questionRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        SharedPreferenceHelper.PREF_FILE="user_played";
        category = SharedPreferenceHelper.
                getSharedPreferenceString(getApplicationContext(),"category",null)
                .toLowerCase();
        getAllFunfacts();
    }

    public DisplayAllFunFacts get()
    {
        return this;
    }

    private void getAllFunfacts()
    {
        questionsItems = new ArrayList<>();
        int category_id = DB.getInstance(getApplicationContext()).categoriesQuestionDao().getCategoryIdByName(category);
        //getAllFunfacts the all questions by category id
          List<Questions> questionsList = DB.getInstance(getApplicationContext())
                  .questionsDao().getQuestionByCategoryId(category_id);
          for(Questions q : questionsList)
          {
              com.example.forest.quickguessv2.RecyclerView.Questions questions =
                      new com.example.forest.quickguessv2.RecyclerView.Questions(q.getFun_facts(),q.getFun_facts_image());
                    questionsItems.add(questions);
          }
        adapter = new FunFactsAdapter(questionsItems,getApplicationContext());
        questionRecyclerView.setAdapter(adapter);
    }


}
