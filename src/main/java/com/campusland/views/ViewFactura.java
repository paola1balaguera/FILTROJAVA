package com.campusland.views;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.campusland.exceptiones.facturaexceptions.FacturaExceptionInsertDataBase;
import com.campusland.respository.models.Cliente;
import com.campusland.respository.models.DescuentoFactura;
import com.campusland.respository.models.Factura;
import com.campusland.respository.models.ItemFactura;
import com.campusland.respository.models.Producto;

public class ViewFactura extends ViewMain {

    private static List<DescuentoFactura> descuentos = new ArrayList<>();

    public static void startMenu() {
        int op = 0;

        do {
            op = mostrarMenu();
            switch (op) {
                case 1:
                    crearFactura();
                    break;
                case 2:
                    listarFactura();
                    break;
                case 3:
                    informeTotalVentas();
                    break;
                case 4:
                    startMenuDescuento();
                    break;
                case 5:
                    generarReporteProductoMasVendido();
                    break;
                case 6:
                    generarReporteClientesPorCompras();

                case 7:
                    return;
                default:
                    System.out.println("Opcion no valida");
                    break;
            }

        } while (op >= 1 && op < 6);

    }

    public static void startMenuDescuento() {
        int op = 0;

        do {
            op = mostrarMenuModuloDescuento();
            switch (op) {
                case 1:
                    crearDescuento();
                    break;
                case 2:
                    editarDescuento();
                    break;
                case 3:
                    eliminarDescuento();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Opcion no valida");
                    break;
            }

        } while (op >= 1 && op < 4);
    }

    public static int mostrarMenu() {
        System.out.println("----Menu--Factura----");
        System.out.println("1. Crear factura.");
        System.out.println("2. Listar factura.");
        System.out.println("3. Informe total de ventas, descuentos e impuestos.");
        System.out.println("4. Modulo de gestion de descuentos");
        System.out.println("5. Generar reporte del producto más vendido");
        System.out.println("6. Generar reporte de clientes por compra ");
        System.out.println("7. Salir ");
        return leer.nextInt();
    }

    public static int mostrarMenuModuloDescuento() {
        System.out.println("----Menu--Descuento----");
        System.out.println("1. Crear descuento");
        System.out.println("2. Editar descuento");
        System.out.println("3. Eliminar descuento");
        System.out.println("4. Salir ");
        return leer.nextInt();
    }

    public static void listarFactura() {
        System.out.println("Lista de Facturas");
        for (Factura factura : serviceFactura.listar()) {
            factura.display();
            System.out.println();
        }
    }

    public static void informeTotalVentas() {
        System.out.println("Informe total de ventas");

        int totalVentas = serviceFactura.totalVentas();
        int totalImpuestos = serviceFactura.totalImpuestos();
        System.out.println("Total Ventas " + totalVentas);
        System.out.println("Total Impuestos " + totalImpuestos);
    }

    public static void crearFactura() {
        System.out.println("-- Creación de Factura ---");

        Cliente cliente;
        do {
            cliente = ViewCliente.buscarGetCliente();
        } while (cliente == null);

        Factura factura = new Factura(0, LocalDateTime.now(), cliente);
        List<DescuentoFactura> descuentos = new ArrayList<>();

        System.out.println("-- Se creó la factura -----------------");
        System.out.println("-- Seleccione los productos a comprar por código");

        do {
            Producto producto = ViewProducto.buscarGetProducto();

            if (producto != null) {
                System.out.print("Cantidad: ");
                int cantidad = leer.nextInt();
                ItemFactura item = new ItemFactura(cantidad, producto);
                factura.agregarItem(item);

                System.out.println("Agregar otro producto: si o no");
                String otroItem = leer.next();
                if (!otroItem.equalsIgnoreCase("si")) {
                    break;
                }
            }

        } while (true);

        List<DescuentoFactura> descuentosAplicados = aplicarDescuentos(factura);
        factura.getDescuentos().addAll(descuentosAplicados);

        try {
            serviceFactura.crear(factura);
            System.out.println("Se creó la factura");
            factura.display();
        } catch (FacturaExceptionInsertDataBase e) {
            System.out.println(e.getMessage());
        }
    }

