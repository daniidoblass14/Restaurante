package com.example.restaurante;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TotalRecaudado extends AppCompatActivity {

    private int total;
    private TextView tvTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.total_layout);

        tvTotal = (TextView) findViewById(R.id.tvTotal);

        //Abrimos la base de datos, de forma leectura.
        ActivitySQLiteHelper acdbh = new ActivitySQLiteHelper(TotalRecaudado.this, "restaurante",null,1);
        SQLiteDatabase db = acdbh.getReadableDatabase();

        Cursor c = db.rawQuery(" SELECT SUM(precio) FROM pagos ",null);

        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                total = c.getInt(0);
            } while(c.moveToNext());
        }

        tvTotal.setText(String.valueOf(total));
    }
}
