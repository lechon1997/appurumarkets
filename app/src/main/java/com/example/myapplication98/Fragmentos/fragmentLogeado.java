package com.example.myapplication98.Fragmentos;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.myapplication98.Controladores.ControladorUsuario;
import com.example.myapplication98.Controladores.ControladorVista;
import com.example.myapplication98.Modelo.Usuario;
import com.example.myapplication98.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragmentLogeado#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragmentLogeado extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ControladorUsuario CU = ControladorUsuario.getInstance();
    private ControladorVista CV = ControladorVista.getInstance();

    private Button btnDatosP;
    private Button btnDatosV;
    private Button btnCerrarS;
    private Button btnCrearP;

    private Fragment fragment;
    private Fragment fragment2;
    private View myView;
    private boolean isBig;
    private boolean isBig2;

    private boolean verDatosP;

    private String mParam1;
    private String mParam2;

    public fragmentLogeado() {
        isBig = false;
        isBig2 = false;
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragmentLogeado.
     */
    // TODO: Rename and change types and number of parameters
    public static fragmentLogeado newInstance(String param1, String param2) {
        fragmentLogeado fragment = new fragmentLogeado();
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
        myView =  inflater.inflate(R.layout.fragment_logeado, container, false);
        return myView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnDatosP = myView.findViewById(R.id.btnDatosP);
        btnDatosV = myView.findViewById(R.id.btnDatosEmpresa);
        btnCerrarS = myView.findViewById(R.id.btnCerrarSesion);
        btnCrearP = myView.findViewById(R.id.CrearProducto);

        btnCrearP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                fragment = CV.AltaP();
                transaction.replace(R.id.fragmentConteiner,fragment);
                transaction.commit();
            }
        });

        Usuario u = CU.getUsuario();
        if(u.getTipoUsuario().equals("cliente")){
            btnDatosV.setVisibility(View.INVISIBLE);
            btnDatosV.setHeight(0);
        }

        btnDatosV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isBig2){
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    fragment2 = CV.getFGDatosEmpresa();
                    transaction.replace(R.id.contenedorDatosCompra,fragment2);
                    transaction.commit();
                    isBig2 = true;
                }else{
                    getFragmentManager().beginTransaction().remove(fragment2).commit();
                    isBig2 = false;
                }

            }
        });

        btnDatosP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isBig){
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    fragment = CV.getFGDatosCliente();
                    transaction.replace(R.id.contenedorDatosP,fragment);
                    transaction.commit();
                    isBig = true;
                }else{
                    getFragmentManager().beginTransaction().remove(fragment).commit();
                    isBig = false;
                }

                /*
                FrameLayout ctn = myView.findViewById(R.id.contenedorDatosP);
                ctn.setPivotY(0f);
                if(!isBig){

                    ObjectAnimator scaleY = ObjectAnimator.ofFloat(ctn, "scaleY", 700);
                    scaleY.setInterpolator(new DecelerateInterpolator());
                    scaleY.setDuration(750);
                    scaleY.start();
                    isBig = true;

                } else{
                    ObjectAnimator scaleY = ObjectAnimator.ofFloat(ctn, "scaleY", 0);
                    scaleY.setInterpolator(new DecelerateInterpolator());
                    scaleY.setDuration(750);
                    scaleY.start();
                    isBig = false;
                }

                */
            }
        });

        btnCerrarS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                String usuKey = sharedPref.getString("usuarioRecordado","asd");
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean("recordado",false);
                editor.commit();

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentConteiner,CV.getFGLogin());
                transaction.commit();
            }
        });
    }
}