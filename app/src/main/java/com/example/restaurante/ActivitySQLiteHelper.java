package com.example.restaurante;
/**
 * autor : Daniel Doblas Florido
 * fecha : 19/02/2023
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class ActivitySQLiteHelper extends SQLiteOpenHelper {
    String CREATE_TABLE_RESERVAS = "CREATE TABLE reservas (numeroTelefono INTEGER PRIMARY KEY, nombre TEXT, fecha TEXT, hora TEXT, personas INTEGER, mesas INTEGER)";
    String CREATE_TABLE_CONFIR = "CREATE TABLE confirmaciones (numeroTelefono INTEGER, nombre TEXT)";
    String CREATE_TABLE_PAGOS = "CREATE TABLE pagos (numeroTelefono INTEGER, nombre TEXT,fecha TEXT,precio INTEGER)";


    public ActivitySQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_RESERVAS);
        db.execSQL(CREATE_TABLE_CONFIR);
        db.execSQL(CREATE_TABLE_PAGOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
