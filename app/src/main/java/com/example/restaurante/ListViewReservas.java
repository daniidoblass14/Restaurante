package com.example.restaurante;
/**
 * autor : Daniel Doblas Florido
 * fecha : 19/02/2023
 */
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ListViewReservas  extends AppCompatActivity implements View.OnClickListener{

    private ListView lista;
    private TextView tvReservasDia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_layout);
        lista = (ListView) findViewById(R.id.Lista);
        tvReservasDia = (TextView) findViewById(R.id.tvReservasDia);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy");
        String currentDate = dateFormat.format(calendar.getTime());

        tvReservasDia.setText("TODAS LAS RESERVAS DEL DIA :" +currentDate);

        //Abrimos la base de datos, de forma leectura.
        ActivitySQLiteHelper acdbh = new ActivitySQLiteHelper(ListViewReservas.this, "restaurante",null,1);
        SQLiteDatabase db = acdbh.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT nombre,hora,numeroTelefono FROM reservas WHERE fecha = '"+currentDate+"'",null);

        List<TotalReservas> reservasList = new ArrayList<>();

        //Nos aseguramos de que existe al menos un registro
        if (cursor.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                TotalReservas reserva = new TotalReservas(cursor.getString(0),cursor.getString(1),
                        String.valueOf(cursor.getInt(2)));
                reservasList.add(reserva);
            } while(cursor.moveToNext());
        }

        // Convertimos la lista en un arreglo de TotalReservas
        TotalReservas[] reservasArray = reservasList.toArray(new TotalReservas[reservasList.size()]);

        AdapterReservas adapter = new AdapterReservas(this, reservasArray);
        lista.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {

    }
}
