package com.cqust.exam2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cqust.exam2.R;
import com.cqust.exam2.bean.User;
import com.cqust.exam2.broadcast.BroadcastActivity;
import com.cqust.exam2.service.TestMusicService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("nowActivity:", "Main--onCreate");

        Intent intent_fromLogin=getIntent();
        User user=(User)intent_fromLogin.getSerializableExtra("userName");
        if(user!=null){
            String hello_str = "Hello,"+user.getUserName()+"!";
            TextView hello_tv = this.<TextView>findViewById(R.id.hello_tv);
            hello_tv.setText(hello_str);
            Toast.makeText(this, "登录成功！", Toast.LENGTH_SHORT).show();
        }

        Button bt_openTimer = findViewById(R.id.open_timer);
        bt_openTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TimerActivity.class);
                startActivity(intent);
            }
        });

        Button bt_broadcast = findViewById(R.id.open_broadcast);
        bt_broadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BroadcastActivity.class);
                startActivity(intent);
            }
        });

        Button bt_open_mail_list = findViewById(R.id.open_mail_list);
        bt_open_mail_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ContactsActivity.class);
                startActivity(intent);
            }
        });

        Button bt_quit = findViewById(R.id.quit);
        bt_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getSharedPreferences("remember_data", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("自动登录:", false);
                editor.commit();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();//销毁此Activity
            }
        });

        Button bt_music = findViewById(R.id.to_music);
        bt_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TestMusicService.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.to_showImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShowImageActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.to_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, OrderActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("nowActivity:", "Main--onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("nowActivity:", "Main--onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("nowActivity:", "Main--onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("nowActivity:", "Main--onDestroy");
    }

}