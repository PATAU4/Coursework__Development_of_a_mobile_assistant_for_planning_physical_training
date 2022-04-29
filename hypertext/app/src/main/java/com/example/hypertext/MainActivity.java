package com.example.hypertext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button login;
    Button reg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);

        reg = (Button) findViewById(R.id.reg);
        reg.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                Intent sign_in_intent = new Intent(MainActivity.this, Sign_In.class);
                startActivity(sign_in_intent);
                break;
            case R.id.reg:
                Intent sign_up_intent = new Intent(MainActivity.this, Sign_Up.class);
                startActivity(sign_up_intent);
                break;
            default:
                break;
        }
    }


}