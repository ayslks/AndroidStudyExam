package com.cqust.exam2.activity;

import static com.cqust.exam2.activity.LoginActivity.hintKeyBoards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.cqust.exam2.R;
import com.cqust.exam2.bean.User;

public class RegisterActivity extends AppCompatActivity {

    private EditText newUserName;
    private EditText newUserPassword;
    private CheckBox CB_man;
    private CheckBox CB_woman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button bt_register = findViewById(R.id.bt_register);
        CB_man = findViewById(R.id.CB_man);
        CB_woman = findViewById(R.id.CB_woman);
        newUserName = findViewById(R.id.newUserName);
        newUserPassword = findViewById(R.id.newUserPassword);

        if (CB_man.isChecked()){
            CB_woman.setChecked(false);
        }
        if (CB_woman.isChecked()){
            CB_man.setChecked(true);
        }

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hintKeyBoards(view);
                String name = newUserName.getText().toString().trim();
                String password = newUserPassword.getText().toString().trim();
                String sex = "";
                if (CB_woman.isChecked()) sex = "女";
                if (CB_man.isChecked()) sex = "男";
                User newUser = new User(name, password, sex);
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                intent.putExtra("newUser", newUser);
                startActivity(intent);
                finish();
            }
        });

    }
}