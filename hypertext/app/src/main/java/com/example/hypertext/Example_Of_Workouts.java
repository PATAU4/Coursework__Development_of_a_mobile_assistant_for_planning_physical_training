package com.example.hypertext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Example_Of_Workouts extends AppCompatActivity implements View.OnClickListener {
    ImageView go_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example_of_workouts);


        go_back = findViewById(R.id.go_back);
        go_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Integer intID = getIntent().getIntExtra("id", 0);
        String txtLogin = getIntent().getStringExtra("login");

        switch (v.getId()) {
            case R.id.go_back:
                Intent go_back_intent = new Intent(Example_Of_Workouts.this, Activity_Tips.class);
                go_back_intent.putExtra("id", intID);
                go_back_intent.putExtra("login", txtLogin);
                startActivity(go_back_intent);
                break;
        }
    }
}