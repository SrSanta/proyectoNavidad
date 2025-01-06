<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="org.tienda.model.Pedido" %>
<%@ page import="org.tienda.model.Producto" %>
<%@ page import="java.util.List" %>

<%
    List<Pedido> pedidos = (List<Pedido>) request.getAttribute("pedidos");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Historial de Pedidos</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 10px;
            text-align: left;
            border: 1px solid #ddd;
        }

        th {
            background-color: #f4f4f4;
        }
    </style>
</head>
<body>
<%@ include file="/WEB-INF/jsp/comun/header.jspf" %>
<h1>Historial de Pedidos</h1>

<%
    if (pedidos != null && !pedidos.isEmpty()) {
%>
<table>
    <thead>
    <tr>
        <th>ID Pedido</th>
        <th>Fecha</th>
        <th>Productos</th>
    </tr>
    </thead>
    <tbody>
    <%
        // Mostrar los pedidos
        for (Pedido pedido : pedidos) {
    %>
    <tr>
        <td><%= pedido.getIdPedido() %></td>
        <td><%= pedido.getFechaPedido() %></td>
        <td>
                <ul>
                    <%
                        List<Producto> productos = pedido.getProductos();
                        if (productos != null && !productos.isEmpty()) {
                            for (Producto producto : productos) {
                    %>
                    <li><%= producto.getNombre() %> (Cantidad: <%= producto.getCantidad() %>)</li>
                    <%
                        }
                    } else {
                    %>
                    <li>No hay productos registrados para este pedido.</li>
                    <%
                        }
                    %>
                </ul>
        </td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>
<%
} else {
%>
<p>No tienes pedidos registrados.</p>
<%
    }
%>
<%@ include file="/WEB-INF/jsp/comun/footer.jspf" %>
</body>
</html>