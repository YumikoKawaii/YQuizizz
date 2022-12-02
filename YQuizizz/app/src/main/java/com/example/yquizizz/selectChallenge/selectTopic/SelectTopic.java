package com.example.yquizizz.selectChallenge.selectTopic;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yquizizz.R;
import com.example.yquizizz.selectChallenge.SelectChallenge;
import com.example.yquizizz.selectChallenge.selectDifficulty.SelectDifficulty;

import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectTopic#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectTopic extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View fragView;
    private RecyclerView topicSelector;

    public SelectTopic() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelectTopic.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectTopic newInstance(String param1, String param2) {
        SelectTopic fragment = new SelectTopic();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
                System.out.println(topic);
                Fragment nextFrag = new SelectDifficulty();
                try {
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.body, nextFrag, "findThisFragment")
                            .addToBackStack(null)
                            .commit();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });

        topicSelector = (RecyclerView) fragView.findViewById(R.id.topicSelector);
        topicSelector.setAdapter(topic_adapter);
        topicSelector.setLayoutManager(new LinearLayoutManager(getContext()));

        return fragView;
    }
}