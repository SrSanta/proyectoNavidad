<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Inicio - Mi Tienda</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      margin: 0;
      padding: 0;
    }

    .main {
      text-align: center;
      padding: 20px;
    }

    h1 {
      color: #333;
    }

    .buttons-container a {
      display: inline-block;
      margin: 10px;
      padding: 10px 20px;
      color: #fff;
      background-color: #007BFF;
      text-decoration: none;
      border-radius: 4px;
      transition: background-color 0.3s ease;
    }

    .buttons-container a:hover {
      background-color: #0056b3;
    }
  </style>
</head>
<body>
<%@ include file="/WEB-INF/jsp/comun/header.jspf" %>

<div class="main">
  <h1>Bienvenido a Mi Tienda</h1>
  <p>Tu lugar para comprar los mejores productos. Explora nuestras categorías y empieza a disfrutar hoy mismo.</p>

  <div class="buttons-container">
    <a href="<%= request.getContextPath() %>/productos">Explorar Productos</a>
    <a href="<%= request.getContextPath() %>/tienda/categorias/">Explorar Categorias</a>
  </div>
</div>

<%@ include file="/WEB-INF/jsp/comun/footer.jspf" %>
</body>
</html>