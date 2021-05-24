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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_edit_inf_usu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_edit_inf_usu extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ControladorUsuario CU = ControladorUsuario.getInstance();
    private ControladorVista CV = ControladorVista.getInstance();
    private View myView;
    private ArrayAdapter adapterDep;
    private AutoCompleteTextView acLocalidad;
    private AutoCompleteTextView acDepartamento;

    private Map<Integer,Integer> localidades;
    private List<String> localidadesNombre;

    private int idDepartamento;
    private int idLocalidad;

    private Usuario usu;
    private boolean seteado;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_edit_inf_usu() {
        idDepartamento = 0;
        idLocalidad = 0;
        seteado = false;
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_edit_inf_usu.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_edit_inf_usu newInstance(String param1, String param2) {
        fragment_edit_inf_usu fragment = new fragment_edit_inf_usu();
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
        myView = inflater.inflate(R.layout.fragment_edit_inf_usu, container, false);
        return myView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

        boolean recordado = sharedPref.getBoolean("recordado",false);
        Usuario usuario = null;

        //CHEQUEO SI EL USUARIO ESTÁ RECORDADO
        if(recordado){
            String usuKey = sharedPref.getString("usuarioRecordado","asd");
            String datos = sharedPref.getString(usuKey,"asd");
            Gson gson = new Gson();
            usuario = gson.fromJson(datos, Usuario.class);
            usu = usuario;
        } else
            usuario = CU.getUsuario();

        //EXTRAIGO LOS ELEMENTOS DE LA VISTA DEL FRAGMENTO
        TextInputEditText tietCedula = myView.findViewById(R.id.etci2);
        TextInputEditText tietNombre = myView.findViewById(R.id.tietnombre2);
        TextInputEditText tietApellido = myView.findViewById(R.id.etapellido2);
        TextInputEditText tietCorreo = myView.findViewById(R.id.etcorreo2);
        TextInputEditText tietTelefono = myView.findViewById(R.id.ettelefono2);
        acDepartamento = myView.findViewById(R.id.autoCompletedDep2);
        acLocalidad = myView.findViewById(R.id.autoCompleteloc2);
        idLocalidad = usuario.getLocalidad().getId();
        idDepartamento = usuario.getDepartamento().getId();


        //CREO Y SETEO EL ADAPTADOR CON LOS DEPARTAMENTOS
        adapterDep = ArrayAdapter.createFromResource(getContext(),
                R.array.departamentos_values, R.layout.drop_item_menu);
        adapterDep.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        acDepartamento.setAdapter(adapterDep);

        //SETEO EL DEPARTAMENTO AL QUE PERTENECE EL USUARIO
        acDepartamento.setText(usuario.getDepartamento().getNombre(),false);

        //ESCUCHAR EL CAMBIO DE OPCION EN AUTOCOMPLETE DEPARTAMENTO
        AdapterView.OnItemClickListener adapterDep = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                idDepartamento = position + 1;
                getLocalidades(position + 1);
            }
        };

        //ESCUCHAR EL CAMBIO DE OPCION EN AUTOCOMPLETE LOCALIDAD
        AdapterView.OnItemClickListener adapterLoc = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idLocalidad = localidades.get(position);
            }
        };

        acDepartamento.setOnItemClickListener(adapterDep);
        acLocalidad.setOnItemClickListener(adapterLoc);

        //OBTENER LOCALIDADES DEL DEPARTAMENTO DEL USUARIO
        getLocalidades(usuario.getDepartamento().getId());

        //SETEO DE INFORMACION DEL USUARIO
        tietCedula.setText(usuario.getCedula());
        tietNombre.setText(usuario.getPrimer_nombre());
        tietApellido.setText(usuario.getPrimer_apellido());
        tietCorreo.setText(usuario.getEmail());
        tietTelefono.setText(usuario.getTelefono());

        //EVENTO CONFIRMAR INFORMACION
        Button btnConfirmar = myView.findViewById(R.id.btnConfirmarInfo);
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ci = tietCedula.getText().toString();
                String nom = tietNombre.getText().toString();
                String ape = tietApellido.getText().toString();
                String email = tietCorreo.getText().toString();
                String tel = tietTelefono.getText().toString();
                cargarWS(ci,nom,ape,email,tel);
            }
        });
    }

    private void getLocalidades(int id) {
        String url = "http://192.168.1.11/urumarkets/public/api/getLocalidades?id="+String.valueOf(id);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String nombre = "";
                        try {

                            JSONArray ja = response.getJSONArray("localidades");
                            localidadesNombre = new ArrayList<>();
                            localidades = new HashMap<>();

                            for (int i = 0; i < ja.length();i++) {

                                JSONObject jo = ja.getJSONObject(i);
                                nombre = jo.getString("nombre");
                                localidades.put(i,jo.optInt("id"));
                                localidadesNombre.add(nombre);
                            }

                            acLocalidad.clearListSelection();
                            acLocalidad.setText("");
                            acLocalidad.setAdapter(null);
                            ArrayAdapter adapter2 =new ArrayAdapter(getContext(),R.layout.drop_item_menu,localidadesNombre);
                            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_item);
                            acLocalidad.setAdapter(adapter2);
                            acLocalidad.setSelection(0);

                            if(!seteado){
                                acLocalidad.setText(usu.getLocalidad().getNombre(),false);
                                seteado = true;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });


        RequestQueue queue = MySingleton.getInstance(getContext()).getRequestQueue();
        MySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);

    }

    private void cargarWS(String cedula, String nombre, String apellido, String correo, String telefono) {

        String LOGIN_REQUEST_URL = "http://192.168.1.11/urumarkets/public/api/actualizarDatosU";

        // JSON data
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("id", usu.getId());
            jsonObject.put("nombre", nombre);
            jsonObject.put("nombre2", "");
            jsonObject.put("pApellido", apellido);
            jsonObject.put("apellido2", "");
            jsonObject.put("documento", cedula);
            jsonObject.put("telefono", telefono);
            jsonObject.put("correoE", correo);
            jsonObject.put("departamento",String.valueOf(idDepartamento));
            jsonObject.put("localidad", String.valueOf(idLocalidad));

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

                                Toast.makeText(getContext(),"OK",Toast.LENGTH_SHORT).show();

                                /*
                                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                transaction.replace(R.id.fragmentConteiner,CV.getFGInicio());
                                transaction.commit();
                                */

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