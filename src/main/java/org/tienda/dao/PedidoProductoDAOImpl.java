package org.tienda.dao;

import org.tienda.model.Pedido;
import org.tienda.model.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PedidoProductoDAOImpl extends AbstractDAOImpl implements PedidoProductoDAO {

    @Override
    public void agregarProductosAlPedido(Pedido pedido) {
        String sql = "INSERT INTO pedido_productos (id_pedido, id_producto, cantidad) VALUES (?, ?, ?)";
        try (Connection conn = connectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            for (Producto producto : pedido.getProductos()) {
                ps.setInt(1, pedido.getIdPedido());
                ps.setInt(2, producto.getIdProducto());
                ps.setInt(3, producto.getCantidad());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarProductosPorPedido(int idPedido) {
        String sql = "DELETE FROM pedido_productos WHERE id_pedido = ?";
        try (Connection conn = connectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPedido);
            executeUpdate(ps);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Producto> obtenerProductosPorPedido(int idPedido) {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT p.* FROM productos p " +
                "JOIN pedido_productos pp ON p.id_producto = pp.id_producto " +
                "WHERE pp.id_pedido = ?";
        try (Connection conn = connectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPedido);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Producto producto = new Producto();
                    producto.setIdProducto(rs.getInt("id_producto"));
                    producto.setNombre(rs.getString("nombre"));
                    producto.setPrecio(rs.getBigDecimal("precio"));
                    producto.setDescripcion(rs.getString("descripcion"));
                    productos.add(producto);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return productos;
    }
}
