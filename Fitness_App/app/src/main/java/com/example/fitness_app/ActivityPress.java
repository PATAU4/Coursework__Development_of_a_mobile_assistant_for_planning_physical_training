package com.example.fitness_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityPress extends AppCompatActivity implements View.OnClickListener {
    Button runBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_press);

        runBtn = findViewById(R.id.runBtn);
        runBtn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.runBtn:
                Intent intent = new Intent(ActivityPress.this, ActivityMetInfo.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}