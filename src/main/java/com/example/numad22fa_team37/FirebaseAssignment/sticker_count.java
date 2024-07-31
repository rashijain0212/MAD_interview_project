package com.example.numad22fa_team37.FirebaseAssignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.numad22fa_team37.FirebaseAssignment.HomeScreen.HomeScreenActivity;
import com.example.numad22fa_team37.FirebaseAssignment.HomeScreen.UserAdapter;
import com.example.numad22fa_team37.FirebaseAssignment.Models.Message;
import com.example.numad22fa_team37.FirebaseAssignment.Models.User;
import com.example.numad22fa_team37.FirebaseAssignment.Sticker.StickerAdapter;
import com.example.numad22fa_team37.FirebaseAssignment.Sticker.StickerList;
import com.example.numad22fa_team37.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class sticker_count extends AppCompatActivity {
    private final String Tag = "FirebaseAssignmentActivity";
    private String username;
    private List<Message> usersLst;
    private StickerCountAdapter mAdapter;
    private Map<Integer,Integer> stickerCountMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
        setTitle("Users");
        Log.v(Tag, "onCreate Called");
        username = getSharedPreferences("username", MODE_PRIVATE).getString("key_username", null);
        usersLst = new ArrayList<>();
        stickerCountMap = new HashMap<>();
        RecyclerView recyclerView = findViewById(R.id.recyclerMovieView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        DatabaseReference mUsers = mDatabase.child("messages");

        mAdapter = new StickerCountAdapter(usersLst, this, this);
        recyclerView.setAdapter(mAdapter);
        mUsers.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        usersLst.clear();
                        stickerCountMap.clear();
                        handleMessages(dataSnapshot.getChildren());
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(sticker_count.this, "Something went wrong try login again!", Toast.LENGTH_SHORT).show();
                    }
                });
    }



    public String getCurrentUser() {
        return this.username;
    }

    private void handleMessages(Iterable<DataSnapshot> messages) {
        messages.forEach(message -> {
            Message newMessage = message.getValue(Message.class);
            if (newMessage != null) {
                usersLst.add(newMessage);
            }
            usersLst.sort(Comparator.comparing(Message::getSentOn));
            //stickerCountMap[Message::getStickerId]=

        });
    }
    }

