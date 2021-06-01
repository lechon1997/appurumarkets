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
import android.widget.CheckBox;
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
 * Use the {@link AltaProducto#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AltaProducto extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ControladorVista CV = ControladorVista.getInstance();
    private ControladorUsuario CU = ControladorUsuario.getInstance();
    private View myView;

    private Button altaP;
    private Button btnRegistrarse;
    private CheckBox cbRecuerdame;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AltaProducto() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AltaProducto.
     */
    // TODO: Rename and change types and number of parameters
    public static AltaProducto newInstance(String param1, String param2) {
        AltaProducto fragment = new AltaProducto();
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

    private void autenticarUsuario(String nombreProducto, String descripcionProducto, String stock, String checkboxOferta, String checkboxTienePrecio, String tipoMoneda, String precioProducto,
                                   String productosPorPersona, String porcentajeOfertaProducto, String estadoProducto, String publicacion) {
        //String url = "http://192.168.1.11/urumarkets/public/api/autenticarUsuario";

        String LOGIN_REQUEST_URL = "http://192.168.1.8/urumarkets/public/api/altaproductowbs";
        //altaproductowbs
        // JSON data
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("nombreProducto", nombreProducto);
            jsonObject.put("descripcionProducto", descripcionProducto);
            jsonObject.put("stock", stock);
            jsonObject.put("checkboxOferta", checkboxOferta);
            jsonObject.put("checkboxTienePrecio", checkboxTienePrecio);
            jsonObject.put("tipoMoneda", tipoMoneda);
            jsonObject.put("precioProducto", precioProducto);
            jsonObject.put("productosPorPersona", productosPorPersona);
            jsonObject.put("porcentajeOfertaProducto", porcentajeOfertaProducto);
            jsonObject.put("estadoProducto", estadoProducto);
            jsonObject.put("publicacion", publicacion);
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
                                Toast.makeText(getContext(),"creado correctamente",Toast.LENGTH_SHORT).show();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_alta_producto, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //jsonObject.put("nombreProducto", nombreProducto);     ---
        //jsonObject.put("descripcionProducto", descripcionProducto);   ---
        //jsonObject.put("oferta", oferta);     ----
        //jsonObject.put("checkboxOferta", checkboxOferta);         ---
        //jsonObject.put("checkboxTienePrecio", checkboxTienePrecio);       ---
        //jsonObject.put("tipoMoneda", tipoMoneda);                 ---
        //jsonObject.put("precioProducto", precioProducto);         ---
        //jsonObject.put("productosPorPersona", productosPorPersona);           ---
        //jsonObject.put("porcentajeOfertaProducto", porcentajeOfertaProducto);     ----
        //jsonObject.put("estadoProducto", estadoProducto);
        //jsonObject.put("publicacion", publicacion);
        altaP = (Button)view.findViewById(R.id.altaP) ;

        altaP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputEditText servicio = view.findViewById(R.id.switch3);
                TextInputEditText nombreart2 = view.findViewById(R.id.nombreart2);
                TextInputEditText descripcion2 = view.findViewById(R.id.descripcion2);
                TextInputEditText tieneprecio = view.findViewById(R.id.switch1);
                TextInputEditText moneda = view.findViewById(R.id.planets_spinner);
                TextInputEditText precio2 = view.findViewById(R.id.precio2);
                TextInputEditText stock2 = view.findViewById(R.id.stock2);
                TextInputEditText limistexpersona2 = view.findViewById(R.id.limistexpersona2);
                TextInputEditText estaenoferta = view.findViewById(R.id.switch2);
                TextInputEditText oferta2 = view.findViewById(R.id.oferta2);
                TextInputEditText estado = view.findViewById(R.id.planets_spinner2);


                String serviciotext = servicio.getText().toString();
                String nombrearticulo = nombreart2.getText().toString();
                String descripciontext = descripcion2.getText().toString();
                String tienepreciotext = tieneprecio.getText().toString();
                String monedatext = moneda.getText().toString();
                String preciotext = precio2.getText().toString();
                String stock = stock2.getText().toString();
                String limistexpersona = limistexpersona2.getText().toString();
                String estaenofertatext = estaenoferta.getText().toString();
                String oferta = oferta2.getText().toString();
                String estadotext = estado.getText().toString();

                autenticarUsuario(nombrearticulo,descripciontext,stock,estaenofertatext, tienepreciotext,monedatext, preciotext,
                        limistexpersona, oferta, estadotext, serviciotext);
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
}