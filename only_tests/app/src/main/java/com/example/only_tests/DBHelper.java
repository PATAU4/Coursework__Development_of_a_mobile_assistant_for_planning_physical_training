package com.example.only_tests;


import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


public class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context) {
            super(context, "test_table", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL("create table test_table (id integer primary key autoincrement, login text, password text);");
        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

    }
