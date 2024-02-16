package com.campusland.respository;

import java.util.List;
import com.campusland.respository.models.DescuentoFactura;

public interface RepositoryDescuento {
    
    List<DescuentoFactura> listar();

    DescuentoFactura porId(int id);

    void crear(DescuentoFactura descuento);

    void editar(DescuentoFactura descuento);

    void eliminar(DescuentoFactura descuento);

    void eliminarDescuento(int id);
}
