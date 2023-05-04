package com.cqust.exam2.service;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.cqust.exam2.R;
import com.cqust.exam2.util.Tool;


public class TestMusicService extends AppCompatActivity {

    int status = 1;
    private static SeekBar music_sb;
    private static TextView cur_tv, dur_tv, title;
    private ImageView next_img, previous_img, play_img, loop_img;
    private String[] musics = new String[]{
            "music1",
            "music2",
            "music3",
            "music4",
            "music5",
    };
    private Intent service;
    private MusicServiceReceiver musicServiceReceiver;
    public static final String UPDATE_ACTION = "com.cqust.action.UPDATE_ACTION";
    public static final String CKL_ACTION = "com.cqust.action.CKL_ACTION";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_music_service);
        ImageOnClickListener listener = new ImageOnClickListener();
        initActivity();

        play_img.setOnClickListener(listener);
        next_img.setOnClickListener(listener);
        previous_img.setOnClickListener(listener);
        loop_img.setOnClickListener(listener);

        music_sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Intent intent = new Intent(CKL_ACTION);
                intent.putExtra("seekTo", seekBar.getProgress());
                sendBroadcast(intent);
            }
        });
    }

    class ImageOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(CKL_ACTION);
            switch (view.getId())
            {
                case R.id.play_img:
                    intent.putExtra("action", 1);
                    break;
                case R.id.loop_img:
                    intent.putExtra("action", 2);
                    break;
                case R.id.previous_img:
                    intent.putExtra("action", 3);
                    break;
                case R.id.next_img:
                    intent.putExtra("action", 4);
            }
            sendBroadcast(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(musicServiceReceiver);
    }

    class MusicServiceReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            int update = intent.getIntExtra("status", -1);
            int cur_music = intent.getIntExtra("cur_music", -1);
            title.setText(musics[cur_music]);
            if(cur_music >= 0){
                int cur_time = intent.getIntExtra("cur_time", -1)/1000;
                cur_tv.setText(Tool.getMusicTime(cur_time)+"");
                int dur_time = intent.getIntExtra("dur_time", -1)/1000;
                dur_tv.setText(Tool.getMusicTime(dur_time)+"");
                music_sb.setMax(dur_time);
                music_sb.setProgress(cur_time);
            }
            switch (update){
                case 1:
                    status = 1;
                    music_sb.setProgress(0);
                    cur_tv.setText("00:00");
                    play_img.setImageResource(R.drawable.round_play_arrow_24);
                    break;
                case 2:
                    status = 2;
                    play_img.setImageResource(R.drawable.round_pause_24);
                    break;
                case 3:
                    status = 3;
                    play_img.setImageResource(R.drawable.round_play_arrow_24);
                    break;
            }
        }
    }

    private void initActivity(){
        music_sb = findViewById(R.id.music_sb);
        cur_tv = findViewById(R.id.cur_tv);
        dur_tv = findViewById(R.id.dur_tv);
        next_img = findViewById(R.id.next_img);
        previous_img = findViewById(R.id.previous_img);
        play_img = findViewById(R.id.play_img);
        loop_img = findViewById(R.id.loop_img);
        title = findViewById(R.id.title);

        musicServiceReceiver = new MusicServiceReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(UPDATE_ACTION);
        registerReceiver(musicServiceReceiver, filter);

        service = new Intent(TestMusicService.this, MusicService.class);
        startService(service);
    }
}