package com.example.myapplication98.Fragmentos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
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
import com.example.myapplication98.Controladores.ControladorVista;
import com.example.myapplication98.Modelo.Departamento;
import com.example.myapplication98.Modelo.Localidad;
import com.example.myapplication98.Modelo.Usuario;
import com.example.myapplication98.R;
import com.example.myapplication98.WebService.MySingleton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

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

    private static final int GALLERY_REQUEST_CODE = 123;
    ImageView imageView;
    Button btnPick;
    String imagen_uri;

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

    private void AltaProducto(String nombreProducto, String descripcionProducto, String stock, String checkboxOferta, String checkboxTienePrecio, String tipoMoneda, String precioProducto,
                                   String productosPorPersona, String porcentajeOfertaProducto, String estadoProducto, String publicacion, String usuid, String uri) {
        //String url = "http://192.168.1.11/urumarkets/public/api/autenticarUsuario";

        String LOGIN_REQUEST_URL = "http://"+ Config.IP_LOCAL_HOST +"/urumarkets/public/api/altaproductowbs";
        //altaproductowbs
        // JSON data
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("nombreProducto", nombreProducto);
            jsonObject.put("descripcionProducto", descripcionProducto);
            jsonObject.put("stockProducto", stock);
            jsonObject.put("checkboxOferta", checkboxOferta);
            jsonObject.put("checkboxTienePrecio", checkboxTienePrecio);
            jsonObject.put("tipoMoneda", tipoMoneda);
            jsonObject.put("precioProducto", precioProducto);
            jsonObject.put("productosPorPersona", productosPorPersona);
            jsonObject.put("porcentajeOfertaProducto", porcentajeOfertaProducto);
            jsonObject.put("estadoProducto", estadoProducto);
            jsonObject.put("publicacion", publicacion);
            jsonObject.put("usuario_id", usuid);
            //jsonObject.put("file", uri);
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

    /*public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode,@Nullable Intent data){
        if(requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null){
            Uri imageData = data.getData();

            //imagen_uri = getRealPathFromURI(this.getContext(), imageData);

            imagen_uri = imageData.getPath();
            imageView.setImageURI(imageData);
        }

    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        altaP = view.findViewById(R.id.altaP);

        imageView = view.findViewById(R.id.myImageView);
        btnPick = view.findViewById(R.id.btnPickImage);
        btnPick.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(intent.ACTION_GET_CONTENT);
                startActivityForResult(intent.createChooser(intent,"elije tu foto"),GALLERY_REQUEST_CODE);
            }
        });

        altaP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnRegistrarse= view.findViewById(R.id.altaP);

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Switch servicio = (Switch) view.findViewById(R.id.switch3);
                TextInputEditText nombreart2 = view.findViewById(R.id.nombreart2);
                TextInputEditText descripcion2 = view.findViewById(R.id.descripcion2);
                Switch tieneprecio = (Switch) view.findViewById(R.id.switch1);
                Spinner moneda = (Spinner) view.findViewById(R.id.planets_spinner);
                TextInputEditText precio2 = view.findViewById(R.id.precio2);
                TextInputEditText stock2 = view.findViewById(R.id.stock2);
                TextInputEditText limistexpersona2 = view.findViewById(R.id.limistexpersona2);
                Switch estaenoferta = (Switch) view.findViewById(R.id.switch2);
                TextInputEditText oferta2 = view.findViewById(R.id.oferta2);
                Spinner estado = (Spinner) view.findViewById(R.id.planets_spinner2);

                /*Uri selectedImageURI = data.getData();
                Uri imageFilePath = Uri.parse("some/path/to/file.png");
                imageView.setTag(imageFilePath.toString());*/
                String uri = imagen_uri;

                //Switch callSwitch = v.findViewById(R.id.signalCallSwitch);
                String publicacion;
                if (servicio != null && servicio.isChecked()) {
                    publicacion = "servicio";
                } else {
                    publicacion = "productito";
                }

                String tienepreciotext;
                String preciotext = precio2.getText().toString();
                if (tieneprecio != null && tieneprecio.isChecked()) {
                    tienepreciotext = "on";
                } else {
                    tienepreciotext = "off";
                    preciotext = "0";
                }

                String estaenofertatext;
                String oferta = oferta2.getText().toString();
                if (estaenoferta != null && estaenoferta.isChecked()) {
                    estaenofertatext = "on";
                } else {
                    estaenofertatext = "off";
                    oferta = "0";
                }

                String monedatext = moneda.getSelectedItem().toString();
                String estadotext = estado.getSelectedItem().toString();

                String nombrearticulo = nombreart2.getText().toString();
                String descripciontext = descripcion2.getText().toString();


                String stock = stock2.getText().toString();
                String limistexpersona = limistexpersona2.getText().toString();


                if(nombrearticulo=="" || nombrearticulo.isEmpty()){
                    Snackbar alert = Snackbar.make(view, "Debe ingresar un nombre para la publicacion.", 3000);
                    alert.show();
                }else if(descripciontext=="" || descripciontext.isEmpty()){
                    Snackbar alert = Snackbar.make(view, "Debe ingresar la descripcion.", 3000);
                    alert.show();
                }else if(preciotext=="" || preciotext.isEmpty()){
                    Snackbar alert = Snackbar.make(view, "Debe el precio.", 3000);
                    alert.show();
                }else if(stock=="" || stock.isEmpty()){
                    Snackbar alert = Snackbar.make(view, "Debe ingresar el stock disponible.", 3000);
                    alert.show();
                }else if(limistexpersona=="" || limistexpersona.isEmpty()){
                    Snackbar alert = Snackbar.make(view, "Debe ingresar el minimo por persona.", 3000);
                    alert.show();
                }else if(oferta=="" || oferta.isEmpty()){
                    Snackbar alert = Snackbar.make(view, "Debe ingresar el porcentaje de la oferta.", 3000);
                    alert.show();
                }else{
                    AltaProducto(nombrearticulo,descripciontext,stock,estaenofertatext, tienepreciotext,monedatext, preciotext,
                            limistexpersona, oferta, estadotext, publicacion,"1",uri);

                    Snackbar mySnackbar = Snackbar.make(view, "Articulo creado.", 3000);
                    mySnackbar.show();
                }
            }
        });
    }
}