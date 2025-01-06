package org.tienda.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.tienda.dao.CategoriaDAO;
import org.tienda.dao.CategoriaDAOImpl;
import org.tienda.model.Categoria;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "CategoriasServlet", value = "/tienda/categorias/*")
public class CategoriasServlet extends HttpServlet {

    private CategoriaDAO categoriaDAO;

    @Override
    public void init() throws ServletException {
        categoriaDAO = new CategoriaDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher;
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || "/".equals(pathInfo)) {
            List<Categoria> listaCategorias = categoriaDAO.obtenerCategorias();

            request.setAttribute("listaCategorias", listaCategorias);
            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/categorias/categorias.jsp");

        } else {
            pathInfo = pathInfo.replaceAll("/$", "");
            String[] pathParts = pathInfo.split("/");

            if (pathParts.length == 2 && "crear".equals(pathParts[1])) {
                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/categorias/crearCategoria.jsp");

            } else if (pathParts.length == 3 && "editar".equals(pathParts[1])) {
                try {
                    int idCategoria = Integer.parseInt(pathParts[2]);
                    Optional<Categoria> categoriaOptional = Optional.ofNullable(categoriaDAO.obtenerCategoriaPorId(idCategoria));

                    if (categoriaOptional.isPresent()) {
                        request.setAttribute("categoria", categoriaOptional.get());
                        dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/categorias/editarCategoria.jsp");
                    } else {
                        request.setAttribute("error", "Categoría no encontrada");
                        dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/categorias/categorias.jsp");
                    }
                } catch (NumberFormatException e) {
                    request.setAttribute("error", "ID inválido");
                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/categorias/categorias.jsp");
                }

            } else {
                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/categorias/categorias.jsp");
            }
        }

        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String __method__ = request.getParameter("__method__");

        if (__method__ == null) {
            String nombre = request.getParameter("nombre");
            String descripcion = request.getParameter("descripcion");

            Categoria nuevaCategoria = new Categoria();
            nuevaCategoria.setNombre(nombre);
            nuevaCategoria.setDescripcion(descripcion);

            categoriaDAO.agregarCategoria(nuevaCategoria);

        } else if ("put".equalsIgnoreCase(__method__)) {
            doPut(request, response);

        } else if ("delete".equalsIgnoreCase(__method__)) {
            doDelete(request, response);

        } else {
            System.out.println("Operación POST no soportada");
        }

        response.sendRedirect(request.getContextPath() + "/tienda/categorias");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idCategoriaStr = request.getParameter("idCategoria");
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");

        try {
            int idCategoria = Integer.parseInt(idCategoriaStr);
            Categoria categoria = categoriaDAO.obtenerCategoriaPorId(idCategoria);

            if (categoria != null) {
                categoria.setNombre(nombre);
                categoria.setDescripcion(descripcion);
                categoriaDAO.actualizarCategoria(categoria);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idCategoriaStr = request.getParameter("idCategoria");

        try {
            int idCategoria = Integer.parseInt(idCategoriaStr);
            categoriaDAO.eliminarCategoria(idCategoria);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}