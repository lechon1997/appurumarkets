package com.example.myapplication98.Modelo;

public class ItemCarrito {
    private Publicacion p;
    private int cantidad;

    public ItemCarrito(Publicacion p, int cantidad) {
        this.p = p;
        this.cantidad = cantidad;
    }

    public Publicacion getP() {
        return p;
    }

    public void setP(Publicacion p) {
        this.p = p;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
