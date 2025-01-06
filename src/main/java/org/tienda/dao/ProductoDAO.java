package org.tienda.dao;

import org.tienda.model.Producto;

import java.util.List;

public interface ProductoDAO {
    void agregarProducto(Producto producto);
    Producto obtenerProductoPorId(int idProducto);
    List<Producto> obtenerProductos();
    void actualizarProducto(Producto producto);
    void eliminarProducto(int idProducto);
}