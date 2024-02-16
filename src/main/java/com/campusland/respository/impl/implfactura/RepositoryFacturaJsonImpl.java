package com.campusland.respository.impl.implfactura;

import java.util.List;

import com.campusland.exceptiones.clienteexceptions.ClienteNullException;
import com.campusland.exceptiones.facturaexceptions.FacturaExceptionInsertDataBase;
import com.campusland.respository.RepositoryFactura;
import com.campusland.respository.models.Cliente;
import com.campusland.respository.models.Factura;
import com.campusland.utils.conexionpersistencia.conexionbdjson.ConexionBDJsonFacturas;

public class RepositoryFacturaJsonImpl implements RepositoryFactura {

    ConexionBDJsonFacturas conexion = ConexionBDJsonFacturas.getConexion();

    @Override
    public List<Factura> listar() {
        return conexion.getData(Factura.class);
    }

    @Override
    public void crear(Factura factura) throws FacturaExceptionInsertDataBase {
        List<Factura> listFacturas = conexion.getData(Factura.class);
        listFacturas.add(factura);
        conexion.saveData(listFacturas);
       
    }

    @Override
    public Cliente porDocumento(String documento) throws ClienteNullException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'porDocumento'");
    }

    @Override
    public int totalVentas() {
        return 0;
    }

}
