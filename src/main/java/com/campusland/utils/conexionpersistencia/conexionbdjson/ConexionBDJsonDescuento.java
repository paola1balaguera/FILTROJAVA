package com.campusland.utils.conexionpersistencia.conexionbdjson;

import com.campusland.respository.models.DescuentoFactura;

public class ConexionBDJsonDescuento extends ConexionBDJsonBase<DescuentoFactura> {

    private static ConexionBDJsonDescuento conexionDescuentos;

    private ConexionBDJsonDescuento() {
        super("descuentos.json");
    }

    public static ConexionBDJsonDescuento getConexion() {
        if (conexionDescuentos != null) {
            return conexionDescuentos;
        } else {
            conexionDescuentos = new ConexionBDJsonDescuento();
            return conexionDescuentos;
        }
    }
}

