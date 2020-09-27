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

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Locale;

import java.util.ArrayList;
import java.util.Locale;


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
}