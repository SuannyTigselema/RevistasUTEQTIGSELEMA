package com.example.revistasuteq;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.revistasuteq.adaptadores.adpCategoria;
import com.example.revistasuteq.adaptadores.adpEdicion;
import com.example.revistasuteq.objetos.categoria;
import com.example.revistasuteq.objetos.edicion;
import com.example.revistasuteq.objetos.revista;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class activity_articulos extends AppCompatActivity {

    public static void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws CertificateException {
                }

                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

            }};

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (Exception ignored) {
        }
    }
    RecyclerView edicionRcl;
    ProgressDialog progress;
    ArrayList<categoria> lstCategoria;
    String revistaID;
    TextView txt,txtTitulo;
    RecyclerView rclArticulos;
    ArrayList<String> arrayListGroup;
    LinearLayoutManager layoutManagerGroup;
    adpCategoria adapterGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articulos);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#176803"));
        }
        final edicion edic_selec = (edicion) getIntent().getSerializableExtra("edicion");
       // edicionID=b.getString("edicion");
      /*  Toast toast1=Toast.makeText(getApplicationContext(),
                "Seleccionaste: " + edic_selec.getId(), Toast.LENGTH_SHORT);
        toast1.show();*/
        txtTitulo=findViewById(R.id.txtTituloEdicionA);
        txtTitulo.setText("Vol."+edic_selec.getVolumen()+" Núm."+edic_selec.getNumero()+":\n"+edic_selec.getTitulo());
        rclArticulos=new RecyclerView(this);
        rclArticulos=findViewById(R.id.rclArticulosA);
        rclArticulos.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        lstCategoria=new ArrayList<categoria>();
        handleSSLHandshake();
        progress=new ProgressDialog(this);
        progress.setMessage("Consultando...");
        progress.show();
        SharedPreferences prefe = this.getSharedPreferences("MyPREFERENCES", MODE_PRIVATE);
        String idioma = prefe.getString("Idioma", "es_ES");
        //String url="https://revistas.uteq.edu.ec/ws/pubs.php?i_id="+edic_selec.getId()+"&locale="+idioma;

        String url="https://revistas.uteq.edu.ec/ws/pubssections.php?i_id="+edic_selec.getId()+"&locale="+idioma;
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jobReq = new JsonArrayRequest(Request.Method.GET, url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        categoria rev=null;
                        try {
                            for (int i=0;i<response.length();i++){
                                rev=new categoria();
                                JSONObject obj = response.getJSONObject(i);
                                rev.setSection(obj.getString("section"));
                                rev.setSectionID(obj.getString("section_id"));
                                lstCategoria.add(rev);
                            }
                            progress.hide();
                            adpCategoria adapter=new adpCategoria(activity_articulos.this,lstCategoria,edic_selec.getId());

                            rclArticulos.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            txt.setText(response.toString());
                            Toast.makeText(activity_articulos.this, "No se ha podido establecer conexión con el servidor" +
                                    " "+response, Toast.LENGTH_LONG).show();
                            progress.hide();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        VolleyLog.e("Error: ", volleyError.getMessage());
                        System.out.println();
                        txt.setText(volleyError.toString());
                        Toast.makeText(activity_articulos.this, "No se ha podido establecer conexión con el servidor" +
                                " "+volleyError.toString(), Toast.LENGTH_LONG).show();
                        Log.d("ERROR: ", volleyError.toString());
                        progress.hide();
                    }
                });
        queue.add(jobReq);


        handleSSLHandshake();
/*
        arrayListGroup =new ArrayList<>();
        for (int i=1;i<=10;i++)
        {
            arrayListGroup.add("Grupo"+i);
        }
        adapterGroup=new adpCategoria(activity_articulos.this,arrayListGroup);
        layoutManagerGroup = new LinearLayoutManager(this);
        rclArticulos.setLayoutManager(layoutManagerGroup);
        rclArticulos.setAdapter(adapterGroup);*/







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
        if(id == R.id.btnSuscripciones) {
            Intent intent = new Intent(this, activity_suscripciones.class);
            startActivity(intent);
            this.finish();
        }
        if(id == R.id.btnCreditos) {
            Intent intent = new Intent(this, activity_creditos.class);
            startActivity(intent);
            this.finish();
        }
        if(id == R.id.btnIdioma) {
            //Invocar algo para el idioma
        }
        return super.onOptionsItemSelected(item);
    }
}