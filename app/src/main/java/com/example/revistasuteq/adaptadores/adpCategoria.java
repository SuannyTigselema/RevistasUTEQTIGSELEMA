package com.example.revistasuteq.adaptadores;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.revistasuteq.Ediciones;
import com.example.revistasuteq.R;
import com.example.revistasuteq.activity_articulos;
import com.example.revistasuteq.activity_detalle_articulo;
import com.example.revistasuteq.objetos.articulo;
import com.example.revistasuteq.objetos.autores;
import com.example.revistasuteq.objetos.categoria;
import com.example.revistasuteq.objetos.galeys;
import com.example.revistasuteq.objetos.keywords;

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

public class adpCategoria extends RecyclerView.Adapter<adpCategoria.MyViewHolder> {
    private Activity activity;
    ArrayList<categoria> arrayListGroup;
    String edicionID;

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
    public adpCategoria(Activity activity, ArrayList<categoria> arrayListGroup,String edicionID)
    {
        this.activity=activity;
        this.arrayListGroup=arrayListGroup;
        this.edicionID=edicionID;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_categ,parent,false);
        handleSSLHandshake();
        return new adpCategoria.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.txtCategoria.setText(arrayListGroup.get(position).getSection());
        //https://revistas.uteq.edu.ec/ws/pubs.php?i_id=issued_ID&section=section_ID
        String url="https://revistas.uteq.edu.ec/ws/pubs.php?i_id="+edicionID+"&section="+arrayListGroup.get(position).getSectionID()+"";
        final ArrayList<articulo> arrayListMember = new ArrayList<articulo>();
        final ArrayList<galeys> arrayListGaleys = new ArrayList<galeys>();
        final ArrayList<keywords> arrayListKeys = new ArrayList<keywords>();
        final ArrayList<autores> arrayListAut = new ArrayList<autores>();
        RequestQueue queue = Volley.newRequestQueue(activity);
        JsonArrayRequest jobReq = new JsonArrayRequest(Request.Method.GET, url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        articulo rev=null;

                        try {
                            for (int i=0;i<response.length();i++){
                                rev=new articulo();
                                JSONObject obj = response.getJSONObject(i);
                                rev.setSeccion(obj.getString("section"));
                                rev.setPublicacion_id(obj.getString("publication_id"));
                                rev.setTitulo(obj.getString("title"));
                                rev.setDoi(obj.getString("doi"));
                                rev.setResumen(obj.getString("abstract"));
                                rev.setFecha_publicacion(obj.getString("date_published"));
                                rev.setSubmission_id(obj.getString("submission_id"));
                                rev.setSection_id(obj.getString("section_id"));
                                rev.setSeq(obj.getString("seq"));
                                galeys gal;

                                for (int j=0;j<obj.getJSONArray("galeys").length();j++)
                                {
                                    gal=new galeys();
                                    JSONObject obj_galeys=obj.getJSONArray("galeys").getJSONObject(j);
                                    gal.setGalley_id(obj_galeys.getString("galley_id"));
                                    gal.setLabel(obj_galeys.getString("label"));
                                    gal.setFile_id(obj_galeys.getString("file_id"));
                                    gal.setUrlViewGalley(obj_galeys.getString("UrlViewGalley"));
                                    arrayListGaleys.add(gal);
                                }
                                rev.setLstGaleys(arrayListGaleys);
                                autores aut;

                                for (int j=0;j<obj.getJSONArray("authors").length();j++)
                                {
                                    aut=new autores();
                                    JSONObject obj_keys=obj.getJSONArray("authors").getJSONObject(j);
                                    aut.setNombres(obj_keys.getString("nombres"));
                                    aut.setFiliacion(obj_keys.getString("filiacion"));
                                    aut.setEmail(obj_keys.getString("email"));
                                    arrayListAut.add(aut);
                                }
                                rev.setLstAutores(arrayListAut);
                                keywords key;

                                for (int j=0;j<obj.getJSONArray("keywords").length();j++)
                                {
                                    key=new keywords();
                                    JSONObject obj_keys=obj.getJSONArray("keywords").getJSONObject(j);
                                    key.setKeyword(obj_keys.getString("keyword"));
                                    arrayListKeys.add(key);
                                }
                                rev.setLstKeywordss(arrayListKeys);

                                arrayListMember.add(rev);
                            }
                            adpArticulo adapterMember=new adpArticulo(arrayListMember);
                            LinearLayoutManager layoutManagerMember=new LinearLayoutManager(activity);
                            holder.rclArticulos.addItemDecoration(new DividerItemDecoration(activity,DividerItemDecoration.VERTICAL));
                          //  holder.rclArticulos.setLayoutManager(new GridLayoutManager(activity,2));
                            holder.rclArticulos.setLayoutManager(layoutManagerMember);
                            adapterMember.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    int opcselec=holder.rclArticulos.getChildAdapterPosition(view);
                                    String nombreselec= arrayListMember.get(opcselec).getPublicacion_id();
                                    Intent intent = new Intent(activity, activity_detalle_articulo.class);

                                   // Bundle b = new Bundle();
                                   // b.putString("articuloID", nombreselec);
                                    intent.putExtra("articulo",arrayListMember.get(opcselec));
                                    activity.startActivity(intent);

                                }
                            });
                            holder.rclArticulos.setAdapter(adapterMember);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(activity, "No se ha podido establecer conexión con el servidor" +
                                    " "+response, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        VolleyLog.e("Error: ", volleyError.getMessage());
                        System.out.println();
                        Toast.makeText(activity, "No se ha podido establecer conexión con el servidor" +
                                " "+volleyError.toString(), Toast.LENGTH_LONG).show();
                        Log.d("ERROR: ", volleyError.toString());
                    }
                });
        queue.add(jobReq);

       /* for (int i=1;i<=4;i++)
        {
            arrayListMember[0].add("Member"+i);
        }*/


    }

    @Override
    public int getItemCount() {
        return arrayListGroup.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        int view_type;
        TextView txtCategoria;
        RecyclerView rclArticulos;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCategoria=itemView.findViewById(R.id.nombreCat);
            rclArticulos=itemView.findViewById(R.id.rcvArticulosItem);
        }
    }
}
