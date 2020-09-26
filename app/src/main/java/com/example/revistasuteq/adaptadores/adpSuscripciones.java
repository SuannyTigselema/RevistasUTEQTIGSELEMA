package com.example.revistasuteq.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.revistasuteq.R;
import com.example.revistasuteq.modelos.Articulo;
import com.facebook.shimmer.ShimmerFrameLayout;
import java.util.List;

public class adpSuscripciones extends RecyclerView.Adapter<adpSuscripciones.ViewHolder> implements View.OnClickListener {

    private List<Articulo> datos;
    public adpSuscripciones(List<Articulo> datos){this.datos = datos;}

    public static boolean showShimmer = true;
    int cantShimmer = 5;
    private View.OnClickListener listener;

    @NonNull
    @Override
    public adpSuscripciones.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lyt_shimmer,null,false);

        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adpSuscripciones.ViewHolder holder, int position) {
        try {
            if(showShimmer)
            {
                holder.shimmerFrameLayout.startShimmer();
            }
            else
                {
                holder.shimmerFrameLayout.stopShimmer();
                holder.shimmerFrameLayout.setShimmer(null);

                holder.txtNombreArticulo.setBackground(null);
                holder.txtFechaArticulo.setBackground(null);

                holder.asignar_datos(datos.get(position));
                //holder.txtNombreplatillo.startAnimation((Animation) AnimationUtils.loadAnimation(getAppl,R.anim.scroll_animation));
            }
            //holder.asignar_datos(datos.get(position));
        }
        catch (Exception e){

        }
    }

    @Override
    public int getItemCount() {
        try{
        return showShimmer ? cantShimmer : datos.size();}catch (Exception e) {return cantShimmer;}
    }
    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ShimmerFrameLayout shimmerFrameLayout;
        TextView txtNombreArticulo, txtFechaArticulo;
        Button btnShimmer;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shimmerFrameLayout = itemView.findViewById(R.id.shimmer);
            txtNombreArticulo = (TextView) itemView.findViewById(R.id.txtNombreArticuloS);
            txtFechaArticulo = (TextView) itemView.findViewById(R.id.txtFechaArticuloS);
            btnShimmer = itemView.findViewById(R.id.btnShimmer);
        }

        public void asignar_datos(Articulo valor) {
            txtNombreArticulo.setText(valor.getNombre());
            txtFechaArticulo.setText(valor.getFecha());
            btnShimmer.setText("VER");
        }


    }
}
