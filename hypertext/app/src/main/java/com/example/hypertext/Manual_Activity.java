package com.example.hypertext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Manual_Activity extends AppCompatActivity implements View.OnClickListener {

    ImageView manual_page, home_page, fitness_page;
    Button tips, physical_params;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);

        manual_page = findViewById(R.id.manual_page);
        manual_page.setOnClickListener(this);

        home_page = findViewById(R.id.home_page);
        home_page.setOnClickListener(this);

        fitness_page = findViewById(R.id.fitness_page);
        fitness_page.setOnClickListener(this);

        physical_params = findViewById(R.id.physical_params);
        physical_params.setOnClickListener(this);

        tips = findViewById(R.id.tips);
        tips.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Integer intID = getIntent().getIntExtra("id", 0);
        String txtLogin = getIntent().getStringExtra("login");

        switch (v.getId()) {
            case R.id.fitness_page:
                Intent manual_intent = new Intent(Manual_Activity.this, Fitness_Activity.class);
                manual_intent.putExtra("id", intID);
                manual_intent.putExtra("login", txtLogin);
                startActivity(manual_intent);
                break;
            case R.id.home_page:
                Intent fitness_intent = new Intent(Manual_Activity.this, MainMenu.class);
                fitness_intent.putExtra("id", intID);
                fitness_intent.putExtra("login", txtLogin);
                startActivity(fitness_intent);
                break;
            case R.id.physical_params:
                Intent physical_intent = new Intent(Manual_Activity.this, Physical_Params.class);
                physical_intent.putExtra("id", intID);
                physical_intent.putExtra("login", txtLogin);
                startActivity(physical_intent);
                break;
            case R.id.tips:
                Intent tips_intent = new Intent(Manual_Activity.this, Activity_Tips.class);
                tips_intent.putExtra("id", intID);
                tips_intent.putExtra("login", txtLogin);
                startActivity(tips_intent);
                break;

        }
    }
}