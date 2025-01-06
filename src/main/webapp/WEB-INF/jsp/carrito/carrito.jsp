<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="org.tienda.model.Categoria" %>
<%@ page import="java.util.List" %>
<%@ page import="org.tienda.model.Producto" %>
<%@ page import="java.util.Map" %>
<!DOCTYPE html>
<html>
<head>
  <title>Carrito de Compras</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      margin: 0;
      padding: 0;
      background-color: #f9f9f9;
      color: #333;
    }

    h1, h3 {
      text-align: center;
      margin: 20px;
      color: #555;
    }

    .container {
      width: 90%;
      max-width: 1200px;
      margin: 0 auto;
      padding: 20px;
      background: #fff;
      box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
      border-radius: 8px;
    }

    table {
      width: 100%;
      border-collapse: collapse;
      margin-top: 20px;
    }

    thead {
      background-color: #007bff;
      color: white;
      text-transform: uppercase;
    }

    th, td {
      padding: 12px;
      text-align: left;
      border: 1px solid #ddd;
    }

    tr:nth-child(even) {
      background-color: #f2f2f2;
    }

    tr:hover {
      background-color: #eaf3ff;
    }

    a, form input[type="submit"], button {
      display: inline-block;
      color: #007bff;
      text-decoration: none;
      border: none;
      background: none;
      font-size: 14px;
      cursor: pointer;
    }

    a:hover, form input[type="submit"]:hover, button:hover {
      color: #0056b3;
      text-decoration: underline;
    }

    .empty-cart {
      text-align: center;
      font-size: 18px;
      color: #777;
      margin: 20px;
    }

    .confirm-button {
      text-align: right;
      margin: 20px 0;
    }

    .confirm-button a {
      background-color: #28a745;
      color: white;
      padding: 10px 15px;
      border: none;
      text-decoration: none;
      border-radius: 5px;
    }

    .confirm-button a:hover {
      background-color: #218838;
    }

  </style>
</head>
<body>

<%@ include file="/WEB-INF/jsp/comun/header.jspf" %>

<div class="container">
  <h1>Carrito de Compras</h1>

  <table>
    <thead>
    <tr>
      <th>Producto</th>
      <th>Cantidad</th>
      <th>Precio Unitario</th>
      <th>Subtotal</th>
      <th>Acciones</th>
    </tr>
    </thead>
    <tbody>
    <%
      Map<Producto, Integer> productosConCantidad = (Map<Producto, Integer>) request.getAttribute("productosConCantidad");

      if (productosConCantidad != null && !productosConCantidad.isEmpty()) {
        for (Map.Entry<Producto, Integer> entry : productosConCantidad.entrySet()) {
          Producto producto = entry.getKey();
          int cantidad = entry.getValue();
    %>
    <tr>
      <td><%= producto.getNombre() %></td>
      <td><%= cantidad %></td>
      <td>$<%= producto.getPrecio() %></td>
      <td>$<%= producto.getPrecio().multiply(new java.math.BigDecimal(cantidad)) %></td>
      <td>
        <form action="<%= request.getContextPath() %>/tienda/carrito" method="post">
          <input type="hidden" name="idProducto" value="<%= producto.getIdProducto() %>" />
          <input type="hidden" name="action" value="remove" />
          <button type="submit">Eliminar</button>
        </form>
      </td>
    </tr>
    <%
      }
    } else {
    %>
    <tr>
      <td colspan="5" class="empty-cart">El carrito está vacío.</td>
    </tr>
    <% } %>
    </tbody>
  </table>

  <h3>Categorias disponibles</h3>
  <ul>
    <%
      List<Categoria> listaCategorias = (List<Categoria>) request.getAttribute("listaCategorias");

      if (listaCategorias != null) {
        for (Categoria categoria : listaCategorias) {
    %>
    <li><%= categoria.getNombre() %> - <%= categoria.getDescripcion() %></li>
    <%
        }
      }
    %>
  </ul>

  <div class="confirm-button">
    <a href="${pageContext.request.contextPath}/tienda/confirmarpedido">Confirmar Pedido</a>
  </div>
</div>

<%@ include file="/WEB-INF/jsp/comun/footer.jspf" %>

</body>
</html>