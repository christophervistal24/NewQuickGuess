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
import com.example.forest.quickguess.Utilities.GameOverUtil;
import com.example.forest.quickguess.Utilities.UserUtil;


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
    @BindView(R.id.txtAnswered2) TextView playAnswered2;
    @BindView(R.id.txtneeedToAnswer) TextView needToAnswer;
    @BindView(R.id.txtScore) TextView txtScore;
    @BindView(R.id.txtScore2) TextView txtScore2;
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

        //get the selected category of the user
        this.category = UserUtil.getSelectedCategoryOfUser(getContext());

        this.fm = getFragmentManager();

        //get fragment fun facts
        this.funFactsFragment = (FunFacts) fm.findFragmentById(R.id.fragment_fun_facts);

        //display score and answered question of the user
        this.setScoreAndAnswerOfUser();

        return view;
    }

    private void setScoreAndAnswerOfUser() {
        playAnswered.setText(String.format("Answered : %s", String.valueOf(getAnswered())));
        playAnswered2.setText(String.format("Answered : %s", String.valueOf(getAnswered())));
        txtScore.setText(String.format("Score : %s", String.valueOf(getAnswered() * 100)));
        txtScore2.setText(String.format("Score : %s", String.valueOf(getAnswered() * 100)));
    }

    private int getAnswered()
    {
        return UserStatusRepositories.answeredByCategory(getContext(),category.toLowerCase());
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
        ((AnswerQuestion)getActivity()).finish();
    }


    @Override
    public void onDestroy() {
        unbinder.unbind();
        DB.getInstance(getContext()).destroyInstance();
        super.onDestroy();
    }

}
