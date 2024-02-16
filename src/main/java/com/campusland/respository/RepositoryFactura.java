package com.campusland.respository;

import java.util.List;

import com.campusland.exceptiones.clienteexceptions.ClienteNullException;
import com.campusland.exceptiones.facturaexceptions.FacturaExceptionInsertDataBase;
import com.campusland.respository.models.Cliente;
import com.campusland.respository.models.Factura;

public interface RepositoryFactura {

    List<Factura> listar();

    void crear(Factura factura)throws FacturaExceptionInsertDataBase;
    
    
    Cliente porDocumento(String documento)throws ClienteNullException ;

    int totalVentas();

/*     void crear(Cliente producto);

    void editar(Cliente producto);

    void eliminar(String id); */

}
