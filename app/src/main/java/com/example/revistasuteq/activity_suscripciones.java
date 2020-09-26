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

import com.example.revistasuteq.adaptadores.adpSuscripciones;
import com.example.revistasuteq.modelos.Articulo;

import java.util.ArrayList;
import java.util.List;

public class activity_suscripciones extends AppCompatActivity {

    RecyclerView rclSuscripciones;
    CardView trjShimmer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suscripciones);


        rclSuscripciones = findViewById(R.id.rclSuscripciones);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#176803"));
        }



        LinearLayoutManager linear = new LinearLayoutManager(getApplicationContext());
        linear.setOrientation(LinearLayoutManager.VERTICAL);
        rclSuscripciones.setLayoutManager(linear);
        rclSuscripciones.hasFixedSize();
        adpSuscripciones.showShimmer = true;

        List<Articulo> lista = new ArrayList<Articulo>();
        final adpSuscripciones adpSuscripciones = new adpSuscripciones(lista);
        rclSuscripciones.setAdapter(adpSuscripciones);

        final Articulo a = new Articulo("La ingeniería experimental del centro UTEQusino y pragmáticos de las sesiones virtuales","09/09/2019");
        Articulo b = new Articulo("Aplicaciones distribuidas, procesamiento mediante Hadoop para el BigData","29/19/2017");
        Articulo c = new Articulo("Los centros informáticos de la zona central de la ciudad de Quevedo","12/04/2018");

        final List<Articulo> finalLista = new ArrayList<Articulo>();
        finalLista.add(a);
        finalLista.add(b);
        finalLista.add(c);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adpSuscripciones adpSuscripciones = new adpSuscripciones(finalLista);
                rclSuscripciones.setAdapter(adpSuscripciones);
                adpSuscripciones.showShimmer = false;
                adpSuscripciones.notifyDataSetChanged();

                adpSuscripciones.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int posicion = rclSuscripciones.getChildAdapterPosition(view);
                        Toast.makeText(getApplicationContext(), Integer.toString(posicion),Toast.LENGTH_LONG).show();
                    }
                });
            }
        }, 1000);
    }
}