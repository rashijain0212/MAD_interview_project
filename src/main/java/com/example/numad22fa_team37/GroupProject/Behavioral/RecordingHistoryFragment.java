package com.example.numad22fa_team37.GroupProject.Behavioral;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.numad22fa_team37.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class RecordingHistoryFragment extends Fragment implements RecordingListener {
    private BottomSheetBehavior<ConstraintLayout> bottomSheetBehavior;
    private boolean playFileSelected;

    private MediaPlayer mediaPlayerForAudioRecordings;
    private boolean isCurrentlyPlaying;
    private File audioFileCurrentlyPlaying = null;
    private SeekBar mediaPlayerSeekbar;
    private Handler seekBarHandler;
    private Runnable syncSeekBar;

    private ImageButton playButton;
    private TextView mediaPlayerHeader, mediaPlayerFileNameCurrentlyPlaying;


    public RecordingHistoryFragment() {
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        File[] audioFiles = getAllAudioFiles();
        playFileSelected = false;
        mediaPlayerSeekbar = view.findViewById(R.id.seekBar);
        playButton = view.findViewById(R.id.playImageView);
        ImageButton rewindButton = view.findViewById(R.id.rewindImageView);
        ImageButton forwardButton = view.findViewById(R.id.forwardImageView);
        mediaPlayerHeader = view.findViewById(R.id.headerTitle);
        mediaPlayerFileNameCurrentlyPlaying = view.findViewById(R.id.audioFileNameTextView);


        ConstraintLayout mediaPlayer = view.findViewById(R.id.playAudioPanel);
        bottomSheetBehavior = BottomSheetBehavior.from(mediaPlayer);
        RecyclerView recyclerViewRecordingLists = view.findViewById(R.id.recyclerViewAudioHistory);

        BehavioralRecordingsAdapter behavioralRecordingsAdapter = new BehavioralRecordingsAdapter(audioFiles, this);
        recyclerViewRecordingLists.setHasFixedSize(true);
        recyclerViewRecordingLists.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewRecordingLists.setAdapter(behavioralRecordingsAdapter);

        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED && !playFileSelected) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    Toast.makeText(getContext(), "Play a recording!", Toast.LENGTH_SHORT).show();
                }
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        playButton.setOnClickListener(v -> {
            if (isCurrentlyPlaying) {
                pauseAudio();
            } else {
                if (audioFileCurrentlyPlaying != null) {
                    resumeAudio();
                }
            }
        });

        forwardButton.setOnClickListener(v -> {
            if (isCurrentlyPlaying) {
                pauseAudio();
                int progress = mediaPlayerSeekbar.getProgress();
                progress += 200;
                mediaPlayerForAudioRecordings.seekTo(progress);
                resumeAudio();
            }
        });

        rewindButton.setOnClickListener(v -> {
            if (isCurrentlyPlaying) {
                pauseAudio();
                int progress = mediaPlayerSeekbar.getProgress();
                progress -= 200;
                mediaPlayerForAudioRecordings.seekTo(progress);
                resumeAudio();
            }
        });

        mediaPlayerSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                pauseAudio();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                mediaPlayerForAudioRecordings.seekTo(progress);
                resumeAudio();
            }
        });
    }

    private File[] getAllAudioFiles() {
        BehavioralQuestionActivity activity = (BehavioralQuestionActivity) getActivity();
        String questionId = "-1";
        if (activity != null) {
            questionId = activity.getQuestionId();
        }
        String audioFilePath = requireActivity().getExternalFilesDir("/").getAbsolutePath();
        File audioDirectory = new File(audioFilePath + "/" + questionId);
        return audioDirectory.listFiles();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.recording_history_fragment, container, false);
    }

    @Override
    public void onAudioClick(File file, int position) {
        System.out.println("Recording row clicked");
        System.out.println("Playing " + file.getName());
        mediaPlayerHeader.setText(R.string.playing);
        playFileSelected = true;
        audioFileCurrentlyPlaying = file;
        if (isCurrentlyPlaying) {
            stopAudioOnMediaPlayer();
            playAudioOnMediaPlayer();
        } else {
            playAudioOnMediaPlayer();
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void stopAudioOnMediaPlayer() {

        playButton.setImageDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.ic_play_filled_circle));
        mediaPlayerHeader.setText(R.string.stopped);
        isCurrentlyPlaying = false;
        mediaPlayerForAudioRecordings.stop();
        seekBarHandler.removeCallbacks(syncSeekBar);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void playAudioOnMediaPlayer() {
        mediaPlayerForAudioRecordings = new MediaPlayer();
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        try {
            mediaPlayerForAudioRecordings.setDataSource(audioFileCurrentlyPlaying.getAbsolutePath());
            mediaPlayerForAudioRecordings.prepare();
            mediaPlayerForAudioRecordings.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        isCurrentlyPlaying = true;

        mediaPlayerHeader.setText(R.string.playing);
        playButton.setImageDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.ic_pause_circle_filled));
        mediaPlayerFileNameCurrentlyPlaying.setText(audioFileCurrentlyPlaying.getName());

        mediaPlayerSeekbar.setMax(mediaPlayerForAudioRecordings.getDuration());
        seekBarHandler = new Handler();
        updateRunnable();
        seekBarHandler.postDelayed(syncSeekBar, 0);

        mediaPlayerForAudioRecordings.setOnCompletionListener(mp -> {
            stopAudioOnMediaPlayer();
            mediaPlayerHeader.setText(R.string.finished);
            mediaPlayerSeekbar.setProgress(mediaPlayerForAudioRecordings.getDuration());
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            playFileSelected = false;
        });
    }

    private void updateRunnable() {
        syncSeekBar = new Runnable() {
            @Override
            public void run() {
                mediaPlayerSeekbar.setProgress(mediaPlayerForAudioRecordings.getCurrentPosition());
                seekBarHandler.postDelayed(this, 500);
            }
        };
    }

    @Override
    public void onStop() {
        super.onStop();
        if (isCurrentlyPlaying) {
            stopAudioOnMediaPlayer();
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void pauseAudio() {
        mediaPlayerForAudioRecordings.pause();
        mediaPlayerHeader.setText(R.string.paused);
        playButton.setImageDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.ic_play_filled_circle));
        seekBarHandler.removeCallbacks(syncSeekBar);
        isCurrentlyPlaying = false;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void resumeAudio() {
        mediaPlayerForAudioRecordings.start();
        mediaPlayerHeader.setText(R.string.playing);
        playButton.setImageDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.ic_pause_circle_filled));
        isCurrentlyPlaying = true;

        updateRunnable();
        seekBarHandler.postDelayed(syncSeekBar, 0);
    }
}