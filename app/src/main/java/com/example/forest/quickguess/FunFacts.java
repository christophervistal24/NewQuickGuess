package com.example.forest.quickguess;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.forest.quickguess.DB.Life.LifeRepositories;
import com.example.forest.quickguess.Helpers.SharedPreferenceHelper;
import com.example.forest.quickguess.Utilities.FragmentUtil;
import com.example.forest.quickguess.Utilities.GameOverUtil;
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
//    ImageView imageBackground;
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
        //imageBackground = getActivity().findViewById(R.id.background);
        initClickable();
        return view;
    }

    private void initClickable() {
        ((AnswerQuestion)getActivity()).choice_a.setClickable(true);
        ((AnswerQuestion)getActivity()).choice_b.setClickable(true);
        ((AnswerQuestion)getActivity()).choice_c.setClickable(true);
        ((AnswerQuestion)getActivity()).choice_d.setClickable(true);
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



    private String[] getQuestionInformation()
    {
        SharedPreferenceHelper.PREF_FILE="question";
        String title = SharedPreferenceHelper.getSharedPreferenceString(context,"title",null);
        String content = SharedPreferenceHelper.getSharedPreferenceString(context,"question_content",null);
        String image = SharedPreferenceHelper.getSharedPreferenceString(context,"question_image",null);
        return new String[]{title,content,image};
    }

    private void loadQuestionImage(String image) {
        SharedPreferenceHelper
                .PREF_FILE="user_played";
        //get the category that user played
        String category = SharedPreferenceHelper
                .getSharedPreferenceString(context,"category",null);
        //get the image name
        int imgResId = getResources().getIdentifier(image,"drawable",context.getPackageName());
        if  (imgResId != 0) //check if the image is in the drawable files
        {
            Picasso.with(getContext()).load(imgResId).into(imageFunfacts);
        } else {
            // make an request to API server
                Picasso.with(getContext())
                .load("https://res.cloudinary.com/dpcxcsdiw/image/upload/w_200,h_200,q_auto,fl_lossy/"+category.toLowerCase()+"/"+image)
                .into(imageFunfacts);
        }

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        //get the info about question
        String[] qInfo = getQuestionInformation();
        title.setText(qInfo[0]); // question title
        content.setText(qInfo[1]); // question content
        String image = qInfo[2]; // question image
        loadQuestionImage(image); //get the image of question
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
                disposeFragments();
                GameOverUtil.saveTime(getContext(),System.currentTimeMillis());
                answerQuestionActivity.life.setText(String.valueOf(lifeRepositories.getUserLife()));
                answerQuestionActivity.displayGameOverFragment();
            } else {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentUtil.sDisableFragmentAnimations = true;
                fm.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                FragmentUtil.sDisableFragmentAnimations = false;
                continueLayout();
           }
        }
    }

    private void disposeFragments() {
        for (Fragment fragment:getFragmentManager().getFragments()) {
            getFragmentManager().beginTransaction().remove(fragment).commit();
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
