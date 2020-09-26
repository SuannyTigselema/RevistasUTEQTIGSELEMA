package com.example.revistasuteq;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.revistasuteq.adaptadores.adpRevista;
import com.example.revistasuteq.objetos.revista;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    Button btnContinuar;
    private  ArrayList<String>  list_of_items = new ArrayList<>();

    private CharSequence[] mAlertItems;
    private boolean[] mSelectedItems;
    private String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        // Agregar animaciones
       /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, activityPrincipal.class);
                startActivity(intent);
                finish();
            }
        }, 3000);*/
        //cargarWebService();
        //En caso de que quieran hacer con el bton
        btnContinuar=(Button) findViewById(R.id.btnContinuar);
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            mostrarDialog();
            }
        });
    }
    public void cargarWebService()
    {
        String apiToken="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.Im51bGwi.IDMhwWdhl5Kb-VAkiL4WrpLHwzYJsN876uyi_qIxKDQ";
        String url="https://revistas.uteq.edu.ec/index.php/cyt/api/v1//site?apiToken="+apiToken+"";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jobReq = new JsonArrayRequest(Request.Method.GET, url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        revista rev=null;
                        try {
                            for (int i=0;i<response.length();i++){
                                rev=new revista();
                                JSONObject obj = response.getJSONObject(i);
                                rev.setNombre(obj.getString("supportedLocales"));
                                rev.setPortada_url(obj.getString("title"));
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

    }
    public void mostrarDialog()
    {
        mAlertItems = new CharSequence[]{
                "es_ES",
                "en_US",
                "pt_BR"
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
        Intent intent = new Intent(this, activityPrincipal.class);
        intent.putExtra("local", idioma);

        startActivity(intent);
        this.finish();
    }
}