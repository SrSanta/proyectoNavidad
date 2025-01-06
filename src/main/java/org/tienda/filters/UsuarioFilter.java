package org.tienda.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.tienda.model.Usuario;

import java.io.IOException;

@WebFilter(
        urlPatterns = {
                "/admin/*",
                "/productos/crear",
                "/productos/editar",
                "/productos/borrar",
                "/categorias/crear",
                "/categorias/editar",
                "/categorias/borrar",
                "/tienda/usuarios/listar"
        },
        initParams = @WebInitParam(name = "acceso-concedido-a-rol", value = "Admin")
)
public class UsuarioFilter extends HttpFilter {

    private String rolAcceso;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.rolAcceso = filterConfig.getInitParameter("acceso-concedido-a-rol");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        HttpSession session = httpRequest.getSession(false);
        Usuario usuario = session != null ? (Usuario) session.getAttribute("usuario-logado") : null;

        if (usuario != null && rolAcceso.equals(usuario.getRol())) {
            filterChain.doFilter(httpRequest, httpResponse);
        } else {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/tienda/usuarios/login.jsp");
        }
    }

    @Override
    public void destroy() {
    }
}