package com.example.revistasuteq;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.revistasuteq.firebase.user;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#176803"));
        }

        requestQueue= Volley.newRequestQueue(getApplicationContext() );

        txtAutores = findViewById(R.id.txtAutores);
        txtPalabrasClave = findViewById(R.id.txtpalabrasclaveAD);
        txtResumen = findViewById(R.id.txtResumenAD);
        btnVer = findViewById(R.id.btnVer);
        btnVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDialogOpciones();
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
        ban = false;
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    ban = false;
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
                keys = art_selec.getLstKeywordss().get(i).getKeyword() + ", " + keys;
        }
        txtPalabrasClave.setText(keys);
        String autores = " ";
        for (int i = 0; i < art_selec.getLstAutores().size(); i++) {
            //autores=art_selec.getLstAutores().get(i).getNombres()+"-"+art_selec.getLstAutores().get(i).getFiliacion()+"\n"+autores;
            autores = "• "+art_selec.getLstAutores().get(i).getNombres() + "\n" + autores;
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

    private void enviar_visualizador(String url) {
        Notificar();
        //   Toast.makeText(activity_detalle_articulo.this, mostrarDialogOpciones(), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, activity_visualizador.class);
        intent.putExtra("url", url);
        startActivity(intent);
        //this.finish();
    }
    private  void mostrarDialogOpciones() {
        String urlPDF = "", urlHTML = "";
        //Lo recorre para separar la url de pdf y de html
        for (int i = 0; i < art_selec.getLstGaleys().size(); i++) {
            String label = art_selec.getLstGaleys().get(i).getLabel();
            if (label.equals("PDF")||label.equals("pdf")) {
                urlPDF = art_selec.getLstGaleys().get(i).getUrlViewGalley();
            } else {
                urlHTML = art_selec.getLstGaleys().get(i).getUrlViewGalley();
            }
        }
        final String[] opcElegida = {""};
        int size = art_selec.getLstGaleys().size();
        final CharSequence[] opciones = new CharSequence[size];
        String opc;
        for (int i = 0; i < size; i++) {
            //Las opciones de visualización de las que dispone el artículo
            opc = art_selec.getLstGaleys().get(i).getLabel();
            opciones[i] = opc;
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(Html.fromHtml("<font color='#166E2D'>"+getString(R.string.Opciones)+"</font>"));
        String finalUrlHTML = urlHTML;
        String finalUrlPDF = urlPDF;
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("PDF")||opciones[i].equals("pdf")) {
                    opcElegida[0] = finalUrlPDF;
                    enviar_visualizador(opcElegida[0]);
                } else {
                    if (opciones[i].equals("HTML")||opciones[i].equals("html")) {
                        opcElegida[0] = finalUrlHTML;
                        enviar_visualizador(opcElegida[0]);
                    } else {
                        dialogInterface.dismiss();
                    }
                }
            }
        });
        builder.setIcon(R.drawable.leer);
        builder.show();
    }
    public void Notificar(){
        try {
            try {
                JSONObject jsonObject=new JSONObject();
                String topic=art_selec.getPublicacion_id();
                jsonObject.put( "to","/topics/"+topic);
                JSONObject notificacion = new JSONObject();
                notificacion.put("title","Revistas Científicas UTEQ ");
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy, HH:mm", Locale.getDefault());
                Date date = new Date();
                String fecha = dateFormat.format(date);
                notificacion.put("body","El usuario "+ user.user +" ha descargado el artículo "+ art_selec.getTitulo()+" a las " + fecha.substring(12,17) +" del " + fecha.substring(0,10));
                jsonObject.put("notification",notificacion);
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
               try {
                   requestQueue.add(jsonObjectRequest);
               }catch (Exception ex){
                   ex.getMessage();
               }
                 } catch (JSONException e) {
                e.printStackTrace();
            }
            }catch (Exception e){

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
                        }
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
                        Articulo.put("tittle", art_selec.getTitulo());
                        Articulo.put("dateP", art_selec.getDate_published());
                        Articulo.put("doi", art_selec.getDoi());
                        //Articulo.put("autores", art_selec.getLstAutores());
                        //Articulo.put("galeys", art_selec.getLstGaleys());
                        //Articulo.put("keywords", art_selec.getLstKeywordss());
                        Articulo.put("resume", art_selec.getResumen());
                        Articulo.put("seccion", art_selec.getSeccion());
                        Articulo.put("seccionid", art_selec.getSection_id());
                        Articulo.put("submissionid", art_selec.getSubmission_id());
                        Articulo.put("seq", art_selec.getSeq());
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
        }
        if(id == R.id.btnCreditos) {
            Intent intent = new Intent(this, activity_creditos.class);
            startActivity(intent);
        }
        if(id == R.id.btnIdioma) {
            //Invocar algo para el idioma
        }
        return super.onOptionsItemSelected(item);
    }
}