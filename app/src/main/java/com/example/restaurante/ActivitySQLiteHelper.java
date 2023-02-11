package com.example.restaurante;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class ActivitySQLiteHelper extends SQLiteOpenHelper {
    String CREATE_TABLE_RESERVAS = "CREATE TABLE reservas (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, fecha TEXT, hora TEXT, personas INTEGER, mesas INTEGER)";
    String CREATE_TABLE_CONFIR = "CREATE TABLE confirmaciones (idReserva INTEGER, nombre TEXT)";
    String CREATE_TABLE_PAGOS = "CREATE TABLE pagos (idReserva INTEGER, nombre TEXT,precio INTEGER)";


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
