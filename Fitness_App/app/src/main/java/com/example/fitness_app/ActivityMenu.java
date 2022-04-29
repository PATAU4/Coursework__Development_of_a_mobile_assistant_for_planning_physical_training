package com.example.fitness_app;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import android.widget.AdapterView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class ActivityMenu extends AppCompatActivity implements View.OnClickListener {
    TextView enteredName;
    Button mainButton;
    TextView mainTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mainButton = (Button) findViewById(R.id.ownVeight);
        mainButton.setOnClickListener(this);


        // Принятие данных пользователя из первой активности ( в данном случае передется только имя )
        String name = getIntent().getStringExtra("name");
        enteredName = (TextView) findViewById(R.id.enteredName);
        mainTextView = (TextView) findViewById(R.id.mainTextView);
        enteredName.setText(name);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(ActivityMenu.this, ActivityFitnes.class);
        startActivity(intent);
    }

}



