package com.cqust.exam2.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.cqust.exam2.R;

import java.io.IOException;

public class MusicService extends Service{

    private MediaPlayer mediaPlayer;
    TestReceiver testReceiver;
    int status = 1; // 状态：1表示没有播放，2表示正在播放，3表示暂停
    int current_music = 0; // 记录正在播放的音乐的序号
    AssetManager manager;
    String[] musics = new String[]{
            "music1",
            "music2",
            "music3",
            "music4",
            "music5",
    };

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("lks:","----------Service onCreate-----------");
        manager = getAssets();
        // 注册广播接收CKL_ACTION
        testReceiver = new TestReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(TestMusicService.CKL_ACTION);
        registerReceiver(testReceiver, filter);
        // 播放完成事件绑定监听器
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                current_music++;
                if(current_music > 4){
                    current_music = 0;
                }
                // 发送广播更改歌曲信息
                Intent sentIntent = new Intent(TestMusicService.UPDATE_ACTION);
                sentIntent.putExtra("cur_music", current_music);
                sentIntent.putExtra("cur_time", mediaPlayer.getCurrentPosition());
                sentIntent.putExtra("dur_time", mediaPlayer.getDuration());
                sendBroadcast(sentIntent);
                // 播放音乐
                prepareAndPlay(musics[current_music]);
            }
        });


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        if(mediaPlayer != null){
            mediaPlayer = null;
        }
        mediaPlayer.release();
        handler.removeCallbacks(null);
        Log.i("lks:","--------Service onDestroy--------");
    }

    class TestReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            int action = intent.getIntExtra("action", -1);
            switch (action){
                //播放或暂停
                case 1:
                    //原来处于没有播放状态
                    if (status == 1) {
                        //准备并播放音乐
                        prepareAndPlay(musics[current_music]);
                        status = 2;
                    }
                    //原来处于播放状态
                    else if (status == 2) {
                        //暂停
                        mediaPlayer.pause();
                        status = 3;

                    }
                    //原来处于暂停状态
                    else if (status == 3) {
                        //播放
                        mediaPlayer.start();
                        status = 2;
                    }
                    break;
                // 停止播放
                case 2:
                    if(status == 2 || status == 3){
                        mediaPlayer.stop();
                        status = 1;
                    }
                    break;
                // 播放上一首
                case 3:
                    if(current_music == 0){
                        current_music = 4;
                    }else {
                        current_music = current_music - 1;
                    }
                    if(status == 1 || status == 3){
                        status = 2;
                    }
                    prepareAndPlay(musics[current_music]);
                    break;
                // 播放下一首
                case 4:
                    if(current_music == 4){
                        current_music = 0;
                    }else {
                        current_music = current_music + 1;
                    }
                    if(status == 1 || status == 3){
                        status = 2;
                    }
                    prepareAndPlay(musics[current_music]);
                    break;
            }
            int seekTo = intent.getIntExtra("seekTo", -1);
            if(seekTo != -1) {
                if(status == 1){
                    prepareAndPlay(musics[current_music]);
                    status = 2;
                }
                mediaPlayer.seekTo(seekTo*1000);
            }
            runMusic();
        }
    }

    private void prepareAndPlay(String musicName){
        try{
            mediaPlayer.reset();
            if(musicName.equals("music1"))
                mediaPlayer.setDataSource(getApplicationContext(), Uri.parse("android.resource://"+ getPackageName() + "/" + R.raw.music1));
            if(musicName.equals("music2"))
                mediaPlayer.setDataSource(getApplicationContext(), Uri.parse("android.resource://"+ getPackageName() + "/" + R.raw.music2));
            if(musicName.equals("music3"))
                mediaPlayer.setDataSource(getApplicationContext(), Uri.parse("android.resource://"+ getPackageName() + "/" + R.raw.music3));
            if(musicName.equals("music4"))
                mediaPlayer.setDataSource(getApplicationContext(), Uri.parse("android.resource://"+ getPackageName() + "/" + R.raw.music4));
            if(musicName.equals("music5"))
                mediaPlayer.setDataSource(getApplicationContext(), Uri.parse("android.resource://"+ getPackageName() + "/" + R.raw.music5));
            mediaPlayer.prepare();
            mediaPlayer.start();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private Handler handler;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Intent sentIntent = new Intent(TestMusicService.UPDATE_ACTION);
            sentIntent.putExtra("cur_music", current_music);
            sentIntent.putExtra("cur_time", mediaPlayer.getCurrentPosition());
            sentIntent.putExtra("dur_time", mediaPlayer.getDuration());
            sentIntent.putExtra("status", status);
            sendBroadcast(sentIntent);
            if(status == 3 || status == 1){
                handler.removeCallbacks(null);
                return;
            }
            handler.postDelayed(this, 1000);
        }
    };

    private void runMusic() {
        handler = new Handler();
        handler.post(runnable);
    }

}