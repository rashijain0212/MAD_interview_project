package com.example.numad22fa_team37.FirebaseAssignment;

import androidx.annotation.NonNull;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.numad22fa_team37.FirebaseAssignment.HistoryScreen.HistoryAdapter;
import com.example.numad22fa_team37.FirebaseAssignment.HistoryScreen.HistoryScreen;
import com.example.numad22fa_team37.FirebaseAssignment.HomeScreen.UserAdapter;
import com.example.numad22fa_team37.FirebaseAssignment.Models.Message;
import com.example.numad22fa_team37.FirebaseAssignment.Models.User;
import com.example.numad22fa_team37.FirebaseAssignment.Sticker.StickerScreenActivity;
import com.example.numad22fa_team37.R;

import java.text.DateFormat;
import java.util.List;

public class StickerCountAdapter extends RecyclerView.Adapter<StickerCountAdapter.ViewHolder> {
    private final List<Message> userList;
    private final Context context;
    private final sticker_count sticker_countActivity;

    public StickerCountAdapter(List<Message> userList, Context context, sticker_count sticker_countActivity) {
        this.userList = userList;
        this.context = context;
        this.sticker_countActivity = sticker_countActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_user_list_card, parent, false);
        return new StickerCountAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message m = userList.get(position);
    }


    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView_userName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView_userName = itemView.findViewById(R.id.user_name);

        }
    }
}