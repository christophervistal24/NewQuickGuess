package com.example.forest.quickguess;


import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.forest.quickguess.DB.GameOver.GameoverRepositories;
import com.example.forest.quickguess.DB.Life.LifeRepositories;
import com.example.forest.quickguess.Helpers.FontHelper;
import com.example.forest.quickguess.Helpers.SharedPreferenceHelper;
import com.example.forest.quickguess.Utilities.FragmentUtil;
import com.example.forest.quickguess.Utilities.IOnBackPressed;
import com.facebook.CallbackManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class FunFacts extends Fragment implements View.OnClickListener , IOnBackPressed {

    @BindView(R.id.title) TextView title;
    @BindView(R.id.content) TextView content;
    @BindView(R.id.btnNext) Button btnNext;
    @BindView(R.id.facebookShare) Button facebookShare;
    @BindView(R.id.imageFunfacts) ImageView imageFunfacts;
    AnswerQuestion answerQuestionActivity;


    TextView question;
    ImageView imageBackground;
    LinearLayout timerLayout;

    private Context context;
    private Unbinder unbinder;
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    ShareLinkContent linkContent;
    protected int items[] = new int[]{30, 60, 90};


    @Override
    public void onResume()
    {
        ((AnswerQuestion)getActivity()).clockTick.release();
        ((AnswerQuestion)getActivity()).countDownTimer.cancel();
        super.onResume();
    }

    public FunFacts() {
        // Required empty public constructor
    }

    //TODO REFACTOR
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_fun_facts, container, false);
        unbinder = ButterKnife.bind(this,view);
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        context = getContext();

        question = getActivity().findViewById(R.id.question);
        timerLayout = getActivity().findViewById(R.id.timerLayout);
        imageBackground = getActivity().findViewById(R.id.background);
        ((AnswerQuestion)getActivity()).choice_a.setClickable(true);
        ((AnswerQuestion)getActivity()).choice_b.setClickable(true);
        ((AnswerQuestion)getActivity()).choice_c.setClickable(true);
        ((AnswerQuestion)getActivity()).choice_d.setClickable(true);
        return view;
    }


    @OnClick(R.id.facebookShare)
    public void shareLink()
    {
        linkContent = new ShareLinkContent.Builder()
                .setQuote("Always seek KNOWLEDGE")
                .setContentUrl(Uri.parse("https://playquickguess.herokuapp.com/"))
                .build();
        if (ShareDialog.canShow(ShareLinkContent.class))
        {
            shareDialog.show(linkContent, ShareDialog.Mode.WEB);
        }
    }



    //TODO REFACTOR
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        Typeface fontHelper = new FontHelper().dimboFont(context);
        title.setTypeface(fontHelper);
        content.setTypeface(fontHelper);
        btnNext.setTypeface(fontHelper);
        facebookShare.setTypeface(fontHelper);
        SharedPreferenceHelper.PREF_FILE="question";
        title.setText(SharedPreferenceHelper.getSharedPreferenceString(context,"title",null));
        content.setText(SharedPreferenceHelper.getSharedPreferenceString(context,"question_content",null));
        String image = SharedPreferenceHelper.getSharedPreferenceString(context,"question_image",null);
        SharedPreferenceHelper.PREF_FILE="user_played";
        String category = SharedPreferenceHelper.getSharedPreferenceString(context,"category",null);
        Picasso.with(getContext())
                .load("https://res.cloudinary.com/dpcxcsdiw/image/upload/w_200,h_200,q_auto,fl_lossy/"+category.toLowerCase()+"/"+image)
                .placeholder(R.drawable.placeholder)
                .into(imageFunfacts);
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    @OnClick(R.id.btnNext)
    public void onClick(View view) {
        isGameOverOrNot();
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

    public void isGameOverOrNot()
    {
        answerQuestionActivity = (AnswerQuestion)getActivity();
        LifeRepositories lifeRepositories = null;
        if (answerQuestionActivity != null) {
            lifeRepositories = answerQuestionActivity.lifeRepositories;
        }
        if (lifeRepositories != null) {
            if  (lifeRepositories.getUserLife() <= 0)
            {
                GameoverRepositories.gameOver(lifeRepositories);
                answerQuestionActivity.life.setText(String.valueOf(lifeRepositories.getUserLife()));
                answerQuestionActivity.displayGameOverFragment();
            } else {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentUtil.sDisableFragmentAnimations = true;
                fm.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                FragmentUtil.sDisableFragmentAnimations = false;
                continueLayout();
//                isUserCanSaveFriends();
           }
        }
    }

    protected void continueLayout() {
        answerQuestionActivity.getQuestion();
        answerQuestionActivity.displayQuestionLayout();
        answerQuestionActivity.displayTimer();
        answerQuestionActivity.userPoints = 0;
        answerQuestionActivity.clockTick =  ((AnswerQuestion)getActivity()).soundTick(R.raw.clock_tick);
        answerQuestionActivity.clockTick.start();
    }

    protected void isUserCanSaveFriends() {
       try {
           int category_id = ((AnswerQuestion)getActivity()).q.getCategory_id();
           answerQuestionActivity.friendsRepositories.checkAnsweredQuestion(category_id, items);
       } catch  (NullPointerException ignored) {}
    }



    @Override
    public void onDestroyView() {
        unbinder.unbind();
        linkContent = null;
        super.onDestroyView();
    }

    //TODO REFACTOR
    @Override
    public void onBackPressed() {
        timerLayout.removeAllViews();
        FragmentManager fm = getFragmentManager();
        int count = fm.getBackStackEntryCount();
        for (int i = 0; i < count; ++i) {
            fm.popBackStack();
        }
    }
}
