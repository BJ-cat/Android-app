package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ViewHolder> {

    private List<ContentItem> contentList;
    private Context context;

    public ContentAdapter(Context context, List<ContentItem> contentList) {
        this.context = context;
        this.contentList = contentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ContentItem item = contentList.get(position);
        holder.textView.setText(item.getDescription());
        holder.imageButton.setImageResource(item.getImageResId());

        // Set click listener for ImageButton
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, pageContent.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contentList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageButton imageButton;
        TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            imageButton = itemView.findViewById(R.id.imageButton);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}
