package com.example.myapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import androidx.core.app.NotificationCompat;

/**
 * The RemindObserver class implements the Observer interface.
 * It is responsible for sending notifications to the user
 * when it receives update messages from the subject.
 */
public class RemindObserver implements Observer {
    private Context context;  // Context used for accessing system services

    public RemindObserver(Context context) {
        this.context = context;
    }

    @Override
    public void update(String message) {
        sendNotification(message);
    }

    private void sendNotification(String message) {
        String channelId = "happy_channel";
        String channelName = "Happy Reminder";

        // Get the NotificationManager system service
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Create the notification channel if the Android version is supported
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.happy_mood) // Set the notification icon
                .setContentTitle("Stay Happy!") // Set the notification title
                .setContentText(message) // Set the notification message
                .setPriority(NotificationCompat.PRIORITY_HIGH); // Set the notification priority

        // Notify the user with the built notification
        notificationManager.notify(1, builder.build());
    }
}

