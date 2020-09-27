package com.example.revistasuteq.modelos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Idioma {

    List<String> idiomas;
    String email;
    String titulo;

    public Idioma(JSONObject datos) throws JSONException {
        idiomas = Idiomas(datos.getString("supported_locales"));
        email = datos.getString("contactEmail").toString();
        titulo = datos.getString("title").toString();
    }

    //Construir un objeto de la clase Revista
    public static ArrayList<Idioma> JsonObjectsBuild(JSONArray datos) throws JSONException {
        ArrayList<Idioma> idiomas = new ArrayList<>();
        //Recorrer los datos y agregar un objeto x cada revista
        for (int i = 0; i < datos.length(); i++) {
            idiomas.add(new Idioma(datos.getJSONObject(i)));
        }
        return idiomas;
    }

    public List<String> Idiomas(String texto) throws JSONException {
        List<String> locales = new ArrayList<>();
        JSONArray JSONlstIdiomas = new JSONArray(texto);
        for (int i = 0; i < JSONlstIdiomas.length(); i++) {
            JSONObject idioma = JSONlstIdiomas.getJSONObject(i);
            locales.add(idioma.getString("locale"));
        }
        return locales;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}