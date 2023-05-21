package com.cqust.exam2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cqust.exam2.R;
import com.cqust.exam2.bean.User;

public class LoginActivity extends AppCompatActivity {

    private EditText ET_userName;
    private EditText ET_password;
    private CheckBox remember_ck_password;
    private CheckBox remember_ck_username;
    private Button bt_login;
    private Button bt_signup;
    private CheckBox ck_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("nowActivity:", "Login--onCreate");
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        initView();
        readSP();

        Intent intent_fromRegister = getIntent();
        User newUser=(User)intent_fromRegister.getSerializableExtra("newUser");
        if(newUser!=null){
            Toast.makeText(this, "注册成功！",Toast.LENGTH_SHORT).show();
            ET_userName.setText(newUser.getUserName());
        }

        bt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();//销毁此Activity
            }
        });

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hintKeyBoards(view);
                String name = ET_userName.getText().toString().trim();
                String password = ET_password.getText().toString().trim();
                if(name.equals("2020440493") && password.equals("123456")){
                    rememberNP(name, password);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    User user = new User(name, password);
                    intent.putExtra("userName", user);
                    startActivity(intent);
                    finish();//销毁此Activity
                }else {
                    Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                    LinearLayout fatherLL = findViewById(R.id.fatherLL);
                    TextView noteTV = new TextView(LoginActivity.this);
                    noteTV.setText(getResources().getText(R.string.login_failed));
                    noteTV.setTextColor(getResources().getColor(R.color.red));
                    noteTV.setTextSize(18);
                    fatherLL.addView(noteTV);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("nowActivity:", "Login--onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("nowActivity:", "Login--onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("nowActivity:", "Login--onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("nowActivity:", "Login--onDestroy");
    }

    public static void hintKeyBoards(View view) {
        InputMethodManager manager = ((InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE));
        if (manager != null) {
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void initView(){
        bt_login = findViewById(R.id.login);
        ET_userName = findViewById(R.id.inputUsername);
        ET_password = findViewById(R.id.inputPassword);
        bt_signup = findViewById(R.id.signup);
        remember_ck_username = findViewById(R.id.ck_remember_username);
        remember_ck_password = findViewById(R.id.ck_remember_password);
        ck_login = findViewById(R.id.ck_login);
    }

    private void readSP(){
        SharedPreferences sp = getSharedPreferences("remember_data", MODE_PRIVATE);
        String username = sp.getString("remember_username", "");
        String password = sp.getString("remember_password", "");
        if(!username.equals("")){
            remember_ck_username.setChecked(true);
            if(!password.equals("")){
                remember_ck_password.setChecked(true);
            }
        }
        ET_userName.setText(username);
        ET_password.setText(password);
        if(sp.getBoolean("自动登录:", true)){
            ck_login.setChecked(true);
            Handler handler = new Handler();
            Toast.makeText(LoginActivity.this, "自动登录中", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(LoginActivity.this, EmptyActivity.class);
            startActivity(intent);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    toLogin();
                }
            },5000);
        }
    }

    private void rememberNP(String name, String password){
        SharedPreferences sp = getSharedPreferences("remember_data", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if(remember_ck_username.isChecked()){
            editor.putString("remember_username", name);
            if(remember_ck_password.isChecked()){
                editor.putString("remember_password", password);
            }else{
                remember_ck_password.setChecked(false);
                editor.remove("remember_password");
            }
            editor.commit();
        }else{
            editor.remove("remember_username");
            editor.remove("remember_password");
            editor.clear();
            editor.commit();
        }
        if(ck_login.isChecked()){
            editor.putBoolean("自动登录:", true);
        }else {
            editor.putBoolean("自动登录:", false);
        }
        editor.commit();
    }

    private void toLogin(){
        String name = ET_userName.getText().toString().trim();
        String password = ET_password.getText().toString().trim();

        if(name.equals("2020440493") && password.equals("123456")){
            rememberNP(name, password);
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            User user = new User(name, password);
            intent.putExtra("userName", user);
            startActivity(intent);
            finish();//销毁此Activity
        }else {
            Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
            LinearLayout fatherLL = findViewById(R.id.fatherLL);
            TextView noteTV = new TextView(LoginActivity.this);
            noteTV.setText(getResources().getText(R.string.login_failed));
            noteTV.setTextColor(getResources().getColor(R.color.red));
            noteTV.setTextSize(18);
            fatherLL.addView(noteTV);
        }
    }
}