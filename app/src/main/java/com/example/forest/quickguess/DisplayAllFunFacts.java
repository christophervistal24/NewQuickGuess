package com.example.forest.quickguess;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.forest.quickguess.Adapters.FunFactsAdapter;
import com.example.forest.quickguess.DB.DB;
import com.example.forest.quickguess.DB.Questions.Questions;
import com.example.forest.quickguess.Helpers.SharedPreferenceHelper;
import com.example.forest.quickguess.Helpers.WindowHelper;
import com.example.forest.quickguess.Utilities.EncryptUtil;
import com.example.forest.quickguess.Utilities.InternetConnectionUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DisplayAllFunFacts extends AppCompatActivity {
    private RecyclerView questionRecyclerView;
    private RecyclerView.Adapter adapter;
    private List<com.example.forest.quickguess.RecyclerView.Questions> questionsItems;
    String category , class_id;
    @BindView(R.id.searchFacts) EditText searchFacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_all_fun_facts);
        ButterKnife.bind(this);
        WindowHelper.hideNavigationBar(this);
        questionRecyclerView = findViewById(R.id.recyclerView);
        questionRecyclerView.setHasFixedSize(true);
        questionRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        getCredentials();
        getAllFunfacts();

        searchFacts.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });
    }

    private void filter(String text) {
        questionsItems = new ArrayList<>();
        int category_id = DB.getInstance(getApplicationContext()).categoriesQuestionDao().getCategoryIdByName(category);
        //getall questions by category id
        List<Questions> questionsList = DB.getInstance(getApplicationContext())
                .questionsDao().getQuestionByCategoryIdAndClassID(category_id, Integer.parseInt(class_id));
        for(Questions q : questionsList)
        {

            try {
                if (EncryptUtil.decryptMethod(q.getCorrect_answer()).toLowerCase().contains(text.toLowerCase()))
                {
                    com.example.forest.quickguess.RecyclerView.Questions questions =
                            new com.example.forest.quickguess.RecyclerView.Questions(EncryptUtil.decryptMethod(q.getCorrect_answer()),EncryptUtil.decryptMethod(q.getFun_facts()),EncryptUtil.decryptMethod(q.getFun_facts_image()));
                    questionsItems.add(questions);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        adapter = new FunFactsAdapter(questionsItems,getApplicationContext());
        questionRecyclerView.setAdapter(adapter);
    }


    private void getCredentials() {
        try {
            SharedPreferenceHelper.PREF_FILE="user_played";
            category = SharedPreferenceHelper.
                    getSharedPreferenceString(getApplicationContext(),"category",null)
                    .toLowerCase();
            class_id = SharedPreferenceHelper.getSharedPreferenceString(get(),"class_id",null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getAllFunfacts();
    }

    @Override
    protected void onResume() {
        getCredentials();
        getAllFunfacts();
        super.onResume();
    }

    public DisplayAllFunFacts get()
    {
        return this;
    }

    private void getAllFunfacts()
    {
        questionsItems = new ArrayList<>();
        int category_id = DB.getInstance(getApplicationContext()).categoriesQuestionDao().getCategoryIdByName(category);
        //getall questions by category id
        List<Questions> questionsList = DB.getInstance(getApplicationContext())
                .questionsDao().getQuestionByCategoryIdAndClassID(category_id, Integer.parseInt(class_id));
        for(Questions q : questionsList)
        {
            try {
                com.example.forest.quickguess.RecyclerView.Questions questions =
                        new com.example.forest.quickguess.RecyclerView.Questions(EncryptUtil.decryptMethod(q.getCorrect_answer()),EncryptUtil.decryptMethod(q.getFun_facts()),EncryptUtil.decryptMethod(q.getFun_facts_image()));
                questionsItems.add(questions);
            } catch (Exception e)
            {
                e.printStackTrace();
            }

        }
        adapter = new FunFactsAdapter(questionsItems,getApplicationContext());
        questionRecyclerView.setAdapter(adapter);
    }


}
