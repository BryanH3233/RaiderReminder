package com.example.raiderreminder;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Objects;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "alarm_channel";

    @Override
    public void onReceive(Context context, Intent intent) {
        // Retrieve eventClass from the Intent
        eventClass event = (eventClass) intent.getSerializableExtra("eventClass");

        createNotificationChannel(context);

        //Build Notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.baseline_notification_important_24)
                .setContentTitle("Reminder Notification")
                .setContentText(event.getNotificationMessage())
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        int notificationId = generateNotificationId(event);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(notificationId, builder.build());
    }

    //Creates channel for sending notifications (in newer SDK's)
    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Raider Reminder Channel";
            String description = "Channel for Raider Reminder notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
    // Method to generate a unique notification Id based on event data
    private int generateNotificationId(eventClass event) {
        // Uses a combo of event information data to create a unique id
        return Objects.hash(event.getName(), event.getYear(), event.getMonth(), event.getDay(), event.getHour(), event.getMinute());
    }
}
