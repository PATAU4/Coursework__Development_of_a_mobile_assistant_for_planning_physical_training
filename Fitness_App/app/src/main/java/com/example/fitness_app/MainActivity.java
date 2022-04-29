package com.example.fitness_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



//public class MainActivity extends AppCompatActivity implements View.OnClickListener {
public class MainActivity extends AppCompatActivity {

    Spinner getSpinner;  // пол
    EditText editName;   // имя
    Button enteredButton;// кнопка первой авторизации
    String name = "";
    String sex = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Блок EditText (Обработка введеного имени)
        editName = (EditText) findViewById(R.id.etName);


        // Блок Button (Обработка перехода на новую активность АВТОРИЗОВАННОГО ПОЛЬЗОВАТЕЛЯ (enteredActivity) )
        enteredButton = (Button) findViewById(R.id.btnSave);
        enteredButton.setOnClickListener(clickListener_btnSave);

        // Блок Спиннера (Выбор пола)
        getSpinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getSpinner.setAdapter(adapter);

    }


    View.OnClickListener clickListener_btnSave = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            //if (name == "" & sex == ""){

                    //while (  name != "" & sex != "" ){
                        switch (v.getId()) {

                        case R.id.btnSave:
                            String result_name = editName.getText().toString();
                            SharedPreferences.Editor ed = getPreferences(MODE_PRIVATE).edit();


                            // в переменной name храниться информация с активности авторизации ( имя + пол )
                            // result_name - это имя; getSpinner - это пол.
                            name = result_name;
                            sex = getSpinner.getSelectedItem().toString();

                            // блок записи информации в файл общих настоек ведроида
                            ed.putString("name", name);
                            ed.putString("sex", sex);
                            ed.commit();
                            Log.i("SPREF", name);
                            Log.i("SPREF", sex);

                            // блок чтения информации из файла общин настоек
                            SharedPreferences pref = getPreferences(MODE_PRIVATE);
                            name = pref.getString("name", "");
                            Toast.makeText(getApplicationContext(), "Здравствуйте, " + name, Toast.LENGTH_LONG).show();


                            // запуск новой активности
                            Intent intent = new Intent(MainActivity.this, ActivityMenu.class);
                            intent.putExtra("name", name);
                            intent.putExtra("sex", sex);
                            startActivity(intent);
                            break;

                            default:
                                break;
                        }
                }
            /*}else{
                Intent intent = new Intent(MainActivity.this, EnteredActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("sex", sex);
                startActivity(intent);
             */
    };
}