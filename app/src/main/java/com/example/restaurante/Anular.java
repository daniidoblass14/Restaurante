package com.example.restaurante;
/**
 * autor : Daniel Doblas Florido
 * fecha : 19/02/2023
 */

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Anular extends AppCompatActivity implements View.OnClickListener {

    private EditText edtNumeroTelefono;
    private Button btnAnular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anular_layout);

        edtNumeroTelefono = (EditText) findViewById(R.id.edtNumeroTelefono);
        btnAnular = (Button) findViewById(R.id.btnAnular);

        btnAnular.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        //Abrimos la base de datos, de forma escritura.
        ActivitySQLiteHelper acdbh = new ActivitySQLiteHelper(Anular.this, "restaurante",null,1);
        SQLiteDatabase db = acdbh.getWritableDatabase();

        String numeroTelefonoSeleccionado = edtNumeroTelefono.getText().toString();
        int numeroTelefono = Integer.parseInt(numeroTelefonoSeleccionado);

        int filasEliminidas = db.delete("reservas", "numeroTelefono="+numeroTelefono,null);
        db.close();

        if(filasEliminidas == 0){

            Toast.makeText(Anular.this, "No se encontró ningún registro para ese número de teléfono ", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(Anular.this, "La reserva con numero de telefono : "+ numeroTelefonoSeleccionado + " se eliminó correctamente",
                    Toast.LENGTH_SHORT).show();
        };
    }
}
