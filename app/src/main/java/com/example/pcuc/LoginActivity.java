package com.example.pcuc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.example.pcuc.Common.EncDec;
import com.example.pcuc.Common.EncDec.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {



    SharedPreferences setting;//,setting2;
    SharedPreferences.Editor editor;//,editor2;

    //하드코딩--------------tldlqkf toRldi
    //String admin_id = "admin";
    //String admin_pw = "1234";

    //로그인 버튼 선언
    Button button_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final CheckBox id_save_checkBox = findViewById(R.id.id_save_checkBox);
        final CheckBox auto_login_checkBox = findViewById(R.id.auto_login_checkBox);

        final EditText idText = findViewById(R.id.idText);
        final EditText passwordText = findViewById(R.id.passwordText);

        button_login = findViewById(R.id.loginButton);

        //자동 로그인 정보 저장
        setting = getSharedPreferences("setting",MODE_PRIVATE);
        editor = setting.edit();

        // 아이디저장 정보 저장
        //setting2 = getSharedPreferences("setting2",0);
        // editor2 = setting2.edit();

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(auto_login_checkBox.isChecked()){
                    String ID = idText.getText().toString();
                    String PW = passwordText.getText().toString();

                    editor.putString("ID",ID);
                    editor.putString("PW",PW);
                    editor.putBoolean("Auto_Login_enabled",true);
                    editor.apply();
                }

                if(id_save_checkBox.isChecked() && !auto_login_checkBox.isChecked()){
                    String ID_2 = idText.getText().toString();

                    editor.putString("ID",ID_2);
                    editor.putBoolean("ID_Check",true);
                    editor.apply();


                }



                String login_ID = idText.getText().toString();
                String login_PW = passwordText.getText().toString();

                //버튼 눌렀을 때 인텐트
                if(loginValidation(login_ID,login_PW)) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);

                    finish();



                }
                else {
                    Toast.makeText(LoginActivity.this, "ID 또는 비밀번호를 확인해 주세요.", Toast.LENGTH_LONG).show();
                }



            }
        });

        //체크박스 자동로그인
        id_save_checkBox.setOnClickListener(new CheckBox.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(auto_login_checkBox.isChecked()){
                    id_save_checkBox.setChecked(true);
                }



            }
        });
        //체크박스 아이디저장만
        auto_login_checkBox.setOnClickListener(new CheckBox.OnClickListener(){
            @Override
            public void onClick(View v) {
                id_save_checkBox.setChecked(true);

            }

        });
        //체크박스 풀렸을때 정보 삭제
        id_save_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(!isChecked){

                    editor.clear();
                    editor.commit();

                }

            }
        });
        //체크박스 풀렸을때 정보 삭제
        auto_login_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (!isChecked) {
                    editor.clear();
                    editor.commit();

                }
            }
        });

        //다시 앱켰을때 정보 불러오고 자동로그인
        if(setting.getBoolean("Auto_Login_enabled", false)){

            idText.setText(setting.getString("ID", ""));

            passwordText.setText(setting.getString("PW", ""));

            auto_login_checkBox.setChecked(true);
            id_save_checkBox.setChecked(true);

            //앱 다시 켰을 떄 자동로그인 인텐트
            if(loginValidation(Objects.requireNonNull(setting.getString("ID", "")).trim(),Objects.requireNonNull(setting.getString("PW", "")).trim())){
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                startActivity(intent);

                finish();
            }
            else {
                Toast.makeText(LoginActivity.this, "ID 또는 비밀번호를 확인해 주세요.", Toast.LENGTH_LONG).show();
            }




        }
        //아이디 저장만 눌렀을 때 정보 불러오기
        if(setting.getBoolean("ID_Check", false)){

            idText.setText(setting.getString("ID", ""));


            id_save_checkBox.setChecked(true);


        }

    }
    //로그인 확인
    private boolean loginValidation(String id, String pw){

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", id);
        paramMap.put("country",pw);
        CustomThread thread2 = new CustomThread(paramMap, "login.php");

        thread2.start();
        try {
            thread2.join();
        } catch (InterruptedException ignored) {
        }
        String result = thread2.Getresult();
        boolean login_result = !Objects.equals(result, "");
        String message = login_result? "로그인 성공":"ID 또는 비밀번호를 확인해 주세요.";
        thread2.interrupt();
        String test_tag = "EncDec";
        Log.d(test_tag,EncDec.hash("abc"));
        String Enc_text = EncDec.Encrypt("abc");
        Log.d(test_tag,Enc_text);
        Log.d(test_tag,EncDec.Decrypt(Enc_text));
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
        return login_result;
        /*
        if(login_result) {

        }
        else
            Toast.makeText(LoginActivity.this, , Toast.LENGTH_LONG).show();


        if(admin_id.equals(id) && admin_pw.equals(pw)){
            Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_LONG).show();
           return true;
        }

        else{
            Toast.makeText(LoginActivity.this, "ID 또는 비밀번호를 확인해 주세요.", Toast.LENGTH_LONG).show();

            return false;
        }*/

    }



}





















