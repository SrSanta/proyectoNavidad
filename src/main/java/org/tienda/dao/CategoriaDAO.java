package org.tienda.dao;

import org.tienda.model.Categoria;

import java.util.List;

public interface CategoriaDAO {
    void agregarCategoria(Categoria categoria);
    List<Categoria> obtenerCategorias();
    Categoria obtenerCategoriaPorId(int idCategoria);
    void actualizarCategoria(Categoria categoria);
    void eliminarCategoria(int idCategoria);
}
