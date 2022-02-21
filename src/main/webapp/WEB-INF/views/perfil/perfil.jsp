<%--
  Created by IntelliJ IDEA.
  User: maria
  Date: 19/02/2022
  Time: 13:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Mi perfil</title>
</head>
<body>
<h1>HOLA</h1>

<c:if test="${mensaje.texto != null}">
    <div class="col-md-12">
        <p class="alert alert-danger" id="success-alert">${mensaje.texto}</p>
    </div>
</c:if>

<p class="alert alert-danger" id="cliente">${cliente.nombre}</p>
<c:forEach var="reserva" items="${cliente.reservas}">

    <p class="alert alert-danger" id="cliente">${reserva.id}</p>
    <p class="alert alert-danger" >${reserva.proyeccion.pelicula.titulo}</p>

</c:forEach>
</body>
</html>
