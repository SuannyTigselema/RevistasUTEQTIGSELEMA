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
import java.util.List;

public class activity_detalle_articulo extends AppCompatActivity {
    articulo art_selec;
    TextView txtTitulo, txtDOI, txtPalabrasClave, txtResumen, txtAutores;
    String doi;
    Boolean ban;
    Button btnVer, btnSuscribirse_Detalle;
    int imgResource;

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
        art_selec.getPublicacion_id();
        doi = (art_selec.getDoi());
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(getString(R.string.usuario));
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String json = snapshot.child("Suscripciones").getValue().toString();
                List<String> sList = new ArrayList<String>();
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    ban = false;
                    for (int i = 0; i < jsonObject.length(); i++) {

                        if (doi.equals(jsonObject.get("doi").toString())) {
                            ban = true;
                        }
                        //sList.add(jsonObject.get("doi").toString());
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
                } catch (JSONException e) {
                    e.printStackTrace();
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

    public void suscribirse(View view) {
        if (ban) {
            FirebaseMessaging.getInstance().setAutoInitEnabled(true);
            FirebaseMessaging.getInstance().unsubscribeFromTopic(doi).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(activity_detalle_articulo.this, "Ya no recibira notificaciones de este artículo", Toast.LENGTH_SHORT).show();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference(getString(R.string.usuario));
                    JSONObject object = new JSONObject();
                    try {
                        object.put("doi", "Nul");
                        myRef.child("Suscripciones").setValue(object.toString());
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
            FirebaseMessaging.getInstance().subscribeToTopic(doi).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(activity_detalle_articulo.this, "Recibira notificaciones de este artículo", Toast.LENGTH_SHORT).show();
                    //se subscribio a topic general
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference(getString(R.string.usuario));
                    JSONObject object = new JSONObject();
                    try {
                        object.put("doi", doi);
                        myRef.child("Suscripciones").setValue(object.toString());
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