<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <title>JPA - Hibernate - MVC - Krazo</title>
  <link href="${pageContext.request.contextPath}/resources/css/estilos.css" rel="stylesheet"/>
</head>
<body>
<h1>Editar Pelicula</h1>
<br/>
<c:if test="${not empty error.errores}">
  <div class="alert alert-danger">
    <h4>Lista de errores</h4>
    <dl>
      <c:forEach var="err" items="${error.errores}">
        <dt>${err.getParamName()}</dt>
        <dd>${err.getMessage()}</dd>
      </c:forEach>
    </dl>
  </div>
</c:if>
<br/>
<div>
  <form id="form" name="form" action="${mvc.basePath}/admin/pelicula/nueva/submit" method="post">
    <c:if test="${not empty pregunta.id}">
      <input id="id" name="id" type="hidden" value="${pelicula.id}"/>
    </c:if>
    <div class="row">
      <label for="duracion">Duracion:</label>
      <input id="duracion" name="duracion" type="text" value="${pelicula.duracion}"/>
      <c:if test="${not empty error.getMensaje('duracion')}">
            <span class="alert alert-danger">${error.getMensaje("duracion")}</span>
      </c:if>
    </div>
    <div class="row">
      <label for="titulo">Titulo:</label>
      <input id="titulo" name="dificultad" type="number" value="${pelicula.titulo}"/>
      <c:if test="${not empty error.getMensaje('titulo')}">
        <span class="alert alert-danger">${error.getMensaje("titulo")}</span>
      </c:if>
    </div>
    <div class="row">
      <label for="titulo">Titulo:</label>
      <input id="titulo" name="titulo" type="text" value="${pelicula.titulo}"/>
      <c:if test="${not empty error.getMensaje('titulo')}">
        <span class="alert alert-danger">${error.getMensaje("titulo")}</span>
      </c:if>
    </div>
    <div class="row">
      <label for="director">director:</label>
      <input id="director" name="director" type="text" value="${pelicula.director}"/>
      <c:if test="${not empty error.getMensaje('director')}">
        <span class="alert alert-danger">${error.getMensaje("director")}</span>
      </c:if>
    </div>

    <div>
      <button id="submit" type="submit">Enviar</button>
    </div>
  </form>
</div>

</body>
</html>
