package com.example.forest.quickguessv2;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.forest.quickguessv2.DB.DB;
import com.example.forest.quickguessv2.DB.Life.LifeRepositories;
import com.example.forest.quickguessv2.DB.UserStatus.UserStatusRepositories;
import com.example.forest.quickguessv2.Helpers.SharedPreferenceHelper;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class GameOverFragment extends Fragment  implements  View.OnClickListener{

    @BindView(R.id.txtAnswered) TextView playAnswered;
    @BindView(R.id.txtneeedToAnswer) TextView needToAnswer;
    @BindView(R.id.txtScore) TextView txtScore;

    private Unbinder unbinder;
    private String category;
    FragmentManager fm;
    FunFacts funFactsFragment;

    public GameOverFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_over, container, false);
        unbinder = ButterKnife.bind(this,view);
        SharedPreferenceHelper.PREF_FILE="user_played";
        category = SharedPreferenceHelper.getSharedPreferenceString(getContext(),"category",null);
        fm = getFragmentManager();
        funFactsFragment = (FunFacts) fm.findFragmentById(R.id.fragment_fun_facts);

        playAnswered.setText("Answered Question"+String.valueOf(getAnswered()));
        needToAnswer.setText("Question need to answer to save one friend : "+String.valueOf( getNeedToAnswer()));
        txtScore.setText("Score : "+String.valueOf(getAnswered()*100));
        return view;
    }

    private int getAnswered()
    {
        return UserStatusRepositories.answeredByCategory(getContext(),category.toLowerCase());
    }

    private int getNeedToAnswer()
    {
        int category_id = DB.getInstance(getContext()).categoriesQuestionDao().getCategoryIdByName(category.toLowerCase());
        int remainingNeedToSaveFriends = DB.getInstance(getContext()).friendsDao().countSaveFriendsByCategoryId(category_id);
        int need = 0;
        switch(remainingNeedToSaveFriends)
        {
            case 1 :
                need =  (funFactsFragment.items[2] - getAnswered());
                break;

            case 2 :
                need =  (funFactsFragment.items[1] - getAnswered());
                break;

            case 3 :
                need = (funFactsFragment.items[0] - getAnswered());
                break;
        }
        return need;
    }



    @Override
    @OnClick(R.id.btnPlayAgain)
    public void onClick(View view) {
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
        if (funFactsFragment != null) {
            funFactsFragment.continueLayout();
        }
      }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

}
