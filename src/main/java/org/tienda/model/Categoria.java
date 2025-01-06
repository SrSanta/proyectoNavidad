package org.tienda.model;

import java.util.Objects;

public class Categoria {
    private int idCategoria;
    private String nombre;
    private String descripcion;

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Categoria categoria = (Categoria) o;
        return getIdCategoria() == categoria.getIdCategoria() && Objects.equals(getNombre(), categoria.getNombre()) && Objects.equals(getDescripcion(), categoria.getDescripcion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdCategoria(), getNombre(), getDescripcion());
    }
}