    private static List<DescuentoFactura> aplicarDescuentos(Factura factura) {
        List<DescuentoFactura> descuentosAplicados = new ArrayList<>();
        DescuentoFactura descuentoMontoMinimo = getDescuentoMontoMinimo(factura);
        if (descuentoMontoMinimo != null) {
            descuentosAplicados.add(descuentoMontoMinimo);
        }
        DescuentoFactura descuentoCompra5Unidades = getDescuentoCompra5Unidades(factura);
        if (descuentoCompra5Unidades != null) {
            descuentosAplicados.add(descuentoCompra5Unidades);
        }
        DescuentoFactura descuentoHistorialCompras = getDescuentoHistorialCompras(factura);
        if (descuentoHistorialCompras != null) {
            descuentosAplicados.add(descuentoHistorialCompras);
        }
        DescuentoFactura descuentoViernes = getDescuentoViernes(factura);
        if (descuentoViernes != null) {
            descuentosAplicados.add(descuentoViernes);
        }
        return descuentosAplicados;
    }

    private static DescuentoFactura getDescuentoMontoMinimo(Factura factura) {
        final double porcentajeDescuento = 0.10;
        final double montoMinimo = 1000;

        double totalFactura = factura.getTotalFactura();
        if (totalFactura > montoMinimo) {
            double descuento = totalFactura * porcentajeDescuento;
            return new DescuentoFactura("Monto mínimo", descuento);
        }
        return null;
    }

    private static DescuentoFactura getDescuentoCompra5Unidades(Factura factura) {
        final int cantidadMinima = 5;
        final double montoDescuento = 5;

        double totalDescuento = 0;
        for (ItemFactura item : factura.getItems()) {
            if (item.getCantidad() > cantidadMinima) {
                double descuento = item.getCantidad() * montoDescuento;
                totalDescuento += descuento;
            }
        }
        if (totalDescuento > 0) {
            return new DescuentoFactura("Compra 5 unidades", totalDescuento);
        }
        return null;
    }

    private static DescuentoFactura getDescuentoHistorialCompras(Factura factura) {
        final double porcentajeDescuento = 0.05;
        final int comprasMinimas = 3;

        int totalCompras = factura.getCliente().getTotalCompras();
        double totalFactura = factura.getTotalFactura();
        if (totalCompras >= comprasMinimas) {
            double descuento = totalFactura * porcentajeDescuento;
            return new DescuentoFactura("Historial de compras", descuento);
        }
        return null;
    }

    private static DescuentoFactura getDescuentoViernes(Factura factura) {
        final double porcentajeDescuento = 0.03;
        final DayOfWeek viernes = DayOfWeek.FRIDAY;

        if (factura.getFecha().getDayOfWeek() == viernes) {
            double totalFactura = factura.getTotalFactura();
            double descuento = totalFactura * porcentajeDescuento;
            return new DescuentoFactura("Descuento viernes", descuento);
        }
        return null;
    }

    public static void crearDescuento() {
        System.out.println("-- Creación de Descuento --");
        System.out.println("Seleccione el tipo de descuento:");
        System.out.println("1. Porcentaje");
        System.out.println("2. Monto");
        int tipoDescuento = leer.nextInt();
        leer.nextLine();

        String descripcion;
        double valor;
        switch (tipoDescuento) {
            case 1:
                System.out.println("Ingrese la descripción del descuento:");
                descripcion = leer.nextLine();
                System.out.println("Ingrese el porcentaje de descuento:");
                valor = leer.nextDouble();
                DescuentoFactura descuentoPorcentaje = new DescuentoFactura(descripcion, valor);
                descuentos.add(descuentoPorcentaje);
                System.out.println("Descuento por porcentaje creado exitosamente.");
                break;
            case 2:
                System.out.println("Ingrese la descripción del descuento:");
                descripcion = leer.nextLine();
                System.out.println("Ingrese el monto de descuento:");
                valor = leer.nextDouble();
                DescuentoFactura descuentoMonto = new DescuentoFactura(descripcion, valor);
                descuentos.add(descuentoMonto);
                System.out.println("Descuento por monto creado exitosamente.");
                break;
            default:
                System.out.println("Opción no válida");
                break;
        }
    }

