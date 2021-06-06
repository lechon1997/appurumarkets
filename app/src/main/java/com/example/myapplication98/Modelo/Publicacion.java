package com.example.myapplication98.Modelo;

public class Publicacion {
    private String Titulo;
    private int Precio;
    private String Foto;
    private double Descuento;
    private int Stock;
    private String Descripcion;
    public Publicacion() {

    }

    public Publicacion(String titulo, int precio,String foto,double descuento,int stock,String descripcion) {
        Titulo = titulo;
        Precio = precio;
        Foto = foto;
        Descuento = descuento;
        Stock = stock;
        Descripcion = descripcion;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int stock) {
        Stock = stock;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public double getDescuento() {
        return Descuento;
    }

    public void setDescuento(double descuento) {
        Descuento = descuento;
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
