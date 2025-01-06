<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="org.tienda.model.Usuario" %>
<%@ page import="java.util.Optional" %>
<%@ include file="/WEB-INF/jsp/comun/header.jspf" %>

<html>
<head>
  <title>Lista de Usuarios</title>
  <style>
    .clearfix::after {
      content: "";
      display: block;
      clear: both;
    }
  </style>
</head>
<body>
<main>
  <div id="contenedora" style="float:none; margin: 0 auto;width: 900px;">
    <div class="clearfix">
      <div style="float: left; width: 50%">
        <h1>Usuarios</h1>
      </div>
      <div style="float: none;width: auto;overflow: hidden;min-height: 80px;position: relative;">
        <div style="position: absolute; left: 39%; top : 39%;">
          <%
            Optional<Usuario> usuarioLogueado = Optional.ofNullable((Usuario) session.getAttribute("usuario-logado"));
            if (usuarioLogueado.isPresent() && "Admin".equals(usuarioLogueado.get().getRol())) {
          %>
          <form action="<%= application.getContextPath() %>/tienda/usuarios/crear">
            <input type="submit" value="Crear Usuario">
          </form>
          <%
            }
          %>
        </div>
      </div>
    </div>
    <div class="clearfix">
      <hr/>
    </div>
    <div class="clearfix">
      <div style="float: left;width: 10%">ID Usuario</div>
      <div style="float: left;width: 30%">Nombre</div>
      <%
        if (usuarioLogueado.isPresent() && "Admin".equals(usuarioLogueado.get().getRol())) {
      %>
      <div style="float: left;width: 20%">Password</div>
      <%
        }
      %>
      <div style="float: left;width: 20%; overflow: hidden;">Rol</div>
    </div>
    <div class="clearfix">
      <hr/>
    </div>
    <%
      List<Usuario> listaUsuarios = (List<Usuario>) request.getAttribute("usuarios");

      if (listaUsuarios != null && !listaUsuarios.isEmpty()) {
        for (Usuario usuario : listaUsuarios) {
    %>
    <div style="margin-top: 6px;" class="clearfix">
      <div style="float: left;width: 10%"><%= usuario.getIdUsuario() %></div>
      <div style="float: left;width: 30%"><%= usuario.getNombre() %></div>
      <%
        if (usuarioLogueado.isPresent() && "Admin".equals(usuarioLogueado.get().getRol())) {
      %>
      <div style="float: left;width: 20%"><%= usuario.getPassword().substring(0, 4) %>****</div>
      <%
        }
      %>
      <div style="float: left;width: 20%"><%= usuario.getRol() %></div>
      <div style="float: none;width: auto;overflow: hidden;">
        <%
          if (usuarioLogueado.isPresent() && "Admin".equals(usuarioLogueado.get().getRol())) {
        %>
        <form action="<%= application.getContextPath() %>/tienda/usuarios/editar/<%= usuario.getIdUsuario() %>" style="display: inline;">
          <input type="submit" value="Editar" />
        </form>
        <form action="<%= application.getContextPath() %>/tienda/usuarios/borrar/" method="post" style="display: inline;">
          <input type="hidden" name="__method__" value="delete" />
          <input type="hidden" name="idUsuario" value="<%= usuario.getIdUsuario() %>" />
          <input type="submit" value="Eliminar" />
        </form>
        <%
          }
        %>
      </div>
    </div>
    <%
      }
    } else {
    %>
    <p>No hay registros de usuarios disponibles.</p>
    <%
      }
    %>
  </div>
</main>
<%@ include file="/WEB-INF/jsp/comun/footer.jspf" %>
</body>
</html>