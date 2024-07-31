package com.example.numad22fa_team37.FirebaseAssignment.HistoryScreen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.numad22fa_team37.FirebaseAssignment.Models.Message;
import com.example.numad22fa_team37.R;

import java.text.DateFormat;
import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private final ArrayList<Message> historyList;
    Context context;

    public HistoryAdapter(ArrayList<Message> historyList, Context context) {
        this.historyList = historyList;
        this.context = context;
    }

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.historyscreen_card, parent, false);
        return new HistoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {
        Message m = historyList.get(position);
        holder.image.setImageResource(m.getStickerId());
        holder.user.setText(m.getFrom());
        holder.date.setText(DateFormat.getDateTimeInstance().format(m.getSentOn()));
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView user;
        TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.sticker);
            user = itemView.findViewById(R.id.user);
            date = itemView.findViewById(R.id.timeStamp);
        }
    }
}
