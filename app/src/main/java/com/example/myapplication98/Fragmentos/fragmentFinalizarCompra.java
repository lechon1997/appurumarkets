package com.example.myapplication98.Fragmentos;

import android.content.ClipData;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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
import com.example.myapplication98.Config;
import com.example.myapplication98.Controladores.ControladorUsuario;
import com.example.myapplication98.Modelo.Departamento;
import com.example.myapplication98.Modelo.ItemCarrito;
import com.example.myapplication98.Modelo.Localidad;
import com.example.myapplication98.Modelo.Usuario;
import com.example.myapplication98.Modelo.vendedor;
import com.example.myapplication98.R;
import com.example.myapplication98.WebService.MySingleton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.os.Looper.getMainLooper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragmentFinalizarCompra#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragmentFinalizarCompra extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ControladorUsuario CU = ControladorUsuario.getInstance();
    private View myView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragmentFinalizarCompra() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragmentFinalizarCompra.
     */
    // TODO: Rename and change types and number of parameters
    public static fragmentFinalizarCompra newInstance(String param1, String param2) {
        fragmentFinalizarCompra fragment = new fragmentFinalizarCompra();
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
        myView = inflater.inflate(R.layout.fragment_finalizar_compra, container, false);
        return myView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AutoCompleteTextView ac = myView.findViewById(R.id.MesesVencimiento);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getContext(),R.array.meses_vencimiento, R.layout.drop_item_menu);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ac.setAdapter(adapter);

        Button btn = myView.findViewById(R.id.finalizarcompraleo);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputEditText et_tarjeta = myView.findViewById(R.id.numerotarjeta);
                TextInputEditText et_mes = myView.findViewById(R.id.mesvencimiento);
                TextInputEditText et_anio = myView.findViewById(R.id.mesvencimiento);
                TextInputEditText et_codigo = myView.findViewById(R.id.codigoseguridad);
                TextInputEditText et_titular = myView.findViewById(R.id.titulartarjeta);
                TextInputEditText et_email = myView.findViewById(R.id.imail);
                TextInputEditText et_cedula = myView.findViewById(R.id.cedulaidentidad);

                String tarjeta = et_tarjeta.getText().toString();
                String mes = et_mes.getText().toString();
                String anio = et_anio.getText().toString();
                String codigo = et_codigo.getText().toString();
                String titular = et_titular.getText().toString();
                String email = et_email.getText().toString();
                String cedula = et_cedula.getText().toString();

                ingresarCompra(tarjeta,mes,anio,codigo,titular,email,cedula);
                /*
                if(tarjeta.length() == 0 || mes.length() == 0 || anio.length() == 0 || codigo.length() == 0
                        || titular.length() == 0 || email.length() == 0 || cedula.length() == 0) {
                    Snackbar.make(myView,"Hay campos sin completar",Snackbar.LENGTH_SHORT).show();
                }else{
                    ingresarCompra(tarjeta,mes,anio,codigo,titular,email,cedula);
                }
                */

            }
        });
    }

    private void ingresarCompra(String tarjeta, String mes, String anio, String codigo, String titular, String email, String cedula) {
        String LOGIN_REQUEST_URL = "http://"+ Config.IP_LOCAL_HOST +"/urumarkets/public/api/finalizarshrek";

        List producto = CU.getUsuario().getCarrito();
        ItemCarrito ic = (ItemCarrito)producto.get(0);
        String json = new Gson().toJson(producto);
        // JSON data
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("carrito", json);
            jsonObject.put("id_cliente", String.valueOf(CU.getUsuario().getId()));

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
                        Snackbar.make(myView,"Compra finalizada",Snackbar.LENGTH_SHORT).show();
                        CU.getUsuario().setCarrito(new ArrayList<>());
                        new Handler(getMainLooper()).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getFragmentManager().popBackStack();
                            }
                        }, 1500);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof NetworkError) {
                    Toast.makeText(getContext(), "Can't connect to Internet. Please check your connection.", Toast.LENGTH_LONG).show();
                }
                else if (error instanceof ServerError) {
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
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