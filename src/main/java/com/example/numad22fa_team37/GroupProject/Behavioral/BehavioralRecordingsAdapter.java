package com.example.numad22fa_team37.GroupProject.Behavioral;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.numad22fa_team37.R;

import java.io.File;

public class BehavioralRecordingsAdapter extends RecyclerView.Adapter<BehavioralRecordingsAdapter.RecyclerViewHolder> {

    private final File[] audioRecordingList;
    private final RecordingListener recordingListener;

    public BehavioralRecordingsAdapter(File[] audioRecordingList, RecordingListener recordingListener) {
        this.audioRecordingList = audioRecordingList;
        this.recordingListener = recordingListener;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recording_card, parent, false);
        return new RecyclerViewHolder(view, recordingListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.audioFile = audioRecordingList[position];
        holder.audioTitleTextView.setText(audioRecordingList[position].getName());
        holder.audioDateTextView.setText(TimeCalculator.getTimeDifference(audioRecordingList[position].lastModified()));
    }

    @Override
    public int getItemCount() {
        return audioRecordingList.length;
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView audioTitleTextView;
        private final TextView audioDateTextView;
        private final RecordingListener recordingListener;
        private File audioFile;

        public RecyclerViewHolder(@NonNull View itemView, RecordingListener recordingListener) {
            super(itemView);
            audioTitleTextView = itemView.findViewById(R.id.topicNameQuizProgressTextView);
            audioDateTextView = itemView.findViewById(R.id.audioDateTextView);
            ImageView playImageButton = itemView.findViewById(R.id.audioRowPlayImageView);

            this.recordingListener = recordingListener;
            itemView.setOnClickListener(this);
            playImageButton.setOnClickListener(v -> recordingListener.onAudioClick(audioFile, getAdapterPosition()));
        }

        @Override
        public void onClick(View v) {
            recordingListener.onAudioClick(audioFile, getAdapterPosition());
        }
    }
}
