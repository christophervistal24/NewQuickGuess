package com.example.forest.quickguessv2.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.forest.quickguessv2.DisplayAllFunFacts;
import com.example.forest.quickguessv2.R;
import com.example.forest.quickguessv2.RecyclerView.Questions;
import com.example.forest.quickguessv2.Utilities.TypeFaceUtil;

import java.util.List;

import me.anwarshahriar.calligrapher.Calligrapher;

public class FunFactsAdapter extends RecyclerView.Adapter<FunFactsAdapter.ViewHolder>{
    private static List<Questions> questionsItems;
    private Context context;

    public FunFactsAdapter(List<Questions> questionsItems,Context context) {
        FunFactsAdapter.questionsItems = questionsItems;
        this.context = context;
     }

    @Override
    public FunFactsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fun_facts_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FunFactsAdapter.ViewHolder holder, final int position) {
        final Questions questionsItem = questionsItems.get(position);
        holder.txtQuestion.setTypeface(Typeface.createFromAsset(context.getAssets(),  "fonts/Dimbo_Regular.ttf"));
        holder.questionImage.setTypeface(Typeface.createFromAsset(context.getAssets(),  "fonts/Dimbo_Regular.ttf"));
        holder.txtQuestion.setText(questionsItem.getQuestion_fun_facts());
        holder.questionImage.setText(questionsItem.getImage());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Position" + String.valueOf(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return questionsItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtQuestion;
        TextView questionImage;
        LinearLayout parent;

        ViewHolder(View itemView) {
            super(itemView);
            txtQuestion = itemView.findViewById(R.id.questionFunFacts);
            questionImage = itemView.findViewById(R.id.questionImage);
            parent = itemView.findViewById(R.id.parent);
        }
    }
}

