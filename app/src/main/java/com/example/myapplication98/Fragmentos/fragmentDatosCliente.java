package com.example.myapplication98.Fragmentos;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication98.Controladores.ControladorUsuario;
import com.example.myapplication98.Modelo.Usuario;
import com.example.myapplication98.R;
import com.google.gson.Gson;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragmentDatosCliente#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragmentDatosCliente extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private View myView;
    private ControladorUsuario CU = ControladorUsuario.getInstance();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragmentDatosCliente() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragmentDatosCliente.
     */
    // TODO: Rename and change types and number of parameters
    public static fragmentDatosCliente newInstance(String param1, String param2) {
        fragmentDatosCliente fragment = new fragmentDatosCliente();
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
        myView =  inflater.inflate(R.layout.fragment_datos_cliente, container, false);
        return myView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

        boolean recordado = sharedPref.getBoolean("recordado",false);
        Usuario usuario = null;

        if(recordado){
            String usuKey = sharedPref.getString("usuarioRecordado","asd");
            String datos = sharedPref.getString(usuKey,"asd");
            Gson gson = new Gson();
            usuario = gson.fromJson(datos, Usuario.class);
        } else
            usuario = CU.getUsuario();



        TextView tvNombre = myView.findViewById(R.id.tvSetNombre);
        TextView tvApellido = myView.findViewById(R.id.tvSetApellido);
        TextView tvCedula = myView.findViewById(R.id.tvSetCedula);
        TextView tvTelefono = myView.findViewById(R.id.tvSetTelefono);
        TextView tvEmail = myView.findViewById(R.id.tvSetEmail);
        TextView tvDepartamento = myView.findViewById(R.id.tvSetDepartamento);
        TextView tvLocalidad = myView.findViewById(R.id.tvSetLocalidad);

        tvNombre.setText(usuario.getPrimer_nombre());
        tvApellido.setText(usuario.getPrimer_apellido());
        tvCedula.setText(usuario.getCedula());
        tvTelefono.setText(usuario.getTelefono());
        tvEmail.setText(usuario.getEmail());
        tvDepartamento.setText(usuario.getDepartamento().getNombre());
        tvLocalidad.setText(usuario.getLocalidad().getNombre());


    }
}