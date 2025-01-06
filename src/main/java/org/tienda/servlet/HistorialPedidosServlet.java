package org.tienda.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.tienda.dao.PedidoDAO;
import org.tienda.dao.PedidoDAOImpl;
import org.tienda.model.Pedido;
import org.tienda.model.Usuario;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "HistorialPedidosServlet", value = "/tienda/historialpedidos")
public class HistorialPedidosServlet extends HttpServlet {
    private PedidoDAO pedidoDAO;

    @Override
    public void init() throws ServletException {
        pedidoDAO = new PedidoDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario-logado") == null) {
            response.sendRedirect(request.getContextPath() + "/tienda/usuarios/login");
            return;
        }
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuario-logado");
        if (usuarioLogado == null) {
            response.sendRedirect(request.getContextPath() + "/tienda/usuarios/login");
            return;
        }

        try {
            int usuarioId = usuarioLogado.getIdUsuario();
            List<Pedido> pedidos = pedidoDAO.obtenerPedidosPorUsuario(usuarioId);
            request.setAttribute("pedidos", pedidos);
            request.getRequestDispatcher("/WEB-INF/jsp/pedidos/historialPedidos.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "Ocurrió un error al cargar el historial de pedidos. Inténtalo de nuevo.");
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }
    }
}