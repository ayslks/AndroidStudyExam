package com.cqust.exam2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cqust.exam2.R;

public class TimerActivity extends AppCompatActivity {

    private Handler handler;
    private int runTime = 0;
    private boolean running;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        runTimer();
        Button bt_quit = findViewById(R.id.button_callBack);
        bt_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TimerActivity.this, MainActivity.class);
                startActivity(intent);
                finish();//销毁此Activity
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        running = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        running = true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        running = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        running = false;
        handler.removeCallbacks(null);
    }

    private void runTimer() {
        final TextView timeView = findViewById(R.id.time);
        handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (running) runTime++;
                int hours = runTime / 3600;
                int minutes = (runTime % 3600) / 60;
                int secs = runTime % 60;
                String time = String.format("%02d:%02d:%02d", hours, minutes, secs);
                timeView.setText(time);
                handler.postDelayed(this, 1000);
            }
        });
    }

    public void onClickStart(View view) {
        running=true;
    }

    public void onClickReStart(View view) {
        running=false;
        runTime = 0;
    }

    public void onClickStop(View view) {
        running=false;
    }

}