package org.tienda.model;

import java.util.List;
import java.util.Objects;

public class Pedido {
    private int idPedido;
    private int usuarioId;
    private String fechaPedido;
    private List<Producto> productos;  // Lista de productos en el pedido

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(String fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return idPedido == pedido.idPedido && usuarioId == pedido.usuarioId && Objects.equals(fechaPedido, pedido.fechaPedido) && Objects.equals(productos, pedido.productos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPedido, usuarioId, fechaPedido, productos);
    }
}
