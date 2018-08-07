package com.example.applogin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText id_login;
    private EditText password_login;
    private ImageView avatar_login;
    private CheckBox rememberpassword_login;
    private CheckBox auto_login;
    private Button button_login;
    private SharedPreferences sp;
    private String idvalue;
    private String passwordvalue;
    private static final int PASSWORD_MIWEN = 0x81;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sp = this.getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE);
        //找到相应的布局及控件
        id_login = (EditText) findViewById(R.id.login_id);
        password_login = (EditText) findViewById(R.id.login_password);
        avatar_login = (ImageView) findViewById(R.id.login_avatar);
        rememberpassword_login = (CheckBox) findViewById(R.id.login_rememberpassword);
        auto_login = (CheckBox) findViewById(R.id.login_autologin);
        button_login = (Button) findViewById(R.id.login_button);

        if (sp.getBoolean("ischeck", false)) {
            rememberpassword_login.setChecked(true);
            id_login.setText(sp.getString("PHONEEDIT", ""));
            password_login.setText(sp.getString("PASSWORD", ""));
            //密文密码
            password_login.setInputType(PASSWORD_MIWEN);
            if (sp.getBoolean("auto_ischeck", false)) {
                auto_login.setChecked(true);
//                Intent intent = new Intent(LoginActivity.this, SecondActivity.class);
//                startActivity(intent);
                Toast.makeText(getApplicationContext(), "登录", Toast.LENGTH_SHORT).show();
            }
        }

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_login.getPaint().setFlags(0);
                idvalue = id_login.getText().toString();
                password_login.getPaint().setFlags(0);
                passwordvalue = password_login.getText().toString();


                if (idvalue.equals("123") && passwordvalue.equals("456")) {
                    if (rememberpassword_login.isChecked()) {
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("PHONEEDIT", idvalue);
                        editor.putString("PASSWORD", passwordvalue);
                        editor.commit();
                    }
//                    Intent intent = new Intent(LoginActivity.this, SecondActivity.class);
//                    startActivity(intent);
//                    finish();
                    Toast.makeText(getApplicationContext(), "登录", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "手机号码或密码错误，请重新登录", Toast.LENGTH_SHORT).show();
                }
            }
        });

        rememberpassword_login.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rememberpassword_login.isChecked()) {
                    Log.e("111", "记住密码已选中");
                    sp.edit().putBoolean("ischeck", true).commit();
                } else {
                    Log.e("111", "记住密码没有选中");
                    sp.edit().putBoolean("ischeck", false).commit();
                }
            }
        });

        auto_login.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (auto_login.isChecked()) {
                    Log.e("111", "自动登录已选中");
                    sp.edit().putBoolean("auto_ischeck", true).commit();
                } else {
                    Log.e("111", "自动登录没有选中");
                    sp.edit().putBoolean("auto_ischeck", false).commit();
                }
            }
        });

    }


}
