package org.tienda.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.tienda.dao.CategoriaDAOImpl;
import org.tienda.dao.ProductoDAO;
import org.tienda.dao.ProductoDAOImpl;
import org.tienda.model.Categoria;
import org.tienda.model.Producto;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "productosServlet", urlPatterns = "/productos/*")
public class ProductosServlet extends HttpServlet {

    private ProductoDAO productoDAO = new ProductoDAOImpl();
    private CategoriaDAOImpl categoriaDAO = new CategoriaDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        RequestDispatcher dispatcher;

        if (pathInfo == null || "/".equals(pathInfo)) {
            List<Producto> productos = productoDAO.obtenerProductos();
            List<Categoria> categorias = categoriaDAO.obtenerCategorias();
            Map<Integer, String> mapaCategorias = new HashMap<>();
            for (Categoria categoria : categorias) {
                mapaCategorias.put(categoria.getIdCategoria(), categoria.getNombre());
            }
            request.setAttribute("productos", productos);
            request.setAttribute("mapaCategorias", mapaCategorias);
            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos/lista.jsp");

        } else {
            pathInfo = pathInfo.replaceAll("/$", "");
            String[] pathParts = pathInfo.split("/");

            if (pathParts.length == 2 && "crear".equalsIgnoreCase(pathParts[1])) {
                List<Categoria> categorias = categoriaDAO.obtenerCategorias();
                request.setAttribute("categorias", categorias);
                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos/crear.jsp");

            } else if (pathParts.length == 2) {
                try {
                    int idProducto = Integer.parseInt(pathParts[1]);
                    Producto producto = productoDAO.obtenerProductoPorId(idProducto);
                    if (producto != null) {
                        request.setAttribute("producto", producto);
                        dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos/detalle.jsp");
                    } else {
                        dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos/lista.jsp");
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos/lista.jsp");
                }
            } else if (pathParts.length == 3 && "editar".equalsIgnoreCase(pathParts[1])) {
                try {
                    int idProducto = Integer.parseInt(pathParts[2]);
                    Producto producto = productoDAO.obtenerProductoPorId(idProducto);
                    List<Categoria> categorias = categoriaDAO.obtenerCategorias();

                    if (producto != null) {
                        request.setAttribute("producto", producto);
                        request.setAttribute("categorias", categorias);
                        dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos/editar.jsp");
                    } else {
                        dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos/lista.jsp");
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos/lista.jsp");
                }
            } else {
                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos/lista.jsp");
            }
        }

        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String metodo = request.getParameter("__method__");

        try {
            if (metodo == null) {
                doCreate(request, response);
            } else if ("put".equalsIgnoreCase(metodo)) {
                doPut(request, response);
            } else if ("delete".equalsIgnoreCase(metodo)) {
                doDelete(request, response);
            } else {
                System.out.println("Opción POST no soportada.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/productos");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String idProductoStr = request.getParameter("idProducto");
            String nombre = request.getParameter("nombre");
            String precio = request.getParameter("precio");
            String descripcion = request.getParameter("descripcion");
            String categoriaId = request.getParameter("categoriaId");

            Producto producto = new Producto();
            producto.setIdProducto(Integer.parseInt(idProductoStr));
            producto.setNombre(nombre);
            producto.setPrecio(new BigDecimal(precio));
            producto.setDescripcion(descripcion);
            producto.setCategoriaId(Integer.parseInt(categoriaId));

            productoDAO.actualizarProducto(producto);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String idProductoStr = request.getParameter("idProducto");
            int idProducto = Integer.parseInt(idProductoStr);
            productoDAO.eliminarProducto(idProducto);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doCreate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String nombre = request.getParameter("nombre");
            String precio = request.getParameter("precio");
            String descripcion = request.getParameter("descripcion");
            String categoriaId = request.getParameter("categoriaId");

            Producto nuevoProducto = new Producto();
            nuevoProducto.setNombre(nombre);
            nuevoProducto.setPrecio(new BigDecimal(precio));
            nuevoProducto.setDescripcion(descripcion);
            nuevoProducto.setCategoriaId(Integer.parseInt(categoriaId));

            productoDAO.agregarProducto(nuevoProducto);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}