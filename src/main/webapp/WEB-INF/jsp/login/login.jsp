<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/comun/header.jspf" %>

<html>
<head>
    <title>Iniciar Sesión</title>
    <style>
        .login-container {
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

        .login-button-container {
            text-align: center;
            margin-top: 20px;
        }

        .login-button {
            background-color: #007BFF;
            color: white;
            border: none;
            padding: 10px 20px;
            font-size: 14px;
            cursor: pointer;
            border-radius: 4px;
        }

        .login-button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
<main>
    <div class="login-container">
        <h2>Iniciar Sesión</h2>

        <%
            String error = (String) request.getAttribute("error");
            if (error != null) {
        %>
        <div class="form-error">
            <%= error %>
        </div>
        <% } %>

        <form action="<%= pageContext.getServletContext().getContextPath() %>/tienda/usuarios/login/" method="post">
            <!-- Método lógico para procesamiento -->
            <input type="hidden" name="__method__" value="login" />

            <div class="form-group">
                <label for="usuario" class="form-label">Usuario:</label>
                <input id="usuario" name="nombre" type="text" class="form-input" required />
            </div>

            <div class="form-group">
                <label for="password" class="form-label">Contraseña:</label>
                <input id="password" name="password" type="password" class="form-input" required />
            </div>

            <div class="login-button-container">
                <input type="submit" value="Iniciar sesión" class="login-button" />
            </div>
        </form>
    </div>
</main>
<%@ include file="/WEB-INF/jsp/comun/footer.jspf" %>
</body>
</html>