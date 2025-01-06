package org.tienda.model;

import java.util.Objects;

public class Usuario {
    
    private int idUsuario;
    private String nombre;
    private String password;
    private String rol;

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return getIdUsuario() == usuario.getIdUsuario() && Objects.equals(getNombre(), usuario.getNombre()) && Objects.equals(getPassword(), usuario.getPassword()) && Objects.equals(getRol(), usuario.getRol());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdUsuario(), getNombre(), getPassword(), getRol());
    }
}
