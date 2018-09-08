package com.example.forest.quickguessv2.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.forest.quickguessv2.AnswerQuestion;
import com.example.forest.quickguessv2.Helpers.RedirectHelper;
import com.example.forest.quickguessv2.Helpers.SharedPreferenceHelper;
import com.example.forest.quickguessv2.R;

public class SlideAdapter extends PagerAdapter implements View.OnClickListener {
    private Context context;
    ImageView imgslide;
    // list of images
    private int[] lst_images = {
            R.drawable.ppl,
            R.drawable.plant,
            R.drawable.animal,
            R.drawable.place,
            R.drawable.sp,
            R.drawable.msic,
            R.drawable.tech,
            R.drawable.ntertainment
    };

    // list of titles
    private String[] lst_title = {
         "PEOPLE","PLANTS","ANIMALS","GEOGRAPHY", "SPORTS","MUSIC","TECHNOLOGY","ENTERTAINMENT"
    };
    // list of descriptions
    private String[] lst_description = {
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,",
    };
    // list of background colors
    private int[]  lst_backgroundcolor = {
            Color.rgb(51,149,255),
            Color.rgb(51,149,255),
            Color.rgb(51,149,255),
            Color.rgb(51,149,255),
            Color.rgb(51,149,255),
            Color.rgb(51,149,255),
            Color.rgb(51,149,255),
            Color.rgb(51,149,255)
    };


    public SlideAdapter(Context context) {
        this.context = context;
    }

    public ImageView getImg()
    {
        return imgslide;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.slide,container,false);
        LinearLayout layoutslide = view.findViewById(R.id.slidelinearlayout);
        imgslide = view.findViewById(R.id.slideimg);
        TextView txttitle= view.findViewById(R.id.txttitle);
        TextView description = view.findViewById(R.id.txtdescription);
        Button proceed = view.findViewById(R.id.btnProceed);

        proceed.setText("Play ".concat(lst_title[position]).concat(" Category"));
        proceed.setTag(lst_title[position]);
        proceed.setOnClickListener(this);
//        lst_backgroundcolor[position]
        layoutslide.setBackground(ContextCompat.getDrawable(context, R.drawable.home_bg));
        imgslide.setImageResource(lst_images[position]);
        proceed.setTypeface(Typeface.createFromAsset(view.getContext().getAssets(),  "fonts/Dimbo_Regular.ttf"));
        txttitle.setTypeface(Typeface.createFromAsset(view.getContext().getAssets(),  "fonts/Dimbo_Regular.ttf"));
        description.setTypeface(Typeface.createFromAsset(view.getContext().getAssets(),  "fonts/Dimbo_Regular.ttf"));
        txttitle.setText(lst_title[position]);
        description.setText(lst_description[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }

    @Override
    public int getCount() {
        return lst_title.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view==(LinearLayout)object);
    }

    @Override
    public void onClick(View view) {
      String background = (String) view.getTag();
      SharedPreferenceHelper.PREF_FILE="user_played";
      SharedPreferenceHelper.setSharedPreferenceString(context,"category",background);
      new RedirectHelper("bg_".concat(background).toLowerCase(), view.getContext(), AnswerQuestion.class);
    }


}