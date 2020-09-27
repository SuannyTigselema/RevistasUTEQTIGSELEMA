package com.example.revistasuteq;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.revistasuteq.WebService.Asynchtask;
import com.example.revistasuteq.WebService.WebService;
import com.example.revistasuteq.adaptadores.adpIdioma;
import com.example.revistasuteq.adaptadores.adpRevista;
import com.example.revistasuteq.modelos.Idioma;
import com.example.revistasuteq.objetos.revista;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public class MainActivity extends AppCompatActivity implements Asynchtask {
    Button btnContinuar;
    private  ArrayList<String>  list_of_items = new ArrayList<>();
    adpIdioma adaptador;


    private CharSequence[] mAlertItems;
    private boolean[] mSelectedItems;
    private String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#1B8300"));
        }
        Spinner Slenguajes = (Spinner) findViewById(R.id.spinner);
        TextView mensaje = (TextView) findViewById(R.id.textView3);
        TextView seleccionar = (TextView) findViewById(R.id.textView5);
        Button ingresar = (Button) findViewById(R.id.btnContinuar);

        handleSSLHandshake();

        //WebService
        Map<String, String> datos = new HashMap<String, String>();
        WebService ws= new WebService("https://revistas.uteq.edu.ec/ws/site.php",
                datos, MainActivity.this, MainActivity.this);
        ws.execute("GET");


        // Agregar animaciones
       /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, activityPrincipal.class);
                startActivity(intent);
                finish();
            }
        }, 3000);*/

        /*String url="https://revistas.uteq.edu.ec/ws/site.php";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray=response.getJSONArray("supported_locales");
                            String[] vector=new String[jsonArray.length()];
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject idiomas=jsonArray.getJSONObject(i);
                                vector[i]=idiomas.getString("locale");
                            }
                            String[] idiomas = vector;

                            Slenguajes.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,idiomas));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });*/


        /*RequestQueue request = Volley.newRequestQueue(MainActivity.this);
        StringRequest volley=new StringRequest(Request.Method.GET, "https://revistas.uteq.edu.ec/ws/site.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.length()>0){
                    try {
                        JSONObject jsonArray= new JSONObject(response);
                        JSONArray JSONlista =jsonArray.getJSONArray("supported_locales");
                        String[] vector=new String[jsonArray.length()];
                        for (int i = 0; i < JSONlista.length(); i++) {
                            JSONObject jsonObject = JSONlista.getJSONObject(i);
                            vector[i]=jsonObject.getString("locale");

                        }
                        String[] idiomas = vector;
                        Slenguajes.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,idiomas));

                    }catch (JSONException e){

                    }
                }
            } }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        request.add(volley);*/

        //En caso de que quieran hacer con el bton
        String[] idiomas = {"Español","English"};
        Slenguajes.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.lyt_spinneritem,idiomas));
        Slenguajes.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if(i==1)
                        {
                             mensaje.setText("Welcome to our mobile portal of scientific journals");
                             seleccionar.setText("Select a language to display");
                            ingresar.setText("ENTER");
                        }
                        else
                        {
                            mensaje.setText("Bienvenido a nuestro portal móvil de revistas científicas");
                            seleccionar.setText("Seleccione un idioma a mostrar");
                            ingresar.setText("INGRESAR");
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                    //add some code here
                }
        );
        btnContinuar=(Button) findViewById(R.id.btnContinuar);
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //mostrarDialog();

                String text = Slenguajes.getSelectedItem().toString();
                if (text=="Español"){
                    text="es_ES";
                }else {
                    text="en_US";
                }
                iniciar(text);
            }
        });
    }
    public void mostrarDialog()
    {

        mAlertItems = new CharSequence[]{
                "es_ES",
                "en_US",
        };
        final AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(Html.fromHtml("<font color='#0B871B'>Elige un idioma</font>"));
        builder.setSingleChoiceItems(mAlertItems, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
             //   Snackbar.make(btnContinuar,"item: "+mAlertItems[i],Snackbar.LENGTH_LONG).show();
                dialogInterface.dismiss();
                iniciar(mAlertItems[i].toString());
            }
        });
       builder.setIcon(R.drawable.idioma);
       builder.show();


    }
    public void iniciar(String idioma)
    {
        SharedPreferences prefs = getSharedPreferences("MyPREFERENCES",MainActivity.this.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("Idioma", idioma);
        editor.commit();

        if(idioma.equals("es_ES")){
            Locale localizacion = new Locale("es", "ES");
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
    }

    @Override
    public void processFinish(String result) throws JSONException {
        ArrayList<Idioma> lstIdiomas = new ArrayList<Idioma>();
        try {
            //Procesar APIRest Response
            JSONArray JSONlistaRevistas= new JSONArray(result);
            lstIdiomas = Idioma.JsonObjectsBuild(JSONlistaRevistas);
            adaptador = new adpIdioma(lstIdiomas);
        }
        catch (JSONException e){
            Toast.makeText(this.getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG);
        }
    }

    public static void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
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
}