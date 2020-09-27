package com.example.revistasuteq;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.revistasuteq.objetos.articulo;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class activity_visualizador extends AppCompatActivity {

    String url_selec="";
    ProgressDialog progress;

    WebView myWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizador);
        progress=new ProgressDialog(this);
        progress.setMessage("Cargando...");
        progress.show();
     try {
         Bundle extras = getIntent().getExtras();
         url_selec = extras.getString("url");


         myWebView = (WebView) findViewById(R.id.webview);
         myWebView.getSettings().setJavaScriptEnabled(true);
         myWebView.setWebViewClient(new WebViewClient());
         myWebView.getSettings().setBuiltInZoomControls(true);
         myWebView.loadUrl(url_selec);
         handleSSLHandshake();

         progress.hide();

         myWebView.setWebViewClient(new WebViewClient() {
             @Override
             public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                 handler.proceed();
                 //Toast toast1 = Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT);
             }
         });
        // myWebView.loadUrl("https://revistas.uteq.edu.ec/index.php/csye/article/download/201/313/428");
     }catch (Exception e)
     {
        e.printStackTrace();
     }
     }

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


}