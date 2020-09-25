package com.example.revistasuteq.adaptadores;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.revistasuteq.R;
import com.example.revistasuteq.objetos.articulo;

import java.util.ArrayList;

public class adpArticulo extends RecyclerView.Adapter<adpArticulo.MyViewHolder> implements View.OnClickListener {

    ArrayList<articulo> arrayListMember;

    private View.OnClickListener listener;

    adpArticulo(ArrayList<articulo> arrayListMember) {
        this.arrayListMember = arrayListMember;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_articulos, parent, false);
        view.setOnClickListener(this);
        return new adpArticulo.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txtNombreArticulo.setText(arrayListMember.get(position).getTitulo());
        holder.txtFechaArticulo.setText(arrayListMember.get(position).getFecha_publicacion());

        //Preguntar si está suscrito o no
        //Si esta suscrito el tag es "noti_si"
        //Si no lo está el tag es "noti_no"
        //---------------------------------------------------------------------
        holder.btnActivarNotificar.setTag("noti_no");
        //---------------------------------------------------------------------
        if(holder.btnActivarNotificar.getTag().equals("noti_si"))
            holder.btnActivarNotificar.setImageResource(R.drawable.icon_notificar_activo);
        else
            holder.btnActivarNotificar.setImageResource(R.drawable.icon_notificar_no);
        //---------------------------------------------------------------------

        holder.setOnClickListeners();

    }

    @Override
    public int getItemCount() {
        return arrayListMember.size();
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        int view_type;
        TextView txtNombreArticulo;
        TextView txtFechaArticulo;
        ImageView btnActivarNotificar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombreArticulo = itemView.findViewById(R.id.txtnombreArticulo);
            txtFechaArticulo = itemView.findViewById(R.id.txtfechaArticulo);
            btnActivarNotificar = itemView.findViewById(R.id.btnNotificar_Articulo);

        }

        public void setOnClickListeners() {
            btnActivarNotificar.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if (btnActivarNotificar.getTag().equals("noti_no")) {
                //Cuando se suscribe
                btnActivarNotificar.setImageResource(R.drawable.icon_notificar_activo);
                btnActivarNotificar.setTag("noti_si");
            } else {
                //Cuando deja de suscribirse
                btnActivarNotificar.setImageResource(R.drawable.icon_notificar_no);
                btnActivarNotificar.setTag("noti_no");
            }
            //Obtener el id de la publicación
            Toast.makeText(view.getContext(), "Selección " + arrayListMember.get(getAdapterPosition()).getPublicacion_id(), Toast.LENGTH_SHORT).show();

//            Intent intent = new Intent(view.getContext(), PDF.class);
//            Bundle b = new Bundle();
//            b.putString("url", items.get(getAdapterPosition()).getGaleys());
//
//            intent.putExtras(b);
//            view.getContext().startActivity(intent);
        }
    }
}
