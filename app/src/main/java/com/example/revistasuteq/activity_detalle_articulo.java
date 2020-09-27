package com.example.revistasuteq;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.revistasuteq.objetos.articulo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class activity_detalle_articulo extends AppCompatActivity {
    articulo art_selec;
    TextView txtTitulo, txtDOI, txtPalabrasClave, txtResumen, txtAutores;
    String doi;
    Boolean ban;
    Button btnVer, btnSuscribirse_Detalle;
    int imgResource;
    RequestQueue requestQueue;
    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_articulo);
        btnSuscribirse_Detalle = findViewById(R.id.btnNotificar_Articulo_Detalle);
        txtTitulo = findViewById(R.id.txtTituloDA);
        txtDOI = findViewById(R.id.txtDoiAD);

        txtAutores = findViewById(R.id.txtAutores);
        txtPalabrasClave = findViewById(R.id.txtpalabrasclaveAD);
        txtResumen = findViewById(R.id.txtResumenAD);
        btnVer = findViewById(R.id.btnVer);
        btnVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviar_visualizador();
            }
        });
        //Preguntar si está suscrito o no
        //------------------------------------------------------------------------------------------------------
        //imgResource = R.drawable.icon_suscrito;
        //btnSuscribirse_Detalle.setCompoundDrawablesWithIntrinsicBounds(imgResource, 0, 0, 0);
        //btnSuscribirse_Detalle.setText("Suscrito");
        //------------------------------------------------------------------------------------------------------
        //------------------------------------------------------------------------------------------------------
        //imgResource = R.drawable.icon_no_suscrito_blanco;
        //btnSuscribirse_Detalle.setCompoundDrawablesWithIntrinsicBounds(imgResource, 0, 0, 0);
        //btnSuscribirse_Detalle.setText("Suscribirse");
        //------------------------------------------------------------------------------------------------------
        art_selec = (articulo) getIntent().getSerializableExtra("articulo");
        txtTitulo.setText(art_selec.getTitulo());
        //art_selec.getPublicacion_id();
        doi = (art_selec.getPublicacion_id());
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(getString(R.string.usuario));
        jsonArray= new JSONArray();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ban = false;
                try {
                    String json = snapshot.child("Suscripciones").getValue().toString();
                    try {
                        String id= art_selec.getPublicacion_id();
                        jsonArray = new JSONArray(json);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject= jsonArray.getJSONObject(i);
                            if (id.equals(jsonObject.get("id").toString())) {
                                ban = true;
                            }
                            //sList.add(jsonObject.get("doi").toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }catch (Exception e){
                }
                if (ban) {
                    int imgResource = R.drawable.icon_suscrito;
                    //int imgResource = R.drawable.icon_no_suscrito_blanco;
                    btnSuscribirse_Detalle.getResources();
                    btnSuscribirse_Detalle.setCompoundDrawablesWithIntrinsicBounds(imgResource, 0, 0, 0);
                    btnSuscribirse_Detalle.setText(R.string.Suscripto);
                } else {
                    imgResource = R.drawable.icon_no_suscrito_blanco;
                    btnSuscribirse_Detalle.getResources();
                    btnSuscribirse_Detalle.setCompoundDrawablesWithIntrinsicBounds(imgResource, 0, 0, 0);
                    btnSuscribirse_Detalle.setText(R.string.SuscribirseArticuloDetall);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        txtDOI.setText(art_selec.getDoi());
        txtResumen.setText(Html.fromHtml(art_selec.getResumen()));

        //  txtResumen.setText(art_selec.toString());
        String keys = " ";
        for (int i = 0; i < art_selec.getLstKeywordss().size(); i++) {
            if (i == 0)
                keys = art_selec.getLstKeywordss().get(i).getKeyword() + ".";
            else
                keys = art_selec.getLstKeywordss().get(i).getKeyword() + "," + keys;
        }
        txtPalabrasClave.setText(keys);
        String autores = " ";
        for (int i = 0; i < art_selec.getLstAutores().size(); i++) {
            //autores=art_selec.getLstAutores().get(i).getNombres()+"-"+art_selec.getLstAutores().get(i).getFiliacion()+"\n"+autores;
            autores = art_selec.getLstAutores().get(i).getNombres() + "\n" + autores;
        }
        txtAutores.setText(autores);
        // Bundle b = this.getIntent().getExtras();
        //String articuloID=b.getString("articuloID");
       /* Toast toast1=Toast.makeText(getApplicationContext(),
                "Seleccionaste: " + art_selec.getFecha_publicacion(), Toast.LENGTH_SHORT);
        toast1.show();*/

        txtDOI.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(txtDOI.getText().toString());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    private void enviar_visualizador() {
        Intent intent = new Intent(this, activity_visualizador.class);
        intent.putExtra("articulo", art_selec);

        startActivity(intent);
        this.finish();
    }

    public void Notificar(){
        try {
            JSONObject jsonObject=new JSONObject();
            String topic=art_selec.getPublicacion_id();
            jsonObject.put( "to","/topics/"+topic);
            JSONObject notificacion = new JSONObject();
            notificacion.put("titulo","Estan leyenendo el articulo "+ art_selec.getTitulo());
            notificacion.put("detalle","El usuario "+ getString(R.string.usuario));
            jsonObject.put("data",notificacion);
            String URL="https://fcm.googleapis.com/fcm/send";
            JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.POST,URL,jsonObject,null,null){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> header= new HashMap<>();
                    header.put("content-type","application/json");
                    header.put("authorization","key=AAAAAjKaNiM:APA91bF36kAxkwzCp4m54YHXKQoV5d9Wi8vGNHXtHsvn1ESuIoHBvPAwOhra0-YCE36tnREriY6rIGxorsv6SqxWGHZZ9lAkuj5dC0Mwkdks_4H32Ti1pGUIZXXlm-WwcFGcHYqkotRO");
                    return  header;
                }
            };
            requestQueue.add(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void suscribirse(View view) {
        if (ban) {
            FirebaseMessaging.getInstance().setAutoInitEnabled(true);
            FirebaseMessaging.getInstance().unsubscribeFromTopic(art_selec.getPublicacion_id()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(activity_detalle_articulo.this, "Ya no recibirá notificaciones de este artículo", Toast.LENGTH_SHORT).show();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference(getString(R.string.usuario));
                    JSONArray array = new JSONArray();
                    try {

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject= jsonArray.getJSONObject(i);
                            if (art_selec.getPublicacion_id().equals(jsonObject.get("id").toString())) {
                                jsonArray.remove(i);
                            }
                            //sList.add(jsonObject.get("doi").toString());
                        }
                        /*
                        JSONObject Articulo = new JSONObject();
                        JSONObject Lista = new JSONObject();
                        Articulo.put("id", art_selec.getPublicacion_id());
                        Articulo.put("fecha", art_selec.getFecha_publicacion());
                        array.put(Articulo);
                        //Lista.put("Articulo",Articulo);
                        array.put(Articulo);
                        array.put(Articulo);
                        String josn=array.toString();
                         */
                        myRef.child("Suscripciones").setValue(jsonArray.toString());
                        Toast.makeText(activity_detalle_articulo.this, "Ya no recibirá notificaciones de este artículo", Toast.LENGTH_SHORT).show();
                        //guardado en la bd
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            //Si se suscribió cambiar ícono e imagen
            FirebaseMessaging.getInstance().setAutoInitEnabled(true);
            FirebaseMessaging.getInstance().subscribeToTopic(art_selec.getPublicacion_id()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(activity_detalle_articulo.this, "Recibirá notificaciones de este artículo", Toast.LENGTH_SHORT).show();
                    //se subscribio a topic general
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference(getString(R.string.usuario));
                    JSONArray array = new JSONArray();
                    try {
                        JSONObject Articulo = new JSONObject();
                        Articulo.put("id", art_selec.getPublicacion_id());
                        Articulo.put("fecha", art_selec.getFecha_publicacion());
                        //array.put(Articulo);
                        jsonArray.put(Articulo);
                        myRef.child("Suscripciones").setValue(jsonArray.toString());
                        //guardado en la bd
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //items.stream().collect(Collectors.toMap(Item::getValue, Item::getType));
                    //Toast.makeText(activity_detalle_articulo.this, "Se guardo en la bd como un articulo a recibir notificaciones", Toast.LENGTH_SHORT).show();
                }
            });
//        //Si dejó de suscribirse utilizar esta imagen
//        imgResource = R.drawable.icon_no_suscrito_blanco;
//        btnSuscribirse_Detalle.getResources();
//        btnSuscribirse_Detalle.setCompoundDrawablesWithIntrinsicBounds(imgResource, 0, 0, 0);
//        btnSuscribirse_Detalle.setText("Suscribirse");
        }
    }
}