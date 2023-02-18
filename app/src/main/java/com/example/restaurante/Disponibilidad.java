package com.example.restaurante;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Disponibilidad extends AppCompatActivity implements View.OnClickListener {

    private TextView tvDisponibles;
    private TextView tvOcupadas;
    private TextView tvDisponibilidadDia;
    private int numeroMesasOcupadas;
    private int numeroMesasDisponibles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.disponibilidad_layout);

        tvDisponibles = (TextView) findViewById(R.id.tvDisponiblesEdit);
        tvDisponibilidadDia = (TextView) findViewById(R.id.tvDisponibilidadDia);
        tvOcupadas = (TextView) findViewById(R.id.tvOcupadasEdit);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy");
        String currentDate = dateFormat.format(calendar.getTime());

        tvDisponibilidadDia.setText("DISPONIBILIDAD DE :" +currentDate);

        //Abrimos la base de datos, de forma leectura.
        ActivitySQLiteHelper acdbh = new ActivitySQLiteHelper(Disponibilidad.this, "restaurante",null,1);
        SQLiteDatabase db = acdbh.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT SUM(mesas) FROM reservas WHERE fecha = '"+currentDate+"'",null);

        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                numeroMesasOcupadas = c.getInt(0);
            } while(c.moveToNext());
        }
        tvOcupadas.setText(String.valueOf(numeroMesasOcupadas));
        db.close();

        numeroMesasDisponibles = 25 - numeroMesasOcupadas;
        tvDisponibles.setText(String.valueOf(numeroMesasDisponibles));

        //Toast.makeText(getApplicationContext(), "mesas: "+numeroMesasOcupadas, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onClick(View v) {

    }
}
