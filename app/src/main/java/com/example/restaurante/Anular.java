package com.example.restaurante;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Anular extends AppCompatActivity implements View.OnClickListener {

    private EditText edtIdReserva, edtMotivo;
    private Button btnAnular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anular_layout);

        edtIdReserva = (EditText) findViewById(R.id.edtIdReserva);
        edtMotivo = (EditText) findViewById(R.id.edtMotivo);
        btnAnular = (Button) findViewById(R.id.btnAnular);

        btnAnular.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        //Abrimos la base de datos, de forma escritura.
        ActivitySQLiteHelper acdbh = new ActivitySQLiteHelper(Anular.this, "restaurante",null,1);
        SQLiteDatabase db = acdbh.getWritableDatabase();

        String idSeleccionado = edtIdReserva.getText().toString();
        int id = Integer.parseInt(idSeleccionado);

        db.delete("reservas", "id="+id,null);
        db.close();
    }
}
