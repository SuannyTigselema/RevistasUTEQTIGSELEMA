package com.example.revistasuteq;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.revistasuteq.adaptadores.adpSuscripciones;
import com.example.revistasuteq.certificaciones.SelfSigningClientBuilder;
import com.example.revistasuteq.interfaces.itfRetrofit;
import com.example.revistasuteq.modelos.Articulo;
import com.example.revistasuteq.objetos.articulo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class activity_suscripciones extends AppCompatActivity {

    RecyclerView rclSuscripciones;
    CardView trjShimmer;
    JSONArray jsonArray;
    activity_suscripciones act = this;
    private activityPrincipal cx= new activityPrincipal();
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

        //final Articulo a = new Articulo("La ingeniería experimental del centro UTEQusino y pragmáticos de las sesiones virtuales","09/09/2019");
        //Articulo b = new Articulo("Aplicaciones distribuidas, procesamiento mediante Hadoop para el BigData","29/19/2017");
        //Articulo c = new Articulo("Los centros informáticos de la zona central de la ciudad de Quevedo","12/04/2018");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(getString(R.string.usuario));
        jsonArray= new JSONArray();
        final List<Articulo> finalLista = new ArrayList<Articulo>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    String json = snapshot.child("Suscripciones").getValue().toString();
                    try {
                        jsonArray = new JSONArray(json);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject= jsonArray.getJSONObject(i);
                            String id, tittle, fecha, seccion, seccionid;
                            id=jsonObject.get("id").toString();
                            fecha=jsonObject.get("fecha").toString();
                            tittle=jsonObject.get("tittle").toString();
                            seccion=jsonObject.get("seccion").toString();
                            seccionid=jsonObject.get("seccionid").toString();
                            finalLista.add(new Articulo(tittle,fecha,id, seccion, seccionid));
                            //sList.add(jsonObject.get("doi").toString());
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }catch (Exception e){
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        /*
        finalLista.add(a);
        finalLista.add(b);
        finalLista.add(c);

         */

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
                        /*SharedPreferences prefe = getSharedPreferences("MyPREFERENCES", MODE_PRIVATE);
                        String idioma = prefe.getString("Idioma", "es_ES");
                        Retrofit rf = new Retrofit.Builder()
                                .baseUrl("https://revistas.uteq.edu.ec/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .client(SelfSigningClientBuilder.createClient(getApplicationContext()))
                                .build();
                        itfRetrofit retrofit_interfaz = rf.create(itfRetrofit.class);

                        String url = "https://revistas.uteq.edu.ec/ws/pubs.php?i_id="+finalLista.get(posicion).getId()+"&section="+finalLista.get(posicion).getSeccionid()+"&locale="+idioma;
                        Call<articulo> call = retrofit_interfaz.getArticulo(url);
                        call.enqueue(new Callback<articulo>() {
                            @Override
                            public void onResponse(Call<articulo> call, Response<articulo> response) {
                                //Codigo de respuesta a la petición realizada
                                String cod_respuesta = "Código " + response.code();
                                //Definiendo donde se guardaran los valores obtenidos
                                articulo respuesta = response.body();
                                Toast.makeText(getApplicationContext(), Integer.toString(posicion),Toast.LENGTH_LONG).show();
                            }
                            @Override
                            public void onFailure(Call<articulo> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "No se ha podido establecer conexión con el servidor"+t.toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                        /*Intent intent = new Intent(act.getApplicationContext(), activity_detalle_articulo.class);
                        intent.putExtra("articulo", finalLista.get(posicion).getId());
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), Integer.toString(posicion),Toast.LENGTH_LONG).show();*/
                    }
                });
            }
        }, 1000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        //Toast.makeText(getApplicationContext(), Integer.toString(id), Toast.LENGTH_LONG).show();
        if(id == R.id.btnSuscripciones && !this.getClass().equals(activity_suscripciones.class) ) {
                Intent intent = new Intent(this, activity_suscripciones.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);

                this.finish();
        }
        if(id == R.id.btnCreditos && !this.getClass().equals(activity_creditos.class)) {
                Intent intent = new Intent(this, activity_creditos.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            this.finish();
        }
        if(id == R.id.btnIdioma) {
            Intent intent = new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            activityPrincipal.fa.finish();
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}