    public static void editarDescuento() {
        System.out.println("-- Edición de Descuento --");
        System.out.println("Seleccione el descuento a editar:");

        for (int i = 0; i < descuentos.size(); i++) {
            System.out.println((i + 1) + ". " + descuentos.get(i).getDescripcion());
        }

        int indice = leer.nextInt() - 1;
        leer.nextLine();

        if (indice >= 0 && indice < descuentos.size()) {
            DescuentoFactura descuento = descuentos.get(indice);
            System.out.println("Ingrese la nueva descripción (Enter para mantener la actual):");
            String nuevaDescripcion = leer.nextLine();
            if (!nuevaDescripcion.isEmpty()) {
                descuento.setDescripcion(nuevaDescripcion);
            }

            System.out.println("Ingrese el nuevo valor (Enter para mantener el actual):");
            String nuevoValorStr = leer.nextLine();
            if (!nuevoValorStr.isEmpty()) {
                double nuevoValor = Double.parseDouble(nuevoValorStr);
                descuento.setValor(nuevoValor);
            }

            System.out.println("Descuento editado exitosamente.");
        } else {
            System.out.println("Índice de descuento inválido.");
        }
    }

    public static void eliminarDescuento() {
        System.out.println("-- Eliminación de Descuento --");
        System.out.println("Seleccione el descuento a eliminar:");

        for (int i = 0; i < descuentos.size(); i++) {
            System.out.println((i + 1) + ". " + descuentos.get(i).getDescripcion());
        }

        int indice = leer.nextInt() - 1;
        leer.nextLine();

        if (indice >= 0 && indice < descuentos.size()) {
            descuentos.remove(indice);
            System.out.println("Descuento eliminado exitosamente.");
        } else {
            System.out.println("Índice de descuento inválido.");
        }
    }

    
    public static void generarReporteClientesPorCompras() {
        String url = "jdbc:mysql://localhost:3306/dabase_factura?serverTimezone=America/Santiago";
        String user = "administrador";
        String password = "pepe123";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT CONCAT(c.nombre, ' ', c.apellido) AS nombre_cliente, SUM(IFNULL(i.importe, 0)) AS total_compras " +
                         "FROM cliente c " +
                         "LEFT JOIN factura f ON c.id = f.cliente_id " +
                         "LEFT JOIN item_factura i ON f.numeroFactura = i.factura_numeroFactura " +
                         "GROUP BY c.id " +
                         "ORDER BY total_compras DESC";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            List<String> clientesPorCompras = new ArrayList<>();
            while (rs.next()) {
                String nombreCliente = rs.getString("nombre_cliente");
                double totalCompras = rs.getDouble("total_compras");
                clientesPorCompras.add(nombreCliente + ": " + totalCompras);
            }

            System.out.println("Clientes por compras:");
            clientesPorCompras.forEach(System.out::println);

        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }

    public static void generarReporteProductoMasVendido() {
        String url = "jdbc:mysql://localhost:3306/dabase_factura?serverTimezone=America/Santiago";
        String user = "administrador";
        String password = "pepe123";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT p.nombre AS nombre_producto, SUM(IFNULL(i.cantidad, 0)) AS total_vendido " +
                         "FROM producto p " +
                         "LEFT JOIN item_factura i ON p.codigo = i.producto_codigo " +
                         "GROUP BY p.codigo " +
                         "ORDER BY total_vendido DESC " +
                         "LIMIT 1";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                String nombreProducto = rs.getString("nombre_producto");
                int totalVendido = rs.getInt("total_vendido");
                System.out.println("Producto más vendido:");
                System.out.println(nombreProducto + ": " + totalVendido);
            } else {
                System.out.println("No hay productos vendidos.");
            }

        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }
}
