package com.example.myapplication98.Fragmentos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.service.controls.Control;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication98.Adaptadores.AdapterCarrito;
import com.example.myapplication98.Adaptadores.AdapterPublicacion;
import com.example.myapplication98.Controladores.ControladorUsuario;
import com.example.myapplication98.Controladores.ControladorVista;
import com.example.myapplication98.Modelo.ItemCarrito;
import com.example.myapplication98.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragmentCarrito#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragmentCarrito extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private ControladorUsuario CU = ControladorUsuario.getInstance();
    private static final String ARG_PARAM2 = "param2";
    private View myView;
    private ControladorVista CV = ControladorVista.getInstance();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragmentCarrito() {


        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragmentCarrito.
     */
    // TODO: Rename and change types and number of parameters
    public static fragmentCarrito newInstance(String param1, String param2) {
        fragmentCarrito fragment = new fragmentCarrito();
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
        myView = inflater.inflate(R.layout.fragment_carrito, container, false);
        return myView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CV.getNavigation().setVisibility(View.GONE);
        RecyclerView rv = myView.findViewById(R.id.idCarrito);

        if(CU.getSession()){
            rv.setLayoutManager(new LinearLayoutManager(getContext()));
            AdapterCarrito adapterC = new AdapterCarrito(getContext(),(ArrayList<ItemCarrito>)CU.getUsuario().getCarrito(),this);
            rv.setAdapter(adapterC);
        }
    }
}