package com.example.myapplication98.Modelo;

public class Departamento {
    private int id;
    private String nombre;

    public Departamento() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public static String getNombreDepartamebto(int id ){
        String nombre = "";

        switch (id) {
            case 1:
                nombre = "PAYSANDU";
                break;
            case 2:
                nombre = "MONTEVIDEO";
                break;
            case 3:
                nombre = "ARTIGAS";
                break;
            case 4:
                nombre = "CANELONES";
                break;
            case 5:
                nombre = "TACUAREMBO";
                break;
            case 6:
                nombre = "CERRO LARGO";
                break;
            case 7:
                nombre = "COLONIA";
                break;
            case 8:
                nombre = "DURAZNO";
                break;
            case 9:
                nombre = "FLORES";
                break;
            case 10:
                nombre = "FLORIDA";
                break;
            case 11:
                nombre = "LAVALLEJA";
                break;
            case 12:
                nombre = "MALDONADO";
                break;
            case 13:
                nombre = "RIO NEGRO";
                break;
            case 14:
                nombre = "RIVERA";
                break;
            case 15:
                nombre = "ROCHA";
                break;
            case 16:
                nombre = "SALTO";
                break;
            case 17:
                nombre = "SAN JOSE";
                break;
            case 18:
                nombre = "SORIANO";
                break;
            case 19:
                nombre = "TREINTA Y TRES";
                break;

        }

        return nombre;
    }
}
