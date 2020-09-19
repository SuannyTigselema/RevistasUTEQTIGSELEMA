package com.example.revistasuteq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.revistasuteq.objetos.articulo;

public class activity_detalle_articulo extends AppCompatActivity {

    TextView txtTitulo,txtDOI,txtPalabrasClave,txtResumen,txtAutores;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_articulo);
        txtTitulo=findViewById(R.id.txtTituloDA);
        txtDOI=findViewById(R.id.txtDoiAD);
        txtAutores=findViewById(R.id.txtAutores);
        txtPalabrasClave=findViewById(R.id.txtpalabrasclaveAD);
        txtResumen=findViewById(R.id.txtResumenAD);

        articulo  art_selec = (articulo) getIntent().getSerializableExtra("articulo");
        txtTitulo.setText(art_selec.getTitulo());
        txtDOI.setText(art_selec.getDoi());
        txtResumen.setText(Html.fromHtml(art_selec.getResumen()));
      //  txtResumen.setText(art_selec.toString());
        String keys=" ";
        for (int i =0; i<art_selec.getLstKeywordss().size();i++)
        {
            if(i==0)
                keys=art_selec.getLstKeywordss().get(i).getKeyword()+".";
            else
                keys=art_selec.getLstKeywordss().get(i).getKeyword()+","+keys;
        }
        txtPalabrasClave.setText(keys);
        String autores=" ";
        for (int i =0; i<art_selec.getLstAutores().size();i++)
        {
            //autores=art_selec.getLstAutores().get(i).getNombres()+"-"+art_selec.getLstAutores().get(i).getFiliacion()+"\n"+autores;
            autores=art_selec.getLstAutores().get(i).getNombres()+"\n"+autores;
        }
        txtAutores.setText(autores);
       // Bundle b = this.getIntent().getExtras();
        //String articuloID=b.getString("articuloID");
        Toast toast1=Toast.makeText(getApplicationContext(),
                "Seleccionaste: " + art_selec.getFecha_publicacion(), Toast.LENGTH_SHORT);
        toast1.show();

        txtDOI.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view) {
                Uri uri= Uri.parse(txtDOI.getText().toString());
                Intent intent=new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
    }
}