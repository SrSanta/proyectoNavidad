<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="org.tienda.model.Pedido" %>
<%@ page import="org.tienda.model.Producto" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Pedido Confirmado</title>
  <style>
    table {
      width: 80%;
      border-collapse: collapse;
      margin: 20px auto;
    }
    table, th, td {
      border: 1px solid black;
    }
    th, td {
      padding: 10px;
      text-align: center;
    }
    th {
      background-color: #f2f2f2;
    }
    h1, h3 {
      text-align: center;
    }
  </style>
</head>
<body>
<%@ include file="/WEB-INF/jsp/comun/header.jspf" %>

<h1>¡Gracias por tu compra!</h1>
<p style="text-align:center;">El pedido ha sido procesado correctamente. A continuación se muestran los detalles:</p>

<%
  Pedido pedido = (Pedido) request.getAttribute("pedido");
  if (pedido != null) {
%>
<h3>Detalles del Pedido</h3>
<table>
  <tr>
    <td><strong>ID Pedido:</strong></td>
    <td><%= pedido.getIdPedido() %></td>
  </tr>
  <tr>
    <td><strong>Fecha Pedido:</strong></td>
    <td><%= pedido.getFechaPedido() %></td>
  </tr>
  <tr>
    <td><strong>ID Usuario:</strong></td>
    <td><%= pedido.getUsuarioId() %></td>
  </tr>
</table>

<h3>Lista de Productos</h3>
<table>
  <thead>
  <tr>
    <th>Nombre del Producto</th>
    <th>Cantidad</th>
    <th>Precio Unitario</th>
    <th>Subtotal</th>
  </tr>
  </thead>
  <tbody>
  <%
    List<Producto> productos = pedido.getProductos();
    if (productos != null && !productos.isEmpty()) {
      for (Producto producto : productos) {
  %>
  <tr>
    <td><%= producto.getNombre() %></td>
    <td><%= producto.getCantidad() %></td>
    <td><%= producto.getPrecio() %></td>
    <td><%= producto.getPrecio().multiply(new java.math.BigDecimal(producto.getCantidad())) %></td>
  </tr>
  <%
    }
  } else {
  %>
  <tr>
    <td colspan="4">No hay productos asociados a este pedido.</td>
  </tr>
  <%
    }
  %>
  </tbody>
</table>
<%
} else {
%>
<p style="text-align:center;">El pedido no pudo ser encontrado.</p>
<% } %>

<div style="text-align:center; margin-top: 20px;">
  <a href="../index.jsp">Volver a la tienda</a>
</div>

<%@ include file="/WEB-INF/jsp/comun/footer.jspf" %>
</body>
</html>