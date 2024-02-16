package com.campusland.respository.models;

public class DescuentoFactura {
    private int id;
    private String tipo;
    private double monto;
    private double porcentaje;

    public DescuentoFactura(String tipo, double monto) {
        this.tipo = tipo;
        this.monto = monto;
    }

    public String getTipo() {
        return tipo;
    }

    public double getMonto() {
        return monto;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public int getId() {
       return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public int getDescripcion() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDescripcion'");
    }

    public void setValor(double nuevoValor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setValor'");
    }

    public void setDescripcion(String nuevaDescripcion) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setDescripcion'");
    }

}
