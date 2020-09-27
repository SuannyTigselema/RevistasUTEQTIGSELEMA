package com.example.revistasuteq;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.revistasuteq.objetos.articulo;

public class activity_visualizador extends AppCompatActivity {

    String url_selec="";
    ProgressDialog progress;

    WebView myWebView;
    CharSequence galeys[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizador);
        progress=new ProgressDialog(this);
        progress.setMessage("Cargando...");
        progress.show();

        //Recibe un objeto de tipo artículo que lo envía el activity_detalle_artículo
        Bundle extras = getIntent().getExtras();
        url_selec = extras.getString("url");


        myWebView = (WebView) findViewById(R.id.webview);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient());
        myWebView.getSettings().setBuiltInZoomControls(true);
        myWebView.loadUrl(url_selec);

        progress.hide();
         }

}