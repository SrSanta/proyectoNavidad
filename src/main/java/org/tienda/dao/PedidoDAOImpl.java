package org.tienda.dao;

import org.tienda.dao.PedidoDAO;
import org.tienda.dao.PedidoProductoDAO;
import org.tienda.dao.PedidoProductoDAOImpl;
import org.tienda.model.Pedido;
import org.tienda.model.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PedidoDAOImpl extends AbstractDAOImpl implements PedidoDAO {

    private PedidoProductoDAO pedidoProductoDAO;

    public PedidoDAOImpl() {
        this.pedidoProductoDAO = new PedidoProductoDAOImpl();
    }

    @Override
    public void agregarPedido(Pedido pedido) {
        String sqlPedido = "INSERT INTO pedidos (usuario_id, fecha_pedido) VALUES (?, ?)";
        try (Connection conn = connectDB();
             PreparedStatement psPedido = prepareStmtGeneratedKeys(conn, sqlPedido)) {
            psPedido.setInt(1, pedido.getUsuarioId());
            psPedido.setString(2, pedido.getFechaPedido());

            Optional<Integer> idPedido = executeInsert(psPedido);
            idPedido.ifPresent(pedido::setIdPedido);

            pedidoProductoDAO.agregarProductosAlPedido(pedido);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Pedido> obtenerPedidos() {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM pedidos";
        try (Connection conn = connectDB();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setIdPedido(rs.getInt("id_pedido"));
                pedido.setUsuarioId(rs.getInt("usuario_id"));
                pedido.setFechaPedido(rs.getString("fecha_pedido"));
                pedido.setProductos(pedidoProductoDAO.obtenerProductosPorPedido(pedido.getIdPedido()));
                pedidos.add(pedido);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return pedidos;
    }

    @Override
    public Pedido obtenerPedidoPorId(int idPedido) {
        Pedido pedido = null;
        String sql = "SELECT * FROM pedidos WHERE id_pedido = ?";
        try (Connection conn = connectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPedido);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    pedido = new Pedido();
                    pedido.setIdPedido(rs.getInt("id_pedido"));
                    pedido.setUsuarioId(rs.getInt("usuario_id"));
                    pedido.setFechaPedido(rs.getString("fecha_pedido"));
                    pedido.setProductos(pedidoProductoDAO.obtenerProductosPorPedido(idPedido));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return pedido;
    }

    @Override
    public void eliminarPedido(int idPedido) {
        pedidoProductoDAO.eliminarProductosPorPedido(idPedido);

        String sql = "DELETE FROM pedidos WHERE id_pedido = ?";
        try (Connection conn = connectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPedido);
            executeUpdate(ps);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Pedido> obtenerPedidosPorUsuario(int usuarioId) {
        String sql = "SELECT id_pedido, usuario_id, fecha_pedido FROM pedidos WHERE usuario_id = ? ORDER BY fecha_pedido DESC";
        List<Pedido> pedidos = new ArrayList<>();

        try (Connection conn = connectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Pedido pedido = new Pedido();
                    pedido.setIdPedido(rs.getInt("id_pedido"));
                    pedido.setUsuarioId(rs.getInt("usuario_id"));
                    pedido.setFechaPedido(rs.getString("fecha_pedido"));

                    List<Producto> productos = pedidoProductoDAO.obtenerProductosPorPedido(pedido.getIdPedido());
                    pedido.setProductos(productos != null ? productos : new ArrayList<>()); // Evitar null

                    pedidos.add(pedido);
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return pedidos;
    }
}
