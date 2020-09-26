package com.example.revistasuteq.adaptadores;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
     /*   ArrayList<String> arrayListMember=new ArrayList<>();
        for (int i=1;i<=4;i++)
        {
            arrayListMember.add("Member"+i);
        }
        adpArticulo adapterMember=new adpArticulo(arrayListMember);
        */
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
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombreArticulo=itemView.findViewById(R.id.txtnombreArticulo);
            txtFechaArticulo=itemView.findViewById(R.id.txtfechaArticulo);
        }
    }
}
