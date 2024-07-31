package com.example.numad22fa_team37.GroupProject.Behavioral;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.numad22fa_team37.R;
import com.google.android.material.button.MaterialButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class RecordFragment extends Fragment implements View.OnClickListener {

    private MaterialButton recordBtn;
    private ImageButton showRecordingsBtn;
    boolean isRecording = false;
    private MediaRecorder mediaRecorder;
    private Chronometer chronometer;
    private TextView recordingFileName;
    private String audioFileName;
    private NavController navController;
    private String questionId;

    public RecordFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.record_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        recordBtn = view.findViewById(R.id.recordButton);
        recordBtn.setOnClickListener(this);
        chronometer = view.findViewById(R.id.audio_record_timer);
        recordingFileName = view.findViewById(R.id.fileNameTextView);
        showRecordingsBtn = view.findViewById(R.id.recordHistoryImageButton);
        showRecordingsBtn.setOnClickListener(this);
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "NonConstantResourceId"})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.recordHistoryImageButton:
                BehavioralQuestionActivity activity = (BehavioralQuestionActivity) getActivity();
                String questionId = "-1";
                if (activity != null) {
                    questionId = activity.getQuestionId();
                }
                String filePath = requireActivity().getExternalFilesDir("/").getAbsolutePath();
                File audioDirectory = new File(filePath + "/" + questionId);
                File[] audioFiles = audioDirectory.listFiles();

                if (audioFiles == null || audioFiles.length == 0) {
                    alertUserNoRecordings();
                } else {
                    if (isRecording) {
                        alertUser();
                    } else {
                        navController.navigate(R.id.action_recordFragment_to_audioHistoryFragment);
                    }
                }
                break;
            case R.id.recordButton:
                if (isRecording) {
                    stopRecording();
                    recordBtn.setIcon(getResources().getDrawable(R.drawable.ic_baseline_mic_24, null));
                    isRecording = false;
                } else {
                    //check permission
                    if (hasPermission()) {
                        startRecording();
                        recordBtn.setIcon(getResources().getDrawable(R.drawable.ic_baseline_stop_24, null));
                        isRecording = true;
                    }
                }
                break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (isRecording) {
            stopRecording();
        }
    }


    private void alertUserNoRecordings() {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle("No recordings");
        alertDialog.setMessage("There are no recordings yet, click on the mic button to start recording");
        alertDialog.create();
        alertDialog.show();
    }

    private void alertUser() {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle("Answer is still recording");
        alertDialog.setMessage("Are you sure you want to stop the recording and move to the recording history screen");
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", (dialog, which) -> {
            navController.navigate(R.id.action_recordFragment_to_audioHistoryFragment);
            isRecording = false;
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", (DialogInterface.OnClickListener) null);
        alertDialog.create();
        alertDialog.show();
    }


    private void startRecording() {
        String audioFilePath = requireActivity().getExternalFilesDir("/").getAbsolutePath();
        createFolderForQuestionId();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss", Locale.US);
        Date now = new Date();
        audioFileName = "Recording_" + formatter.format(now) + ".mp3";
        recordingFileName.setText(String.format("Recording, file name: %s", audioFileName));
        mediaRecorder = new MediaRecorder();

        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(audioFilePath + "/" + questionId + "/" + audioFileName);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
        mediaRecorder.start();
    }

    private void stopRecording() {
        chronometer.stop();
        mediaRecorder.stop();
        recordingFileName.setText(String.format("Recording stopped, file saved: %s", audioFileName));
        mediaRecorder.release();
        mediaRecorder = null;
    }


    private void createFolderForQuestionId() {
        BehavioralQuestionActivity activity = (BehavioralQuestionActivity) getActivity();
        if (activity != null) {
            questionId = activity.getQuestionId();
        }
        String dirPath = requireActivity().getExternalFilesDir("/").getAbsolutePath() + "/" + questionId;
        File projDir = new File(dirPath);

        boolean success;
        if (!projDir.exists()) {
            success = projDir.mkdirs();
        } else {
            success = true;
        }

        if (!success) {
            throw new RuntimeException();
        }
    }

    private boolean hasPermission() {
        String recordPermission = Manifest.permission.RECORD_AUDIO;
        if (ActivityCompat.checkSelfPermission(requireContext(), recordPermission) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            int permissionCode = 1;
            ActivityCompat.requestPermissions(requireActivity(), new String[]{recordPermission}, permissionCode);
            return false;
        }
    }
}
