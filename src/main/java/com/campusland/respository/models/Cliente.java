package com.campusland.respository.models;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Cliente {

   
    private String nombre;
    private String apellido;
    private String email;
    private String direccion;
    private String celular;   
    private String documento;
    private int totalCompras;
    private List<Factura> historialCompras = new ArrayList<>();


    public String getFullName(){
        return this.nombre+" "+this.apellido;

    }

    public void agregarCompra(Factura factura) {
        historialCompras.add(factura);
    }

    public int getTotalCompras() {
        return historialCompras.size();
    }

    public void imprimir(){
        System.out.println("Documento: "+this.getDocumento());
        System.out.println("Nombre: "+this.getFullName());
        System.out.println("Email: "+this.getEmail());
        System.out.println("Celular: "+this.getCelular());
        System.out.println("Dirección: "+this.getDireccion());    
    }

    public int getId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getId'");
    }

    public void setId(int int1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setId'");
    }

    public Cliente(String nombre, String apellido, String email, String direccion, String celular, String documento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.direccion = direccion;
        this.celular = celular;
        this.documento = documento;
        this.totalCompras = totalCompras;
        this.historialCompras = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Object stream() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'stream'");
    }


   /*  public List<Compra> getHistorialCompras() {
        return historialCompras;
    } */

/*     public void setHistorialCompras(List<Compra> historialCompras) {
        this.historialCompras = historialCompras;
    } */


    

    
}
