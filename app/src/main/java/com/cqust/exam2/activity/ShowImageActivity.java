package com.cqust.exam2.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cqust.exam2.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ShowImageActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button button;
    private EditText editText;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        initActivity();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(ShowImageActivity.this)
                        .load(editText.getText().toString())
                        .fitCenter()
                        .into(imageView);
//                try {
//                    String str_http = editText.getText().toString();
//                    URL url = new URL(str_http);
//                    getBitmap(url);
//                } catch (MalformedURLException e) {
//                    throw new RuntimeException(e);
//                }
            }
        });

        handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if(msg.what == 1){
                    Bitmap bitmap = (Bitmap) msg.obj;
                    imageView.setImageBitmap(bitmap);
                }
            }
        };
    }

    void initActivity(){
        imageView = findViewById(R.id.show_img);
        button = findViewById(R.id.bt_showImg);
        editText = findViewById(R.id.et_http);
    }

    private void getBitmap(URL url){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setReadTimeout(5000);
                    int code = connection.getResponseCode();
                    InputStream ios = connection.getInputStream();

                    Bitmap bitmap = BitmapFactory.decodeStream(ios);
                    setImageView(bitmap);
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = bitmap;
                    handler.sendMessage(msg);

                    connection.disconnect();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    private void setImageView(Bitmap bitmap){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imageView.setImageBitmap(bitmap);
            }
        });
    }
}