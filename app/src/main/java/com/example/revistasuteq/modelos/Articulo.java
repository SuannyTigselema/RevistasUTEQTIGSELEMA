package com.example.revistasuteq.modelos;

public class Articulo {
    private String nombre, fecha, id ;

    public Articulo(String nombre, String fecha, String id) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
