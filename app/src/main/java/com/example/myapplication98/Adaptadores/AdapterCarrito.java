package com.example.myapplication98.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication98.Modelo.ItemCarrito;
import com.example.myapplication98.Modelo.Publicacion;
import com.example.myapplication98.R;

import java.util.ArrayList;

public class AdapterCarrito extends RecyclerView.Adapter<AdapterCarrito.CarritoHolder>{
    private ArrayList<ItemCarrito> carritoNazi;
    private Fragment fragment;
    private LayoutInflater inflater;

    public AdapterCarrito(Context context, ArrayList<ItemCarrito> publicaciones,Fragment fragment) {
        this.inflater = LayoutInflater.from(context);
        this.carritoNazi = publicaciones;
        this.fragment = fragment;
    }


    @NonNull
    @Override
    public AdapterCarrito.CarritoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_carrito,parent,false);
        return new AdapterCarrito.CarritoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCarrito.CarritoHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return carritoNazi.size();
    }

    public class CarritoHolder extends RecyclerView.ViewHolder {
        TextView tv1,tv2,tv3,tv4;
        ImageView img;
        public CarritoHolder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.carritoTitulo);
            tv2 = itemView.findViewById(R.id.precioCarrito);
            tv3 = itemView.findViewById(R.id.cantidadCarrito);
            img = itemView.findViewById(R.id.imgCarrito);
        }
    }
}
