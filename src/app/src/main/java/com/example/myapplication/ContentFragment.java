package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ContentFragment extends Fragment {

    private RecyclerView recyclerView;
    private ContentAdapter adapter;
    private List<ContentItem> contentList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);

        initializeData();
        adapter = new ContentAdapter(getContext(), contentList);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void initializeData() {
        contentList = new ArrayList<>();
        contentList.add(new ContentItem(R.drawable.example, "Description for 1"));
        contentList.add(new ContentItem(R.drawable.example, "Description for 2"));
        contentList.add(new ContentItem(R.drawable.example,  "Description for 3"));
        contentList.add(new ContentItem(R.drawable.example,  "Description for 4"));
    }
}
