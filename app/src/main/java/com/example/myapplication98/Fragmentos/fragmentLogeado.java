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

import com.example.myapplication98.Controladores.ControladorVista;
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
    private ControladorVista CV = ControladorVista.getInstance();
    private View myView;
    private boolean isBig;

    private boolean verDatosP;
    Fragment fragment;

    private String mParam1;
    private String mParam2;

    public fragmentLogeado() {
        isBig = false;
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
        Button btnDatosP = myView.findViewById(R.id.btnDatosP);
        Button btnCerrarS = myView.findViewById(R.id.btnCerrarSesion);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.contenedorDatosP,CV.getFGDatosCliente());
        transaction.commit();


        btnDatosP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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