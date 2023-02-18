package com.example.restaurante;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Disponibilidad extends AppCompatActivity implements View.OnClickListener {

    private TextView tvDisponibles;
    private TextView tvOcupadas;
    private int numeroMesasOcupadas;
    private int numeroMesasDisponibles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.disponibilidad_layout);

        tvDisponibles = (TextView) findViewById(R.id.tvDisponiblesEdit);
        tvOcupadas = (TextView) findViewById(R.id.tvOcupadasEdit);

        //Abrimos la base de datos, de forma leectura.
        ActivitySQLiteHelper acdbh = new ActivitySQLiteHelper(Disponibilidad.this, "restaurante",null,1);
        SQLiteDatabase db = acdbh.getReadableDatabase();

        Cursor c = db.rawQuery(" SELECT SUM(mesas) FROM reservas ",null);

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
