package com.example.sql_db_test;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnAdd, btnDelete, btnRead;
    EditText etName;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        btnDelete = findViewById(R.id.btnDelete);
        btnRead = findViewById(R.id.btnRead);
        etName = findViewById(R.id.etName);
        btnAdd.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnRead.setOnClickListener(this);
        dbHelper = new DBHelper(this);
    }

    @Override
    public void onClick(View v) {

        String login = etName.getText().toString();


        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Более сложная операция - это чтение из базы. Для этого нам понадобится промежуточный объект - курсор. Именно его возвращает метод запроса
        Cursor c = db.query("study_db", null, null, null, null, null, null);
        switch (v.getId()){
            case R.id.btnAdd:
                cv.put("NAME", login);
                db.insert("study_db", null, cv);
                break;
            case R.id.btnDelete:
                db.delete("study_db", null, null);
                break;
            case R.id.btnRead:
                int idColIndex = c.getColumnIndex("id");
                int loginColIndex = c.getColumnIndex("NAME");
                if (c.moveToFirst()) {
                    do {
                        Log.d("LOG_TAG",
                                "ID = " + c.getInt(idColIndex) +
                                        ", Name = " + c.getString(loginColIndex));
                    } while (c.moveToNext());
                } else
                    Log.d("Table is empty!", "0 rows, 0 columns with data");
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
    public class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context) {
            super(context, "study_db", null, 1);
        }

        //метод используется один раз при создании базы данных.
        // В нем реализуется код, который создает схему данных и
        // при необходимости наполняет базу начальными данными
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL("CREATE TABLE  TABLE_NAME (_id INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

}