package com.example.payv1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class home extends AppCompatActivity {

    FloatingActionButton btn_retiros,btn_consignaciones,btn_trasferencias;

    private TextView etSaldo1;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ImageView trasferenciaBtn,perfilbtn,ahorrobtn,RetiroBtn1,consignarBtn1,info,Alertadialogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        btn_retiros=findViewById(R.id.btn_retiros);
        btn_consignaciones=findViewById(R.id.btn_consignaciones);
        btn_trasferencias=findViewById(R.id.btn_trasferencias);
        trasferenciaBtn=findViewById(R.id.trasferenciaBtn);
        perfilbtn=findViewById(R.id.perfilbtn);
        ahorrobtn=findViewById(R.id.ahorrobtn);
        RetiroBtn1=findViewById(R.id.RetiroBtn1);
        consignarBtn1=findViewById(R.id.consignarBtn1);
        info=findViewById(R.id.info);
        Alertadialogo=findViewById(R.id.Alertadialogo);

        etSaldo1=findViewById(R.id.etSaldo1);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        cargarSaldo1();

        Alertadialogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(home.this);
                builder.setMessage("Aplicacion PayV1.0")
                        .setTitle("Daniel Fuentes Vega");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}});
                builder.setNegativeButton("", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}});

                AlertDialog dialog = builder.create();
                dialog.show();

                }});

        // botones para pasar activity
        btn_retiros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(home.this, Retiros.class );
                startActivity(i);}});// final de boton retiros

        btn_consignaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(home.this, Consignaciones.class );
                startActivity(i);}});// final de boton de consignaciones

        btn_trasferencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(home.this, trasferencias.class );
                startActivity(i); } });// final de boton de trafrencias

        trasferenciaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(home.this, trasferencias.class );
                startActivity(i); } });// final de boton de trafrenciasbtn

        perfilbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(home.this, perfil.class );
                startActivity(i); } });// final de boton de perfil

        ahorrobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(home.this, Consignaciones.class );
                startActivity(i); } });// final de boton de ahorro

        RetiroBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(home.this, Retiros.class );
                startActivity(i); } });// final de boton de retiro

        consignarBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(home.this, Consignaciones.class );
                startActivity(i); } });// final de boton de consignar

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(home.this, informacion.class );
                startActivity(i); } });// final de boton de info




    }
    public void cargarSaldo1() {
        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("User").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String saldop1 =snapshot.child("dineroinicial").getValue().toString();
                    etSaldo1.setText(saldop1 +" COP");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
  }