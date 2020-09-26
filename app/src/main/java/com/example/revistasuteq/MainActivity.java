package com.example.revistasuteq;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;


import android.content.DialogInterface;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.example.revistasuteq.objetos.edicion;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    Button btnContinuar;
    TextView txtTitulo;
    private  ArrayList<String>  list_of_items = new ArrayList<>();

    final ArrayList<String> supported_locales = new ArrayList<>();
    String primary_locale;
    private boolean[] mSelectedItems;
    private String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#176803"));
        }
        // Agregar animaciones
       /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, activityPrincipal.class);
                startActivity(intent);
                finish();
            }
        }, 3000);*/
        //En caso de que quieran hacer con el bton
        txtTitulo=(TextView) findViewById(R.id.lblrevista);
        cargarDatos();

        btnContinuar=(Button) findViewById(R.id.btnContinuar);
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            mostrarDialog();
            }
        });
    }
    public void mostrarDialog()
    {
        final AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(Html.fromHtml("<font color='#0B871B'>Elige un idioma</font>"));
        builder.setSingleChoiceItems(supported_locales.toArray(new String[0]), 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
             //   Snackbar.make(btnContinuar,"item: "+mAlertItems[i],Snackbar.LENGTH_LONG).show();
                dialogInterface.dismiss();
                iniciar(supported_locales.toArray(new String[0])[i]);
            }
        });
       builder.setIcon(R.drawable.idioma);
       builder.show();


    }
    public void iniciar(String idioma)
    {

        if(idioma.equals("es_ES")){
            Locale localizacion = new Locale("en", "ES");
            Locale.setDefault(localizacion);
            Configuration config = new Configuration();
            config.locale = localizacion;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
            Intent intent = new Intent(this, activityPrincipal.class);
            intent.putExtra("local", idioma);
            startActivity(intent);
            this.finish();
        }
        if(idioma.equals("en_US")){
            Locale localizacion = new Locale("en", "US");
            Locale.setDefault(localizacion);
            Configuration config = new Configuration();
            config.locale = localizacion;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
            Intent intent = new Intent(this, activityPrincipal.class);
            intent.putExtra("local", idioma);
            startActivity(intent);
            this.finish();
        }
        if(idioma.equals("pt_BR")){
            Locale localizacion = new Locale("pt", "BR");
            Locale.setDefault(localizacion);
            Configuration config = new Configuration();
            config.locale = localizacion;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
            Intent intent = new Intent(this, activityPrincipal.class);
            intent.putExtra("local", idioma);
            startActivity(intent);
            this.finish();
        }
    }

    public void cargarDatos()
    {
        final String[] titulo = new String[1];
        String url="https://revistas.uteq.edu.ec/ws/site.php";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jobReq = new JsonArrayRequest(Request.Method.GET, url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i=0;i<response.length();i++){
                                JSONObject obj = response.getJSONObject(i);
                                titulo[0] =obj.getString("title");
                                txtTitulo.setText(titulo[0]);
                               // primary_locale=obj.getString("primary_locale");
                                for (int j=0; j<obj.getJSONArray("supported_locales").length();j++)
                                {
                                    JSONObject obj_locales=obj.getJSONArray("supported_locales").getJSONObject(j);
                                    supported_locales.add(obj_locales.getString("locale"));
                                }
                                Toast.makeText(MainActivity.this, supported_locales.get(1), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "No se ha podido establecer conexión con el servidor" +
                                    " "+response, Toast.LENGTH_LONG).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        VolleyLog.e("Error: ", volleyError.getMessage());
                        System.out.println();
                        Toast.makeText(MainActivity.this, "No se ha podido establecer conexión con el servidor" +
                                " "+volleyError.toString(), Toast.LENGTH_LONG).show();
                        Log.d("ERROR: ", volleyError.toString());

                    }
                });
        queue.add(jobReq);

        Ediciones.handleSSLHandshake();


    }
}