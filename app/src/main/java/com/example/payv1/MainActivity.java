package com.example.payv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this,elegirsitienecuenta.class);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                startActivity(intent);
                finish();
            }
        };
        // tiempo de espera
        Timer tiempo = new Timer();
        tiempo.schedule(tarea,1500);


    }

    private class Window {
    }
}

