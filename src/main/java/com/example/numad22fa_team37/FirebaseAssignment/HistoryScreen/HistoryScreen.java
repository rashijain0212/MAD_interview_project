package com.example.numad22fa_team37.FirebaseAssignment.HistoryScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.numad22fa_team37.R;

import com.example.numad22fa_team37.FirebaseAssignment.Models.Message;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;

public class HistoryScreen extends AppCompatActivity {
    private HistoryAdapter hAdapter;
    private ArrayList<Message> historyList;
    private String to;
    private String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("History");
        setContentView(R.layout.history_screen);
        RecyclerView recyclerViewHistory = findViewById(R.id.rvHistory);
        recyclerViewHistory.setHasFixedSize(true);
        recyclerViewHistory.setLayoutManager(new LinearLayoutManager(this));
        to = getIntent().getStringExtra("to");
        from = getIntent().getStringExtra("from");
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference messages = mDatabase.child("messages");
        historyList = new ArrayList<>();
        hAdapter = new HistoryAdapter(historyList, this);
        recyclerViewHistory.setAdapter(hAdapter);
        messages.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        historyList.clear();
                        handleMessages(dataSnapshot.getChildren());
                        hAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(HistoryScreen.this, "Something went wrong try login again!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void handleMessages(Iterable<DataSnapshot> messages) {
        messages.forEach(message -> {
            Message newMessage = message.getValue(Message.class);
            if (newMessage != null && (newMessage.getFrom().equals(from) && newMessage.getTo().equals(to)
                    || newMessage.getFrom().equals(to) && newMessage.getTo().equals(from))) {
                historyList.add(newMessage);
            }
            historyList.sort(Comparator.comparing(Message::getSentOn));
        });
    }
}