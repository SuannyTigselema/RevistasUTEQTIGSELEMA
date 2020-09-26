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
import com.example.revistasuteq.Ediciones;
import com.example.revistasuteq.R;
import com.example.revistasuteq.objetos.categoria;
import com.example.revistasuteq.objetos.edicion;

import java.util.ArrayList;

public class adpEdicion extends RecyclerView.Adapter<adpEdicion.MyViewHolder>
        implements View.OnClickListener{
    private static final int TYPE_HEADER=0;
    private static final int TYPE_LIST=0;


    private Context mContext;
    private View.OnClickListener listener;

    private ArrayList<edicion> mLista;



    public adpEdicion(Context context, ArrayList<edicion> lista) {
        mContext = context;
        this.mLista=lista;
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
        int view_type;
        public TextView lblVolumen,lblNumero, lblTitulo, lblDOI, lblFechaPublicacion;
        public ImageView imgPortadaE;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            lblVolumen=(TextView) itemView.findViewById(R.id.txtVolumen);
            lblNumero=(TextView) itemView.findViewById(R.id.txtNumero);
            lblTitulo=(TextView) itemView.findViewById(R.id.txtTitulo);
            lblDOI=(TextView) itemView.findViewById(R.id.txtDOI);
            lblFechaPublicacion=(TextView) itemView.findViewById(R.id.txtFechaPublicacion);
            imgPortadaE=(ImageView) itemView.findViewById(R.id.imgPortadaE);
       }
    }

    @NonNull
    @Override
    public adpEdicion.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflr = LayoutInflater.from(mContext);
        View view=inflr.inflate(R.layout.item_ediciones,null,false);
        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adpEdicion.MyViewHolder holder, int position) {
        try {
            edicion revi=mLista.get(position);
            holder.lblVolumen.setText(revi.getVolumen());
            holder.lblNumero.setText(revi.getNumero());
            holder.lblTitulo.setText(revi.getTitulo());
            holder.lblDOI.setText(revi.getDoi());
            holder.lblFechaPublicacion.setText(revi.getFecha_publicacion());
            Glide.with(mContext)
                    .load(revi.getImagen())
                    .into(holder.imgPortadaE);
        }catch (Exception e){
           String res=e.toString();
        }
    }

    @Override
    public int getItemCount() {
        return mLista.size();
    }
}
