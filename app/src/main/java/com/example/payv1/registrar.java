package com.example.payv1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class registrar extends AppCompatActivity {

    EditText et_mail,et_pass,et_pass1;
    Button btn_registrar;
    //validacion y autenticacion con email
    AwesomeValidation awesomeValidation;
    FirebaseAuth firebaseAuth;
    DatabaseReference mDatabase;
    private int dineroinicial;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        //Llmamos a firebase y la validacion
        firebaseAuth = firebaseAuth.getInstance();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        //Filtro el campo de editex que chequee si el formato correscponde a un correo valido
        awesomeValidation.addValidation(this,R.id.et_mail, Patterns.EMAIL_ADDRESS,R.string.invalid_mail);
        awesomeValidation.addValidation(this,R.id.et_pass, ".{6,}",R.string.invalid_password);
        awesomeValidation.addValidation(this,R.id.et_pass1, ".{6,}",R.string.invalid_password);

        et_mail = findViewById(R.id.et_mail);
        et_pass = findViewById(R.id.et_pass);
        et_pass1= findViewById(R.id.et_pass1);
        //referencia del nodo principal
        mDatabase = FirebaseDatabase.getInstance().getReference();

        btn_registrar  = findViewById(R.id.btn_registrar);

        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = et_mail.getText().toString();
                String pass = et_pass.getText().toString();
                String pass1 = et_pass1.getText().toString();

                if(pass.equals(pass1)){


                // Condicion de completar y validar datos
                if(awesomeValidation.validate()){
                    firebaseAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                            //si sale bien me mande a una notficicaion
                            if(task.isSuccessful()){

                                dineroinicial = 1000000;

                                Map<String, Object> map = new HashMap<>();
                                map.put("email",mail);
                                map.put("password",pass1);
                                map.put("dineroinicial",dineroinicial);
                                String id = firebaseAuth.getCurrentUser().getUid();
                                mDatabase.child("User").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull @NotNull Task<Void> task2) {
                                        if(task2.isSuccessful()){
                                            Toast.makeText(registrar.this,"Usuario creado",Toast.LENGTH_SHORT).show();
                                            // enviar a pantalla de iniciar session
                                            Intent i = new Intent(registrar.this,login.class);
                                            startActivity(i);
                                            finish();

                                        }else {
                                            Toast.makeText(registrar.this,"No se pudo crear el usuario",Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });

                               }else{// errores
                                // catupta el error para mandarlo en una notificacion
                                String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                                //y trarerme todas las notificaciones de la clase dametoasterror
                                dameToastdeerror(errorCode);


                            }

                        }
                    });

                }else {
                // notificacion por si falta lgun dato
                Toast.makeText(registrar.this,"Completa todos los datos \uD83D\uDE01",Toast.LENGTH_SHORT).show();
                }
                }else{
                    Toast.makeText(registrar.this, "Las contraseñas no son iguales", Toast.LENGTH_SHORT).show();
                }


            }
        });



    }//fin del oncreate


    // todos los posibles erroes
    private void dameToastdeerror(String error){


    }

}