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
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {

    ImageView manual_page, home_page, fitness_page, image;
    TextView popup_text;
    ListView mainListView;
    Button clear;
//#232322 #3D4343
    DBHelper dbHelper;

    ArrayAdapter mArrayAdapter;
    List<String> mNameList = new ArrayList<>();
    int n = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_menu);




        mainListView = findViewById(R.id.mainListView);

        manual_page = findViewById(R.id.manual_page);
        manual_page.setOnClickListener(this);

        home_page = findViewById(R.id.home_page);
        home_page.setOnClickListener(this);

        fitness_page = findViewById(R.id.fitness_page);
        fitness_page.setOnClickListener(this);

        clear = findViewById(R.id.clear);
        clear.setOnClickListener(this);

        popup_text = findViewById(R.id.popup_text);

        mArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                mNameList);

        mainListView.setAdapter(mArrayAdapter);


        CalendarView calendarView = findViewById(R.id.calendarView);

        dbHelper = new DBHelper(this);




        Integer intID = getIntent().getIntExtra("id", 0);

        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Более сложная операция - это чтение из базы. Для этого нам понадобится промежуточный объект - курсор. Именно его возвращает метод запроса

        Cursor c = db.query("dates",
                new String[] {"user_id", "date"},
                "user_id = ?",
                new String[] {Integer.toString(intID)},
                null, null, null);


        int idColIndex = c.getColumnIndex("_id");
        int user_idColIndex = c.getColumnIndex("user_id");
        int dateColIndex = c.getColumnIndex("date");

        if (c.moveToFirst()) {
            do {
                mNameList.add(c.getString(dateColIndex));
                mArrayAdapter.notifyDataSetChanged();
            } while (c.moveToNext());
        }else
        {
            c.close();
        }




        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year,
                                            int month, int dayOfMonth) {


                Integer intID = getIntent().getIntExtra("id", 0);
                String txtLogin = getIntent().getStringExtra("login");
                ContentValues cv = new ContentValues();
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                int mYear = year;
                int mMonth = month;
                int mDay = dayOfMonth;
                String selectedDate = new StringBuilder()
                        .append(mDay)
                        .append("-")
                        .append(mMonth + 1)
                        .append("-").append(mYear)
                        .append(" ").toString();
                Toast.makeText(getApplicationContext(), selectedDate, Toast.LENGTH_LONG).show();

                mNameList.add(selectedDate);
                //mNameList.add(R.array.dates);
                mArrayAdapter.notifyDataSetChanged();

                cv.put("user_id", intID);
                cv.put("date", selectedDate);
                db.insert("dates", null, cv);
            }
        });








        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                System.out.println(mNameList.get(position));
                //mNameList.set(position, "GIGACHAD ABOBA");
                Log.d("LOG_TAG", "itemClick: position = " + position + ", id = "
                        + id);
                //mArrayAdapter.notifyDataSetChanged();
            }
        });
    }
    @Override
    public void onClick(View v) {
        Integer intID = getIntent().getIntExtra("id", 0);
        String txtLogin = getIntent().getStringExtra("login");


        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Более сложная операция - это чтение из базы. Для этого нам понадобится промежуточный объект - курсор. Именно его возвращает метод запроса
        Cursor c = db.query("dates", null, null, null, null, null, null);

        int idColIndex = c.getColumnIndex("_id");
        int user_idColIndex = c.getColumnIndex("user_id");
        int dateColIndex = c.getColumnIndex("date");

        switch (v.getId()) {
            case R.id.manual_page:
                Intent manual_intent = new Intent(MainMenu.this, Manual_Activity.class);
                manual_intent.putExtra("id", intID);
                manual_intent.putExtra("login", txtLogin);
                startActivity(manual_intent);
                break;
            case R.id.fitness_page:
                Intent fitness_intent = new Intent(MainMenu.this, Fitness_Activity.class);
                fitness_intent.putExtra("id", intID);
                fitness_intent.putExtra("login", txtLogin);
                startActivity(fitness_intent);
                break;
            case R.id.clear:
                if (c.moveToFirst()) {
                    do {
                        Log.d("LOG_TAG",
                                "ID = " + c.getInt(idColIndex) +
                                        ", user_id = " + c.getString(user_idColIndex) +
                                        ", date = " + c.getString(dateColIndex));
                    } while (c.moveToNext());
                } else
                {
                    Log.d("users_logs is empty", "0 rows, 0 columns with data");
                    c.close();
                }

                db.delete("dates", null, null);

                mNameList.clear();
                mArrayAdapter.notifyDataSetChanged();
                break;
        }
    }
    public static class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context) {
            super(context, "dates", null, 1);
        }

        //метод используется один раз при создании базы данных.
        // В нем реализуется код, который создает схему данных и
        // при необходимости наполняет базу начальными данными
        @Override
        public void onCreate(SQLiteDatabase db) {
            // создаем таблицу с полями
            db.execSQL("create table  dates(_id integer primary key autoincrement, user_id INTEGER, date TEXT);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}