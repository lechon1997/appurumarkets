package com.example.myapplication98.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.myapplication98.Config;
import com.example.myapplication98.Controladores.ControladorProducto;
import com.example.myapplication98.Controladores.ControladorVista;
import com.example.myapplication98.Modelo.Publicacion;
import com.example.myapplication98.R;
import java.util.ArrayList;


public class AdapterPublicacion extends RecyclerView.Adapter<AdapterPublicacion.PublicacionViewHolder> implements View.OnClickListener{
    private ArrayList<Publicacion> publicaciones;
    private Fragment fragment;
    private LayoutInflater inflater;
    private View.OnClickListener listener;
    private ControladorProducto CP = ControladorProducto.getInstance();
    private ControladorVista CV = ControladorVista.getInstance();

    public AdapterPublicacion(Context context, ArrayList<Publicacion> publicaciones, Fragment fragment){
        this.inflater = LayoutInflater.from(context);
        this.publicaciones = publicaciones;
        this.fragment = fragment;

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
        double descuento = publicaciones.get(position).getDescuento();
        holder.tv1.setText(titulo);
        holder.btn.setText("VER PRODUCTO");
        if(descuento != 0 && !Double.isNaN(descuento)){
            holder.tv2.setText("$"+String.valueOf(Math.round(precio-((precio*descuento)/100))));
            holder.tv3.setText(String.valueOf(Math.round(descuento))+"% OFF");
            holder.tv4.setText("Antes: " + String.valueOf(precio));
        }else if(precio != 0){
            holder.tv2.setText("$"+String.valueOf(precio));
        }else{
            holder.btn.setText("VER SERVICIO");
        }
        String url ="http://"+ Config.IP_LOCAL_HOST +"/urumarkets/public/storage/productos/"+imagenP;
        Glide.with(inflater.getContext()).load(url).into(holder.img);

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   // Toast.makeText(inflater.getContext(),holder.tv1.getText(),Toast.LENGTH_SHORT).show();
                CP.setP(publicaciones.get(position));

                FragmentTransaction transaction = fragment.getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentConteiner,CV.getFGVerProducto()).addToBackStack("tag");
                transaction.commit();
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
        TextView tv1,tv2,tv3,tv4;
        Button btn;
        ImageView img;
        public PublicacionViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.idTitlexd);
            tv2 = itemView.findViewById(R.id.idPrecioxd);
            tv3 = itemView.findViewById(R.id.iddsc);
            tv4 = itemView.findViewById(R.id.precioAntes);
            btn = itemView.findViewById(R.id.btnVerDetalle);
            img = itemView.findViewById(R.id.imgProducto);
        }
    }
}
