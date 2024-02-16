package com.campusland.services.impl;

import java.util.List;

import com.campusland.exceptiones.descuentoexceptions.DescuentoNullException;
import com.campusland.respository.RepositoryDescuento;
import com.campusland.respository.models.DescuentoFactura;
import com.campusland.services.ServiceDescuento;

public class ServiceDescuentoImpl implements ServiceDescuento {

    private final RepositoryDescuento crudRepositoryDescuento;

    public ServiceDescuentoImpl(RepositoryDescuento crudRepositoryDescuento) {
        this.crudRepositoryDescuento = crudRepositoryDescuento;
    }

    @Override
    public List<DescuentoFactura> listar() {
        return this.crudRepositoryDescuento.listar();
    }

    @Override
    public DescuentoFactura porId(int id) throws DescuentoNullException {
        DescuentoFactura descuento = this.crudRepositoryDescuento.porId(id);
        if (descuento != null) {
            return descuento;
        } else {
            throw new DescuentoNullException("No se encontró descuento por id");
        }
    }

    @Override
    public void crear(DescuentoFactura descuento) {
        this.crudRepositoryDescuento.crear(descuento);
    }

    @Override
    public void editar(DescuentoFactura descuento) {
        this.crudRepositoryDescuento.editar(descuento);
    }

    @Override
    public void eliminar(DescuentoFactura descuento) {
        this.crudRepositoryDescuento.eliminar(descuento);
    }
}

