package com.example.yquizizz.mainActivity.selectChallenge.selectTopic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
            holder.topicPicture.setImageResource(getPictureBaseOnTopic(topicList.get(position)));
            holder.topicShortDesc.setText(getDescBaseOnTopic(topicList.get(position)));
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

    private int getPictureBaseOnTopic(String topic) {
        switch (topic) {
            case "Physics":
                return R.mipmap.physic_landscape;
            case "Chemistry":
                return R.mipmap.chemistry_landscape;
            case "History":
                return R.mipmap.history_landscape;
            case "Geography":
                return R.mipmap.geography_landscape;
            case "Art":
                return R.mipmap.art_landscape;
            default:
                return R.mipmap.general_knowledge_landscape;

        }
    }

    private String getDescBaseOnTopic(String topic) {
        switch (topic) {

            case "Physics":
                return context.getString(R.string.physic_short_desc);
            case "Chemistry":
                return context.getString(R.string.chemistry_short_desc);
            case "History":
                return context.getString(R.string.history_short_desc);
            case "Geography":
                return context.getString(R.string.geography_short_desc);
            case "Art":
                return context.getString(R.string.art_short_desc);
            default:
                return context.getString(R.string.general_short_desc);
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
        private ImageView topicPicture;
        private TextView topicShortDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            topicName = (TextView) itemView.findViewById(R.id.topicName);
            topicPicture = (ImageView) itemView.findViewById(R.id.topicPicture);
            topicShortDesc = (TextView) itemView.findViewById(R.id.topicShortDescription);

        }
    }

}
