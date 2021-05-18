package com.example.myapplication98.Fragmentos;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragmentLogin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragmentLogin extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ControladorVista CV = ControladorVista.getInstance();
    private ControladorUsuario CU = ControladorUsuario.getInstance();
    private View myView;

    private Button btnIniciarSesion;
    private Button btnRegistrarse;
    private CheckBox cbRecuerdame;
    private String mParam1;
    private String mParam2;

    public fragmentLogin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragmentLogin.
     */
    // TODO: Rename and change types and number of parameters
    public static fragmentLogin newInstance(String param1, String param2) {
        fragmentLogin fragment = new fragmentLogin();
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
        myView = inflater.inflate(R.layout.fragment_login, container, false);
        return myView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnIniciarSesion = (Button)view.findViewById(R.id.btniniciarSesion) ;
        btnRegistrarse = (Button)view.findViewById(R.id.btnRegistrarse);
        cbRecuerdame = (CheckBox)view.findViewById(R.id.checkBox);

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputEditText etu = view.findViewById(R.id.usuet);
                TextInputEditText etp = view.findViewById(R.id.passusu);
                String usu = etu.getText().toString();
                String pass = etp.getText().toString();
                autenticarUsuario(usu,pass);
            }
        });

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentConteiner,CV.getFGRegistroUsuario());
                transaction.commit();
            }
        });
    }

    private void autenticarUsuario(String usu, String pass) {
        //String url = "http://192.168.1.11/urumarkets/public/api/autenticarUsuario";

        String LOGIN_REQUEST_URL = "http://192.168.1.11/urumarkets/public/api/autenticarUsuario";

        // JSON data
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("email", usu);
            jsonObject.put("password", pass);
        } catch (JSONException e){
            e.printStackTrace();
        }

        // Json request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                LOGIN_REQUEST_URL,
                jsonObject,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response){


                        try {
                            JSONObject jo = response.getJSONObject("respuesta");
                            if(jo.optString("estado").equals("ok")){

                                Usuario usuario = new Usuario();
                                Departamento departamento = new Departamento();
                                Localidad localidad = new Localidad();

                                usuario.setId(jo.optInt("id"));
                                usuario.setPrimer_nombre(jo.optString("pnombre"));
                                usuario.setSegundo_nombre(jo.optString("snombre"));
                                usuario.setPrimer_apellido(jo.optString("papellido"));
                                usuario.setSegundo_apellido(jo.optString("sapellido"));
                                usuario.setCedula(jo.optString("cedula"));
                                usuario.setTelefono(jo.getString("telefono"));
                                usuario.setEmail(jo.getString("email"));

                                departamento.setId(jo.optInt("idDepartamento"));
                                departamento.setNombre(jo.optString("nomDepartamento"));

                                localidad.setId(jo.optInt("idLocalidad"));
                                localidad.setNombre(jo.optString("nomLocalidad"));

                                usuario.setDepartamento(departamento);
                                usuario.setLocalidad(localidad);
                                CU.setUsuario(usuario);

                                if(cbRecuerdame.isChecked()){
                                    SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    Gson gson = new Gson();
                                    String jsonUsuario = gson.toJson(usuario);
                                    editor.putString(jo.getString("email"),jsonUsuario);
                                    editor.putString("usuarioRecordado",jo.getString("email"));
                                    editor.putBoolean("recordado",true);
                                    editor.commit();
                                }


                                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                transaction.replace(R.id.fragmentConteiner,CV.getFGlogeado());
                                transaction.commit();

                            }else if (jo.optString("estado").equals("incorrecto"))
                                Toast.makeText(getContext(),"incorrecto",Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(getContext(),"shrek",Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
            }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof NetworkError) {
                    Toast.makeText(getContext(), "Can't connect to Internet. Please check your connection.", Toast.LENGTH_LONG).show();
                }
                else if (error instanceof ServerError) {
                    Toast.makeText(getContext(), "Unable to login. Either the username or password is incorrect.", Toast.LENGTH_LONG).show();
                }
                else if (error instanceof ParseError) {
                    Toast.makeText(getContext(), "Parsing error. Please try again.", Toast.LENGTH_LONG).show();
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
}