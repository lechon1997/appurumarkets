package com.example.myapplication98.Fragmentos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication98.Controladores.ControladorVista;
import com.example.myapplication98.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragmentRegistroUsuario#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragmentRegistroUsuario extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ControladorVista CV = ControladorVista.getInstance();


    private View view;
    private AutoCompleteTextView autoCompleteTextView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
     RequestQueue request;
     JsonObjectRequest jsonObjectRequest;


    public fragmentRegistroUsuario() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragmentRegistroUsuario.
     */
    // TODO: Rename and change types and number of parameters
    public static fragmentRegistroUsuario newInstance(String param1, String param2) {
        fragmentRegistroUsuario fragment = new fragmentRegistroUsuario();
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
        view = inflater.inflate(R.layout.fragment_registro_usuario, container, false);
        request = Volley.newRequestQueue(getContext());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextInputEditText et = view.findViewById(R.id.tietnombre);
        TextInputLayout til = view.findViewById(R.id.tilnombre);
        Button btnR = view.findViewById(R.id.btnRegistrarse2);

        et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN)
                    Toast.makeText(getContext(),"hola",Toast.LENGTH_SHORT).show();
                return false;
            }
        });


        btnR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(et.length() == 0){
                til.setError("El nombre es obligatorio");
            }
                CheckBox checkBox = (CheckBox)view.findViewById(R.id.checkBox2);
                if(checkBox.isChecked()){
                    //cargar otro fragmento/vista
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragmentConteiner,CV.getFGRegistroEmpreas());
                    transaction.commit();
                }

            cargarWS();
            }
        });

        /*
        autoCompleteTextView = view.findViewById(R.id.autoComplete);
        String[] opciones = {"CI","RUT"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(),R.layout.option_menu_registro_usuario_documento,opciones);
        autoCompleteTextView.setText(arrayAdapter.getItem(0).toString(),false);
        autoCompleteTextView.setAdapter(arrayAdapter);
        */
    }

    private void cargarWS() {
        String url = "http://192.168.1.7/urumarkets/public/api/test-api";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        TextView textView = view.findViewById(R.id.tvws);
        textView.setText("con fallida" + error.getMessage());
    }

    @Override
    public void onResponse(JSONObject response) {
        TextView textView = view.findViewById(R.id.tvws);
        textView.setText("con exito");
    }
}