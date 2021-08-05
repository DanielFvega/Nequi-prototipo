package com.example.payv1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
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

public class Consignaciones extends AppCompatActivity {

    private TextView etSaldo1, saldofinal2;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private EditText montoRetiro;
    private Button btn_retirar1;
    private ImageView atras2,infos1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consignaciones);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        etSaldo1 = findViewById(R.id.etSaldo1);
        montoRetiro=findViewById(R.id.montoRetiro1);
        btn_retirar1=findViewById(R.id.btn_retirar1);
        saldofinal2=findViewById(R.id.saldofinal2);
        atras2=findViewById(R.id.atras2);
        infos1=findViewById(R.id.infos1);
        cargardinero2();

        // boton de informacion
        infos1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Consignaciones.this);
                builder.setMessage("Las consignaciones no tienen comision")
                        .setTitle("Informacion");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}});
                AlertDialog dialog = builder.create();
                dialog.show(); }

        });


        // boton para atras
        atras2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // boton de retirar condicion de campos vacios
        btn_retirar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validarcampovacio()) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(Consignaciones.this);
                    builder.setMessage("¿Estas seguro de hacer la consignacion?")
                            .setTitle("Verifica la consignacion");

                    builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {

                        // SI EL USUARIO UNDE QUE SI HACE EL METODO
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(Consignaciones.this,"Consignacion exitosa ✔",Toast.LENGTH_SHORT).show();


                            String monto = montoRetiro.getText().toString();
                            String id = mAuth.getCurrentUser().getUid();

                            mDatabase.child("User").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        int saldo = Integer.parseInt(snapshot.child("dineroinicial").getValue().toString());
                                        int monto1  = Integer.parseInt(monto);
                                        int saldonueva1 =  saldo + monto1;



                                        String saldoF1 = String.valueOf(saldonueva1);
                                        saldofinal2.setText(saldoF1);

                                        // Actualizar dato de dinero
                                        mDatabase.child("User").child(id).child("dineroinicial").setValue(saldoF1);


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
                            Toast.makeText(Consignaciones.this,"Consignacion rechazada ❌",Toast.LENGTH_SHORT).show();

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

    public void cargardinero2() {
        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("User").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int saldo = Integer.parseInt(snapshot.child("dineroinicial").getValue().toString());

                    etSaldo1.setText(saldo +" Pesos");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}


