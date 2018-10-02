package com.example.forest.quickguess.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.forest.quickguess.R;
import com.example.forest.quickguess.RecyclerView.Ranks;

import java.util.List;

public class RanksAdapter extends RecyclerView.Adapter<RanksAdapter.ViewHolder> {
    private static List<Ranks> rankItems;
    private Context context;

    public RanksAdapter(List<Ranks> ranksList,Context context) {
        RanksAdapter.rankItems = ranksList;
        this.context = context;
    }

    @Override
    public RanksAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ranks_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RanksAdapter.ViewHolder holder, int position) {
        final Ranks rankItem = rankItems.get(position);
        holder.playerTop.setTypeface(Typeface.createFromAsset(context.getAssets(),  "fonts/Dimbo_Regular.ttf"));
        holder.playerName.setTypeface(Typeface.createFromAsset(context.getAssets(),  "fonts/Dimbo_Regular.ttf"));
        holder.playerPoints.setTypeface(Typeface.createFromAsset(context.getAssets(),  "fonts/Dimbo_Regular.ttf"));
        holder.playerTop.setText(String.valueOf(position+1));
        String name = rankItem.getUsername().substring(0, 1).toUpperCase() + rankItem.getUsername().substring(1);
        holder.playerName.setText(name.trim());
        holder.playerPoints.setText(String.valueOf(rankItem.getPoints()));
    }

    @Override
    public int getItemCount() {
        return rankItems.size();
    }
    //id ,username ,points

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView playerName;
        TextView playerPoints;
        TextView playerTop;

        ViewHolder(View itemView) {
            super(itemView);
            playerTop = itemView.findViewById(R.id.top);
            playerName = itemView.findViewById(R.id.playerName);
            playerPoints = itemView.findViewById(R.id.playerPoints);

        }
    }
}
