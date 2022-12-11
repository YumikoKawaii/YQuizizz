package com.example.yquizizz.mainActivity.history;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yquizizz.R;
import com.example.yquizizz.database.HistoryController;
import com.example.yquizizz.database.HistoryModel;

import java.util.ArrayList;

public class History extends Fragment {

    private RecyclerView historyDisplay;

    public static final int id = 5;

    public History() {
        // Required empty public constructor
    }

    public static History newInstance() {
        History fragment = new History();
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

        View view;

        HistoryController controller = new HistoryController(getContext());

        ArrayList<HistoryModel> data = controller.findAll();

        if (data.size() == 0) {
            view = inflater.inflate(R.layout.nothing_here, container, false);
        } else {

            view = inflater.inflate(R.layout.fragment_history, container, false);

            historyDisplay = view.findViewById(R.id.historyDisplay);


            HistoryAdapter adapter = new HistoryAdapter();
            adapter.setHistoryList(data);

            historyDisplay.setAdapter(adapter);
            historyDisplay.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        return view;
    }
}