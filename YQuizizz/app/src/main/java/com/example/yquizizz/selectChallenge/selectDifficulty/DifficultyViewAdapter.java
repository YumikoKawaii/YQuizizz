package com.example.yquizizz.selectChallenge.selectDifficulty;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yquizizz.R;

import java.util.List;

public class DifficultyViewAdapter extends RecyclerView.Adapter<DifficultyViewAdapter.ViewHolder> {

    private List<String> difficultyList;
    private Context context;

    private ItemClickListener itemClickListener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.difficulty_view, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.difficulty.setText(difficultyList.get(position));
        holder.difficulty.setTextColor(getTextColorBaseOnDifficulty(difficultyList.get(position)));
        holder.difficultyBg.setBackground(getBackgroundBaseOnDifficulty(difficultyList.get(position)));
        holder.itemView.setOnClickListener(view -> {
            itemClickListener.onClickItemListener(difficultyList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return difficultyList.size();
    }

    private Drawable getBackgroundBaseOnDifficulty(String difficulty) {
        switch (difficulty) {
            case "Easy":
                return context.getDrawable(R.drawable.easy_bg);
            case "Normal":
                return context.getDrawable(R.drawable.normal_bg);
            case "Hard":
                return context.getDrawable(R.drawable.hard_bg);
            case "Nightmare":
                return context.getDrawable(R.drawable.nightmare_bg);
            default:
                return context.getDrawable(R.drawable.easy_bg);
        }
    }

    private int getTextColorBaseOnDifficulty(String difficulty) {
        switch (difficulty) {
            case "Easy":
                return ContextCompat.getColor(context, R.color.easy);
            case "Normal":
                return ContextCompat.getColor(context, R.color.normal);
            case "Hard":
                return ContextCompat.getColor(context, R.color.hard);
            case "Nightmare":
                return ContextCompat.getColor(context, R.color.black);
            default:
                return ContextCompat.getColor(context, R.color.easy);
        }
    }

    public interface ItemClickListener {
        void onClickItemListener(String difficulty);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setDifficultyList(List<String> difficultyList) {
        this.difficultyList = difficultyList;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView difficulty;
        private RelativeLayout difficultyBg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            difficulty = (TextView) itemView.findViewById(R.id.difficulty);
            difficultyBg = (RelativeLayout) itemView.findViewById(R.id.difficultyBg);
        }
    }
}
