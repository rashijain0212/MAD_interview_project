package com.example.numad22fa_team37.AtYourService;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.numad22fa_team37.AtYourService.Models.Movie;
import com.example.numad22fa_team37.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private final ArrayList<Movie> arrayList;
    Context context;

    public RecyclerAdapter(ArrayList<Movie> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_movie_card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = arrayList.get(position);
        holder.textView_movieName.setText(movie.getTitle());
        holder.textView_movieYear.setText(movie.getYear());
        holder.textView_movieRating.setText(movie.getImdbID());
        holder.textView_movieType.setText(movie.getType());
        Picasso.get()
                .load(movie.getPoster())
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView_movieName;
        TextView textView_movieYear;
        TextView textView_movieRating;
        TextView textView_movieType;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView_movieName = itemView.findViewById(R.id.textView_movieNameValue);
            textView_movieYear = itemView.findViewById(R.id.textView_movieYearValue);
            textView_movieRating = itemView.findViewById(R.id.textView_movieRatingValue);
            textView_movieType = itemView.findViewById(R.id.textView_movieTypeValue);
            image = itemView.findViewById(R.id.image);
        }
    }
}
