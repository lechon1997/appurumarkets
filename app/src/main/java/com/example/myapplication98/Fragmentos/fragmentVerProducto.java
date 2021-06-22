package com.example.myapplication98.Fragmentos;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication98.Config;
import com.example.myapplication98.Controladores.ControladorProducto;
import com.example.myapplication98.Controladores.ControladorUsuario;
import com.example.myapplication98.Controladores.ControladorVista;
import com.example.myapplication98.Modelo.ItemCarrito;
import com.example.myapplication98.Modelo.Publicacion;
import com.example.myapplication98.Modelo.Usuario;
import com.example.myapplication98.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragmentVerProducto#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragmentVerProducto extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ControladorProducto CP = ControladorProducto.getInstance();
    private ControladorVista CV = ControladorVista.getInstance();
    private ControladorUsuario CU = ControladorUsuario.getInstance();
    private View myView;
    private Publicacion p;

    private String mParam1;
    private String mParam2;


    public fragmentVerProducto() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragmentVerProducto.
     */
    // TODO: Rename and change types and number of parameters
    public static fragmentVerProducto newInstance(String param1, String param2) {
        fragmentVerProducto fragment = new fragmentVerProducto();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         myView = inflater.inflate(R.layout.fragment_ver_producto, container, false);
        return myView;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CV.getNavigation().setVisibility(View.GONE);
        p = CP.getP();
        String url ="http://"+ Config.IP_LOCAL_HOST +"/urumarkets/public/storage/productos/"+p.getFoto();
        Glide.with(myView.getContext()).load(url).into((ImageView) myView.findViewById(R.id.imgProducto));

        TextView tvTitulo = myView.findViewById(R.id.verProductoTitulo);
        tvTitulo.setText(p.getTitulo());

        TextView tv2 = myView.findViewById(R.id.idPrecioVP);
        TextView tv3 = myView.findViewById(R.id.iddscVP);
        TextView tv4 = myView.findViewById(R.id.precioAntesVP);



        TextView tvStock = myView.findViewById(R.id.tvStockTotal);
        tvStock.setText(String.valueOf(p.getStock()));

        TextView tvDescripcion = myView.findViewById(R.id.contenidoDesc);
        tvDescripcion.setText(p.getDescripcion());

        int precio = p.getPrecio();
        double descuento = p.getDescuento();

        if(descuento != 0 && !Double.isNaN(descuento)){
            tv2.setText("$"+String.valueOf(Math.round(precio-((precio*descuento)/100))));
            tv3.setText(String.valueOf(Math.round(descuento))+"% OFF");
            tv4.setText("Antes: " + String.valueOf(precio));
        }else if(precio != 0){
            tv2.setText("$"+String.valueOf(precio));
            tv4.setHeight(0);
        }

        ImageView imgAdd = myView.findViewById(R.id.btnAumentar);
        ImageView imgRem = myView.findViewById(R.id.btnRemover);
        TextView tvCantidad = myView.findViewById(R.id.tvCant);

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cant = tvCantidad.getText().toString();
                int cantNumero = Integer.valueOf(cant);
                cantNumero++;
                tvCantidad.setText(String.valueOf(cantNumero));
            }
        });

        imgRem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cant = tvCantidad.getText().toString();
                int cantNumero = Integer.valueOf(cant);
                if(cantNumero != 1){
                    cantNumero--;
                }
                tvCantidad.setText(String.valueOf(cantNumero));
            }
        });

        Button btn = myView.findViewById(R.id.btnAgregarProd);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = myView.findViewById(R.id.tvCant);
                int cantidad = Integer.valueOf(tv.getText().toString());

                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                boolean recordado = sharedPref.getBoolean("recordado",false);

                if(recordado){
                    String usuKey = sharedPref.getString("usuarioRecordado","asd");//key o email que identifica a usuario


                    Usuario u = CU.getUsuario();
                    ItemCarrito nit= new ItemCarrito(CP.getP(),cantidad);
                    //u.agregarAlCarrito(nit);

                    Gson gson = new Gson();
                    SharedPreferences.Editor editor = sharedPref.edit();
                    String jsonUsuario = gson.toJson(u);
                    editor.putString(usuKey,jsonUsuario);
                    editor.apply();

                    Snackbar.make(myView,"Se agregó al carrito",Snackbar.LENGTH_SHORT).show();
                }else if (CU.getSession()){
                    Usuario u = CU.getUsuario();
                    ItemCarrito nit= new ItemCarrito(CP.getP(),cantidad);
                    //u.agregarAlCarrito(nit);
                    Snackbar.make(myView,"Se agregó al carrito",Snackbar.LENGTH_SHORT).show();
                }else{
                    Snackbar.make(myView,"Debe iniciar sesión primero",Snackbar.LENGTH_SHORT).show();
                }


            }
        });
    }
}