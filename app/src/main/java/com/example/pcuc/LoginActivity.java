package com.example.pcuc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    Button button_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        button_login = (Button)findViewById(R.id.loginButton);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //메인화면으로 전환
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                startActivity(intent);
            }
        });
    }
}
