package com.cqust.exam2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.cqust.exam2.R;

// 自动登录过渡页
public class EmptyActivity extends AppCompatActivity {

    private Handler handler;
    private int runTime = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
        runTimer();
    }

    private void runTimer() {
        final TextView timeView = findViewById(R.id.tv_time);
        handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                String time = String.format("自动登录倒计时：%d", runTime);
                runTime--;
                timeView.setText(time);
                handler.postDelayed(this, 1000);
            }
        });
    }
}