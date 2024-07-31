package com.example.numad22fa_team37.GroupProject.Quizz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.numad22fa_team37.R;

import java.util.ArrayList;

public class TechQuizAdapter extends RecyclerView.Adapter<TechQuizAdapter.TechQuizHolder> {

    public int clickPos = 0;
    private RadioButton rbChecked = null;
    private final ArrayList<String> optionsList = new ArrayList<>();
    Qa question;
    Context context;

    public TechQuizAdapter(Qa question, Context context) {
        this.question = question;
        this.context = context;
        optionsList.add(question.getoA());
        optionsList.add(question.getoB());
        optionsList.add(question.getoC());
        optionsList.add(question.getoD());
    }

    @NonNull
    @Override
    public TechQuizHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.options_card, parent, false);
        return new TechQuizHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TechQuizHolder holder, int position) {

        String options = optionsList.get(position);
        holder.optionCard.setText(options);

        holder.radioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question.setChosenAnswer(optionsList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return optionsList.size();
    }

    public class TechQuizHolder extends RecyclerView.ViewHolder {

        public RadioButton radioBtn;
        public TextView optionCard;

        public TechQuizHolder(View itemView){
            super(itemView);
            radioBtn = itemView.findViewById(R.id.radioBtn);
            optionCard = itemView.findViewById(R.id.optionCard);
        }
    }
}
