package com.example.myapplication98.Modelo;

public class Publicacion {
    private String Titulo;
    private int Precio;
    private String Foto;

    public Publicacion() {

    }

    public Publicacion(String titulo, int precio,String foto) {
        Titulo = titulo;
        Precio = precio;
        Foto = foto;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public int getPrecio() {
        return Precio;
    }

    public void setPrecio(int precio) {
        Precio = precio;
    }

    public String getFoto() {
        return Foto;
    }

    public void setFoto(String foto) {
        Foto = foto;
    }
}
