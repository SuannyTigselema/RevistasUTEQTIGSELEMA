package com.example.revistasuteq;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.example.revistasuteq.adaptadores.adpEdicion;
import com.example.revistasuteq.adaptadores.adpEdicionR;
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

public class edicionesR extends AppCompatActivity {

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
    ArrayList<edicion> lstEdicion;
    ArrayList<revista> lstRevista;
    TextView txt,txtTitulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ediciones_r);

        Bundle b = this.getIntent().getExtras();
        revista rev_selec = (revista) getIntent().getSerializableExtra("revista");


        edicionRcl=new RecyclerView(this);
        edicionRcl=(RecyclerView)findViewById(R.id.rclEdiEdicion);
        edicionRcl.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        txtTitulo=findViewById(R.id.txtEdiTituloCab);
        txtTitulo.setText(rev_selec.getNombre());
        lstEdicion=new ArrayList<edicion>();

        handleSSLHandshake();
        progress=new ProgressDialog(this);
        progress.setMessage("Consultando...");
        progress.show();
        SharedPreferences prefe = this.getSharedPreferences("MyPREFERENCES", MODE_PRIVATE);
        String idioma = prefe.getString("Idioma", "es_ES");
        String url="https://revistas.uteq.edu.ec/ws/issues.php?j_id="+rev_selec.getJournal_id()+"&locale="+idioma;
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jobReq = new JsonArrayRequest(Request.Method.GET, url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        edicion rev=null;
                        try {
                            for (int i=0;i<response.length();i++){
                                rev=new edicion();
                                JSONObject obj = response.getJSONObject(i);
                                rev.setId(obj.getString("issue_id"));
                                rev.setVolumen(obj.getString("volume"));
                                rev.setNumero(obj.getString("number"));
                                rev.setTitulo(obj.getString("title"));
                                rev.setDoi(obj.getString("doi"));
                                rev.setFecha_publicacion(obj.getString("date_published"));
                                rev.setImagen(obj.getString("cover"));
                                lstEdicion.add(rev);
                            }
                            progress.hide();
                            adpEdicionR adapter=new adpEdicionR(edicionesR.this,lstEdicion);
                            adapter.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    int opcselec=edicionRcl.getChildAdapterPosition(view);
                                    String nombreselec= lstEdicion.get(opcselec).getId();
                                    Intent intent = new Intent(edicionesR.this,activity_articulos.class);

                                    // Bundle b = new Bundle();
                                    //  b.putString("edicionID", nombreselec);
                                    intent.putExtra("edicion", lstEdicion.get(opcselec));

                                    startActivity(intent);

                                }
                            });
                            edicionRcl.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            txt.setText(response.toString());
                            Toast.makeText(edicionesR.this, "No se ha podido establecer conexión con el servidor" +
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
                        Toast.makeText(edicionesR.this, "No se ha podido establecer conexión con el servidor" +
                                " "+volleyError.toString(), Toast.LENGTH_LONG).show();
                        Log.d("ERROR: ", volleyError.toString());
                        progress.hide();
                    }
                });
        queue.add(jobReq);


        handleSSLHandshake();
    }





}