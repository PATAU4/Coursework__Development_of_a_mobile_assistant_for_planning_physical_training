package com.example.hypertext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Physical_Params extends AppCompatActivity implements View.OnClickListener {

    TextView info,info2;
    Button to_main, edit_data;

    Edit_Physical_Params.DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physical_params);




        Integer intID = getIntent().getIntExtra("id", 0);
        String txtLogin = getIntent().getStringExtra("login");

        to_main = findViewById(R.id.to_main);
        to_main.setOnClickListener(this);

        edit_data = findViewById(R.id.edit_data);
        edit_data.setOnClickListener(this);
        dbHelper = new Edit_Physical_Params.DBHelper(this);

        info = findViewById(R.id.info);
        info2 = findViewById(R.id.info2);

        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Более сложная операция - это чтение из базы. Для этого нам понадобится промежуточный объект - курсор. Именно его возвращает метод запроса
        Cursor c = db.query("physical_params_table",
                new String[] {"user_id, weight", "height"},
                "user_id = ?",
                new String[] {intID.toString()},
                null, null, null);

        //Cursor c = db.query("physical_params_table", null, null, null, null, null, null);

        int user_idColIndex = c.getColumnIndex("user_id");
        int weightColIndex = c.getColumnIndex("weight");
        int heightColIndex = c.getColumnIndex("height");

        if (c.moveToFirst()) {
            do {
                System.out.println("user_id = " + c.getString(user_idColIndex) +
                        "; weight = " + c.getInt(weightColIndex) +
                        "; height = " + c.getInt(heightColIndex) );

                info.setText("weight = " + c.getInt(weightColIndex) + "; height = " + c.getInt(heightColIndex));
                //info2.setText(""+ (Math.pow( (c.getInt(heightColIndex)/100), 2)) );
                double param = c.getInt(weightColIndex) / Math.pow((c.getInt(heightColIndex)/100.0),2);
                info.setText("weight = " + c.getInt(weightColIndex) + "; height = " + c.getInt(heightColIndex));
                if (param < 16){
                    info2.setText("У вас выраженный дефицит массы тела, ешьте больше белков и сложных углеводов. \nНачинайте интенсивно тренироваться");
                }
                else if (param > 18.5 && param < 16){
                    info2.setText("У вас недостаточная масса тела, но ничего страшного.\n Правильная диета и дисциплина в тренировках, сделает вас красавчиком!");
                }
                else if (param > 18.5 && param < 25){
                    info2.setText("Вы в чудесной форме!");
                }
                else if (param > 25 && param < 30){
                    info2.setText("У вас избыточная масса тела, но ничего страшного.\n Правильная диета и дисциплина в тренировках, сделает вас красавчиком!");
                }
                else if (param > 35 && param < 40){
                    info2.setText("Скорее всего у вас ожирение первой степени =(\n Но ничего страшного! Ограниченные тренировки перерастут в полноценные\n И вы будете на седьмом небе от счаться!");
                }
                else if (param > 40){
                    info2.setText("Скорее всего у вас ожирение второй и более степени =(\n Но ничего страшного! Ограниченные тренировки перерастут в полноценные\n И вы будете на седьмом небе от счаться!");
                }

            } while (c.moveToNext());

        }else
            Log.d("users_logs is empty", "0 rows, 0 columns with data");
        c.close();

    }

    @Override
    public void onClick(View v) {

        Integer intID = getIntent().getIntExtra("id", 0);
        String txtLogin = getIntent().getStringExtra("login");

        switch (v.getId()) {
            case R.id.to_main:
                Intent to_main_intent = new Intent(Physical_Params.this, Manual_Activity.class);
                to_main_intent.putExtra("id", intID);
                to_main_intent.putExtra("login", txtLogin);
                startActivity(to_main_intent);
                break;
            case R.id.edit_data:
                Intent edit_data_intent = new Intent(Physical_Params.this, Edit_Physical_Params.class);
                edit_data_intent.putExtra("id", intID);
                edit_data_intent.putExtra("login", txtLogin);
                startActivity(edit_data_intent);
                break;
        }
    }
}