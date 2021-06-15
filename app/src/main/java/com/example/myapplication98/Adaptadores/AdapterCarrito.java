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

import com.bumptech.glide.Glide;
import com.example.myapplication98.Config;
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
        return new CarritoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCarrito.CarritoHolder holder, int position) {
        String titulo = carritoNazi.get(position).getP().getTitulo();
        int precio = carritoNazi.get(position).getP().getPrecio();
        double descuento = carritoNazi.get(position).getP().getDescuento();

        int cantidad = carritoNazi.get(position).getCantidad();
        String imagenP = carritoNazi.get(position).getP().getFoto();
        if(descuento != 0 && !Double.isNaN(descuento)){
            holder.tv2.setText("$"+String.valueOf(Math.round(precio-((precio*descuento)/100))));
            holder.tv4.setText("$"+ String.valueOf(cantidad * Math.round(precio-((precio*descuento)/100))));
        }else if(precio != 0){
            holder.tv2.setText("$"+String.valueOf(precio));
            holder.tv4.setText("$"+ String.valueOf(cantidad * precio));
        }

        String url ="http://"+ Config.IP_LOCAL_HOST +"/urumarkets/public/storage/productos/"+imagenP;
        Glide.with(inflater.getContext()).load(url).into(holder.img);

        holder.tv1.setText(titulo);
        holder.tv3.setText(String.valueOf(cantidad));
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
            tv4 = itemView.findViewById(R.id.totalCarrito);
            img = itemView.findViewById(R.id.imgCarrito);
        }
    }
}
