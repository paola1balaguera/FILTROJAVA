package com.campusland.respository.impl.implfactura;

import java.util.List;

import com.campusland.exceptiones.clienteexceptions.ClienteNullException;
import com.campusland.respository.RepositoryFactura;
import com.campusland.respository.models.Cliente;
import com.campusland.respository.models.Factura;
import com.campusland.utils.conexionpersistencia.conexiondblist.ConexionBDList;

public class RepositoryFacturaImp implements RepositoryFactura {

    ConexionBDList conexion = ConexionBDList.getConexion();

    @Override
    public List<Factura> listar() {
        return conexion.getListFacturas();
        
    }

    @Override
    public void crear(Factura factura) {
       conexion.getListFacturas().add(factura);
        
    }

    @Override
    public Cliente porDocumento(String documento) throws ClienteNullException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'porDocumento'");
    }

    @Override
    public int totalVentas() {
        return 2;
    }

}
