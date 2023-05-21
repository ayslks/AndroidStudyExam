package com.cqust.exam2.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.FragmentManager;
import android.os.Bundle;

import com.cqust.exam2.R;
import com.cqust.exam2.fragment.Fragment1;

public class OrderActivity extends FragmentActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

    }


}