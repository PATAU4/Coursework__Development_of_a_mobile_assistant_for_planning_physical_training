package com.example.hypertext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Fitness_Activity extends AppCompatActivity implements View.OnClickListener {
    ImageView manual_page, home_page, fitness_page, menu_of_training;
    Button new_train, delete_table_training;
    Creating_Train_Activity.DBHelper dbHelper;
    TextView select_training, selected_training, info;
    ArrayList<String> list_of_trainings = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness);



        manual_page = findViewById(R.id.manual_page);
        manual_page.setOnClickListener(this);

        home_page = findViewById(R.id.home_page);
        home_page.setOnClickListener(this);

        fitness_page = findViewById(R.id.fitness_page);
        fitness_page.setOnClickListener(this);

        new_train = findViewById(R.id.new_train);
        new_train.setOnClickListener(this);

        delete_table_training = findViewById(R.id.delete_table_training);
        delete_table_training.setOnClickListener(this);


        dbHelper = new Creating_Train_Activity.DBHelper(this);


        select_training = (TextView) findViewById(R.id.select_training);
        selected_training = (TextView) findViewById(R.id.selected_training);
        menu_of_training = (ImageView) findViewById(R.id.menu_of_training);
        info = (TextView) findViewById(R.id.info);

        // для tvColor и tvSize необходимо создавать контекстное меню
        registerForContextMenu(menu_of_training);
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
            case R.id.manual_page:
                Intent manual_intent = new Intent(Fitness_Activity.this, Manual_Activity.class);
                manual_intent.putExtra("id", intID);
                manual_intent.putExtra("login", txtLogin);
                startActivity(manual_intent);
                break;
            case R.id.home_page:
                Intent fitness_intent = new Intent(Fitness_Activity.this, MainMenu.class);
                fitness_intent.putExtra("id", intID);
                fitness_intent.putExtra("login", txtLogin);
                startActivity(fitness_intent);
                break;
            case R.id.new_train:
                Intent new_train_intent = new Intent(Fitness_Activity.this, Creating_Train_Activity.class);
                new_train_intent.putExtra("id", intID);
                new_train_intent.putExtra("login", txtLogin);
                startActivity(new_train_intent);
                break;
            case R.id.delete_table_training:
                db.delete("users_training_table", null, null);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.menu_of_training:



                Integer intID = getIntent().getIntExtra("id", 0);
                ContentValues cv = new ContentValues();
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                // Более сложная операция - это чтение из базы. Для этого нам понадобится промежуточный объект - курсор. Именно его возвращает метод запроса

                Cursor c = db.query("users_training_table",
                        null,
                        "user_id = ?",
                        new String[] {Integer.toString(intID)},
                        null, null, null);

                int idColIndex = c.getColumnIndex("workout_id");
                int user_idColIndex = c.getColumnIndex("user_id");
                int loginColIndex = c.getColumnIndex("login");
                int training_nameColIndex = c.getColumnIndex("training_name");
                int exerciseColIndex = c.getColumnIndex("exercise");
                int number_of_approachesColIndex = c.getColumnIndex("number_of_approaches");
                int repetition_amountColIndex = c.getColumnIndex("repetition_amount");

                if (c.moveToFirst()) {
                    do {
                        System.out.println(c.getString(training_nameColIndex));
                        list_of_trainings.add(c.getString(training_nameColIndex));
                    } while (c.moveToNext());
                    System.out.println(list_of_trainings);
                }else
                    Log.d("users_logs is empty", "0 rows, 0 columns with data");
                    c.close();


                Set<String> set = new HashSet<>(list_of_trainings);
                // чистим коллекцию
                list_of_trainings.clear();
                // помещаем из коллекции сета обратно в вашу коллекцию,
                // которая на данный момент ещё пустая
                list_of_trainings.addAll(set);

                for (int k=0; k<list_of_trainings.size();k++){
                    menu.add(0, k, 0, list_of_trainings.get(k));
                }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        for (int i=0; i < list_of_trainings.size(); i++){
            if ( item.getItemId() == i ){
                selected_training.setTextColor(Color.RED);
                selected_training.setTextSize(26);
                selected_training.setText(list_of_trainings.get(i));


                Cursor cursor = db.query("users_training_table",
                        new String[] {"training_name", "exercise", "number_of_approaches", "repetition_amount"},
                        "training_name = ?",
                        new String[] {list_of_trainings.get(i)},
                        null, null, null);

                int exerciseColIndex = cursor.getColumnIndex("exercise");
                int number_of_approachesColIndex = cursor.getColumnIndex("number_of_approaches");
                int repetition_amountColIndex = cursor.getColumnIndex("repetition_amount");


                info.setTextColor(Color.WHITE);
                info.setTextSize(20);

                String added_train = "";
                if (cursor.moveToFirst()) {
                    do {
                        added_train += "Упражнение: "+cursor.getString(exerciseColIndex)+"  "+ "\n"+
                                "   "+cursor.getString(number_of_approachesColIndex)+"  "+ "\n"+
                                "   "+cursor.getString(repetition_amountColIndex) +"  "+"\n";
                    } while (cursor.moveToNext());
                    info.setText(added_train);
                }else
                {
                    cursor.close();
                }
            }
        }
        return super.onContextItemSelected(item);
    }
}

