package com.example.revistasuteq.interfaces;

import com.example.revistasuteq.modelos.Articulo;
import com.example.revistasuteq.objetos.articulo;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface itfRetrofit {

    // url del javarest
    @GET
    Call<articulo> getArticulo(@Url String url);
}

