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

    public TextView lblVolumen;
    public TextView lblNumero;
    public TextView lblTitulo;
    public TextView lblDOI;
    public TextView lblFechaPublicacion;
    public ImageView imgPortadaE;

    public adpEdicion(Context context, ArrayList<edicion> lista) {
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
        int view_type;
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
        View view;
        view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ediciones,null,false);
        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adpEdicion.MyViewHolder holder, int position) {
        try {
            lblVolumen.setText(mLista.get(position).getVolumen());
            lblNumero.setText(mLista.get(position).getNumero());
            lblTitulo.setText(mLista.get(position).getTitulo());
            lblDOI.setText(mLista.get(position).getDoi());
            lblFechaPublicacion.setText(mLista.get(position).getFecha_publicacion());
            Glide.with(mContext)
                    .load(mLista.get(position).getImagen())
                    .into(imgPortadaE);
        }catch (Exception e){
           String res=e.toString();
        }
    }

    @Override
    public int getItemCount() {
        return mLista.size();
    }
}
