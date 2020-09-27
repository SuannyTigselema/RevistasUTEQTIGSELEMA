package com.example.revistasuteq;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.example.revistasuteq.adaptadores.adpCreditos;
import com.example.revistasuteq.adaptadores.adpSuscripciones;
import com.example.revistasuteq.modelos.Articulo;
import com.example.revistasuteq.modelos.Credito;

import java.util.ArrayList;
import java.util.List;

public class activity_creditos extends AppCompatActivity {

    RecyclerView rclAutores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creditos);

        rclAutores = findViewById(R.id.rclCreditos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#176803"));
        }



        LinearLayoutManager linear = new LinearLayoutManager(getApplicationContext());
        linear.setOrientation(LinearLayoutManager.VERTICAL);
        rclAutores.setLayoutManager(linear);
        rclAutores.hasFixedSize();
        adpSuscripciones.showShimmer = true;

        List<Credito> lista = new ArrayList<Credito>();
        final adpCreditos adpCreditos = new adpCreditos(lista);
        rclAutores.setAdapter(adpCreditos);

        Credito a = new Credito("ALVAREZ CARPIO GILBERTO GERMAN");
        Credito b = new Credito("BAÑO MERA JAIME FABRICIO");
        Credito c = new Credito("BRIONES MONTALVO CRISTHIAN DANILO");
        Credito d = new Credito("CELI YELA MANUEL CELSO");
        Credito e = new Credito("CORDERO BAZURTO JOSE STEVEN");
        Credito f = new Credito("JAYA PURUNCAJA RUBEN DARIO");
        Credito g = new Credito("MENDOZA RISCO BRYAN ALFREDO");
        Credito h = new Credito("MORALES VELOZ PAOLA SOFIA");
        Credito i = new Credito("MOREIRA TORRES DANIELA MISHEL");
        Credito j = new Credito("MUÑOZ PISCO JEANNY XAVIER");
        Credito k = new Credito("ORDOÑEZ GUERRERO KARINA MICHELLE");
        Credito l = new Credito("ORTIZ TERAN INGRID LISBETH");
        Credito m = new Credito("PINCAY BAQUE JOHN JAVIER");
        Credito n = new Credito("TIGSELEMA EGRE SUANNY GABRIELA");
        Credito o = new Credito("VALLE MENA ANGEL ESTALIN");
        Credito p = new Credito("VERGARA LOOR JOSELYN AYLIN");
        Credito q = new Credito("VINUEZA VEGA KEVIN STALIN");



        final List<Credito> finalLista = new ArrayList<Credito>();
        finalLista.add(a);
        finalLista.add(b);
        finalLista.add(c);
        finalLista.add(d);
        finalLista.add(e);
        finalLista.add(f);
        finalLista.add(g);
        finalLista.add(h);
        finalLista.add(i);
        finalLista.add(j);
        finalLista.add(k);
        finalLista.add(l);
        finalLista.add(m);
        finalLista.add(n);
        finalLista.add(o);
        finalLista.add(p);
        finalLista.add(q);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adpCreditos adpCreditos = new adpCreditos(finalLista);
                rclAutores.setAdapter(adpCreditos);
                adpCreditos.showShimmer = false;
                adpCreditos.notifyDataSetChanged();

                adpCreditos.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int posicion = rclAutores.getChildAdapterPosition(view);
                        Toast.makeText(getApplicationContext(), Integer.toString(posicion),Toast.LENGTH_LONG).show();
                    }
                });
            }
        }, 1000);






    }

}