package com.example.myapplication98.Controladores;

import com.example.myapplication98.Modelo.Publicacion;
import com.example.myapplication98.Modelo.Usuario;

public class ControladorProducto {

    private static ControladorProducto Instance = null;
    private Publicacion p;

    public static ControladorProducto getInstance(){
        if (Instance == null)
            Instance = new ControladorProducto();
        return Instance;
    }

    public Publicacion getP() {
        return p;
    }

    public void setP(Publicacion p) {
        this.p = p;
    }
}
