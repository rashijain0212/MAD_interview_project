package com.example.numad22fa_team37.FirebaseAssignment.HomeScreen;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.numad22fa_team37.FirebaseAssignment.HistoryScreen.HistoryScreen;
import com.example.numad22fa_team37.FirebaseAssignment.Models.User;
import com.example.numad22fa_team37.FirebaseAssignment.Sticker.StickerScreenActivity;
import com.example.numad22fa_team37.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private final List<User> userList;
    private final Context context;
    private final HomeScreenActivity homeScreenActivity;

    public UserAdapter(List<User> userList, Context context, HomeScreenActivity homeScreenActivity) {
        this.userList = userList;
        this.context = context;
        this.homeScreenActivity = homeScreenActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_user_list_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        User user = userList.get(position);

        holder.textView_userName.setText(user.getUsername());

        holder.btnShowHistory.setOnClickListener(v -> {
            Intent intent = new Intent(context, HistoryScreen.class);
            intent.putExtra("from", this.homeScreenActivity.getCurrentUser());
            intent.putExtra("to", user.getUsername());
            context.startActivity(intent);
        });

        holder.btnSendSticker.setOnClickListener(v -> {
            Intent intent = new Intent(context, StickerScreenActivity.class);
            intent.putExtra("from", this.homeScreenActivity.getCurrentUser());
            intent.putExtra("to", user.getUsername());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView_userName;
        private final Button btnSendSticker;
        private final Button btnShowHistory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView_userName = itemView.findViewById(R.id.user_name);
            btnShowHistory = itemView.findViewById(R.id.show_history_btn);
            btnSendSticker = itemView.findViewById(R.id.send_msg_btn);
        }
    }
}
