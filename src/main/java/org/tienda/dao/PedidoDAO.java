package org.tienda.dao;

import org.tienda.model.Pedido;

import java.util.List;

public interface PedidoDAO {
    void agregarPedido(Pedido pedido);
    List<Pedido> obtenerPedidos();
    Pedido obtenerPedidoPorId(int idPedido);
    void eliminarPedido(int idPedido);
    List<Pedido> obtenerPedidosPorUsuario(int idCliente);

}
