package org.tienda.dao;

import org.tienda.model.Pedido;
import org.tienda.model.Producto;

import java.util.List;

public interface PedidoProductoDAO {
    void agregarProductosAlPedido(Pedido pedido);
    void eliminarProductosPorPedido(int idPedido);
    List<Producto> obtenerProductosPorPedido(int idPedido);
}
