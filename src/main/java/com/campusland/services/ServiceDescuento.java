package com.campusland.services;

import java.util.List;

import com.campusland.exceptiones.descuentoexceptions.DescuentoNullException;
import com.campusland.respository.models.DescuentoFactura;

public interface ServiceDescuento {

    List<DescuentoFactura> listar();

    DescuentoFactura porId(int id) throws DescuentoNullException;

    void crear(DescuentoFactura descuento);

    void editar(DescuentoFactura descuento);

    void eliminar(DescuentoFactura descuento);

}
