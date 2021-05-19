package com.example.myapplication98.Fragmentos;

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
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication98.Controladores.ControladorUsuario;
import com.example.myapplication98.Controladores.ControladorVista;
import com.example.myapplication98.Modelo.Departamento;
import com.example.myapplication98.Modelo.Localidad;
import com.example.myapplication98.Modelo.Usuario;
import com.example.myapplication98.R;
import com.example.myapplication98.WebService.MySingleton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragmentRegistroEmpresa#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragmentRegistroEmpresa extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener{


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ControladorUsuario CU = ControladorUsuario.getInstance();
    private ControladorVista CV = ControladorVista.getInstance();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button b ;
    private TextInputEditText etrut ;
    private TextInputEditText etrs ;
    private TextInputEditText etfant ;
    private Spinner tipoOrg ;
    private TextInputEditText etrub;
    private TextInputEditText ettele;
    private TextInputEditText etdire;
    private TextInputEditText etdesc;


    private View myView;

    public fragmentRegistroEmpresa() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragmentRegistroEmpresa.
     */
    // TODO: Rename and change types and number of parameters
    public static fragmentRegistroEmpresa newInstance(String param1, String param2) {
        fragmentRegistroEmpresa fragment = new fragmentRegistroEmpresa();
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
        myView = inflater.inflate(R.layout.fragment_registro_empresa, container, false);
        return myView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //aca programar
         b = (Button) myView.findViewById(R.id.boton);
         etrut = myView.findViewById(R.id.etrut);
         etrs = myView.findViewById(R.id.razonsocial);
         etfant = myView.findViewById(R.id.nomfantasia);
         tipoOrg = myView.findViewById(R.id.spinner2);
         etrub = myView.findViewById(R.id.etrubro);
         ettele = myView.findViewById(R.id.ettel);
         etdire = myView.findViewById(R.id.etdire);
         etdesc = myView.findViewById(R.id.etdesc);




        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String RUT = etrut.getText().toString();
                String rs = etrs.getText().toString();
                String fantasia = etfant.getText().toString();
                String org = tipoOrg.getSelectedItem().toString();
                String rubro = etrub.getText().toString();
                String telEmp = ettele.getText().toString();
                String dir = etdire.getText().toString();
                String desc = etdesc.getText().toString();
                Usuario u = CU.getUsuarioEnMemoria();
                altaEmpresa(u, RUT, rs, fantasia, org, rubro, telEmp, dir, desc);
            }
        });
    }

    private void altaEmpresa(Usuario u, String rut, String rs, String fantasia, String org, String rubro, String telEmp, String dir, String desc) {
        String LOGIN_REQUEST_URL = "http://192.168.1.11/urumarkets/public/api/altaempws";

        // JSON data
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("pnombre", u.getPrimer_nombre());
            jsonObject.put("snombre", "notiene");
            jsonObject.put("papellido", u.getPrimer_apellido());
            jsonObject.put("sapellido", "notiene");
            jsonObject.put("cedula", u.getCedula());
            jsonObject.put("telefono", u.getCedula());
            jsonObject.put("email", u.getEmail());
            jsonObject.put("pass",  u.getPassword());
            jsonObject.put("Departamento", String.valueOf(u.getDepartamento().getId()));
            jsonObject.put("localidad", String.valueOf(u.getLocalidad().getId()));
            jsonObject.put("Rut",rut);
            jsonObject.put("razonsocial",rs);
            jsonObject.put("nombrefantasia",fantasia);
            jsonObject.put("tipoOrg","ORG");
            jsonObject.put("rubro",rubro);
            jsonObject.put("telefonoEmpresa",telEmp);
            jsonObject.put("direccion",dir);
            jsonObject.put("Descripcion",desc);

        } catch (JSONException e){
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                LOGIN_REQUEST_URL,
                jsonObject,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response){
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragmentConteiner,CV.getFGLogin());
                        transaction.commit();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof NetworkError) {
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                }
                else if (error instanceof ServerError) {
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                }
                else if (error instanceof ParseError) {
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                }
                error.printStackTrace();
            }
        }){

            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<String,String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        RequestQueue queue = MySingleton.getInstance(getContext()).getRequestQueue();
        MySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {

    }
}