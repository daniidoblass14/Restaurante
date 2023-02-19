package com.example.restaurante;

/**
 * autor : Daniel Doblas Florido
 * fecha : 19/02/2023
 */

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class Reservas extends AppCompatActivity implements View.OnClickListener{
    private Button btnFecha,btnHora,btnReserva;
    private EditText edtFecha, edtHora,edtNombre,edtNumeroTlf;
    private int dia,mes,ano,hora,minutos;
    private Spinner spinnerPersonas;
    private String fechaBaseDatos;
    private String horaBaseDatos;
    private String numeroSeleccionado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservas_layout);

        //Conectamos los botones y editText
        btnReserva = (Button) findViewById(R.id.btnReserva);
        btnFecha = (Button) findViewById(R.id.btnFecha);
        btnHora = (Button) findViewById(R.id.btnHora);
        edtNumeroTlf = (EditText) findViewById(R.id.edtNumero);
        edtFecha = (EditText) findViewById(R.id.edtFecha);
        edtHora = (EditText) findViewById(R.id.edtHora);
        edtNombre = (EditText) findViewById(R.id.edtNombre);
        spinnerPersonas = (Spinner) findViewById(R.id.spinnerPersonas);

        btnFecha.setOnClickListener(this);
        btnHora.setOnClickListener(this);
        btnReserva.setOnClickListener(this);

        String[] items = {"1", "2", "3","4", "5", "6","7", "8"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPersonas.setAdapter(adapter);


    }
    @Override
    public void onClick(View v) {

        if(v == btnFecha ){

            //Calendario para establecer la fecha minima.
            final Calendar calendarMin = Calendar.getInstance();
            final Calendar calendarMax = Calendar.getInstance();

            dia = calendarMin.get(Calendar.DAY_OF_MONTH);
            mes = calendarMin.get(Calendar.MONTH);
            ano = calendarMin.get(Calendar.YEAR);

            // Establece la fecha mínima en el año actual
            calendarMin.set(Calendar.YEAR, ano);

            // Establece la fecha máxima en el año actual + 10
            calendarMax.set(Calendar.YEAR, ano + 2);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    edtFecha.setText(dayOfMonth+"/"+ (month+1)+"/"+year);
                    fechaBaseDatos = edtFecha.getText().toString();
                }
            }
                    , dia,mes,ano);
            // Aplica los límites de fecha al DatePicker
            DatePicker datePicker = datePickerDialog.getDatePicker();
            datePicker.setMinDate(calendarMin.getTimeInMillis());
            datePicker.setMaxDate(calendarMax.getTimeInMillis());
            datePickerDialog.show();
        }

        if(v == btnHora){

            final Calendar calendar = Calendar.getInstance();

            hora = calendar.get(Calendar.HOUR_OF_DAY);
            minutos = calendar.get(Calendar.MINUTE);

            TimePickerDialog timerPickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    if(hourOfDay > 15|| hourOfDay < 12){
                        AlertDialog.Builder builder = new AlertDialog.Builder(Reservas.this);
                        builder.setMessage("Error: La hora debe ser de 12:00 a 16:00");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        return;
                    }
                    String selectedTime = String.format("%02d:%02d", hourOfDay, minute);
                    edtHora.setText(selectedTime);
                    horaBaseDatos = edtHora.getText().toString();
                }
            },hora,minutos,true);

            timerPickerDialog.show();
        }

        if( v == btnReserva){
            numeroSeleccionado = spinnerPersonas.getSelectedItem().toString();

            int personas = Integer.parseInt(numeroSeleccionado);
            int mesas;
            //Abrimos la base de datos, de forma escritura.
            ActivitySQLiteHelper acdbh = new ActivitySQLiteHelper(Reservas.this, "restaurante",null,1);
            SQLiteDatabase db = acdbh.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("numeroTelefono", Integer.parseInt(edtNumeroTlf.getText().toString()));
            values.put("nombre", edtNombre.getText().toString());
            values.put("fecha",edtFecha.getText().toString());
            values.put("hora",edtHora.getText().toString());
            values.put("personas",personas);
            if(personas > 4){
                mesas = 2;
            }
            else{
                mesas = 1;
            }
            values.put("mesas",mesas);

            db.insert("reservas",null,values);
            db.close();

            Toast.makeText(getApplicationContext(), "numero: "+personas, Toast.LENGTH_SHORT).show();
        }
    }
}
