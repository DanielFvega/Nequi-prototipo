package com.example.payv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class elegirsitienecuenta extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elegirsitienecuenta);

     }
    public void entrar1 (View view){
        Intent i = new Intent(elegirsitienecuenta.this, login.class );
        startActivity(i);
    }
    public void registrar (View view) {
        Intent i = new Intent(this,registrar.class);
        startActivity(i);

    }
    //botones de contactanos

    public void facebook (View view) { Uri uri = Uri.parse("https://www.facebook.com");
    Intent intent = new Intent(Intent.ACTION_VIEW, uri);overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    startActivity(intent);}
    public void gamil (View view) { Uri uri = Uri.parse("https://mail.google.com/mail/u/0/#inbox");
    Intent intent = new Intent(Intent.ACTION_VIEW, uri);overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    startActivity(intent);}
    public void youtube (View view) { Uri uri = Uri.parse("https://www.youtube.com/");
    Intent intent = new Intent(Intent.ACTION_VIEW, uri);overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    startActivity(intent); }




}