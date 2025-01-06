<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/comun/header.jspf" %>

<html>
<head>
    <title>Editar Usuario</title>
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

        .form-input {
            width: 100%;
            padding: 8px;
            font-size: 14px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }

        .form-error {
            color: red;
            font-size: 12px;
            margin-bottom: 15px;
            text-align: center;
        }

        .button-container {
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
    </style>
</head>
<body>
<main>
    <div class="form-container">
        <h2>Editar Usuario</h2>

        <%
            String error = (String) request.getAttribute("error");
            if (error != null) {
        %>
        <div class="form-error">
            <%= error %>
        </div>
        <% } %>

        <form action="<%= pageContext.getServletContext().getContextPath() %>/tienda/usuarios/" method="post">
            <input type="hidden" name="__method__" value="put" />
            <input type="hidden" name="idUsuario" value="<%= request.getAttribute("usuario") != null ? ((org.tienda.model.Usuario) request.getAttribute("usuario")).getIdUsuario() : "" %>" />

            <div class="form-group">
                <label for="nombre" class="form-label">Nombre:</label>
                <input id="nombre" name="nombre" type="text" class="form-input" required
                       value="<%= request.getAttribute("usuario") != null ? ((org.tienda.model.Usuario) request.getAttribute("usuario")).getNombre() : "" %>" />
            </div>

            <div class="form-group">
                <label for="rol" class="form-label">Rol:</label>
                <select id="rol" name="rol" class="form-input" required>
                    <option value="admin" <%= request.getAttribute("usuario") != null && ((org.tienda.model.Usuario) request.getAttribute("usuario")).getRol().equals("admin") ? "selected" : "" %>>Admin</option>
                    <option value="user" <%= request.getAttribute("usuario") != null && ((org.tienda.model.Usuario) request.getAttribute("usuario")).getRol().equals("user") ? "selected" : "" %>>User</option>
                </select>
            </div>

            <div class="form-group">
                <label for="password" class="form-label">Contraseña Nueva:</label>
                <input id="password" name="password" type="password" class="form-input" required/>
            </div>

            <div class="button-container">
                <input type="submit" value="Actualizar" class="form-button" />
            </div>
        </form>
    </div>
</main>
<%@ include file="/WEB-INF/jsp/comun/footer.jspf" %>
</body>
</html>