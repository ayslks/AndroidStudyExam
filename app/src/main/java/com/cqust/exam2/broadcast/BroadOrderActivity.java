package com.cqust.exam2.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cqust.exam2.R;
import com.cqust.exam2.util.DateUtil;


public class BroadOrderActivity extends AppCompatActivity implements View.OnClickListener {
    private final static String TAG = "BroadOrderActivity";
    private final static String ORDER_ACTION = "com.example.chapter09.order";
    private CheckBox ck_abort;
    private TextView tv_order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broad_order);
        ck_abort = findViewById(R.id.ck_abort);
        tv_order = findViewById(R.id.tv_order);
        findViewById(R.id.btn_send_order).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_send_order) {
            tv_order.setText("");
            Intent intent = new Intent(ORDER_ACTION); // 创建一个指定动作的意图
            sendOrderedBroadcast(intent, null); // 发送有序广播
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 多个接收器处理有序广播的顺序规则为：
        // 1、优先级越大的接收器，越早收到有序广播；
        // 2、优先级相同的时候，越早注册的接收器越早收到有序广播
        orderAReceiver = new OrderAReceiver(); // 创建一个有序广播的接收器A
        IntentFilter filterA = new IntentFilter(ORDER_ACTION);// 创建一个意图过滤器A，只处理ORDER_ACTION的广播
        filterA.setPriority(8); // 设置过滤器A的优先级，数值越大优先级越高
        registerReceiver(orderAReceiver, filterA); // 注册接收器A，注册之后才能正常接收广播

        orderBReceiver = new OrderBReceiver(); // 创建一个有序广播的接收器B
        IntentFilter filterB = new IntentFilter(ORDER_ACTION);// 创建一个意图过滤器B，只处理ORDER_ACTION的广播
        filterB.setPriority(10); // 设置过滤器B的优先级，数值越大优先级越高
        registerReceiver(orderBReceiver, filterB); // 注册接收器B，注册之后才能正常接收广播

        orderCReceiver = new OrderCReceiver();
        IntentFilter filterC = new IntentFilter(ORDER_ACTION);
        filterC.setPriority(12);
        registerReceiver(orderCReceiver, filterC);

        orderDReceiver = new OrderDReceiver();
        IntentFilter filterD = new IntentFilter(ORDER_ACTION);
        filterD.setPriority(14);
        registerReceiver(orderDReceiver, filterD);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(orderAReceiver); // 注销接收器A，注销之后就不再接收广播
        unregisterReceiver(orderBReceiver); // 注销接收器B，注销之后就不再接收广播
        unregisterReceiver(orderCReceiver);
        unregisterReceiver(orderDReceiver);
    }

    private OrderAReceiver orderAReceiver; // 声明有序广播接收器A的实例
    // 定义一个有序广播的接收器A
    private class OrderAReceiver extends BroadcastReceiver {
        // 一旦接收到有序广播，马上触发接收器的onReceive方法
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getAction().equals(ORDER_ACTION)) {
                String desc = String.format("%s%s 接收器A收到一个谣言\n",
                        tv_order.getText().toString(), DateUtil.getNowTime());
                tv_order.setText(desc);
            }
        }
    }

    private OrderBReceiver orderBReceiver; // 声明有序广播接收器B的实例
    // 定义一个有序广播的接收器B
    private class OrderBReceiver extends BroadcastReceiver {
        // 一旦接收到有序广播B，马上触发接收器的onReceive方法
        @Override
        public void onReceive(Context context, Intent intent) {
            String desc = String.format("%s%s 接收器B（智者）收到一个谣言\n",
                    tv_order.getText().toString(), DateUtil.getNowTime());
            tv_order.setText(desc);
            if (ck_abort.isChecked()) {
                abortBroadcast();
                String desc1 = String.format("%s 谣言止于智者\n", tv_order.getText().toString());
                tv_order.setText(desc1);
            }
        }
    }

    private OrderCReceiver orderCReceiver;
    private class OrderCReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getAction().equals(ORDER_ACTION)) {
                String desc = String.format("%s%s 接收器C收到一个谣言\n",
                        tv_order.getText().toString(), DateUtil.getNowTime());
                tv_order.setText(desc);
            }
        }
    }

    private OrderDReceiver orderDReceiver;
    private class OrderDReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getAction().equals(ORDER_ACTION)) {
                String desc = String.format("%s%s 接收器D收到一个谣言\n",
                        tv_order.getText().toString(), DateUtil.getNowTime());
                tv_order.setText(desc);
            }
        }
    }
}
