package com.example.restaurante;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ArrayList<View> botones = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);

        RelativeLayout botonesMenu = findViewById(R.id.layoutMenu);
        botones = botonesMenu.getTouchables();

        for(View button : botones){

            ((Button)button).setOnClickListener(this);
        }

    }

    @Override
    public void onClick(View v) {

        Intent intentReservas = new Intent(this,Reservas.class);
        Intent intentAnular = new Intent(this,Anular.class);
        Intent intentPagar = new Intent(this,Pago.class);
        Intent intentConfirmacion = new Intent(this,Confirmacion.class);
        Intent intentDisponibilidad = new Intent(this,Disponibilidad.class);

        if(v.getId() == R.id.btnReservas){

            startActivity(intentReservas);
        }
        if(v.getId() == R.id.btnAnular){

            startActivity(intentAnular);
        }
        if(v.getId() == R.id.btnPago){

            startActivity(intentPagar);
        }
        if(v.getId() == R.id.btnConfirmacion){

            startActivity(intentConfirmacion);
        }
        if(v.getId() == R.id.btnDisponibilidad){

            startActivity(intentDisponibilidad);
        }
    }
}