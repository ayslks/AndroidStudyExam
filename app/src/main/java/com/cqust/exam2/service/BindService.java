package com.cqust.exam2.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class BindService extends Service {

    public BindService(){

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("lks","----------Service onBind----------");
        return new Binder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("lks","----------Service onCreate----------");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("lks","----------Service onUnbind----------");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("lks","----------Service onDestroy----------");
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }
}
