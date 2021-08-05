package com.example.payv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class error extends AppCompatActivity {

    Button btn_intentadenuevo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);

        btn_intentadenuevo=findViewById(R.id.btn_intentadenuevo);
    }

        public void intentar(View view) {
            Intent i = new Intent(error.this,login.class);
            startActivity(i);

        }
    }
