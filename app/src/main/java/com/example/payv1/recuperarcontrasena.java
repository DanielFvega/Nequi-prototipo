package com.example.payv1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
//prueba de git
// puto
// jejeje
// mazorcon
// bueno pues ya
public class recuperarcontrasena extends AppCompatActivity {

    private Button btn_recuperar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperarcontrasena);
        btn_recuperar=findViewById(R.id.btn_recuperar);


        btn_recuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Toast.makeText(recuperarcontrasena.this,"Estamos trabajando en esta pantalla :)",Toast.LENGTH_SHORT).show();

            }
        });
    }
}