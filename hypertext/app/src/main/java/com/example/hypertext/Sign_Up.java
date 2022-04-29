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

import java.util.HashMap;
import java.util.Map;

public class Sign_Up extends AppCompatActivity implements View.OnClickListener {

    Button continue_reg, get_data, delete_db;
    EditText login_reg, pass_reg;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        continue_reg = findViewById(R.id.continue_reg);
        delete_db = findViewById(R.id.delete_db);
        get_data = findViewById(R.id.get_data);

        login_reg = findViewById(R.id.login_reg);
        pass_reg = findViewById(R.id.pass_reg);

        continue_reg.setOnClickListener(this);
        delete_db.setOnClickListener(this);
        get_data.setOnClickListener(this);

        dbHelper = new DBHelper(this);
    }

    @Override
    public void onClick(View v) {

        String login = login_reg.getText().toString();
        String password = pass_reg.getText().toString();

        boolean login_check = true;
        Integer login_id = null;

        Map<Integer,String> dict_of_login_info = new HashMap<Integer, String>();

        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Более сложная операция - это чтение из базы. Для этого нам понадобится промежуточный объект - курсор. Именно его возвращает метод запроса
        Cursor c = db.query("users_logs", null, null, null, null, null, null);
        switch (v.getId()){
            case R.id.continue_reg:

                if (login.length() > 5 && password.length() > 5 ) {


                    int idColIndex_check = c.getColumnIndex("id");
                    int loginColIndex_check = c.getColumnIndex("login");

                    if (c.moveToFirst()) {
                        do {
                            if (c.getString(loginColIndex_check).equals(login)){
                                Toast toast = Toast.makeText(getApplicationContext(),
                                        "Введенный логин равен логину из базы данных! ", Toast.LENGTH_SHORT);
                                toast.show();
                                login_check = false;
                            }

                        } while (c.moveToNext());
                    }
                    if (login_check == true){
                        cv.put("login", login);
                        cv.put("password", password);
                        db.insert("users_logs", null, cv);

                        login_check = false;

                        Intent sign_in_intent = new Intent(Sign_Up.this, Sign_In.class);
                        startActivity(sign_in_intent);
                                    }
                        }else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Минимальное количество символов в логине и пароле - 6! ", Toast.LENGTH_SHORT);
                            toast.show();
                               }
                break;

            case R.id.delete_db:
                db.delete("users_logs", null, null);
                break;

            case R.id.get_data:
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
                    Log.d("users_logs is empty", "0 rows, 0 columns with data");
                c.close();
                break;

            default:
                if ( db != null ) {
                    db.close();
                }
                break;
        }
    }
    //Для начала нам необходимо создать вспомогательный класс,
    // который будет инкапсулировать работу с базой данных
    public static class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context) {
            super(context, "users_logs", null, 1);
        }

        //метод используется один раз при создании базы данных.
        // В нем реализуется код, который создает схему данных и
        // при необходимости наполняет базу начальными данными
        @Override
        public void onCreate(SQLiteDatabase db) {
            // создаем таблицу с полями
            db.execSQL("create table users_logs (id integer primary key autoincrement, login TEXT, password text);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

}
