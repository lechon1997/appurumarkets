package com.example.myapplication98.Controladores;

import com.example.myapplication98.Modelo.Usuario;
import com.example.myapplication98.Modelo.vendedor;

public class ControladorUsuario {
    private static ControladorUsuario Instance = null;

    private Usuario usuario;
    private Usuario usuarioEnMemoria;
    private vendedor Empresa;

    public vendedor getEmpresa() {
        return Empresa;
    }

    public void setEmpresa(vendedor empresa) {
        Empresa = empresa;
    }

    private boolean SesionIniciada;

    public  void setSession(){
        SesionIniciada = true;
    }

    public void setCerrarSession(){
        SesionIniciada = false;
        usuario = null;
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
