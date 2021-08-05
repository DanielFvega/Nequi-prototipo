package com.example.payv1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.Result;

public class Retiros extends AppCompatActivity {

    private TextView etSaldo, saldofinal,etSaldo01,cajero;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private EditText montoRetiro;
    private Button btn_retirar;
    private ImageView atras1,infos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retiros);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        etSaldo = findViewById(R.id.etSaldo);
        montoRetiro=findViewById(R.id.montoRetiro);
        btn_retirar=findViewById(R.id.btn_retirar);
        saldofinal=findViewById(R.id.saldofinal);
        atras1=findViewById(R.id.atras1);
        etSaldo01=findViewById(R.id.etSaldo01);
        cajero=findViewById(R.id.cajero);
        infos=findViewById(R.id.infos);
        cargardinero();


        // boton para atras
        atras1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // boton de informacion
        infos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Retiros.this);
                builder.setMessage("Todas los retiros tienen una comision de 3000Cop, si vas a retirar en cajero guarda el numero")
                        .setTitle("Informacion");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}});
                AlertDialog dialog = builder.create();
                dialog.show(); }

          });

        // boton de retirar condicion de campos vacios
        btn_retirar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (validarcampovacio()) {

                   AlertDialog.Builder builder = new AlertDialog.Builder(Retiros.this);
                   builder.setMessage("Estas seguro de hacer el retiro?")
                           .setTitle("Retiro");

                   builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {

                       // SI EL USUARIO UNDE QUE SI HACE EL METODO
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                         Toast.makeText(Retiros.this,"Retiro exitoso ✔",Toast.LENGTH_SHORT).show();


                           String monto = montoRetiro.getText().toString();
                           String id = mAuth.getCurrentUser().getUid();

                           mDatabase.child("User").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                               @Override
                               public void onDataChange(@NonNull DataSnapshot snapshot) {
                                   if (snapshot.exists()) {
                                       int saldo = Integer.parseInt(snapshot.child("dineroinicial").getValue().toString());
                                       int monto1  = Integer.parseInt(monto);
                                       int saldonueva =saldo - monto1 - 3000;
                                       String saldoF = String.valueOf(saldonueva);


                                       // Actualizar dato de dinero

                                       mDatabase.child("User").child(id).child("dineroinicial").setValue(saldoF);


                                       SharedPreferences prefe = getSharedPreferences("puntajes", Context.MODE_PRIVATE);
                                       String v = String.valueOf(prefe.getInt("puntaje", 0));
                                       int num = 1 + (int) (Math.random() * 10000);
                                       String cadena = String.valueOf(num);
                                       cajero.setText("Numero para Cajero "+cadena);

                                   }
                               }

                               @Override
                               public void onCancelled(@NonNull DatabaseError error) {

                               }
                           });
                       }
                   });
                   // SI EL USUARIO DICE QUE NO R
                   builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                           Toast.makeText(Retiros.this,"Retiro rechazado ❌",Toast.LENGTH_SHORT).show();

                       }
                   });

                   AlertDialog dialog = builder.create();
                   dialog.show();

               }
            }

        });

    }
    // validar si no esta vacio
        public boolean validarcampovacio(){
        boolean retorno=true;

        String monto = montoRetiro.getText().toString();
        if(monto.isEmpty()){
            montoRetiro.setError("Ingrese algun monto");
            retorno=false;
        }

        return retorno;
    }
        // visualizar el dinero en la parte superior

        public void cargardinero() {
        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("User").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int saldo = Integer.parseInt(snapshot.child("dineroinicial").getValue().toString());

                    etSaldo.setText(saldo +" Pesos");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}


