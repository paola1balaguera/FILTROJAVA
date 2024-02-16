package com.campusland.respository.impl.implDescuento;

import java.util.List;

import com.campusland.respository.RepositoryDescuento;
import com.campusland.respository.models.DescuentoFactura;
//import com.campusland.utils.conexionpersistencia.conexionbdjson.ConexionBDJsonDescuentos;
import com.campusland.utils.conexionpersistencia.conexionbdjson.ConexionBDJsonDescuento;

public class RepositoryDescuentoJsonImpl implements RepositoryDescuento {

    ConexionBDJsonDescuento conexion = ConexionBDJsonDescuento.getConexion();

    @Override
    public List<DescuentoFactura> listar() {
        return conexion.getData(DescuentoFactura.class);
    }

    @Override
    public DescuentoFactura porId(int id) {
        DescuentoFactura resultado = null;
        for (DescuentoFactura descuento : conexion.getData(DescuentoFactura.class)) {
            if (descuento.getId() == id) {
                resultado = descuento;
                break;
            }
        }
        return resultado;
    }

    @Override
    public void crear(DescuentoFactura descuento) {
        List<DescuentoFactura> listaDescuentos = conexion.getData(DescuentoFactura.class);
        listaDescuentos.add(descuento);
        conexion.saveData(listaDescuentos);
    }

    @Override
    public void editar(DescuentoFactura descuento) {
        List<DescuentoFactura> listaDescuentos = conexion.getData(DescuentoFactura.class);
        boolean change = false;
        for (DescuentoFactura descuentoCurrent : listaDescuentos) {
            if (descuentoCurrent.getId() == descuento.getId()) {
                descuentoCurrent.setTipo(descuento.getTipo());
                descuentoCurrent.setPorcentaje(descuento.getPorcentaje());
                change = true;
                break;
            }
        }
        if (change)
            conexion.saveData(listaDescuentos);
    }

    @Override
    public void eliminar(DescuentoFactura descuento) {
        List<DescuentoFactura> listaDescuentos = conexion.getData(DescuentoFactura.class);
        boolean change = false;
        for (DescuentoFactura descuentoCurrent : listaDescuentos) {
            if (descuentoCurrent.getId() == descuento.getId()) {
                listaDescuentos.remove(descuentoCurrent);
                change = true;
                break;
            }
        }
        if (change)
            conexion.saveData(listaDescuentos);
    }

    @Override
    public void eliminarDescuento(int id) {
        List<DescuentoFactura> listaDescuentos = conexion.getData(DescuentoFactura.class);
        boolean change = false;
        for (DescuentoFactura descuento : listaDescuentos) {
            if (descuento.getId() == id) {
                listaDescuentos.remove(descuento);
                change = true;
                break;
            }
        }
        if (change)
            conexion.saveData(listaDescuentos);
    }
}


