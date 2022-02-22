<%--
  Created by IntelliJ IDEA.
  User: maria
  Date: 20/02/2022
  Time: 1:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>JPA - Hibernate - MVC - Krazo</title>
  <link href="${pageContext.request.contextPath}/resources/css/estilos.css" rel="stylesheet"/>
</head>
<body>
<h1>${not empty pelicula.id ? 'Editar' : 'Nueva'} Pelicula</h1>
<br/>
<c:if test="${not empty error.errores}}">
  <div class="row">
    <div class="col-md-12">
      <p class="alert alert-danger">${error.errores}</p>
        <%--        <c:forEach var="err" items="${error.errores}">--%>
        <%--          <p>${err.getParamName()}</p>--%>
        <%--          <p>${err.getMessage()}</p>--%>
        <%--        </c:forEach>--%>
    </div>
  </div>
</c:if>
<br/>
<div>
  <form action="${mvc.basePath}/pelicula/admin/nueva/submit" method="post">

    <c:if test="${not empty pelicula.id}">
      <input id="id" name="id" type="hidden" value="${pelicula.id}"/>
    </c:if>
    <div class="row">
      <label for="director">director:</label>
      <input id="director" name="director" type="text" value="${pelicula.director}"/>
      <c:if test="${not empty error.getMensaje('director')}">
        <span class="alert alert-danger">${error.getMensaje("director")}</span>
      </c:if>
    </div>
    <div class="row">
      <label for="estudio">estudio:</label>
      <input id="estudio" name="estudio" type="text" value="${pelicula.estudio}"/>
      <c:if test="${not empty error.getMensaje('estudio')}">
        <span class="alert alert-danger">${error.getMensaje("estudio")}</span>
      </c:if>
    </div>
    <div class="row">
      <label for="poster">poster:</label>
      <input id="poster" name="poster" type="text" value="${pelicula.poster}"/>
      <c:if test="${not empty error.getMensaje('poster')}">
        <span class="alert alert-danger">${error.getMensaje("poster")}</span>
      </c:if>
    </div>
    <div class="row">
      <label for="titulo">Titulo:</label>
      <input id="titulo" name="dificultad" type="text" value="${pelicula.titulo}"/>
      <c:if test="${not empty error.getMensaje('titulo')}">
        <span class="alert alert-danger">${error.getMensaje("titulo")}</span>
      </c:if>
    </div>
    <div class="row">
      <label for="duracion">dura:</label>
      <input id="duracion" name="duracion" type="number" value="${pelicula.duracion}"/>
      <c:if test="${not empty error.getMensaje('duracion')}">
        <span class="alert alert-danger">${error.getMensaje("duracion")}</span>
      </c:if>
    </div>
    <div class="row">
      <label for="descripcion">descripcion:</label>
      <input id="descripcion" name="descripcion" type="text" value="${pelicula.descripcion}"/>
      <c:if test="${not empty error.getMensaje('descripcion')}">
        <span class="alert alert-danger">${error.getMensaje("descripcion")}</span>
      </c:if>
    </div>

    <div class="row">
      <label for="enproyeccion">en proyeccion:</label>
      <input id="enproyeccion" name="enproyeccion" type="checkbox" value="${pelicula.enProyeccion}"/>
      <c:if test="${not empty error.getMensaje('enProyeccion')}">
        <span class="alert alert-danger">${error.getMensaje("enProyeccion")}</span>
      </c:if>
    </div>

    <div>
      <button id="submit" type="submit">Enviar</button>
    </div>
  </form>
</div>

</body>
</html>
