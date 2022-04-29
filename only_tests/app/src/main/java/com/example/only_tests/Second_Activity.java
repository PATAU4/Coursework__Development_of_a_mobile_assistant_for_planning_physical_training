package com.example.only_tests;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.only_tests.R;

public class Second_Activity extends AppCompatActivity implements View.OnClickListener{
    Button create_table, read_table;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        create_table = findViewById(R.id.create_table);
        create_table.setOnClickListener(this);

        read_table = findViewById(R.id.read_table);
        read_table.setOnClickListener(this);






    }
    @Override
    public void onClick(View v) {
        String login = getIntent().getStringExtra("login");
        String password = getIntent().getStringExtra("password");
        switch (v.getId()) {
            case R.id.create_table:
                Intent create_intent = new Intent(Second_Activity.this, DBHelper.class);
                create_intent.putExtra("login", login);
                create_intent.putExtra("password", password);
                startActivity(create_intent);
                break;

            case R.id.read_table:
                Intent read_intent = new Intent(Second_Activity.this, ReadDB.class);
                startActivity(read_intent);
                break;
        }
    }
}