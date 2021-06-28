package com.example.myapplication98.Fragmentos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myapplication98.Controladores.ControladorVista;
import com.example.myapplication98.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragmentBuscador#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragmentBuscador extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ControladorVista CV = ControladorVista.getInstance();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;

    public fragmentBuscador() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_buscador.
     */
    // TODO: Rename and change types and number of parameters
    public static fragmentBuscador newInstance(String param1, String param2) {
        fragmentBuscador fragment = new fragmentBuscador();
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
        view = inflater.inflate(R.layout.fragment_buscador, container, false);

        /*Button btn = view.findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ControladorVista CV = ControladorVista.getInstance();
                fragmentCarrito a = CV.getFGCarrito();
                getFragmentManager().beginTransaction().remove(a).commit();

                int index = getActivity().getFragmentManager().getBackStackEntryCount() - 1;
                FragmentManager.BackStackEntry backEntry = getFragmentManager().getBackStackEntryAt(index);
                String tag = backEntry.getName();
                Fragment fragment = getFragmentManager().findFragmentByTag(tag);

                CV.clearFGCarrito();
                a.onDestroy();
            }
        });*/
        return view;
    }
}