package com.example.myapplication98.Fragmentos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication98.Controladores.ControladorUsuario;
import com.example.myapplication98.Controladores.ControladorVista;
import com.example.myapplication98.Modelo.Usuario;
import com.example.myapplication98.Modelo.vendedor;
import com.example.myapplication98.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragmentDatosEmpresa#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragmentDatosEmpresa extends Fragment {

    private ControladorUsuario CU = ControladorUsuario.getInstance();
    private ControladorVista CV = ControladorVista.getInstance();
    private View myView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragmentDatosEmpresa() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragmentDatosEmpresa.
     */
    // TODO: Rename and change types and number of parameters
    public static fragmentDatosEmpresa newInstance(String param1, String param2) {
        fragmentDatosEmpresa fragment = new fragmentDatosEmpresa();
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
        myView =  inflater.inflate(R.layout.fragment_datos_empresa, container, false);
        return myView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vendedor usuario = CU.getEmpresa();

        TextView tvRut = myView.findViewById(R.id.tvSetRut);
        TextView tvRazonSocial = myView.findViewById(R.id.tvSetRazonSocial);
        TextView tvFantasia = myView.findViewById(R.id.tvSetFantasia);
        TextView tvTipoOrg = myView.findViewById(R.id.tvSetTipoOrg);
        TextView tvRubro = myView.findViewById(R.id.tvSetRubro);
        TextView tvTelEmpresa = myView.findViewById(R.id.tvSetTelEmpresa);
        TextView tvDireccion = myView.findViewById(R.id.tvSetDireccion);
        TextView tvDescripcion = myView.findViewById(R.id.tvSetDescripcion);


        tvRut.setText(String.valueOf(usuario.getRUT()));
        tvRazonSocial.setText(usuario.getRazonSocial());
        tvFantasia.setText(usuario.getNombreFantasia());
        tvTipoOrg.setText(usuario.getTipoOrganizacion());
        tvRubro.setText(usuario.getRubro());
        tvTelEmpresa.setText(String.valueOf(usuario.getTelefonoEmpresa()));
        tvDireccion.setText(usuario.getDireccion());
        tvDescripcion.setText(usuario.getDescripcion());

        Button btnEdit = myView.findViewById(R.id.btnEditarDatos);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentConteiner,CV.getedit_inf_emp());
                transaction.commit();
            }
        });
    }
}