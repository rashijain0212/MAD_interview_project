package com.example.numad22fa_team37.FirebaseAssignment.Sticker;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.numad22fa_team37.R;

public class StickerViewHolder extends RecyclerView.ViewHolder {
    private final ImageView image;


    public StickerViewHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.sticker);
    }

    public void bindThisData(Integer stickerId, OnItemClickListener onItemClickListener) {
        image.setImageResource(stickerId);
        image.setOnClickListener(view -> onItemClickListener.onItemClick(view, stickerId));
    }
}
