package com.example.hypertext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class Greetings_Activity extends AppCompatActivity implements Animation.AnimationListener {
    private TextView popup_text;
    private Animation animation = null;
    boolean animation_check = true;






/*   public static void wait(int ms)
    {
        try
        {
            Thread.sleep(ms);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greetings);

        Integer intID = getIntent().getIntExtra("id", 0);
        String txtLogin = getIntent().getStringExtra("login");

        popup_text = findViewById(R.id.popup_text);
        popup_text.setText("Добро пожаловать, " + txtLogin);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale);
        animation.setAnimationListener(this);
        popup_text.startAnimation(animation);

    }

    @Override
    public void onAnimationStart(Animation animation) {
        popup_text.setVisibility(View.VISIBLE);
    }
    @Override
    public void onAnimationEnd(Animation animation) {
        Integer intID = getIntent().getIntExtra("id", 0);
        String txtLogin = getIntent().getStringExtra("login");

        popup_text.setVisibility(View.INVISIBLE);
        Intent sign_in_intent = new Intent(Greetings_Activity.this, MainMenu.class);
        sign_in_intent.putExtra("id", intID);
        sign_in_intent.putExtra("login", txtLogin);
        startActivity(sign_in_intent);
    }
    @Override
    public void onAnimationRepeat(Animation animation) {
        popup_text.setVisibility(View.VISIBLE);
    }
}