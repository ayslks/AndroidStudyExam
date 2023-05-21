package com.cqust.exam2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
                picture_iv.setImageResource(R.drawable.order1);
            case 2:
                picture_iv.setImageResource(R.drawable.order2);
            case 3:
                picture_iv.setImageResource(R.drawable.order3);
            case 4:
                picture_iv.setImageResource(R.drawable.order4);
            case 5:
                picture_iv.setImageResource(R.drawable.order5);
            case 6:
                picture_iv.setImageResource(R.drawable.order6);
            case 7:
                picture_iv.setImageResource(R.drawable.order7);
            case 8:
                picture_iv.setImageResource(R.drawable.order8);
        }
        context_tv.setText(context);

    }

    private void initActivity(){
        picture_iv = findViewById(R.id.picture_iv);
        context_tv = findViewById(R.id.context_tv);
    }
}