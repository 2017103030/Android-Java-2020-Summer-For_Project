package org.techtown.creative_total_construction;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.text.NoCopySpan;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class RestartService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default");
        builder.setSmallIcon(R.mipmap.ic_launcher);

        Notification notification = builder.build();
        startForeground(9, notification);
        Intent in = new Intent(this, MyService.class);
        startService(in);

        stopForeground(true);
        stopSelf();
        return START_NOT_STICKY;

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public void onDestroy() {
        super.onDestroy();
    }
}
