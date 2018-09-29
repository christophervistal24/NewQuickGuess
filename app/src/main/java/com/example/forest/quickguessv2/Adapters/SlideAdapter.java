package com.example.forest.quickguessv2.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.forest.quickguessv2.AnswerQuestion;
import com.example.forest.quickguessv2.DB.DB;
import com.example.forest.quickguessv2.Helpers.RedirectHelper;
import com.example.forest.quickguessv2.Helpers.SharedPreferenceHelper;
import com.example.forest.quickguessv2.R;

public class SlideAdapter extends PagerAdapter implements View.OnClickListener {
    private Context context;
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




    public SlideAdapter(Context context) {
        this.context = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.slide,container,false);
        setArrows(position , view);
        RelativeLayout layoutslide = view.findViewById(R.id.slidelinearlayout);
        ImageView pageBackground = view.findViewById(R.id.pageBackground);
        TextView description = view.findViewById(R.id.txtdescription);
        TextView no = view.findViewById(R.id.txtNoOfAnswered);
        layoutslide.setTag(lst_title[position]);
        layoutslide.setOnClickListener(this);
        pageBackground.setImageResource(context.getResources().getIdentifier(list_background[position],"drawable",context.getPackageName()));
        description.setTypeface(Typeface.createFromAsset(view.getContext().getAssets(),  "fonts/Dimbo_Regular.ttf"));
        no.setTypeface(Typeface.createFromAsset(view.getContext().getAssets(),  "fonts/Dimbo_Regular.ttf"));
        int category_id = DB.getInstance(context).categoriesQuestionDao().getCategoryIdByName(lst_title[position].toLowerCase());
        int answered_question = DB.getInstance(context).userStatusDao().countAnsweredQuestion(category_id);
        description.setText(R.string.tap_to_start);
        no.setText( String.format("%s %s ",String.valueOf(answered_question) , "%"));
     /*   YoYo.with(Techniques.DropOut)
                .duration(800)
                .delay(500)
                .repeat(-1)
                .playOn(description);
*/
        container.addView(view);
        return view;
    }

    private void setArrows(int position , View v) {
    ImageView left = v.findViewById(R.id.leftArrow);
        ImageView right = v.findViewById(R.id.rightArrow);
        if (position >= 1 && position <= 6)
        {
            left.setImageResource(R.drawable.left);
           /* YoYo.with(Techniques.Pulse)
                    .duration(800)
                    .delay(500)
                    .repeat(-1)
                    .pivotX(-200)
                    .playOn(left);*/
            /*YoYo.with(Techniques.Pulse)
                    .duration(800)
                    .delay(500)
                    .pivotX(200)
                    .repeat(-1)
                    .playOn(right);*/
        } else if (position == 0) {
            /*YoYo.with(Techniques.Pulse)
                    .duration(800)
                    .delay(500)
                    .pivotX(200)
                    .repeat(-1)
                    .playOn(right);*/
        } else if (position == 7)
        {
            right.setVisibility(View.GONE);
            left.setImageResource(R.drawable.left);
           /* YoYo.with(Techniques.Pulse)
                    .duration(800)
                    .delay(500)
                    .pivotX(200)
                    .repeat(-1)
                    .playOn(left);*/
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
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
          String category = (String) view.getTag();
          SharedPreferenceHelper.PREF_FILE="user_played";
          SharedPreferenceHelper.setSharedPreferenceString(context,"category",category);
          new RedirectHelper(view.getContext(), AnswerQuestion.class);
    }


}
