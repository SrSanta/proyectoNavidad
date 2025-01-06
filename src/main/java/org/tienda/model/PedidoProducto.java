package org.tienda.model;

import java.util.Objects;

public class PedidoProducto {
    private int idPedido;
    private int idProducto;
    private int cantidad;

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PedidoProducto that = (PedidoProducto) o;
        return idPedido == that.idPedido && idProducto == that.idProducto && cantidad == that.cantidad;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPedido, idProducto, cantidad);
    }
}
