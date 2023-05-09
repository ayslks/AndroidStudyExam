package com.cqust.exam2.service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.cqust.exam2.R;

public class TestBindServiceActivity extends AppCompatActivity {

    private Button bt1,bt2;
    ServiceConnection connection = new myServiceConnection();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_bind_service);
        findViewById(R.id.bt_bind).setOnClickListener(this::onClick);
        findViewById(R.id.bt_unbind).setOnClickListener(this::onClick);
    }

    public void onClick(View view){
        Intent intent = new Intent(this, BindService.class);

        switch (view.getId()){
            case R.id.bt_bind:
                Log.i("lks","---------bind_button-----------");
                bindService(intent, connection, BIND_AUTO_CREATE);
                break;
            case R.id.bt_unbind:
                Log.i("lks","---------unbind_button-----------");
                unbindService(connection);
                connection = null;
                break;
        }
    }

    class myServiceConnection implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i("lks","--------onServiceConnected-----componentName:"+componentName+"------------");
            Log.i("lks","--------iBinder:"+iBinder+"------------");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i("lks","--------onServiceDisconnected----componentName:"+componentName+"------------");
        }
    }
}