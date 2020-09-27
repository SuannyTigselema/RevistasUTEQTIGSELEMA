package com.example.revistasuteq.modelos;

public class Articulo {
    private String nombre, fecha, id, seccion, seccionid;

    public Articulo(String nombre, String fecha, String id, String seccion, String seccionid) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.id = id;
        this.seccion = seccion;
        this.seccionid = seccionid;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getSeccionid() {
        return seccionid;
    }

    public void setSeccionid(String seccionid) {
        this.seccionid = seccionid;
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
