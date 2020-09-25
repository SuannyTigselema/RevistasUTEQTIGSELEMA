package com.example.revistasuteq;

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

public class activity_detalle_articulo extends AppCompatActivity {
    articulo art_selec;
    TextView txtTitulo, txtDOI, txtPalabrasClave, txtResumen, txtAutores;
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
        imgResource = R.drawable.icon_suscrito;
        btnSuscribirse_Detalle.setCompoundDrawablesWithIntrinsicBounds(imgResource, 0, 0, 0);
        btnSuscribirse_Detalle.setText("Suscrito");
        //------------------------------------------------------------------------------------------------------
        //------------------------------------------------------------------------------------------------------
        imgResource = R.drawable.icon_no_suscrito_blanco;
        btnSuscribirse_Detalle.setCompoundDrawablesWithIntrinsicBounds(imgResource, 0, 0, 0);
        btnSuscribirse_Detalle.setText("Suscribirse");
        //------------------------------------------------------------------------------------------------------


        art_selec = (articulo) getIntent().getSerializableExtra("articulo");
        txtTitulo.setText(art_selec.getTitulo());
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

    public void suscribirse(View view)
    {
        //Si se suscribió cambiar ícono e imagen
        int imgResource = R.drawable.icon_suscrito;
        btnSuscribirse_Detalle.getResources();
        btnSuscribirse_Detalle.setCompoundDrawablesWithIntrinsicBounds(imgResource, 0, 0, 0);
        btnSuscribirse_Detalle.setText("Suscrito");

//        //Si dejó de suscribirse utilizar esta imagen
//        imgResource = R.drawable.icon_no_suscrito_blanco;
//        btnSuscribirse_Detalle.getResources();
//        btnSuscribirse_Detalle.setCompoundDrawablesWithIntrinsicBounds(imgResource, 0, 0, 0);
//        btnSuscribirse_Detalle.setText("Suscribirse");
    }
}