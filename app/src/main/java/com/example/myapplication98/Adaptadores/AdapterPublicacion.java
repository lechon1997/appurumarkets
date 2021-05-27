package com.example.myapplication98.Adaptadores;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication98.Config;
import com.example.myapplication98.Modelo.Publicacion;
import com.example.myapplication98.R;

import java.util.ArrayList;

public class AdapterPublicacion extends RecyclerView.Adapter<AdapterPublicacion.PublicacionViewHolder> implements View.OnClickListener{
    private ArrayList<Publicacion> publicaciones;
    private LayoutInflater inflater;
    private View.OnClickListener listener;

    public AdapterPublicacion(Context context, ArrayList<Publicacion> publicaciones){
        this.inflater = LayoutInflater.from(context);
        this.publicaciones = publicaciones;
    }

    @NonNull
    @Override
    public PublicacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_publicacion,parent,false);
        view.setOnClickListener(this);
        return new PublicacionViewHolder(view);
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull PublicacionViewHolder holder, int position) {
        String titulo = publicaciones.get(position).getTitulo();
        int precio = publicaciones.get(position).getPrecio();
        String imagenP = publicaciones.get(position).getFoto();
        holder.tv1.setText(titulo);
        holder.tv2.setText(String.valueOf(precio));
        String url ="http://"+ Config.IP_LOCAL_HOST +"/urumarkets/public/storage/productos/"+imagenP;
        Glide.with(inflater.getContext()).load(url).into(holder.img);

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(inflater.getContext(),holder.tv1.getText(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return publicaciones.size();
    }

    @Override
    public void onClick(View v) {
        if(listener != null){
            listener.onClick(v);
        }

    }

    public class PublicacionViewHolder extends RecyclerView.ViewHolder {
        TextView tv1,tv2;
        Button btn;
        ImageView img;
        public PublicacionViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.idTitlexd);
            tv2 = itemView.findViewById(R.id.idPrecioxd);
            btn = itemView.findViewById(R.id.btnVerDetalle);
            img = itemView.findViewById(R.id.imgProducto);
        }
    }
}
