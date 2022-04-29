package com.example.a5_2;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class NotificationService extends Service {
    final String LOG_TAG = "ACTIVITY LOG";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "onCreate()");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "onStartCommand()");
        createNotificationChannel(); // создаём канал для уведомлений


        Intent intent1 = new Intent(this, MainActivity.class);
        Intent intent2 = new Intent(this, SecondActivity.class);
        Intent intent3 = new Intent(this, ThirdActivity.class);

        // задаём отложенный интент открывающий активити
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent1, 0);
        PendingIntent pendingIntent2 = PendingIntent.getActivity(this, 0, intent2, 0);
        PendingIntent pendingIntent3 = PendingIntent.getActivity(this, 0, intent3, 0);

        Notification notification = new NotificationCompat.Builder(this, "ChannelId1")
                .setContentTitle("Заголовок уведомления")
                .setContentText("Текст уведомления")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .addAction(R.mipmap.ic_launcher,"1", pendingIntent2) // addAction - добавление кнопок в уведомление
                .addAction(R.mipmap.ic_launcher, "2", pendingIntent3)
                .build();

        startForeground(1, notification);

        return START_STICKY;
    }

    // с android Oreo нужно создать канала уведомления
    private void createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    "ChannelId1", "Foreground notification", NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }
    }

    @Override
    public void onDestroy() {
        Log.d(LOG_TAG, "onDestroy()");
        stopForeground(true);
        stopSelf();
        super.onDestroy();
    }

}