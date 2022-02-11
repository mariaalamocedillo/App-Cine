<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <title>JPA - Hibernate - MVC - Krazo</title>
  <link href="${pageContext.request.contextPath}/resources/css/estilos.css" rel="stylesheet"/>
</head>
<body>
<h1>Lista Peliculas</h1>
<br/>
<c:if test="${not empty peliculas}">
  <ul id="peliculas" >
    <c:forEach var="pelicula" items="${peliculas}">
      <li>
          <span>${pregunta.id} ${pelicula.id}</span>
          <span>${pregunta.director} ${pelicula.director}</span>
          <span>${pregunta.titulo} ${pelicula.titulo}</span>
          <span>
            <a href="${mvc.basePath}/admin/pelicula/${pelicula.id}">Detalle</a>
            <a href="${mvc.basePath}/admin/pelicula/${pelicula.id}/editar">Editar</a>
          </span>
      </li>
    </c:forEach>
  </ul>
</c:if>
<br />


<c:if test="${not empty mensaje}">
  <div class="alert alert-success">
    <p>${mensaje.texto}</p>
  </div>
</c:if>

<a href="${mvc.basePath}/admin/pelicula/nueva">Nueva pelicula</a>

</body>
</html>

