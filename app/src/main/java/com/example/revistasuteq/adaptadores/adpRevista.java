package com.example.revistasuteq.adaptadores;

import android.content.Context;
import android.text.Html;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.bumptech.glide.Glide;
import com.example.revistasuteq.R;
import com.example.revistasuteq.objetos.revista;

import org.json.JSONArray;

import java.util.ArrayList;

public class adpRevista extends RecyclerView.Adapter<adpRevista.MyViewHolder>
        implements View.OnClickListener {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_LIST = 0;



    private Context mContext;
    private int selectedPos = RecyclerView.NO_POSITION;
    private View.OnClickListener listener;
    private ArrayList<revista> mLista;



    public adpRevista(Context context, ArrayList<revista> lista) {
        mContext = context;
        mLista = lista;
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }


    @NonNull
    @Override
    public adpRevista.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_revista, null, false);

        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }


    @Override
    public void onBindViewHolder(@NonNull adpRevista.MyViewHolder holder, int position) {
        try {
            holder.lblNombre.setText(mLista.get(position).getNombre());
            holder.lblDescripcion.setText(Html.fromHtml(mLista.get(position).getDescripcion()));
            holder.lblabr.setText(mLista.get(position).getAbrev());
            Glide.with(mContext)
                    .load(mLista.get(position).getPortada_url())
                    .into(holder.imgPortada);
            holder.itemView.setSelected(selectedPos == position);



        } catch (Exception e) {

        }
    }

    @Override
    public int getItemCount() {
        return mLista.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        int view_type;
        public RelativeLayout expandableView;
        public Button arrowBtn;
        public CardView cardView;

        public TextView lblNombre, lblDescripcion, lblabr;
        public ImageView imgPortada;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            lblNombre = (TextView) itemView.findViewById(R.id.txtNombre);
            lblabr = (TextView) itemView.findViewById(R.id.desc);
            lblDescripcion = (TextView) itemView.findViewById(R.id.txtDescripcion);
            imgPortada = (ImageView) itemView.findViewById(R.id.imgPortada);


            expandableView = itemView.findViewById(R.id.expandableView);
            arrowBtn = itemView.findViewById(R.id.arrowBtn);
            cardView = itemView.findViewById(R.id.cardView);

            arrowBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (expandableView.getVisibility() == View.GONE) {
                        TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                        expandableView.setVisibility(View.VISIBLE);
                        arrowBtn.setBackgroundResource(R.drawable.expand2);
                    } else {
                        TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                        expandableView.setVisibility(View.GONE);
                        arrowBtn.setBackgroundResource(R.drawable.expand);
                    }
                }
            });
        }
    }
}
