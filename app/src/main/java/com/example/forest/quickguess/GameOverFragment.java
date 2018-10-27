package com.example.forest.quickguess;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.forest.quickguess.DB.DB;
import com.example.forest.quickguess.DB.UserStatus.UserStatusRepositories;
import com.example.forest.quickguess.Helpers.SharedPreferenceHelper;
import com.example.forest.quickguess.Utilities.FragmentUtil;


import java.util.Objects;

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
    @BindView(R.id.gameOverLayout) RelativeLayout gameOverLayout;
    @BindView(R.id.btnPlayAgain) Button btnPlayAgain;

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
        int category_id = DB.getInstance(getContext()).categoriesQuestionDao().getCategoryIdByName(category.toLowerCase());
//        int level_id = ((AnswerQuestion)getActivity()).questionRepositories.questionClassier(category_id);


        fm = getFragmentManager();
        funFactsFragment = (FunFacts) fm.findFragmentById(R.id.fragment_fun_facts);

        playAnswered.setText("Answered : "+String.valueOf(getAnswered()));
//        needToAnswer.setText("Question need to answer to save one friend : "+String.valueOf( getNeedToAnswer()));
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
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (FragmentUtil.sDisableFragmentAnimations) {
            Animation a = new Animation() {};
            a.setDuration(0);
            return a;
        }
        return super.onCreateAnimation(transit, enter, nextAnim);
    }


    @Override
    @OnClick(R.id.btnPlayAgain)
    public void onClick(View view) {
        FragmentUtil.sDisableFragmentAnimations = true;
        fm.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentUtil.sDisableFragmentAnimations = false;
//        funFactsFragment.continueLayout();
        ((AnswerQuestion)Objects.requireNonNull(getActivity())).finish();
      }


    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

}
