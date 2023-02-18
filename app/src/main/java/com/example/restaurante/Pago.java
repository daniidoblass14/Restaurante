package com.example.restaurante;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class Pago extends AppCompatActivity implements View.OnClickListener {

    private Button btnPagar, btnCuenta;
    private TextView tvPrecio;
    private EditText edtIdReserva;
    private String nombre;
    private int cuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pago_layout);

        btnCuenta = (Button) findViewById(R.id.btnCuenta);
        btnPagar = (Button) findViewById(R.id.btnPagar);
        tvPrecio = (TextView) findViewById(R.id.tvPrecio);
        edtIdReserva = (EditText) findViewById(R.id.edtNumeroTelefono);

        btnPagar.setOnClickListener(this);
        btnCuenta.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.btnCuenta){

            btnCuenta.setEnabled(false);
            btnPagar.setVisibility(View.VISIBLE);
            btnPagar.setEnabled(true);
            tvPrecio.setVisibility(View.VISIBLE);
            tvPrecio.setEnabled(true);

            //Generamos el precio de la cuenta
            Random random = new Random();
            cuenta = random.nextInt(86) + 25;
            tvPrecio.setText(String.valueOf(cuenta));

        }
        if(v.getId() == R.id.btnPagar){

            int numeroTelefono = Integer.parseInt(edtIdReserva.getText().toString());

            //Abrimos la base de datos, de forma leectura.
            ActivitySQLiteHelper acdbh = new ActivitySQLiteHelper(Pago.this, "restaurante",null,1);
            SQLiteDatabase db = acdbh.getReadableDatabase();

            //Obtenemos el nombre de la reserva.
            Cursor cNombre = db.rawQuery("SELECT nombre FROM reservas WHERE id = "+numeroTelefono,null);

            if (cNombre.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    nombre = cNombre.getString(0);
                } while(cNombre.moveToNext());
            }
            cNombre.close();

            //Comprobamos que si existe en la bbdd
            Cursor c = db.rawQuery("SELECT COUNT(*) FROM pagos WHERE numeroTelefono = "+numeroTelefono,null);
            if (c.moveToFirst() && c.getInt(0) == 0) {
                // Sentencia SQL para insertar el nuevo registro
                db.execSQL("INSERT INTO pagos (numeroTelefono, nombre, precio) VALUES ('" + numeroTelefono + "', '" + nombre + "','" + cuenta + "')");
                Toast.makeText(getApplicationContext(), "CONFIRMADO ", Toast.LENGTH_SHORT).show();
            } else {
                // Mensaje de error, el registro ya existe
                Toast.makeText(Pago.this, "El registro ya existe", Toast.LENGTH_SHORT).show();
            }
            // Cerrar el cursor
            c.close();
            // Cerrar la base de datos
            db.close();

        }
    }
}
