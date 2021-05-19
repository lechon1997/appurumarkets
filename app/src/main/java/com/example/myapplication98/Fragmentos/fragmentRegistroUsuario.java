package com.example.myapplication98.Fragmentos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.example.myapplication98.Controladores.ControladorUsuario;
import com.example.myapplication98.Controladores.ControladorVista;
import com.example.myapplication98.Modelo.Departamento;
import com.example.myapplication98.Modelo.Localidad;
import com.example.myapplication98.Modelo.Usuario;
import com.example.myapplication98.R;
import com.example.myapplication98.WebService.MySingleton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private ControladorUsuario CU = ControladorUsuario.getInstance();

    private Map<Integer,Integer> localidades;
    private List<String> localidadesNombre;

    private TextInputEditText etNombre;
    private TextInputEditText etNombre2;
    private TextInputEditText etCi;
    private TextInputEditText etApellido;
    private TextInputEditText etApellido2;
    private TextInputEditText etCorreo;
    private TextInputEditText etTelefono;
    private TextInputEditText pass;
    private TextInputEditText pass2;

    private TextInputLayout ticedula;
    private TextInputLayout tinombre;
    private TextInputLayout tiapellido;
    private TextInputLayout ticorreo;
    private TextInputLayout titelefono;
    private TextInputLayout tipassword;
    private TextInputLayout tipassword2;

    private ArrayAdapter adapter;
    private AutoCompleteTextView ac;
    private AutoCompleteTextView ac2;

    private int idDepartamento;
    private int idLocalidad;

    private Button btnR;
    private View view;
    private AutoCompleteTextView autoCompleteTextView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



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
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        etNombre = view.findViewById(R.id.tietnombre);
        btnR = view.findViewById(R.id.btnRegistrarse2);
        etCi = view.findViewById(R.id.etci);
        etApellido = view.findViewById(R.id.etapellido);
        etCorreo = view.findViewById(R.id.etcorreo);
        etTelefono = view.findViewById(R.id.ettelefono);
        pass = view.findViewById(R.id.pass);
        pass2 = view.findViewById(R.id.pass2);


        ticedula = (TextInputLayout)view.findViewById(R.id.TIcedula);
        tinombre = (TextInputLayout)view.findViewById(R.id.tilnombre);
        tiapellido =  (TextInputLayout)view.findViewById(R.id.TIapellido);
        ticorreo =  (TextInputLayout)view.findViewById(R.id.TIcorreo);
        titelefono =  (TextInputLayout)view.findViewById(R.id.TItelefono);
        tipassword =  (TextInputLayout)view.findViewById(R.id.TIpassword);
        tipassword2 =  (TextInputLayout)view.findViewById(R.id.TIpassword2);

        ac = view.findViewById(R.id.autoCompletedepartamentos);
        ac2 = view.findViewById(R.id.autoCompletelocalidades);

        adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.departamentos_values, R.layout.drop_item_menu);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ac.setAdapter(adapter);

        AdapterView.OnItemClickListener autoc = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                idDepartamento = position + 1;
                getDepartamentosWS(position + 1);
            }
        };

        AdapterView.OnItemClickListener autoc2 = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idLocalidad = localidades.get(position);
            }
        };

        ac.setOnItemClickListener(autoc);
        ac2.setOnItemClickListener(autoc2);

        etCi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(etCi.length() != 0){
                    ticedula.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etNombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etNombre.length() != 0) {
                    tinombre.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etApellido.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(etApellido.length() != 0) {
                    tiapellido.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etCorreo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(etCorreo.length() == 0){
                    ticorreo.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etTelefono.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(etTelefono.length() != 0){
                    titelefono.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(pass.length() != 0){
                    tipassword.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        pass2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(pass2.length() != 0){
                    tipassword2.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etCi.length() == 0) {
                    ticedula.setError("Campo obligatorio");
                } else if (etNombre.length() == 0) {
                    tinombre.setError("Campo obligatorio");
                } else if (etApellido.length() == 0) {
                    tiapellido.setError("Campo obligatorio");
                } else if (etCorreo.length() == 0) {
                    ticorreo.setError("Campo obligatorio");
                } else if (etTelefono.length() == 0) {
                    titelefono.setError("Campo obligatorio");
                } else if (pass.length() == 0) {
                    tipassword.setError("Campo obligatorio");
                } else if (pass2.length() == 0) {
                    tipassword2.setError("Campo obligatorio");
                } else {
                    String cedula = etCi.getText().toString();
                    String nombre = etNombre.getText().toString();
                    String apellido = etApellido.getText().toString();
                    String correo = etCorreo.getText().toString();
                    String telefono = etTelefono.getText().toString();
                    String passwd = pass.getText().toString();
                    String passwd2 = pass2.getText().toString();
                    CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox2);

                    if (checkBox.isChecked()) {
                        Departamento dep = new Departamento();
                        Localidad loc = new Localidad();

                        dep.setId(idDepartamento);
                        loc.setId(idLocalidad);

                        Usuario u = new Usuario(nombre,apellido,correo,cedula,telefono,dep,loc,passwd);
                        CU.setUsuarioEnMemoria(u);

                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragmentConteiner, CV.getFGRegistroEmpreas());
                        transaction.commit();
                    }else{
                        cargarWS(cedula, nombre, "", apellido, "", correo, telefono, passwd, passwd2);
                    }
                }
            }
        });
    }


    private void getDepartamentosWS(int id) {

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

                            ac2.clearListSelection();
                            ac2.setText("");
                            ac2.setAdapter(null);
                            ArrayAdapter adapter2 =new ArrayAdapter(getContext(),R.layout.drop_item_menu,localidadesNombre);
                            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_item);
                            ac2.setAdapter(adapter2);
                            ac2.setSelection(0);

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

    private void cargarWS(String cedula, String nombre, String nombre2, String apellido, String apellido2, String correo, String telefono, String passwd, String passwd2) {
        String url = "http://192.168.1.11/urumarkets/public/api/altaUsuws";

        StringRequest datos = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentConteiner,CV.getFGLogin());
                transaction.commit();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                TextView textView = view.findViewById(R.id.tvws);
                textView.setText("con fallida" + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("nombre", nombre);
                parametros.put("nombre2", nombre2);
                parametros.put("pApellido", apellido);
                parametros.put("apellido2", apellido2);
                parametros.put("documento", cedula);
                parametros.put("telefono", telefono);
                parametros.put("correoE", correo);
                parametros.put("passwd", passwd);
                parametros.put("passwd2", passwd2);
                parametros.put("departamento",String.valueOf(idDepartamento));
                parametros.put("localidad", String.valueOf(idLocalidad));
                return parametros;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("Content-Type", "application/x-www-form-urlencoded");
                return parametros;
            }
        };

        MySingleton.getInstance(getContext()).addToRequestQueue(datos);

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