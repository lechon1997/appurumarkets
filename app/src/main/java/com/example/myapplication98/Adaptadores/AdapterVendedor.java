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
import com.example.myapplication98.Modelo.VendedorSinGanas;
import com.example.myapplication98.R;
import java.util.ArrayList;

public class AdapterVendedor extends RecyclerView.Adapter<AdapterVendedor.VendedorViewHolder>{
    private ArrayList<VendedorSinGanas> vendedores;
    private Fragment fragment;
    private LayoutInflater inflater;

    public AdapterVendedor(Context context, ArrayList<VendedorSinGanas> vendedores, Fragment fragment) {
        this.inflater = LayoutInflater.from(context);
        this.vendedores = vendedores;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public VendedorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_vendedor,parent,false);
        return new AdapterVendedor.VendedorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VendedorViewHolder holder, int position) {
        holder.tv1.setText("Empresa: " + vendedores.get(position).getNombreFantasia());
        holder.tv2.setText("E-mail: " + vendedores.get(position).getEmail());
        holder.tv3.setText("Telefono: " + vendedores.get(position).getTelefono());
        holder.tv4.setText("Departamento: " + vendedores.get(position).getDepartamento());
        holder.tv5.setText("Localidad: " + vendedores.get(position).getLocalidad());
        holder.tv6.setText("Dirección: "+ vendedores.get(position).getDireccion());
        String imagenP = vendedores.get(position).getImg();
        String url ="http://"+ Config.IP_LOCAL_HOST +"/urumarkets/public/storage/empresa/"+imagenP;
        Glide.with(inflater.getContext()).load(url).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return vendedores.size();
    }

    public class VendedorViewHolder extends RecyclerView.ViewHolder {
        TextView tv1,tv2,tv3,tv4,tv5,tv6;
        ImageView img;
        public VendedorViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.reck);
            tv2 = itemView.findViewById(R.id.mail);
            tv3 = itemView.findViewById(R.id.teltel);
            tv4 = itemView.findViewById(R.id.Depa);
            tv5 = itemView.findViewById(R.id.LocaLoca);
            tv6 = itemView.findViewById(R.id.dirchuerk);
            img = itemView.findViewById(R.id.imgrancia);
        }
    }
}
