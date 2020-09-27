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
    articulo art_selec;
    String urlPDF="",url="";
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
        art_selec = (articulo) getIntent().getSerializableExtra("articulo");

        //Lo recorre para separar la url de pdf y de html
        for (int i=0;i<art_selec.getLstGaleys().size();i++) {
          String label=  art_selec.getLstGaleys().get(i).getLabel();
          if(label.equals("PDF")) {
              urlPDF = art_selec.getLstGaleys().get(i).getUrlViewGalley();
          }
          else
          {
              url = art_selec.getLstGaleys().get(i).getUrlViewGalley();
          }
        }


        myWebView = (WebView) findViewById(R.id.webview);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient());
        myWebView.getSettings().setBuiltInZoomControls(true);

        mostrarDialogOpciones();

        progress.hide();
         }

    private void mostrarDialogOpciones() {
        int size=art_selec.getLstGaleys().size();
        final CharSequence[] opciones=new CharSequence[size];
        String opc;
        for (int i =0; i<size;i++)
        {
            //Las opciones de visualización de las que dispone el artículo
            opc=art_selec.getLstGaleys().get(i).getLabel();
            opciones[i]=opc;
        }

        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Elige una Opción");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("PDF")){
                    myWebView.loadUrl(urlPDF);
                }else{
                    if (opciones[i].equals("HTML")){
                        myWebView.loadUrl(url);
                    }else{
                        dialogInterface.dismiss();
                    }
                }
            }
        });
        builder.show();
    }
}