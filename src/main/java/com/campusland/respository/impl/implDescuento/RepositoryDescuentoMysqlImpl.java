package com.campusland.respository.impl.implDescuento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.campusland.respository.RepositoryDescuento;
import com.campusland.respository.models.DescuentoFactura;
import com.campusland.utils.conexionpersistencia.conexionbdmysql.ConexionBDMysql;

public class RepositoryDescuentoMysqlImpl implements RepositoryDescuento {

    private Connection getConnection() throws SQLException {
        return ConexionBDMysql.getInstance();
    }

    @Override
    public List<DescuentoFactura> listar() {
        List<DescuentoFactura> listDescuentos = new ArrayList<>();

        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM descuento")) {
            while (rs.next()) {
                listDescuentos.add(crearDescuento(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listDescuentos;
    }

    @Override
    public DescuentoFactura porId(int id) {
        DescuentoFactura descuento = null;

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM descuento WHERE id=?")) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    descuento = crearDescuento(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return descuento;
    }

    @Override
    public void crear(DescuentoFactura descuento) {
        String sql = "INSERT INTO descuento(tipo, porcentaje) VALUES(?, ?)";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, descuento.getTipo());
            stmt.setDouble(2, (double) descuento.getPorcentaje());
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void editar(DescuentoFactura descuento) {
        String sql = "UPDATE descuento SET tipo=?, porcentaje=? WHERE id=?";
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, descuento.getTipo());
            stmt.setDouble(2, (double) descuento.getPorcentaje());
            stmt.setInt(3, (int) descuento.getId());
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void eliminar(DescuentoFactura descuento) {
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement("DELETE FROM descuento WHERE id=?")) {
            stmt.setInt(1, (int) descuento.getId());
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private DescuentoFactura crearDescuento(ResultSet rs) throws SQLException {
        DescuentoFactura descuento = new DescuentoFactura(null, 0);
        descuento.setId(rs.getInt("id"));
        descuento.setTipo(rs.getString("tipo"));
        descuento.setPorcentaje(rs.getDouble("porcentaje"));
        return descuento;
    }

    public void eliminarDescuento(int id) {}
}
