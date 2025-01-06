package org.tienda.dao;

import org.tienda.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioDAO {
    void agregarUsuario(Usuario usuario);
    List<Usuario> obtenerUsuarios();
    Optional<Usuario> obtenerUsuarioPorId(int idUsuario);
    Optional<Usuario> obtenerUsuarioPorNombre(String nombre);
    void eliminarUsuario(int idUsuario);
    void actualizarUsuario(Usuario usuario);
}
