package com.example.myapplication98.Controladores;

import com.example.myapplication98.Modelo.Usuario;

public class ControladorUsuario {
    private static ControladorUsuario Instance = null;

    private Usuario usuario;
    private Usuario usuarioEnMemoria; // reemplazar con bundle
    private boolean SesionIniciada;

    public  void setSession(){
        SesionIniciada = true;
    }

    public void setCerrarSession(){
        SesionIniciada = false;
    }

    public boolean getSession(){
        return SesionIniciada;
    }

    private ControladorUsuario() {
        SesionIniciada = false;
    }

    public static ControladorUsuario getInstance() {
        if (Instance == null)
            Instance = new ControladorUsuario();
        return Instance;

    }

    public Usuario getUsuarioEnMemoria() {
        return usuarioEnMemoria;
    }

    public void setUsuarioEnMemoria(Usuario usuarioEnMemoria) {
        this.usuarioEnMemoria = usuarioEnMemoria;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
