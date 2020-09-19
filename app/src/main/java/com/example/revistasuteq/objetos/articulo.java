package com.example.revistasuteq.objetos;

import java.io.Serializable;
import java.util.ArrayList;

public class articulo implements Serializable {
    String seccion;
    String publicacion_id;
    String titulo;
    String doi;
    String resumen;
    String fecha_publicacion;
    String date_published;
    String submission_id;
    String section_id;
    String seq;
    ArrayList<galeys> lstGaleys;
    ArrayList<keywords> lstKeywordss;
    ArrayList<autores> lstAutores;

    public String getDate_published() {
        return date_published;
    }

    public void setDate_published(String date_published) {
        this.date_published = date_published;
    }

    public String getSubmission_id() {
        return submission_id;
    }

    public void setSubmission_id(String submission_id) {
        this.submission_id = submission_id;
    }

    public String getSection_id() {
        return section_id;
    }

    public void setSection_id(String section_id) {
        this.section_id = section_id;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public ArrayList<galeys> getLstGaleys() {
        return lstGaleys;
    }

    public void setLstGaleys(ArrayList<galeys> lstGaleys) {
        this.lstGaleys = lstGaleys;
    }

    public ArrayList<keywords> getLstKeywordss() {
        return lstKeywordss;
    }

    public void setLstKeywordss(ArrayList<keywords> lstKeywordss) {
        this.lstKeywordss = lstKeywordss;
    }

    public ArrayList<autores> getLstAutores() {
        return lstAutores;
    }

    public void setLstAutores(ArrayList<autores> lstAutores) {
        this.lstAutores = lstAutores;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getPublicacion_id() {
        return publicacion_id;
    }

    public void setPublicacion_id(String publicacion_id) {
        this.publicacion_id = publicacion_id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public String getFecha_publicacion() {
        return fecha_publicacion;
    }

    public void setFecha_publicacion(String fecha_publicacion) {
        this.fecha_publicacion = fecha_publicacion;
    }
}
