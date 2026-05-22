package com.example.weather;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresPermission;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class StudyEndReceiver extends BroadcastReceiver {
    public static final String ACTION_STUDY_END = "com.example.myapplication.ACTION_STUDY_END";
    private static final String CHANNEL_ID = "study_end_channel";
    private static final int NOTIFICATION_ID = 1;

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION_STUDY_END.equals(intent.getAction())) {
            // 创建通知
            createNotificationChannel(context);
            showNotification(context);
        }
    }

    /**
     * 创建通知渠道，适用于 Android 8.0 及以上
     * @param context 上下文
     */
    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "背景音乐通知";
            String description = "正在播放背景音乐";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // 注册渠道
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    /**
     * 显示通知
     * @param context 上下文
     */
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private void showNotification(Context context) {
        // 创建点击通知后的跳转意图，这里跳转到 MainActivity，可根据实际情况修改
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // 构建通知
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("背景音乐通知")
                .setContentText("正在播放背景音乐！")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        // 显示通知
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        Notification notification = builder.build();
        notificationManager.notify(NOTIFICATION_ID, notification);
    }
}