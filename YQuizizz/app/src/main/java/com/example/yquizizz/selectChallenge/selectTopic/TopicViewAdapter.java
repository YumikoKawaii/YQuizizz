package com.example.yquizizz.selectChallenge.selectTopic;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yquizizz.R;

import java.util.ArrayList;
import java.util.List;

public class TopicViewAdapter extends RecyclerView.Adapter<TopicViewAdapter.ViewHolder> {

    private List<String> topicList = new ArrayList<>();
    private Context context;

    private ItemClickListener itemClickListener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_view, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            holder.topicName.setText(topicList.get(position));
            holder.topicIcon.setImageResource(getIconBaseOnTopic(topicList.get(position)));
            holder.topicBg.setBackground(getBackgroundBaseOnTopic(topicList.get(position)));

            holder.itemView.setOnClickListener( view -> {
                itemClickListener.onClickItemListener(topicList.get(position));
            });
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public int getItemCount() {
        return topicList.size();
    }

    private Drawable getBackgroundBaseOnTopic(String topic) {
        switch (topic) {
            case "Computer Science":
                return AppCompatResources.getDrawable(context, R.drawable.computer_science_bg);
            case "Mathematics":
                return AppCompatResources.getDrawable(context, R.drawable.mathematics_bg);
            case "Physics":
                return AppCompatResources.getDrawable(context, R.drawable.physics_bg);
            case "Chemistry":
                return AppCompatResources.getDrawable(context, R.drawable.chemistry_bg);
            case "History":
                return AppCompatResources.getDrawable(context, R.drawable.history_bg);
            case "Geography":
                return AppCompatResources.getDrawable(context, R.drawable.geography_bg);
            case "Art":
                return AppCompatResources.getDrawable(context, R.drawable.art_bg);
            case "Electrical Engineering":
                return AppCompatResources.getDrawable(context, R.drawable.electrical_engineering_bg);
            case "Robotics":
                return AppCompatResources.getDrawable(context, R.drawable.robotics_bg);
            case "Biology":
                return AppCompatResources.getDrawable(context, R.drawable.biology_bg);
            case "Literature":
                return AppCompatResources.getDrawable(context, R.drawable.literature_bg);
            default:
                return AppCompatResources.getDrawable(context, R.drawable.computer_science_bg);
        }
    }

    private int getIconBaseOnTopic(String topic) {
        switch (topic) {
            case "Computer Science":
                return R.mipmap.computer_science;
            case "Mathematics":
                return R.mipmap.mathematics;
            case "Physics":
                return R.mipmap.physics;
            case "Chemistry":
                return R.mipmap.chemistry;
            case "History":
                return R.mipmap.history_book;
            case "Geography":
                return R.mipmap.geography;
            case "Art":
                return R.mipmap.art;
            case "Electrical Engineering":
                return R.mipmap.electrical_engineering;
            case "Robotics":
                return R.mipmap.robotics;
            case "Biology":
                return R.mipmap.biology;
            case "Literature":
                return R.mipmap.literature;
            default:
                return R.mipmap.madoka;
        }
    }

    public void setTopicList(List<String> topicList) {
        this.topicList = topicList;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public interface ItemClickListener {
        void onClickItemListener(String topic);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView topicName;
        private ImageView topicIcon;
        private ConstraintLayout topicBg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            topicName = (TextView) itemView.findViewById(R.id.topicName);
            topicIcon = (ImageView) itemView.findViewById(R.id.topicIcon);
            topicBg = (ConstraintLayout) itemView.findViewById(R.id.topicBg);
        }
    }

}
