package com.cqust.exam2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.cqust.exam2.R;

public class ShowOrderActivity extends AppCompatActivity {

    private ImageView picture_iv;
    private TextView context_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_order);
        initActivity();
        Intent intent = getIntent();
        String context = intent.getStringExtra("context");
        int pid = Integer.parseInt(intent.getStringExtra("pid"));
        switch (pid){
            case 1:
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.order1);
                picture_iv.setImageBitmap(bitmap);
                break;
            case 2:
                picture_iv.setImageResource(R.drawable.order2);
                break;
            case 3:
                picture_iv.setImageResource(R.drawable.order3);
                break;
            case 4:
                picture_iv.setImageResource(R.drawable.order4);
                break;
            case 5:
                picture_iv.setImageResource(R.drawable.order5);
                break;
            case 6:
                picture_iv.setImageResource(R.drawable.order6);
                break;
            case 7:
                picture_iv.setImageResource(R.drawable.order7);
                break;
            case 8:
                picture_iv.setImageResource(R.drawable.order8);
                break;
        }
        context_tv.setText(context);
    }

    private void initActivity(){
        picture_iv = findViewById(R.id.picture_iv);
        context_tv = findViewById(R.id.context_tv);
    }
}