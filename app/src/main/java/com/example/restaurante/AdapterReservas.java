package com.example.restaurante;

/**
 * autor : Daniel Doblas Florido
 * fecha : 19/02/2023
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AdapterReservas extends ArrayAdapter {

    Activity context;
    TotalReservas[] datos;

    public AdapterReservas(Activity context,TotalReservas[]datos){
        super(context,R.layout.adaptador_layout,datos);
        this.datos = datos;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View item = inflater.inflate(R.layout.adaptador_layout,null);

        TextView nombre = (TextView) item.findViewById(R.id.LblNombre);
        nombre.setText(datos[position].getNombre());

        TextView hora = (TextView) item.findViewById(R.id.LblHora);
        hora.setText(datos[position].getHora());

        TextView numero = (TextView) item.findViewById(R.id.LblNumero);
        numero.setText(datos[position].getNumero());

        return item;
    }
}
