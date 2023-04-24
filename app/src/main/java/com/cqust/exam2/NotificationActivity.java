package com.cqust.exam2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class NotificationActivity extends AppCompatActivity {

    private Button button;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        button = findViewById(R.id.notification_bt);
        button.setOnClickListener(this::onClick);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onClick(View view){
        if(view.getId() == R.id.notification_bt){
            // 创件通知跳转

            // 创建通知
            String nfc_id = "testNotification";
            Notification notification = new Notification.Builder(this, nfc_id)
                    .setSmallIcon(R.drawable.contacts_image)
                    .setContentText("通知内容：6666666666666666666")
                    .setAutoCancel(true)
                    .build();
            // 获取系统通知管理器
            NotificationManager notificationManager = (NotificationManager)this.getSystemService(NOTIFICATION_SERVICE);
            // 创建通知通道
            NotificationChannel channel = new NotificationChannel(nfc_id, "666", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
            // 发送通知
            notificationManager.notify(1123, notification);
        }
    }
}