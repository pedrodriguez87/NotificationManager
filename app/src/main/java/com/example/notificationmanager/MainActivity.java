package com.example.notificationmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final NotificationManager notifManager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);

        final NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, "channelId")
                        .setAutoCancel(true)
                        .setSmallIcon(R.drawable.ic_notification)
                        .setContentTitle("BTS MDSD")
                        .setContentText("Hello class!")
                        .setContentIntent(getPendingIntentWithRequestCode(23));

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel("NOTIFICATION_CHANNEL_ID",
                    "NOTIFICATION_CHANNEL_NAME",
                    importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            builder.setChannelId("NOTIFICATION_CHANNEL_ID");
            notifManager.createNotificationChannel(notificationChannel);
        }

        final View layout = findViewById(R.id.main_activity__background);
        layout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i("MainActivity", "background clicked");
                notifManager.notify(
                        new Random().nextInt(4),
                        builder.build());
            }

        });
    }

    private PendingIntent getPendingIntentWithRequestCode(int requestCode) {
        Intent tapActionIntent = new Intent(this, SecondActivity.class);
        return PendingIntent.getActivity(this,
                requestCode,
                tapActionIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
    }
}
