package org.tienda.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.tienda.dao.ProductoDAO;
import org.tienda.dao.ProductoDAOImpl;
import org.tienda.model.Producto;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "/CarritoServlet", value = "/tienda/carrito/*")
public class CarritoServlet extends HttpServlet {

    private ProductoDAO productoDAO;

    @Override
    public void init() throws ServletException {
        productoDAO = new ProductoDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Map<Integer, Integer> carrito = (Map<Integer, Integer>) session.getAttribute("carrito");

        if (carrito == null) {
            carrito = new HashMap<>();
        }

        Map<Producto, Integer> productosConCantidad = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : carrito.entrySet()) {
            Producto producto = productoDAO.obtenerProductoPorId(entry.getKey());
            if (producto != null) {
                productosConCantidad.put(producto, entry.getValue());
            }
        }

        request.setAttribute("productosConCantidad", productosConCantidad);
        request.getRequestDispatcher("/WEB-INF/jsp/carrito/carrito.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Map<Integer, Integer> carrito = (Map<Integer, Integer>) session.getAttribute("carrito");

        if (carrito == null) {
            carrito = new HashMap<>();
        }

        String action = request.getParameter("action");

        if ("add".equals(action)) {
            try {
                int idProducto = Integer.parseInt(request.getParameter("idProducto"));
                int cantidad = Integer.parseInt(request.getParameter("cantidad"));

                Producto producto = productoDAO.obtenerProductoPorId(idProducto);
                if (producto != null) {
                    carrito.put(idProducto, carrito.getOrDefault(idProducto, 0) + cantidad);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

        } else if ("remove".equals(action)) {
            try {
                int idProducto = Integer.parseInt(request.getParameter("idProducto"));
                carrito.remove(idProducto);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        session.setAttribute("carrito", carrito);

        response.sendRedirect(request.getContextPath() + "/tienda/carrito");
    }
}