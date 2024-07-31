package com.example.numad22fa_team37.FirebaseAssignment.Sticker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.numad22fa_team37.R;

import java.util.ArrayList;

public class StickerAdapter extends RecyclerView.Adapter<StickerViewHolder> {

    private final ArrayList<Integer> stickerList;
    private final Context context;
    private final OnItemClickListener onItemClickListener;

    public StickerAdapter(ArrayList<Integer> stickerList, Context context, OnItemClickListener onItemClickListener) {
        this.stickerList = stickerList;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public StickerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_single_sticker_card, parent, false);
        return new StickerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StickerViewHolder holder, int position) {
        Integer stickerId = stickerList.get(position);
        holder.bindThisData(stickerId, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return stickerList.size();
    }
}
