package com.example.restaurante;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ArrayList<View> botones = new ArrayList<>();
    private String currentDate;
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);

        RelativeLayout botonesMenu = findViewById(R.id.layoutMenu);
        botones = botonesMenu.getTouchables();

        for(View button : botones){

            ((Button)button).setOnClickListener(this);
        }

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy");
        currentDate = dateFormat.format(calendar.getTime());

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "Este es el hilo cada 10 segundos", Toast.LENGTH_SHORT).show();
                handler.postDelayed(runnable, 10000); // Ejecutar cada 10 segundos
            }
        };

        handler.post(runnable); // Iniciar el hilo
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable); // Detener el hilo al salir de la actividad
    }

    @Override
    public void onClick(View v) {

        Intent intentReservas = new Intent(this,Reservas.class);
        Intent intentAnular = new Intent(this,Anular.class);
        Intent intentPagar = new Intent(this,Pago.class);
        Intent intentConfirmacion = new Intent(this,Confirmacion.class);
        Intent intentDisponibilidad = new Intent(this,Disponibilidad.class);
        Intent intentTotalRecaudado = new Intent(this,TotalRecaudado.class);

        if(v.getId() == R.id.btnReservas){

            int totalMesas = 0;

            //Abrimos la base de datos, de forma leectura.
            ActivitySQLiteHelper acdbh = new ActivitySQLiteHelper(MainActivity.this, "restaurante",null,1);
            SQLiteDatabase db = acdbh.getReadableDatabase();

            //Obtenemos el nombre de la reserva.
            Cursor c = db.rawQuery("SELECT SUM(mesas) FROM reservas WHERE fecha = '"+currentDate+"'",null);

            //Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    totalMesas = c.getInt(0);
                } while(c.moveToNext());
            }

            if(totalMesas >= 25){

                Toast.makeText(getApplicationContext(), "TODAS LAS MESAS RESERVADAS PARA HOY", Toast.LENGTH_SHORT).show();
            }
            else{

                startActivity(intentReservas);
            }

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
        if(v.getId() == R.id.btnTotalRecaudado){

            startActivity(intentTotalRecaudado);
        }
    }
}