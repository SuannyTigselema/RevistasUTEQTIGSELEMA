package com.example.revistasuteq.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.revistasuteq.R;
import com.example.revistasuteq.certificaciones.GlideApp;
import com.example.revistasuteq.objetos.edicion;

import java.util.ArrayList;

public class adpEdicionR extends RecyclerView.Adapter<adpEdicionR.MyViewHolder>
        implements View.OnClickListener{
    private static final int TYPE_HEADER=0;
    private static final int TYPE_LIST=0;


    private Context mContext;
    private View.OnClickListener listener;

    private ArrayList<edicion> mLista;

    public adpEdicionR(Context context, ArrayList<edicion> lista) {
        mContext = context;
        mLista=lista;
    }

    @Override
    public void onClick(View view) {
        if(listener!=null)
        {
            listener.onClick(view);
        }
    }
    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPortada;
        TextView txtTitulo, txtDoi, txtPubli;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPortada= (ImageView)itemView.findViewById(R.id.imgEdiPort);
            txtTitulo= (TextView) itemView.findViewById(R.id.txtEdiTitulo);
            txtDoi= (TextView) itemView.findViewById(R.id.txtEdiDOI);
            txtPubli= (TextView) itemView.findViewById(R.id.txtEdiPublicado);

       }
    }

    @NonNull
    @Override
    public adpEdicionR.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflr = LayoutInflater.from(mContext);
        View view=inflr.inflate(R.layout.itemediciones,null,false);
        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adpEdicionR.MyViewHolder holder, int position) {
        try {

           holder.txtTitulo.setText("Vol. "+mLista.get(position).getVolumen()
                    +" Núm. "+mLista.get(position).getNumero()
                    +": "+mLista.get(position).getTitulo());

            holder.txtDoi.setText(mLista.get(position).getDoi());
            holder.txtPubli.setText(mLista.get(position).getFecha_publicacion());

            GlideApp.with(mContext)
                    .load(mLista.get(position).getImagen())
                    .into(holder.imgPortada);



        }catch (Exception e){
           String res=e.toString();
        }
    }

    @Override
    public int getItemCount() {
        return mLista.size();
    }
}
