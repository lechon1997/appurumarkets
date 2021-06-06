package com.example.myapplication98.Fragmentos;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.myapplication98.Adaptadores.AdapterPublicacion;
import com.example.myapplication98.Config;
import com.example.myapplication98.Controladores.ControladorVista;
import com.example.myapplication98.Modelo.Departamento;
import com.example.myapplication98.Modelo.Localidad;
import com.example.myapplication98.Modelo.Publicacion;
import com.example.myapplication98.Modelo.Usuario;
import com.example.myapplication98.R;
import com.example.myapplication98.WebService.MySingleton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragmentInicio#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragmentInicio extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private View myView;
    private ControladorVista CV = ControladorVista.getInstance();
    private AdapterPublicacion adapterPublicacion;
    private RecyclerView recyclerViewPublicacion;
    private ArrayList<Publicacion> ListaPublicaciones;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragmentInicio() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragmentInicio.
     */
    // TODO: Rename and change types and number of parameters
    public static fragmentInicio newInstance(String param1, String param2) {
        fragmentInicio fragment = new fragmentInicio();
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
        myView =  inflater.inflate(R.layout.fragment_inicio, container, false);
        return  myView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CV.getNavigation().setVisibility(View.VISIBLE);
        recyclerViewPublicacion = myView.findViewById(R.id.recycler_view);
        ListaPublicaciones = new ArrayList<>();
        getPublicaciones();


    }





    private void MostrarLista() {
        recyclerViewPublicacion.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterPublicacion = new AdapterPublicacion(getContext(),ListaPublicaciones,this);
        recyclerViewPublicacion.setAdapter(adapterPublicacion);

    }

    private void getPublicaciones() {

        String LOGIN_REQUEST_URL = "http://"+ Config.IP_LOCAL_HOST +"/urumarkets/public/api/listarPublicacionesWs";


        // Json request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                LOGIN_REQUEST_URL,
                null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response){

                        Publicacion publicacion = null;
                        try {

                            JSONArray ja = response.getJSONArray("respuesta");

                            for (int i = 0; i < ja.length();i++) {
                                JSONObject jo = ja.getJSONObject(i);
                                String titulo = jo.optString("titulo");
                                int precio = jo.optInt("precio");
                                int stock = jo.optInt("stock");
                                String descripcion = jo.optString("descripcion");
                                String img = jo.optString("foto");
                                double dsc = jo.optDouble("porcentajeOferta");
                                publicacion = new Publicacion(titulo, precio, img,dsc,stock,descripcion);
                                ListaPublicaciones.add(publicacion);
                            }
                            MostrarLista();
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