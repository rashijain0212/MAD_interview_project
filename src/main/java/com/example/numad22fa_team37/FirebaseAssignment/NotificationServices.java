package com.example.numad22fa_team37.FirebaseAssignment;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.numad22fa_team37.FirebaseAssignment.HomeScreen.HomeScreenActivity;
import com.example.numad22fa_team37.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class NotificationServices extends FirebaseMessagingService {

    private static final String CHANNEL_ID = "CHANNEL_ID";
    private static final String CHANNEL_NAME = "CHANNEL_NAME";
    private static final String CHANNEL_DESCRIPTION = "CHANNEL_DESCRIPTION";


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onNewToken(@NonNull String newToken) {
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        myClassifier(remoteMessage);
    }

    private void myClassifier(RemoteMessage remoteMessage) {

        String identificator = remoteMessage.getFrom();
        if (identificator != null) {
            if (remoteMessage.getData().size() > 0) {
                RemoteMessage.Notification notification = remoteMessage.getNotification();
                showNotification(notification);
            }
        }
    }

    private void showNotification(RemoteMessage.Notification remoteMessageNotification) {

        Intent intent = new Intent(this, HomeScreenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_MUTABLE);

        Notification notification;
        NotificationCompat.Builder builder;
        NotificationManager notificationManager = getSystemService(NotificationManager.class);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            // Configure the notification channel
            notificationChannel.setDescription(CHANNEL_DESCRIPTION);
            notificationManager.createNotificationChannel(notificationChannel);
            builder = new NotificationCompat.Builder(this, CHANNEL_ID);

        } else {
            builder = new NotificationCompat.Builder(this);
        }

        notification = builder.setContentTitle(remoteMessageNotification.getTitle())
                .setContentText("New sticker received!")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(BitmapFactory.decodeResource(getResources(), Integer.parseInt(remoteMessageNotification.getBody())))
                        .bigLargeIcon(null))
                .build();
        notificationManager.notify(0, notification);
    }
}
