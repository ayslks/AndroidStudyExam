package com.cqust.exam2.broadcast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cqust.exam2.MainActivity;
import com.cqust.exam2.R;

public class BroadcastActivity extends AppCompatActivity {

    private final static String TAG = "BroadcastActivity";
    private TextView tv_standard;
    private String mDesc = "Toast查看标准广播的收听信息";
    private static final String MY_ACTION = "com.cqust.exam2.standard";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);

        tv_standard = findViewById(R.id.tv_standard);
        tv_standard.setText(mDesc);
        findViewById(R.id.btn_send_standard).setOnClickListener(this::onClick);
        Button bt_quit = findViewById(R.id.bt_callBack);
        bt_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BroadcastActivity.this, MainActivity.class);
                startActivity(intent);
                finish();//销毁此Activity
            }
        });

        LinearLayout bk_lv = findViewById(R.id.bk_lv);
        Button bt_toOrder = new Button(this);
        bk_lv.addView(bt_toOrder);
        bt_toOrder.setText("去发送有序广播");
        bt_toOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BroadcastActivity.this, BroadOrderActivity.class);
                startActivity(intent);
            }
        });

        Button bt_toMinute = new Button(this);
        bk_lv.addView(bt_toMinute);
        bt_toMinute.setText("去监听系统分钟广播");
        bt_toMinute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BroadcastActivity.this, SystemMinuteActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onClick(View v) {
        if (v.getId() == R.id.btn_send_standard) {
            Intent intent = new Intent(MY_ACTION); // 创建指定动作的意图
            sendBroadcast(intent); // 发送标准广播
        }
    }

    private StandardReceiver standardReceiver; // 声明一个标准广播的接收器实例
    @Override
    protected void onStart() {
        super.onStart();
        standardReceiver = new StandardReceiver(); // 创建一个标准广播的接收器
        // 创建一个意图过滤器，只处理STANDARD_ACTION的广播
        IntentFilter filter = new IntentFilter(MY_ACTION);
        registerReceiver(standardReceiver, filter); // 注册接收器，注册之后才能正常接收广播
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(standardReceiver); // 注销接收器，注销之后就不再接收广播
    }

    // 定义一个标准广播的接收器
    private class StandardReceiver extends BroadcastReceiver {
        // 一旦接收到标准广播，马上触发接收器的onReceive方法
        @Override
        public void onReceive(Context context, Intent intent) {
            // 广播意图非空，且接头暗号正确
            if (intent != null && intent.getAction().equals(MY_ACTION)) {
                String msg = "接收到一个标准广播";
                tv_standard.setText(mDesc);
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }
        }
    }
}