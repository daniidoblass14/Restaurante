package com.example.restaurante;
/**
 * autor : Daniel Doblas Florido
 * fecha : 19/02/2023
 */
public class TotalReservas {

    private String nombre;
    private String hora;
    private  String numero;

    public TotalReservas(String nombre, String fecha,String numero){

        this.nombre = nombre;
        this.hora = fecha;
        this.numero = numero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
