package com.example.myapplication98.Controladores;

import com.example.myapplication98.Fragmentos.fragmentBuscador;
import com.example.myapplication98.Fragmentos.fragmentCarrito;
import com.example.myapplication98.Fragmentos.fragmentCuenta;
import com.example.myapplication98.Fragmentos.fragmentInicio;
import com.example.myapplication98.Fragmentos.fragmentLogin;
import com.example.myapplication98.Fragmentos.fragmentRegistroEmpresa;
import com.example.myapplication98.Fragmentos.fragmentRegistroUsuario;

public class ControladorVista{
    private static ControladorVista Instance = null;
    private fragmentCarrito FGCarrito;
    private fragmentCuenta FGCuenta;
    private fragmentInicio FGInicio;
    private fragmentBuscador FGBuscador;
    private fragmentLogin FGLogin;
    private fragmentRegistroUsuario FGRegistroUsuario;
    private fragmentRegistroEmpresa FGRegistroEmpreas;

    private ControladorVista() {
        this.FGCarrito = null;
        this.FGCuenta = null;
        this.FGInicio = null;
        this.FGBuscador = null;
        this.FGLogin = null;
        this.FGRegistroUsuario = null;
        this.FGRegistroEmpreas = null;
    }

    public static ControladorVista getInstance(){
        if(Instance == null)
            Instance = new ControladorVista();
        return Instance;
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
        if(FGLogin == null)
            FGLogin = new fragmentLogin();
        return FGLogin;
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
}
