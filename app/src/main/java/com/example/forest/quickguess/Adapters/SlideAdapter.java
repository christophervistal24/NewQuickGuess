package com.example.forest.quickguess.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.forest.quickguess.AnswerQuestion;
import com.example.forest.quickguess.DB.DB;
import com.example.forest.quickguess.DB.Life.LifeRepositories;
import com.example.forest.quickguess.DB.Questions.QuestionRepositories;
import com.example.forest.quickguess.Helpers.RedirectHelper;
import com.example.forest.quickguess.Helpers.SharedPreferenceHelper;
import com.example.forest.quickguess.MainActivity;
import com.example.forest.quickguess.R;
public class SlideAdapter extends PagerAdapter implements View.OnClickListener {
    private Context context;
    private YoYo.YoYoString descriptionAnimation;
    private YoYo.YoYoString leftArrowAnimation;
    private YoYo.YoYoString rightArrowAnimation;
    private LifeRepositories lifeRepositories;
    // list of titles
    private String[] lst_title = {
         "PEOPLE","PLANTS","ANIMALS","GEOGRAPHY", "SPORTS","MUSIC","TECHNOLOGY","ENTERTAINMENT"
    };

    // list of background colors
    private String[]  list_background = {
            "cat_people_2",
            "cat_plants_2",
            "cat_animals_2",
            "cat_geo_2",
            "cat_sports_2",
            "cat_music_2",
            "cat_tech_2",
            "cat_enter_2"
    };

    private String[] difficulty_level_id = {
        "1",
        "2",
        "3"
    };




    public SlideAdapter(Context context) {
        this.context = context;
        lifeRepositories = new LifeRepositories(context);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.slide,container,false);
        setArrows(position , view);
        ImageView pageBackground = view.findViewById(R.id.pageBackground);
        TextView no = view.findViewById(R.id.txtNoOfAnswered);
        Button btnEasy = view.findViewById(R.id.easyLevel);
        Button btnModerate = view.findViewById(R.id.moderateLevel);
        Button btnDifficult = view.findViewById(R.id.difficultLevel);
        eventHandler(lst_title[position], btnEasy, btnModerate, btnDifficult);

        pageBackground.setImageResource(context.getResources().getIdentifier(list_background[position],"drawable",context.getPackageName()));
        no.setTypeface(Typeface.createFromAsset(view.getContext().getAssets(),  "fonts/Dimbo_Regular.ttf"));
        int category_id = DB.getInstance(context).categoriesQuestionDao().getCategoryIdByName(lst_title[position].toLowerCase());
        int answered_question = DB.getInstance(context).userStatusDao().countAnsweredQuestion(category_id);
        no.setText( String.format("%s %s ",String.valueOf(answered_question) , "%"));
        container.addView(view);
        return view;
    }

    //add some tags and events
    private void eventHandler(String tag, Button btnEasy, Button btnModerate, Button btnDifficult) {
        btnEasy.setTag(R.id.class_id,difficulty_level_id[0]);
        btnEasy.setTag(R.id.category, tag);
        btnModerate.setTag(R.id.class_id,difficulty_level_id[1]);
        btnModerate.setTag(R.id.category, tag);
        btnDifficult.setTag(R.id.class_id,difficulty_level_id[2]);
        btnDifficult.setTag(R.id.category, tag);
        btnEasy.setOnClickListener(this);
        btnModerate.setOnClickListener(this);
        btnDifficult.setOnClickListener(this);
    }


    private void setArrows(int position , View v) {
    ImageView left = v.findViewById(R.id.leftArrow);
        ImageView right = v.findViewById(R.id.rightArrow);
        if (position >= 1 && position <= 6)
        {
            left.setImageResource(R.drawable.left);
           leftArrowAnimation =  YoYo.with(Techniques.Pulse)
                    .duration(800)
                    .delay(500)
                    .repeat(-1)
                    .pivotX(-200)
                    .playOn(left);
            rightArrowAnimation = YoYo.with(Techniques.Pulse)
                    .duration(800)
                    .delay(500)
                    .pivotX(200)
                    .repeat(-1)
                    .playOn(right);
        } else if (position == 0) {
            rightArrowAnimation = YoYo.with(Techniques.Pulse)
                    .duration(800)
                    .delay(500)
                    .pivotX(200)
                    .repeat(-1)
                    .playOn(right);
        } else if (position == 7)
        {
            right.setVisibility(View.GONE);
            left.setImageResource(R.drawable.left);
            leftArrowAnimation =  YoYo.with(Techniques.Pulse)
                    .duration(800)
                    .delay(500)
                    .pivotX(200)
                    .repeat(-1)
                    .playOn(left);
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        descriptionAnimation = null;
        leftArrowAnimation = null;
        rightArrowAnimation = null;
        container.removeView((RelativeLayout)object);
    }

    @Override
    public void restoreState(@Nullable Parcelable state, @Nullable ClassLoader loader) {}

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
    @Override
    public int getCount() {
        return lst_title.length;
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view==(RelativeLayout)object);
    }


    @Override
    public void onClick(View view) {
        String class_id = String.valueOf(view.getTag(R.id.class_id));
        String category = (String)view.getTag(R.id.category);
        QuestionRepositories.class_id = Integer.parseInt(class_id);
          SharedPreferenceHelper.PREF_FILE="user_played";
          SharedPreferenceHelper.setSharedPreferenceString(context,"category",category);
          if (lifeRepositories.getUserLife() <= 0)
          {
              AlertDialog.Builder builder = new AlertDialog.Builder(context);
              builder.setMessage("GAME OVER");
              builder.show();
          } else {
              new RedirectHelper(view.getContext(), AnswerQuestion.class);
          }
    }




}
