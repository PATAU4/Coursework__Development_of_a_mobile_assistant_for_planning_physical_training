package com.example.hypertext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Edit_Physical_Params extends AppCompatActivity implements View.OnClickListener {
    Button to_main, edit_data;
    EditText weight, height;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_physical_params);

        weight = findViewById(R.id.weight);
        height = findViewById(R.id.height);

        to_main = findViewById(R.id.to_main);
        to_main.setOnClickListener(this);

        edit_data = findViewById(R.id.edit_data);
        edit_data.setOnClickListener(this);

        dbHelper = new DBHelper(this);
    }

    @Override
    public void onClick(View v) {
        Integer intID = getIntent().getIntExtra("id", 0);
        String txtLogin = getIntent().getStringExtra("login");


        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Более сложная операция - это чтение из базы. Для этого нам понадобится промежуточный объект - курсор. Именно его возвращает метод запроса
        Cursor c = db.query("physical_params_table", null, null, null, null, null, null);


        int user_idColIndex = c.getColumnIndex("user_id");
        int weightColIndex = c.getColumnIndex("weight");
        int heightColIndex = c.getColumnIndex("height");



        switch (v.getId()) {
            case R.id.to_main:
                Intent to_main_intent = new Intent(Edit_Physical_Params.this, Physical_Params.class);
                to_main_intent.putExtra("id", intID);
                to_main_intent.putExtra("login", txtLogin);
                startActivity(to_main_intent);
                break;
            case R.id.edit_data:
                    if (weight.getText().length() < 1 && height.getText().length() < 1){
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Мало символов для внесения информации ", Toast.LENGTH_SHORT);
                        toast.show();
                    }else {
                        cv.put("user_id", intID);
                        cv.put("weight", Integer.parseInt(weight.getText().toString()));
                        cv.put("height", Integer.parseInt(height.getText().toString()));
                        db.insert("physical_params_table", null, cv);
                    }
                if (c.moveToFirst()) {
                    do {
                        System.out.println("user_id = " + c.getString(user_idColIndex) +
                        "; Вес = " + c.getInt(weightColIndex) +
                        "; Рост = " + c.getInt(heightColIndex) );
                    } while (c.moveToNext());

                }else
                    Log.d("users_logs is empty", "0 rows, 0 columns with data");
                c.close();
                break;
        }
    }
    public static class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context) {
            super(context, "physical_params_table", null, 1);
        }

        //метод используется один раз при создании базы данных.
        // В нем реализуется код, который создает схему данных и
        // при необходимости наполняет базу начальными данными
        @Override
        public void onCreate(SQLiteDatabase db) {
            // создаем таблицу с полями
            db.execSQL("create table physical_params_table (_id integer primary key autoincrement, user_id INTEGER, weight INTEGER, height INTEGER);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}