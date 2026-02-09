package com.xpamii.push_notification.service;

import android.app.PendingIntent;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.xpamii.push_notification.activity.MainActivity;

public class MessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {

        String title = message.getNotification() != null ?
                message.getNotification().getTitle() : "Title";

        String messageBody = message.getNotification() != null ?
                message.getNotification().getBody() : "Empty Body";

        sendNotification(title, messageBody);
    }

    private void sendNotification(String title, String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        int requestCode = 0;
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_IMMUTABLE);

    }

}
