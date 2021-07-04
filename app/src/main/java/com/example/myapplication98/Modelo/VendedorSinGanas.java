package com.example.myapplication98.Modelo;

public class VendedorSinGanas {
    private int id;
    private String email;
    private String telefono;
    private String img;
    private String nombreFantasia;
    private String departamento;
    private String localidad;
    private String direccion;

    public VendedorSinGanas(int id, String email, String telefono, String img, String nombreFantasia, String departamento, String localidad, String direccion) {
        this.id = id;
        this.email = email;
        this.telefono = telefono;
        this.img = img;
        this.nombreFantasia = nombreFantasia;
        this.departamento = departamento;
        this.localidad = localidad;
        this.direccion = direccion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getNombreFantasia() {
        return nombreFantasia;
    }

    public void setNombreFantasia(String nombreFantasia) {
        this.nombreFantasia = nombreFantasia;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
