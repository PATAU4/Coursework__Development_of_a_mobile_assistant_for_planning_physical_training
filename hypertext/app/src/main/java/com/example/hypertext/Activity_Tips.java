package com.example.hypertext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Activity_Tips extends AppCompatActivity implements View.OnClickListener {
    Button to_main, to_next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        to_main = findViewById(R.id.to_main);
        to_main.setOnClickListener(this);

        to_next = findViewById(R.id.to_next);
        to_next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Integer intID = getIntent().getIntExtra("id", 0);
        String txtLogin = getIntent().getStringExtra("login");

        switch (v.getId()) {
            case R.id.to_main:
                Intent to_main_intent = new Intent(Activity_Tips.this, Manual_Activity.class);
                to_main_intent.putExtra("id", intID);
                to_main_intent.putExtra("login", txtLogin);
                startActivity(to_main_intent);
                break;
            case R.id.to_next:
                Intent to_next_intent = new Intent(Activity_Tips.this, Example_Of_Workouts.class);
                to_next_intent.putExtra("id", intID);
                to_next_intent.putExtra("login", txtLogin);
                startActivity(to_next_intent);
                break;

        }
    }
}