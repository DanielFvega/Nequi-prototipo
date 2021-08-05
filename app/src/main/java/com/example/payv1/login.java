package com.example.payv1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;


public class login extends AppCompatActivity {
    //variables


    Button btn_login;
    EditText et_email, et_pass;
    TextView et_recuperar,et_registrar;

    // base de datos
    AwesomeValidation awesomeValidation;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Llmamos a firebase y la validacion
        firebaseAuth = firebaseAuth.getInstance();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        // si el usuario pasa la validacion que valla a home
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        //Filtro el campo de editex que chequee si el formato correscponde a un correo valido
        awesomeValidation.addValidation(this,R.id.et_mail, Patterns.EMAIL_ADDRESS,R.string.invalid_mail);
        awesomeValidation.addValidation(this,R.id.et_pass, ".{6,}",R.string.invalid_password);


        btn_login = findViewById(R.id.btn_loginprincipal);
        et_email = findViewById(R.id.et_mail);
        et_pass = findViewById(R.id.et_pass);
        et_recuperar  = findViewById(R.id.et_recuperar);
        et_registrar  = findViewById(R.id.et_registrar);


        et_recuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(login.this,recuperarcontrasena.class);
                startActivity(i);
            }
        });

        // boton que me lleva al la actividad registrar

        et_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(login.this,registrar.class);
                startActivity(i);

            }
        });


       btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // si esta validado
                if(awesomeValidation.validate()){
                    // variables en get mail y pass
                    String mail = et_email.getText().toString();
                    String pass = et_pass.getText().toString();

                    // ecompletar email y password
                    firebaseAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                            //Es exitoso el login
                            if(task.isSuccessful()){
                                irhome();
                            }else  {
                                Intent i = new Intent(login.this,error.class);
                                startActivity(i);

                            }
                        }
                    });

                }
            } // final del onclick del login
        });

        // boton que me lleva al la actividad registrar

        et_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(login.this,registrar.class);
                startActivity(i);

            }
        });


    } // final del onCreate

    public void irhome (){
        Intent i = new Intent(login.this,home.class);
        // enviar algun dato
        i.putExtra( "mail",et_email.getText().toString());
        // NO CREAR NUEVAS TAREAS
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);

    }// final del onCreate



    }



   

