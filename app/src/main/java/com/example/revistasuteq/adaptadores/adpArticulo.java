package com.example.revistasuteq.adaptadores;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.revistasuteq.R;
import com.example.revistasuteq.objetos.articulo;

import java.util.ArrayList;

public class adpArticulo extends RecyclerView.Adapter<adpArticulo.MyViewHolder>  implements View.OnClickListener{

    ArrayList<articulo> arrayListMember;

    private View.OnClickListener listener;
    adpArticulo( ArrayList<articulo> arrayListMember)
    {
        this.arrayListMember=arrayListMember;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_articulos,parent,false);
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
        //Se puede omitir el tag, xk lo usaba para el clic..
        //---------------------------------------------------------------------
        holder.icon_notificacion.setTag("noti_no");
        //---------------------------------------------------------------------
        if(holder.icon_notificacion.getTag().equals("noti_si"))
            holder.icon_notificacion.setImageResource(R.drawable.icon_notificar_activo);
        else
            holder.icon_notificacion.setImageResource(R.drawable.icon_notificar_no);
        //---------------------------------------------------------------------
    }

    @Override
    public int getItemCount() {
        return arrayListMember.size();
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
        TextView txtNombreArticulo;
        TextView txtFechaArticulo;
        ImageView icon_notificacion;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombreArticulo=itemView.findViewById(R.id.txtnombreArticulo);
            txtFechaArticulo=itemView.findViewById(R.id.txtfechaArticulo);
            icon_notificacion=itemView.findViewById(R.id.btnNotificar_Articulo);
        }
    }
}
