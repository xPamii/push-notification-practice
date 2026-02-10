package com.xpamii.push_notification.service;

import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.xpamii.push_notification.R;
import com.xpamii.push_notification.activity.MainActivity;

public class MessagingService extends FirebaseMessagingService {

    private static final String TAG = MessagingService.class.getSimpleName();

    @Override
    public void onNewToken(@NonNull String token) {
        Log.d(TAG, "Token: " + token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {

        String title = message.getNotification() != null ?
                message.getNotification().getTitle() : "Title";// Empty Title

        String messageBody = message.getNotification() != null ?
                message.getNotification().getBody() : "Empty Body"; // Empty Body

        sendNotification(title, messageBody); // call sendNotification method to send notification to user device
    }

    private void sendNotification(String title, String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        int requestCode = 0; // Request Code for notification
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_IMMUTABLE); // Pending Intent for notification.

        String channelID = "default_channel"; // Channel ID for notification.
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelID)
                .setSmallIcon(R.drawable.ic_launcher_foreground) // Icon for notification
                .setContentTitle(title) // Title for notification
                .setContentText(messageBody) // Message for notification
                .setAutoCancel(true) // Cancel notification after click
                .setContentIntent(pendingIntent);


        android.app.NotificationManager notificationManager = (android.app.NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) { // Check version of Android device
            NotificationChannel channel = new NotificationChannel(channelID, "Default Channel", android.app.NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);// Create channel for notification
        }
        int notificationID = 0; // ID for notification
        notificationManager.notify(notificationID, notificationBuilder.build()); // Send notification to user device with notification ID and notification builder.
    }

}
