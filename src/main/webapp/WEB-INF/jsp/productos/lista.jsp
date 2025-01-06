<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="org.tienda.model.Producto" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Productos</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f9f9f9;
            color: #333;
        }

        .container {
            width: 90%;
            max-width: 1200px;
            margin: 20px auto;
            padding: 20px;
            background: #fff;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
        }

        h1 {
            text-align: center;
            margin-bottom: 20px;
            color: #555;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        thead {
            background-color: #007bff;
            color: white;
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
            background-color: #e0f7ff;
        }

        button {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 5px 10px;
            border-radius: 5px;
            cursor: pointer;
        }

        button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>

<%@ include file="/WEB-INF/jsp/comun/header.jspf" %>

<div class="container">
    <h1>Lista de Productos</h1>
    <%
        if (esAdmin) {
    %>
    <div class="create-button">
        <a href="${pageContext.request.contextPath}/productos/crear">Crear Nuevo Producto</a>
    </div>
    <%
        }
    %>
    <table>
        <thead>
        <tr>
            <th>Nombre</th>
            <th>Descripcion</th>
            <th>Precio</th>
            <th>Categoria</th>
            <th>Acciones</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<Producto> productos = (List<Producto>) request.getAttribute("productos");
            Map<Integer, String> mapaCategorias = (Map<Integer, String>) request.getAttribute("mapaCategorias");

            if (productos != null && !productos.isEmpty()) {
                for (Producto producto : productos) {
        %>
        <tr>
            <td><%= producto.getNombre() %></td>
            <td><%= producto.getDescripcion() %></td>
            <td>$<%= producto.getPrecio() %></td>
            <td><%= (mapaCategorias.containsKey(producto.getCategoriaId())) ?
                    mapaCategorias.get(producto.getCategoriaId()) :
                    "Sin categoria" %>
            </td>
            <td>

                <%
                    if (usuarioLogado.isPresent()) {
                %>
                <form action="<%= request.getContextPath() %>/tienda/carrito" method="post" style="display:inline;">
                    <input type="hidden" name="action" value="add" />
                    <input type="hidden" name="idProducto" value="<%= producto.getIdProducto() %>" />
                    <label for="cantidad_<%= producto.getIdProducto() %>">Cantidad:</label>
                    <input type="number" name="cantidad" id="cantidad_<%= producto.getIdProducto() %>" value="1" min="1" />
                    <button type="submit">Añadir al carrito</button>
                </form>
                <%
                } else {
                %>
                <p style="color: red;">Inicia sesión para añadir productos al carrito.</p>
                <%
                    }
                %>
                <% if (esAdmin) { %>
                <a href="<%= request.getContextPath() %>/productos/editar/<%= producto.getIdProducto() %>">Editar</a>

                <form action="<%= request.getContextPath() %>/productos" method="post" style="display:inline;">
                    <input type="hidden" name="__method__" value="delete" />
                    <input type="hidden" name="idProducto" value="<%= producto.getIdProducto() %>" />
                    <input type="submit" value="Eliminar" />
                </form>
                <% } %>
            </td>
        </tr>
        <%
            }
        } else {
        %>
        <tr>
            <td colspan="5" style="text-align: center;">No hay productos disponibles.</td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>

<%@ include file="/WEB-INF/jsp/comun/footer.jspf" %>

</body>
</html>