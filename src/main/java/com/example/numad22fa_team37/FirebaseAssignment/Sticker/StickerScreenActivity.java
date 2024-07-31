package com.example.numad22fa_team37.FirebaseAssignment.Sticker;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.numad22fa_team37.FirebaseAssignment.Models.Message;
import com.example.numad22fa_team37.FirebaseAssignment.Models.User;
import com.example.numad22fa_team37.FirebaseAssignment.Utils.Utils;
import com.example.numad22fa_team37.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

public class StickerScreenActivity extends AppCompatActivity implements OnItemClickListener {
    private String userTo;
    private String userFrom;
    private final static String SERVER_KEY = "key=AAAA0brce0U:APA91bFcc08xzfxdjsrvtwElK56E6fuT_42VG3bVfr6KqNHcWezA5FBUWxNiMh8TbByuU2Ou5ArFRo0zT-1wsT7oGEHxpaPgZ1fKijQ5rtc2KDw25nLveTcVHKiavoy73xCO6wxbiFBx";
    private DatabaseReference messages;
    private DatabaseReference mDatabase;
    private ArrayList<Integer> mImageIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker_screen);
        setTitle("Stickers");
        mImageIds = StickerList.getStickerList();
        RecyclerView recyclerView = findViewById(R.id.recyclerStickerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userTo = this.getIntent().getStringExtra("to");
        userFrom = this.getIntent().getStringExtra("from");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        messages = mDatabase.child("messages");
        StickerAdapter mAdapter = new StickerAdapter(mImageIds, this, this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(View view, Integer stickerId) {
        Message message = new Message(userTo, userFrom, System.currentTimeMillis(), stickerId);
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();
        messages.child(uuidAsString).setValue(message, (error, ref) -> {
            if (error != null) {
                Toast.makeText(view.getContext(), "Failed to send sticker", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(view.getContext(), "Sticker Sent!", Toast.LENGTH_LONG).show();
                mDatabase.child("users").child(userTo).get().addOnCompleteListener(task -> {
                    User user = task.getResult().getValue(User.class);
                    if (user != null) {
                        new Thread(() -> sendMessageToDevice(user.getToken(), userFrom, stickerId)).start();
                    }
                });

            }
        });
    }

    private void sendMessageToDevice(String targetToken, String userFrom, int stickerId) {

        // Prepare data
        JSONObject jPayload = new JSONObject();
        JSONObject jNotification = new JSONObject();
        JSONObject jdata = new JSONObject();
        try {
            jNotification.put("title", "Message from " + userFrom);
            jNotification.put("body", stickerId);
            jNotification.put("sound", "default");
            jNotification.put("badge", "1");

            jdata.put("title", "Message from " + userFrom);
            jdata.put("content", stickerId);

            jPayload.put("to", targetToken); // CLIENT_REGISTRATION_TOKEN);


            jPayload.put("priority", "high");
            jPayload.put("notification", jNotification);
            jPayload.put("data", jdata);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Utils.fcmHttpConnection(SERVER_KEY, jPayload);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mImageIds.clear();
    }
}