package org.tienda.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.tienda.dao.PedidoDAO;
import org.tienda.dao.PedidoDAOImpl;
import org.tienda.dao.ProductoDAOImpl;
import org.tienda.model.Pedido;
import org.tienda.model.Producto;
import org.tienda.model.Usuario;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ConfirmarPedidoServlet", value = "/tienda/confirmarpedido/*")
public class ConfirmarPedidoServlet extends HttpServlet {
    private PedidoDAO pedidoDAO;
    private ProductoDAOImpl productoDAO;

    @Override
    public void init() throws ServletException {
        pedidoDAO = new PedidoDAOImpl();
        productoDAO = new ProductoDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        Usuario usuarioLogado = (Usuario) (session != null ? session.getAttribute("usuario-logado") : null);
        if (usuarioLogado == null) {
            response.sendRedirect(request.getContextPath() + "/tienda/usuarios/login");
            return;
        }

        Map<Integer, Integer> carrito = (Map<Integer, Integer>) session.getAttribute("carrito");

        if (carrito == null || carrito.isEmpty()) {
            request.setAttribute("mensaje", "El carrito está vacío. No se puede confirmar un pedido.");
            request.getRequestDispatcher("/WEB-INF/jsp/carrito/carrito.jsp").forward(request, response);
            return;
        }

        try {
            Pedido pedido = new Pedido();
            pedido.setUsuarioId(usuarioLogado.getIdUsuario());
            pedido.setFechaPedido(LocalDateTime.now().toString());

            List<Producto> productosPedido = new ArrayList<>();
            for (Map.Entry<Integer, Integer> entry : carrito.entrySet()) {
                int idProducto = entry.getKey();
                int cantidad = entry.getValue();

                Producto productoBase = productoDAO.obtenerProductoPorId(idProducto);
                if (productoBase != null) {
                    Producto producto = new Producto();
                    producto.setIdProducto(idProducto);
                    producto.setNombre(productoBase.getNombre());
                    producto.setCantidad(cantidad);
                    producto.setPrecio(productoBase.getPrecio());
                    productosPedido.add(producto);
                } else {
                    System.err.println("El producto con ID " + idProducto + " no existe en la base de datos.");
                }
            }

            pedido.setProductos(productosPedido);

            pedidoDAO.agregarPedido(pedido);

            session.removeAttribute("carrito");

            request.setAttribute("pedido", pedido);
            request.getRequestDispatcher("/WEB-INF/jsp/pedidos/pedidoConfirmado.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "Hubo un error al procesar tu pedido. Por favor, inténtalo nuevamente.");
            request.getRequestDispatcher("/WEB-INF/jsp/carrito/carrito.jsp").forward(request, response);
        }
    }
}