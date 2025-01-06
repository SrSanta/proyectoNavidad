<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/comun/header.jspf" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Crear Categoria</title>
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
    <h2>Crear Categoria</h2>
    <form action="${pageContext.request.contextPath}/tienda/categorias" method="post">
      <div class="form-group">
        <label for="nombre" class="form-label">Nombre:</label>
        <input type="text" id="nombre" name="nombre" class="form-input" required>
      </div>

      <div class="form-group">
        <label for="descripcion" class="form-label">Descripcion:</label>
        <textarea id="descripcion" name="descripcion" class="form-input" rows="4" required></textarea>
      </div>

      <div class="form-button-container">
        <button type="submit" class="form-button">Crear</button>
      </div>
    </form>

    <div class="link-container">
      <a href="${pageContext.request.contextPath}/tienda/categorias" class="back-link">Volver a la lista de categorias</a>
    </div>
  </div>
</main>
<%@ include file="/WEB-INF/jsp/comun/footer.jspf" %>
</body>
</html>