package com.example.only_tests;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.only_tests.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextPassword, editTextText;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextPassword = findViewById(R.id.editTextPassword);
        editTextText = findViewById(R.id.editTextText);

        button = findViewById(R.id.button);

        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        String login = editTextText.getText().toString();
        String password = editTextPassword.getText().toString();

        switch (v.getId()) {
            case R.id.button:
                Intent intent = new Intent(MainActivity.this, Second_Activity.class);
                intent.putExtra("login", login);
                intent.putExtra("password", password);

                // показываем новое Activity
                startActivity(intent);
                break;
        }
    }
}
