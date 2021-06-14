package com.example.myapplication98.Modelo;

public class vendedor extends Usuario{
    private int id;
    private int RUT;
    private String razonSocial;
    private String nombreFantasia;
    private String tipoOrganizacion;
    private String rubro;
    private int telefonoEmpresa;
    private String direccion;
    private String descripcion;

    public vendedor(String primer_nombre, String primer_apellido, String email, String cedula, String telefono, Departamento departamento, Localidad localidad, String pass, int id, int RUT, String razonSocial, String nombreFantasia, String tipoOrganizacion, String rubro, int telefonoEmpresa, String direccion, String descripcion) {
        super(primer_nombre, primer_apellido, email, cedula, telefono, departamento, localidad, pass);
        this.id = id;
        this.RUT = RUT;
        this.razonSocial = razonSocial;
        this.nombreFantasia = nombreFantasia;
        this.tipoOrganizacion = tipoOrganizacion;
        this.rubro = rubro;
        this.telefonoEmpresa = telefonoEmpresa;
        this.direccion = direccion;
        this.descripcion = descripcion;
    }

    public vendedor(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public int getRUT() {
        return RUT;
    }

    public void setRUT(int RUT) {
        this.RUT = RUT;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNombreFantasia() {
        return nombreFantasia;
    }

    public void setNombreFantasia(String nombreFantasia) {
        this.nombreFantasia = nombreFantasia;
    }

    public String getTipoOrganizacion() {
        return tipoOrganizacion;
    }

    public void setTipoOrganizacion(String tipoOrganizacion) {
        this.tipoOrganizacion = tipoOrganizacion;
    }

    public String getRubro() {
        return rubro;
    }

    public void setRubro(String rubro) {
        this.rubro = rubro;
    }

    public int getTelefonoEmpresa() {
        return telefonoEmpresa;
    }

    public void setTelefonoEmpresa(int telefonoEmpresa) {
        this.telefonoEmpresa = telefonoEmpresa;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    
}
