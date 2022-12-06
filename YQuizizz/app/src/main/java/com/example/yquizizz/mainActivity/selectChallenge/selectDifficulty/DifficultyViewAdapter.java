package com.example.yquizizz.mainActivity.selectChallenge.selectDifficulty;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
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
        holder.difficulty.setBackground(getTextBackgroundBasOnDifficulty(difficultyList.get(position)));
        holder.difficultyShortDesc.setText(getShortDescBaseOnDifficulty(difficultyList.get(position)));
        holder.difficultyPicture.setImageResource(getDifficultyPictureBaseOnDifficulty(difficultyList.get(position)));
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

    private Drawable getTextBackgroundBasOnDifficulty(String difficulty) {
        switch (difficulty) {
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

    private int getDifficultyPictureBaseOnDifficulty(String difficulty) {
        switch (difficulty) {
            case "Easy":
                return R.mipmap.easy;
            case "Normal":
                return R.mipmap.normal;
            case "Hard":
                return R.mipmap.hard;
            default:
                return R.mipmap.nightmare;
        }
    }

    private String getShortDescBaseOnDifficulty(String difficulty) {
        switch (difficulty) {
            case "Easy":
                return context.getString(R.string.easy_short_desc);
            case "Normal":
                return context.getString(R.string.normal_short_desc);
            case "Hard":
                return context.getString(R.string.hard_short_desc);
            default:
                return context.getString(R.string.nightmare_short_desc);
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
        private TextView difficultyShortDesc;
        private ImageView difficultyPicture;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            difficulty = (TextView) itemView.findViewById(R.id.difficulty);
            difficultyShortDesc = (TextView) itemView.findViewById(R.id.difficultyShortDesc);
            difficultyPicture = (ImageView) itemView.findViewById(R.id.difficultyPicture);
        }
    }
}
