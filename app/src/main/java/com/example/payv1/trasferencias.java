package com.example.payv1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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

public class trasferencias extends AppCompatActivity {

    private TextView etSaldo3, saldofinal3;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private EditText montoRetiro3;
    private Button btn_retirar3;
    private ImageView  atras3,infos3;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trasferencias);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        etSaldo3 = findViewById(R.id.etSaldo3);
        montoRetiro3=findViewById(R.id.montoRetiro3);
        btn_retirar3=findViewById(R.id.btn_retirar3);
        saldofinal3=findViewById(R.id.saldofinal3);
        atras3=findViewById(R.id.atras3);
        infos3=findViewById(R.id.infos3);
        cargardinero3();

        // boton informacion
        infos3.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
         AlertDialog.Builder builder = new AlertDialog.Builder(trasferencias.this);
         builder.setMessage("Todas las trasferencias tienen una comision de 8000Cop")
         .setTitle("Informacion");
         builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {}});
          AlertDialog dialog = builder.create();
           dialog.show(); }

        });



        // boton para atras
        atras3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        // boton de retirar condicion de campos vacios
        btn_retirar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validarcampovacio()) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(trasferencias.this);
                    builder.setMessage("¿Estas seguro de hacer la transferencia?")
                            .setTitle("Verifica la transferencia");

                    builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {

                        // SI EL USUARIO UNDE QUE SI HACE EL METODO
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(trasferencias.this,"transferencia exitosa ✔",Toast.LENGTH_SHORT).show();


                            String monto = montoRetiro3.getText().toString();
                            String id = mAuth.getCurrentUser().getUid();

                            mDatabase.child("User").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        int saldo3 = Integer.parseInt(snapshot.child("dineroinicial").getValue().toString());
                                        int monto3  = Integer.parseInt(monto);

                                        int saldonueva3 =  saldo3 - monto3 - 8000 ;




                                        String saldoF3 = String.valueOf(saldonueva3);
                                        saldofinal3.setText(saldoF3);

                                        // Actualizar dato de dinero
                                        mDatabase.child("User").child(id).child("dineroinicial").setValue(saldoF3);

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            String monto01 = montoRetiro3.getText().toString();
                            mDatabase.child("User").child("aT99DBkkL7Q6LWGKuwlzIn2plAu2").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                    // envair dinero
                                    int trans = Integer.parseInt(snapshot.child("dineroinicial").getValue().toString());
                                    int monto00  = Integer.parseInt(monto01);
                                    int montofinal = trans + monto00;

                                    String saldodin = String.valueOf(montofinal);

                                    mDatabase.child("User").child("aT99DBkkL7Q6LWGKuwlzIn2plAu2").child("dineroinicial").setValue(saldodin);

                                }

                                @Override
                                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                }
                            });

                        }
                    });
                    // SI EL USUARIO DICE QUE NO R
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(trasferencias.this,"transferencia rechazada ❌",Toast.LENGTH_SHORT).show();

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

        String monto = montoRetiro3.getText().toString();
        if(monto.isEmpty()){
            montoRetiro3.setError("Ingress algun monto");
            retorno=false;
        }

        return retorno;
    }


    // visualizar el dinero en la parte superior

    public void cargardinero3() {
        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("User").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int saldo = Integer.parseInt(snapshot.child("dineroinicial").getValue().toString());

                    etSaldo3.setText(saldo +" Pesos");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}


