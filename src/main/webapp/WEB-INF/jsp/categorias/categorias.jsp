<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="org.tienda.model.Categoria" %>
<%@ page import="org.tienda.model.Usuario" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Optional" %>
<%@ include file="/WEB-INF/jsp/comun/header.jspf" %>

<html>
<head>
  <title>Lista de Categorias</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      margin: 0;
      padding: 0;
      background-color: #f9f9f9;
      color: #333;
    }

    h1 {
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

    .create-button {
      text-align: right;
      margin: 20px 0;
    }

    .create-button a {
      background-color: #28a745;
      color: white;
      padding: 10px 15px;
      border: none;
      text-decoration: none;
      border-radius: 5px;
      font-size: 14px;
    }

    .create-button a:hover {
      background-color: #218838;
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

    a, form input[type="submit"] {
      display: inline-block;
      color: #007bff;
      text-decoration: none;
      border: none;
      background: none;
      font-size: 14px;
      cursor: pointer;
    }

    a:hover, form input[type="submit"]:hover {
      color: #0056b3;
      text-decoration: underline;
    }

    form {
      display: inline;
    }

    p {
      text-align: center;
      font-size: 16px;
      color: #777;
    }
  </style>
</head>
<body>
<div class="container">
  <h1>Lista de Categorias</h1>

  <% if (esAdmin) { %>
  <div class="create-button">
    <a href="${pageContext.request.contextPath}/tienda/categorias/crear">Crear Nueva Categoria</a>
  </div>
  <% } %>

  <%
    List<Categoria> listaCategorias = (List<Categoria>) request.getAttribute("listaCategorias");
  %>

  <%
    if (listaCategorias != null && !listaCategorias.isEmpty()) {
  %>
  <table>
    <thead>
    <tr>
      <th>ID</th>
      <th>Nombre</th>
      <th>Descripcion</th>
      <% if (esAdmin) { %>
      <th>Acciones</th>
      <% } %>
    </tr>
    </thead>
    <tbody>
    <%
      for (Categoria categoria : listaCategorias) {
    %>
    <tr>
      <td><%= categoria.getIdCategoria() %></td>
      <td><%= categoria.getNombre() %></td>
      <td><%= categoria.getDescripcion() %></td>
      <% if (esAdmin) { %>
      <td>
        <a href="<%= request.getContextPath() %>/tienda/categorias/editar/<%= categoria.getIdCategoria() %>">Editar</a> |
        <form action="${pageContext.request.contextPath}/tienda/categorias" method="post">
          <input type="hidden" name="__method__" value="delete" />
          <input type="hidden" name="idCategoria" value="<%= categoria.getIdCategoria() %>" />
          <input type="submit" value="Eliminar" />
        </form>
      </td>
      <% } %>
    </tr>
    <%
      }
    %>
    </tbody>
  </table>
  <%
  } else {
  %>
  <p>No se encontraron categorias.</p>
  <%
    }
  %>
</div>
</body>
<%@ include file="/WEB-INF/jsp/comun/footer.jspf" %>
</html>