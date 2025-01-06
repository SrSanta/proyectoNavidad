package org.tienda.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.tienda.dao.UsuarioDAO;
import org.tienda.dao.UsuarioDAOImpl;
import org.tienda.model.Usuario;
import org.tienda.util.Util;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@WebServlet(name = "usuariosServlet", value = "/tienda/usuarios/*")
public class UsuarioServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher;
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || "/".equals(pathInfo) || "/listar".equals(pathInfo)) {
            UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
            request.setAttribute("usuarios", usuarioDAO.obtenerUsuarios());
            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuarios/listarUsuario.jsp");
        } else if ("/login".equals(pathInfo)) {
            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login/login.jsp");
        } else if ("/crear".equals(pathInfo)) {
            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuarios/crearUsuario.jsp");
        } else if (pathInfo.startsWith("/editar")) {
            String[] pathParts = pathInfo.split("/");
            if (pathParts.length == 3) {
                try {
                    int idUsuario = Integer.parseInt(pathParts[2]);
                    UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
                    Optional<Usuario> usuarioOpt = usuarioDAO.obtenerUsuarioPorId(idUsuario);
                    if (usuarioOpt.isPresent()) {
                        request.setAttribute("usuario", usuarioOpt.get());
                        dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuarios/editarUsuario.jsp");
                    } else {
                        request.setAttribute("error", "El usuario no fue encontrado.");
                        dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuarios/listarUsuario.jsp");
                    }
                } catch (NumberFormatException e) {
                    request.setAttribute("error", "El ID del usuario es inválido.");
                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuarios/listarUsuario.jsp");
                }
            } else {
                request.setAttribute("error", "Ruta inválida para editar usuario.");
                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuarios/listarUsuario.jsp");
            }
        } else {
            request.setAttribute("error", "Ruta no encontrada.");
            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuarios/listarUsuario.jsp");
        }

        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String __method__ = request.getParameter("__method__");

        if (__method__ == null) {
            Usuario usuario = new Usuario();
            usuario.setNombre(request.getParameter("nombre"));
            usuario.setRol(request.getParameter("rol"));

            try {
                usuario.setPassword(Util.hashPassword(request.getParameter("password")));
            } catch (NoSuchAlgorithmException e) {
                request.setAttribute("error", "Error al encriptar la contraseña.");
                request.getRequestDispatcher("/WEB-INF/jsp/usuarios/crearUsuario.jsp").forward(request, response);
                return;
            }

            UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
            usuarioDAO.agregarUsuario(usuario);
            response.sendRedirect(request.getContextPath() + "/tienda/usuarios/listar");
            return;

        } else if ("login".equalsIgnoreCase(__method__)) {
            doLogin(request, response);
            return;

        } else if ("logout".equalsIgnoreCase(__method__)) {
            doLogout(request, response);
            response.sendRedirect(request.getContextPath() + "/tienda/usuarios/login");
            return;

        } else if ("put".equalsIgnoreCase(__method__)) {
            doPut(request, response);
        } else if ("delete".equalsIgnoreCase(__method__)) {
            doDelete(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/tienda/usuarios/listar");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
        try {
            int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(idUsuario);
            usuario.setNombre(request.getParameter("nombre"));
            usuario.setRol(request.getParameter("rol"));

            try {
                usuario.setPassword(Util.hashPassword(request.getParameter("password")));
            } catch (NoSuchAlgorithmException e) {
                request.setAttribute("error", "Error al encriptar la contraseña.");
                request.getRequestDispatcher("/WEB-INF/jsp/usuarios/editarUsuario.jsp").forward(request, response);
                return;
            }

            usuarioDAO.actualizarUsuario(usuario);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "El ID del usuario es inválido.");
        }
        response.sendRedirect(request.getContextPath() + "/tienda/usuarios/listar");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
        try {
            int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
            usuarioDAO.eliminarUsuario(idUsuario);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        try {
            response.sendRedirect(request.getContextPath() + "/tienda/usuarios/listar");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombre = request.getParameter("nombre");
        String password = request.getParameter("password");

        UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
        Optional<Usuario> usuarioOpt = usuarioDAO.obtenerUsuarioPorNombre(nombre);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            try {
                String hashedPassword = Util.hashPassword(password);

                if (usuario.getPassword().equals(hashedPassword)) {
                    HttpSession session = request.getSession(true);
                    session.setAttribute("usuario-logado", usuario);
                    request.getRequestDispatcher("/index.jsp").forward(request, response);
                } else {
                    request.setAttribute("error", "Contraseña incorrecta.");
                    request.getRequestDispatcher("/WEB-INF/jsp/login/login.jsp").forward(request, response);
                }
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("Error al encriptar la contraseña", e);
            }
        } else {
            request.setAttribute("error", "Usuario no encontrado.");
            request.getRequestDispatcher("/WEB-INF/jsp/login/login.jsp").forward(request, response);
        }
    }

    private void doLogout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}