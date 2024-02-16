package com.campusland.respository.models;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Factura {
    private int numeroFactura;
    private LocalDateTime fecha;
    private Cliente cliente;
    private List<ItemFactura> items;
    private List<DescuentoFactura> descuentos;
    private static int nextNumeroFactura;

    public Factura(int i, LocalDateTime fecha, Cliente cliente) {
        this.numeroFactura = ++nextNumeroFactura;
        this.fecha = fecha;
        this.cliente = cliente;
        this.items = new ArrayList<>();
        this.descuentos = new ArrayList<>();
    }

    public double getTotalFacturaIva() {
        double totalFactura = this.getTotalFactura();
        double PorcentajeIva = 0.19;
        double totalIva, totalFacturaIva = 0;

        totalIva = totalFactura * PorcentajeIva;

        totalFacturaIva = totalIva + totalFactura;

        return totalFacturaIva;
    }

    public double getTotalFactura() {
        double totalFactura = 0;
        for (ItemFactura item : items) {
            totalFactura += item.getImporte();
        }
        return totalFactura;
    }

    public double getTotalFacturaConDescuentos() {
        double totalFactura = getTotalFactura();
        totalFactura -= getDescuentoMontoMinimo(totalFactura);
        totalFactura -= getDescuentoCompra5Unidades(totalFactura);
        totalFactura -= getDescuentoHistorialCompras(totalFactura);
        totalFactura -= getDescuentoViernes(totalFactura);
        return totalFactura;
    }

    private double getDescuentoMontoMinimo(double totalFactura) {
        final double porcentajeDescuento = 0.10;
        final double montoMinimo = 1000;

        if (totalFactura > montoMinimo) {
            double descuento = totalFactura * porcentajeDescuento;
            descuentos.add(new DescuentoFactura(null, descuento));
            return descuento;
        }
        return 0;
    }

    private double getDescuentoCompra5Unidades(double totalFactura) {
        final int cantidadMinima = 5;
        final double montoDescuento = 5;

        double descuento = 0;
        for (ItemFactura item : items) {
            if (item.getCantidad() > cantidadMinima) {
                descuento += item.getCantidad() * montoDescuento;
                descuentos.add(new DescuentoFactura(null, descuento));
            }
        }
        return descuento;
    }

    private double getDescuentoHistorialCompras(double totalFactura) {
        final double porcentajeDescuento = 0.05;
        final int comprasMinimas = 3;

        int totalCompras = cliente.getTotalCompras();
        if (totalCompras >= comprasMinimas) {
            double descuento = totalFactura * porcentajeDescuento;
            descuentos.add(new DescuentoFactura(null, descuento));
            return descuento;
        }
        return 0;
    }

    private double getDescuentoViernes(double totalFactura) {
        final double porcentajeDescuento = 0.03;
        final DayOfWeek viernes = DayOfWeek.FRIDAY;

        if (fecha.getDayOfWeek() == viernes) {
            double descuento = totalFactura * porcentajeDescuento;
            descuentos.add(new DescuentoFactura(null, descuento));
            return descuento;
        }
        return 0;
    }

    public void agregarItem(ItemFactura item) {
        this.items.add(item);
    }

    public void agregarDescuento(DescuentoFactura descuento) {
        this.descuentos.add(descuento);
    }

    public void display() {
        System.out.println();
        System.out.println("Factura: " + this.numeroFactura);
        System.out.println("Cliente: " + cliente.getFullName());
        System.out.println("Fecha: " + this.fecha);
        System.out.println("-----------Detalle Factura----------------------");
        for (ItemFactura item : this.items) {
            System.out.println(item.getProducto().getNombre() + "                " + item.getCantidad() + "                 "
                    + item.getProducto().getPrecioVenta() * item.getCantidad());
        }
        System.out.println();
        System.out.println("Total:                                                      " + getTotalFactura());
        System.out.println("Total con IVA:                                              " + getTotalFacturaIva());
        System.out.println("Total a pagar con descuentos:                               " + getTotalFacturaConDescuentos());
    }

}
