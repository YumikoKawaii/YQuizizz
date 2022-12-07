package com.example.yquizizz.mainActivity.selectChallenge.selectTopic;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yquizizz.R;
import com.example.yquizizz.mainActivity.selectChallenge.selectDifficulty.SelectDifficulty;

import java.util.Arrays;

public class SelectTopic extends Fragment {


    private View fragView;
    private RecyclerView topicSelector;

    private SelectDifficulty selectDifficulty;

    public SelectTopic() {
        // Required empty public constructor
    }

    public static SelectTopic newInstance() {
        SelectTopic fragment = new SelectTopic();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragView = inflater.inflate(R.layout.fragment_select_topic, container, false);

        TopicViewAdapter topic_adapter = new TopicViewAdapter();
        topic_adapter.setContext(fragView.getContext());
        topic_adapter.setTopicList(Arrays.asList(fragView.getResources().getStringArray(R.array.topicList)));

        topic_adapter.setItemClickListener(new TopicViewAdapter.ItemClickListener() {
            @Override
            public void onClickItemListener(String topic) {

                Bundle bundle = new Bundle();
                bundle.putString("topic", topic);
                selectDifficulty = new SelectDifficulty();
                selectDifficulty.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .replace(R.id.body, selectDifficulty, "findThisFragment")
                        .addToBackStack(null)
                        .commit();


            }
        });

        topicSelector = (RecyclerView) fragView.findViewById(R.id.topicSelector);
        topicSelector.setAdapter(topic_adapter);
        topicSelector.setLayoutManager(new LinearLayoutManager(getContext()));

        return fragView;
    }

    public void cancelAllTimer() {
        if (selectDifficulty != null) {
            selectDifficulty.cancelAllTimer();
        }
    }

}