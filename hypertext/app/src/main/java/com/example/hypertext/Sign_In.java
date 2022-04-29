package com.example.hypertext;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;


public class Sign_In extends AppCompatActivity implements View.OnClickListener{

    Button login;
    EditText enter_password, enter_login;
    Sign_Up.DBHelper dbHelper;
    TextView create_acc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);


        create_acc = findViewById(R.id.create_acc);
        create_acc.setOnClickListener(this);

        login = findViewById(R.id.login);
        login.setOnClickListener(this);

        enter_login = findViewById(R.id.enter_login);
        enter_password = findViewById(R.id.enter_password);

        dbHelper = new Sign_Up.DBHelper(this);


    }
    @Override
    public void onClick(View v) {


        Map <Integer,String> dict_of_login_info = new HashMap<Integer, String>();
        Map <Integer,String> dict_of_password_info = new HashMap<Integer, String>();


        Integer login_id = null;
        Integer password_id = null;

        Integer user_id = -1;

        String login_data = enter_login.getText().toString();
        String password_data = enter_password.getText().toString();

        Boolean login_check = false;
        Boolean password_check = false;

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Более сложная операция - это чтение из базы. Для этого нам понадобится промежуточный объект - курсор. Именно его возвращает метод запроса
        Cursor c = db.query("users_logs", null, null, null, null, null, null);
        switch (v.getId()) {
            case R.id.login:

                /*Toast toast = Toast.makeText(getApplicationContext(),
                        " entered login = "+login_data, Toast.LENGTH_SHORT);
                toast.show();*/

                int idColIndex = c.getColumnIndex("id");
                int loginColIndex = c.getColumnIndex("login");
                int passwordColIndex = c.getColumnIndex("password");


                if (c.moveToFirst()) {
                    do {
                        Log.d("LOG_TAG",
                                "ID = " + c.getInt(idColIndex) +
                                        ", login = " + c.getString(loginColIndex) +
                                        ", password = " + c.getString(passwordColIndex));


                        dict_of_login_info.put( c.getInt(idColIndex), c.getString(loginColIndex));
                        dict_of_password_info.put( c.getInt(idColIndex), c.getString(passwordColIndex));


                    } while (c.moveToNext());

                } else
                    Log.d("users_logs is empty", "0 rows, 0 columns with data");
                c.close();




                for (Map.Entry<Integer, String> entry : dict_of_login_info.entrySet()) {
                        //System.out.println("ID =  " + entry.getKey() + " Логин = " + entry.getValue());

                        if (entry.getValue().equals(login_data)){
                            login_check = true;
                            login_id = entry.getKey();
                        }
                }

                for (Map.Entry<Integer, String> entry : dict_of_password_info.entrySet()) {


                    //System.out.println("ID =  " + entry.getKey() + " Пароль = " + entry.getValue());

                    if (entry.getValue().equals(password_data)){
                        password_check = true;
                        password_id = entry.getKey();
                    }
                    //System.out.println(password_check);
                }
                if ( login_check && password_check && login_id == password_id){

                    Intent sign_in_intent = new Intent(Sign_In.this, Greetings_Activity.class);

                    sign_in_intent.putExtra("id", login_id);
                    sign_in_intent.putExtra("login", login_data);
                    sign_in_intent.putExtra("password", password_data);

                    startActivity(sign_in_intent);
                }
                break;

            case R.id.create_acc:
                Intent sign_in_intent = new Intent(Sign_In.this, Sign_Up.class);
                startActivity(sign_in_intent);
                break;

            default:
                if (db != null) {
                    db.close();
                }
                break;
        }
    }
}



