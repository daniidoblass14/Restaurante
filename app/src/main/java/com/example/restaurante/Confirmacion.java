package com.example.restaurante;

/**
 * autor : Daniel Doblas Florido
 * fecha : 19/02/2023
 */

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Confirmacion extends AppCompatActivity implements View.OnClickListener {
    private EditText edtIdReserva;
    private ArrayList<View> botones = new ArrayList<>();
    private TableLayout tablaConfirmados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmaciones_layout);

        edtIdReserva = (EditText) findViewById(R.id.edtNumeroTelefono);
        tablaConfirmados = (TableLayout) findViewById(R.id.table_layout);

        RelativeLayout botonesConfirmacion = findViewById(R.id.layoutConfirmacion);
        botones = botonesConfirmacion.getTouchables();

        for(View button : botones){
            if(button instanceof Button){
                ((Button)button).setOnClickListener(this);
            }
        }
    }
    @SuppressLint("Range")
    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.btnConfirmar){

            String nombre ="";
            int numeroTelefono = Integer.parseInt(edtIdReserva.getText().toString());

            //Abrimos la base de datos, de forma leectura.
            ActivitySQLiteHelper acdbh = new ActivitySQLiteHelper(Confirmacion.this, "restaurante",null,1);
            SQLiteDatabase db = acdbh.getReadableDatabase();

            Cursor cNombre = db.rawQuery(" SELECT nombre FROM reservas WHERE numeroTelefono="+numeroTelefono,null);

            //Nos aseguramos de que existe al menos un registro
            if (cNombre.moveToFirst()) {
                //Recorremos el cursor hasta que no haya m??s registros
                do {
                    nombre = cNombre.getString(0);
                } while(cNombre.moveToNext());
            }

            //Comprobamos que si existe en la bbdd
            Cursor c = db.rawQuery("SELECT COUNT(*) FROM confirmaciones WHERE numeroTelefono = "+numeroTelefono,null);

            if (c.moveToFirst() && c.getInt(0) == 0) {
                // Sentencia SQL para insertar el nuevo registro
                db.execSQL("INSERT INTO confirmaciones (numeroTelefono, nombre) VALUES ('" + numeroTelefono + "', '" + nombre + "')");
                Toast.makeText(getApplicationContext(), "CONFIRMADO ", Toast.LENGTH_SHORT).show();
            } else {
                // Mensaje de error, el registro ya existe
                Toast.makeText(Confirmacion.this, "YA SE HA CONFRIMADO ANTERIORMENTE", Toast.LENGTH_SHORT).show();
            }

            db.close();
        }

        if(v.getId() == R.id.btnVisualizarConfirmados){

            tablaConfirmados.removeAllViews();

            ActivitySQLiteHelper acdbh = new ActivitySQLiteHelper(Confirmacion.this, "restaurante",null,1);
            SQLiteDatabase db = acdbh.getReadableDatabase();

            Cursor c = db.rawQuery(" SELECT * FROM confirmaciones ",null);

            //Creamos una fila para los nombres de las columnas.
            TableRow columnNamesRow = new TableRow(this);
            columnNamesRow.setLayoutParams(new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            //Creamos las celdas para los nombres de las columnas.
            TextView column1Name = new TextView(this);
            column1Name.setText("ID Reserva");
            column1Name.setGravity(Gravity.CENTER);
            columnNamesRow.addView(column1Name);

            TextView column2Name = new TextView(this);
            column2Name.setText("Nombre");
            column2Name.setGravity(Gravity.CENTER);
            columnNamesRow.addView(column2Name);

            // A??adir la fila de nombres de columnas a la tabla
            tablaConfirmados.addView(columnNamesRow);

            while(c.moveToNext()){

                //Creamos una fila para tabla.
                TableRow tableRow = new TableRow(this);
                tableRow.setLayoutParams(new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                //Creamos las celdas para las filas.
                TextView col1 = new TextView(this);
                col1.setText(c.getString(c.getColumnIndex("numeroTelefono")));
                col1.setGravity(Gravity.CENTER);
                tableRow.addView(col1);

                TextView col2 = new TextView(this);
                col2.setText(c.getString(c.getColumnIndex("nombre")));
                col2.setGravity(Gravity.CENTER);
                tableRow.addView(col2);

                // A??adir la fila a la tabla
                tablaConfirmados.addView(tableRow);
            }
            // Cerrar el cursor
            c.close();

            // Cerrar la base de datos
            db.close();
        }
    }
}
