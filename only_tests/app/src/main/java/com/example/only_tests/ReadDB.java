package com.example.only_tests;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ReadDB extends AppCompatActivity implements View.OnClickListener {
    Button button;
    EditText etName;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_db);


        button = findViewById(R.id.button);


        button.setOnClickListener(this);
        dbHelper = new DBHelper(this);
    }

    @Override
    public void onClick(View v) {


        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Более сложная операция - это чтение из базы. Для этого нам понадобится промежуточный объект - курсор. Именно его возвращает метод запроса
        Cursor c = db.query("test_table", null, null, null, null, null, null);
        switch (v.getId()) {
            case R.id.button:
                int idColIndex = c.getColumnIndex("id");
                int loginColIndex = c.getColumnIndex("login");
                int passwordColIndex = c.getColumnIndex("password");
                if (c.moveToFirst()) {
                    do {
                        Log.d("LOG_TAG",
                                "ID = " + c.getInt(idColIndex) +
                                        ", login = " + c.getString(loginColIndex) +
                                        ", password = " + c.getString(passwordColIndex));
                    } while (c.moveToNext());
                } else
                    Log.d("Table is empty!", "0 rows, 0 columns with data");
                c.close();
                break;
            default:
                if (db != null) {
                    db.close();
                }
                break;
        }

    }
}