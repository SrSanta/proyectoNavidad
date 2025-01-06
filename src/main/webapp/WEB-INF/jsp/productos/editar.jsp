<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/comun/header.jspf" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Producto</title>
    <style>
        .form-container {
            margin: 50px auto;
            width: 400px;
            border: 1px solid #ccc;
            border-radius: 5px;
            padding: 20px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
        }
        h2 {
            text-align: center;
            color: #333;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-label {
            display: block;
            font-weight: bold;
            margin-bottom: 5px;
        }
        .form-input, .form-select {
            width: 100%;
            padding: 8px;
            font-size: 14px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }
        .form-button-container {
            text-align: center;
            margin-top: 20px;
        }
        .form-button {
            background-color: #007BFF;
            color: white;
            border: none;
            padding: 10px 20px;
            font-size: 14px;
            cursor: pointer;
            border-radius: 4px;
        }
        .form-button:hover {
            background-color: #0056b3;
        }
        .link-container {
            text-align: center;
            margin-top: 20px;
        }
        .back-link {
            text-decoration: none;
            color: #007BFF;
            font-size: 14px;
        }
        .back-link:hover {
            color: #0056b3;
        }
    </style>
</head>
<body>
<main>
    <div class="form-container">
        <h2>Editar Producto</h2>
        <form action="${pageContext.request.contextPath}/productos" method="post">
            <input type="hidden" name="__method__" value="put">
            <input type="hidden" name="idProducto" value="${producto.idProducto}">

            <div class="form-group">
                <label for="nombre" class="form-label">Nombre:</label>
                <input type="text" id="nombre" name="nombre" class="form-input"
                       value="${producto.nombre}" required>
            </div>

            <div class="form-group">
                <label for="precio" class="form-label">Precio:</label>
                <input type="number" step="0.01" id="precio" name="precio" class="form-input"
                       value="${producto.precio}" required>
            </div>

            <div class="form-group">
                <label for="descripcion" class="form-label">Descripción:</label>
                <textarea id="descripcion" name="descripcion" class="form-input" rows="4"
                          required>${producto.descripcion}</textarea>
            </div>

            <div class="form-group">
                <label for="categoriaId" class="form-label">Categoría:</label>
                <select id="categoriaId" name="categoriaId" class="form-select" required>
                    <%
                        List<org.tienda.model.Categoria> categorias =
                                (List<org.tienda.model.Categoria>) request.getAttribute("categorias");
                        if (categorias != null && !categorias.isEmpty()) {
                            for (org.tienda.model.Categoria categoria : categorias) {
                    %>
                    <option value="<%= categoria.getIdCategoria() %>">
                        <%= categoria.getNombre() %>
                    </option>
                    <%
                        }
                    } else {
                    %>
                    <option value="">No hay categorías disponibles</option>
                    <%
                        }
                    %>
                </select>
            </div>

            <div class="form-button-container">
                <button type="submit" class="form-button">Actualizar</button>
            </div>
        </form>

        <div class="link-container">
            <a href="${pageContext.request.contextPath}/productos" class="back-link">Volver a la lista de productos</a>
        </div>
    </div>
</main>
<%@ include file="/WEB-INF/jsp/comun/footer.jspf" %>
</body>
</html>
