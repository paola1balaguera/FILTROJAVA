package com.campusland.respository.impl.implDescuento;

import java.util.List;

import com.campusland.respository.RepositoryDescuento;
import com.campusland.respository.models.DescuentoFactura;
import com.campusland.utils.conexionpersistencia.conexiondblist.ConexionBDList;

public class RepositoryDescuentoListImpl implements RepositoryDescuento {

    ConexionBDList conexion = ConexionBDList.getConexion();

    @Override
    public List<DescuentoFactura> listar() {
        return conexion.getListaDescuentos();
    }

    @Override
    public DescuentoFactura porId(int id) {
        DescuentoFactura resultado = null;
        for (DescuentoFactura descuento : conexion.getListaDescuentos()) {
            if (descuento.getId() == id) {
                resultado = descuento;
                break;
            }
        }
        return resultado;
    }

    @Override
    public void crear(DescuentoFactura descuento) {
        conexion.getListaDescuentos().add(descuento);
    }

    @Override
    public void editar(DescuentoFactura descuento) {
        for (DescuentoFactura descuentoCurrent : conexion.getListaDescuentos()) {
            if (descuentoCurrent.getId() == descuento.getId()) {
                descuentoCurrent.setTipo(descuento.getTipo());
                descuentoCurrent.setPorcentaje(descuento.getPorcentaje());
                break;
            }
        }
    }

    @Override
    public void eliminar(DescuentoFactura descuento) {
        List<DescuentoFactura> listaDescuentos = conexion.getListaDescuentos();
        for (int i = 0; i < listaDescuentos.size(); i++) {
            if (listaDescuentos.get(i).getId() == descuento.getId()) {
                listaDescuentos.remove(i);
                break;
            }
        }
    }

    @Override
    public void eliminarDescuento(int id) {
        List<DescuentoFactura> listaDescuentos = conexion.getListaDescuentos();
        for (int i = 0; i < listaDescuentos.size(); i++) {
            if (listaDescuentos.get(i).getId() == id) {
                listaDescuentos.remove(i);
                break;
            }
        }
    }
}
