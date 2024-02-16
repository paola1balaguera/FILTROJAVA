package com.campusland.respository.models;

public class Impuesto {
    private int id;
    private int año;
    private double porcentaje;


    public int getId() {
        return id;
    }

    
    public int getAño() {
        return año;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setId(int id) {
        this.id = id;
    }


    public void setAño(int año) {
        this.año = año;
    }


    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }
}
