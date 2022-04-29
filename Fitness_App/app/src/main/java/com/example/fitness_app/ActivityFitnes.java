package com.example.fitness_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityFitnes extends AppCompatActivity implements View.OnClickListener {
    Button pressBtn;
    Button pressBtn_Table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitnes);
        pressBtn = findViewById(R.id.btnPress);
        pressBtn.setOnClickListener(this);

        pressBtn_Table = findViewById(R.id.btnTable);
        pressBtn_Table.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPress:
                Intent intent = new Intent(ActivityFitnes.this, ActivityPress.class);
                startActivity(intent);
                break;
            case R.id.btnTable:
                Intent intent2 = new Intent(ActivityFitnes.this, ActivityInfo.class);
                startActivity(intent2);
                break;
            default:
                break;
        }
    }
}