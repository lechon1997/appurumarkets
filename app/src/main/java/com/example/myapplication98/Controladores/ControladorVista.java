package com.example.myapplication98.Controladores;

import com.example.myapplication98.Fragmentos.AltaProducto;
import com.example.myapplication98.Fragmentos.fragmentBuscador;
import com.example.myapplication98.Fragmentos.fragmentCarrito;
import com.example.myapplication98.Fragmentos.fragmentCuenta;
import com.example.myapplication98.Fragmentos.fragmentDatosCliente;
import com.example.myapplication98.Fragmentos.fragmentDatosEmpresa;
import com.example.myapplication98.Fragmentos.fragmentInicio;
import com.example.myapplication98.Fragmentos.fragmentLogeado;
import com.example.myapplication98.Fragmentos.fragmentLogin;
import com.example.myapplication98.Fragmentos.fragmentPrueba;
import com.example.myapplication98.Fragmentos.fragmentRegistroEmpresa;
import com.example.myapplication98.Fragmentos.fragmentRegistroUsuario;
import com.example.myapplication98.Fragmentos.fragmentVerProducto;
import com.example.myapplication98.Fragmentos.fragment_edit_inf_usu;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ControladorVista{
    private static ControladorVista Instance = null;
    private fragmentCarrito FGCarrito;
    private fragmentCuenta FGCuenta;
    private fragmentInicio FGInicio;
    private fragmentBuscador FGBuscador;
    private fragmentLogin FGLogin;
    private BottomNavigationView navigation;
    private fragmentRegistroUsuario FGRegistroUsuario;
    private fragmentRegistroEmpresa FGRegistroEmpreas;
    private fragmentLogeado FGLogeado;

    private ControladorVista() {
        this.FGCarrito = null;
        this.FGCuenta = null;
        this.FGInicio = null;
        this.FGBuscador = null;
        this.FGLogin = null;
        this.FGRegistroUsuario = null;
        this.FGRegistroEmpreas = null;
        this.FGLogeado = null;
    }

    public static ControladorVista getInstance(){
        if(Instance == null)
            Instance = new ControladorVista();
        return Instance;
    }

    public BottomNavigationView getNavigation() {
        return navigation;
    }

    public void setNavigation(BottomNavigationView navigation) {
        this.navigation = navigation;
    }

    public fragmentCarrito getFGCarrito() {
        if(FGCarrito == null)
            FGCarrito = new fragmentCarrito();
        return FGCarrito;
    }

    public void clearFGCarrito(){
        FGCarrito = null;
    }

    public fragmentCuenta getFGCuenta() {
        if(FGCuenta == null)
            FGCuenta = new fragmentCuenta();
        return FGCuenta;
    }

    public void clearFGCuenta(){
        FGCuenta = null;
    }

    public fragmentInicio getFGInicio() {
        if(FGInicio == null)
            FGInicio = new fragmentInicio();
        return FGInicio;
    }

    public void clearFGInicio(){
        FGInicio = null;
    }

    public fragmentBuscador getFGBuscador() {
        if(FGBuscador == null)
            FGBuscador = new fragmentBuscador();
        return FGBuscador;
    }

    public void clearFGBuscador(){
        FGBuscador = null;
    }

    public fragmentLogin getFGLogin() {
        return new fragmentLogin();
    }

    public void clearFGLogin(){
        FGLogin = null;
    }

    public fragmentRegistroUsuario getFGRegistroUsuario() {
        if(FGRegistroUsuario == null)
            FGRegistroUsuario = new fragmentRegistroUsuario();
        return FGRegistroUsuario;
    }

    public void clearFGRegistroUsuario(){
        FGRegistroUsuario = null;
    }

    public fragmentRegistroEmpresa getFGRegistroEmpreas(){
        if(FGRegistroEmpreas == null)
            FGRegistroEmpreas = new fragmentRegistroEmpresa();
        return FGRegistroEmpreas;
    }
    public void clearFGRegistroEmpresa(){
        FGRegistroEmpreas = null;
    }

    public fragmentLogeado getFGlogeado(){
        return new fragmentLogeado();
    }
    public void clearFGLogeado(){
        FGLogeado = null;
    }

    public fragmentDatosCliente getFGDatosCliente(){
        return new fragmentDatosCliente();
    }

    public fragmentPrueba getFGPrueba(){
        return new fragmentPrueba();
    }

    public fragmentDatosEmpresa getFGDatosEmpresa(){
        return new fragmentDatosEmpresa();
    }

    public fragment_edit_inf_usu getFGEditarDatosUsu(){
        return new fragment_edit_inf_usu();
    }


    public AltaProducto AltaP(){
        return new AltaProducto();
    }

    public fragmentVerProducto getFGVerProducto(){
        return new fragmentVerProducto();
    }

}
