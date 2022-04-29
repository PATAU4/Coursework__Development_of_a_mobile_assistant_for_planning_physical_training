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
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Array;
import java.util.ArrayList;

public class Creating_Train_Activity extends AppCompatActivity implements View.OnClickListener{
    Button create_train;
    EditText name_train, train1, number_of_approaches_1, repetition_amount_1;

    ImageView go_back, go_next;
    ListView mainListView;

    ArrayAdapter mArrayAdapter;
    ArrayList mNameList = new ArrayList();



    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creating_train);

        name_train = findViewById(R.id.name_train);
        train1 = findViewById(R.id.train1);
        repetition_amount_1 = findViewById(R.id.repetition_amount_1);
        number_of_approaches_1 = findViewById(R.id.number_of_approaches_1);

        mainListView = findViewById(R.id.mainListView);

        create_train = findViewById(R.id.create_train);
        create_train.setOnClickListener(this);

        go_back = findViewById(R.id.go_back);
        go_back.setOnClickListener(this);

        go_next = findViewById(R.id.go_next);
        go_next.setOnClickListener(this);


        mArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                mNameList);

        mainListView.setAdapter(mArrayAdapter);

        dbHelper = new DBHelper(this);
    }
    @Override
    public void onClick(View v) {
        Integer intID = getIntent().getIntExtra("id", 0);
        String txtLogin = getIntent().getStringExtra("login");


        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Более сложная операция - это чтение из базы. Для этого нам понадобится промежуточный объект - курсор. Именно его возвращает метод запроса
        Cursor c = db.query("users_training_table", null, null, null, null, null, null);
        switch (v.getId()) {
            case R.id.go_back:
                Intent manual_intent = new Intent(Creating_Train_Activity.this, Fitness_Activity.class);
                manual_intent.putExtra("id", intID);
                manual_intent.putExtra("login", txtLogin);
                startActivity(manual_intent);
                break;
            case R.id.go_next:
                for (int i=0; i<mNameList.size(); i++){
                    ArrayList return_values = new ArrayList();

                    //System.out.println(train1.getText().toString());
                    //System.out.println(number_of_approaches_1.getText().toString());
                    //System.out.println(repetition_amount_1.getText().toString());


                    System.out.println("Возвращаемое значение: ");
                    for (String retval : mNameList.get(i).toString().split(",")) {
                        return_values.add(retval);
                    }
                    System.out.println(return_values);
                    //System.out.println(return_values.get(0));

                    if (name_train.toString().length() < 3){
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Минимальное количесто символов в названии - 3! ", Toast.LENGTH_SHORT);
                        toast.show();
                    }else{
                        cv.put("user_id", intID);
                        cv.put("login", txtLogin);
                        cv.put("training_name", name_train.getText().toString());
                        cv.put("exercise", return_values.get(0).toString());
                        cv.put("number_of_approaches", return_values.get(1).toString());
                        cv.put("repetition_amount", return_values.get(2).toString());

                        db.insert("users_training_table", null, cv);
                        Intent fitness_intent = new Intent(Creating_Train_Activity.this, Fitness_Activity.class);
                        fitness_intent.putExtra("id", intID);
                        fitness_intent.putExtra("login", txtLogin);
                        startActivity(fitness_intent);
                        return_values.clear();
                    }
                }
                break;
            case R.id.create_train:
                if ( (name_train.getText().toString().length() > 2) && (train1.getText().toString().length() > 2) && (number_of_approaches_1.getText().toString().length() > 0) && (repetition_amount_1.getText().toString().length() > 0) ){
                    mNameList.add(train1.getText().toString()+", Подходов:"+number_of_approaches_1.getText().toString() +", Повторений:"+repetition_amount_1.getText().toString());
                    mArrayAdapter.notifyDataSetChanged();
                    System.out.println("ID "+intID);
                    train1.setText("");
                    number_of_approaches_1.setText("");
                    repetition_amount_1.setText("");
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Мало символов для создания упражнения ", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
        }
    }
    public static class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context) {
            super(context, "users_training_table", null, 1);
        }

        //метод используется один раз при создании базы данных.
        // В нем реализуется код, который создает схему данных и
        // при необходимости наполняет базу начальными данными
        @Override
        public void onCreate(SQLiteDatabase db) {
            // создаем таблицу с полями
            db.execSQL("create table users_training_table (workout_id integer primary key autoincrement, user_id INTEGER, login TEXT, training_name text,exercise text, number_of_approaches text, repetition_amount text);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}

