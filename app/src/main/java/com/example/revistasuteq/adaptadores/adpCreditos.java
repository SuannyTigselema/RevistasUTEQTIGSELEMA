package com.example.revistasuteq.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.revistasuteq.R;
import com.example.revistasuteq.modelos.Credito;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

public class adpCreditos extends RecyclerView.Adapter<adpCreditos.ViewHolder> implements View.OnClickListener {

    private List<Credito> datos;
    public adpCreditos(List<Credito> datos){this.datos = datos;}

    public static boolean showShimmer = true;
    int cantShimmer = 5;
    private View.OnClickListener listener;

    @NonNull
    @Override
    public adpCreditos.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_credito,null,false);

        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adpCreditos.ViewHolder holder, int position) {
        try {
            if(showShimmer)
            {
                holder.shimmerFrameLayout.startShimmer();
            }
            else
                {
                holder.shimmerFrameLayout.stopShimmer();
                holder.shimmerFrameLayout.setShimmer(null);

                holder.txtNombreAutor.setBackground(null);

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
        TextView txtNombreAutor;
        ImageView btnShimmer;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shimmerFrameLayout = itemView.findViewById(R.id.shimmer);
            txtNombreAutor = (TextView) itemView.findViewById(R.id.txtNombreAutor);
            btnShimmer = itemView.findViewById(R.id.imageViewliAu);
        }

        public void asignar_datos(Credito valor) {
            txtNombreAutor.setText(valor.getNombre());
           // btnShimmer.setText("VER");
        }


    }
}
