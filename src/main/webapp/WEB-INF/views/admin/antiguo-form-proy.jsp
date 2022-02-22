<%--
  Created by IntelliJ IDEA.
  User: maria
  Date: 20/02/2022
  Time: 1:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>






<form id="form" name="form" action="${mvc.basePath}/admin/proyeccion/nueva/submit" method="post">

  <c:if test="${not empty proyeccion.id}">
    <input id="id" name="id" type="hidden" value="${proyeccion.id}"/>
  </c:if>


  <c:if test="${not empty pelicula}">
    <input id="id" name="id" value="${pelicula.id}" placeholder="${pelicula.titulo}" diabled/>
  </c:if>

  <c:if test="${not empty peliculas}">
    <div class="row">
      <label for="pelicula" class="form-label">Pelicula</label>
      <select id="pelicula" name="pelicula" class="form-select" multiple required>
        <c:forEach var="pelicula" items="${peliculas}">
          <option value="${pelicula.id}">${pelicula.titulo}</option>
        </c:forEach>
      </select>
    </div>
  </c:if>
  <c:if test="${not empty error.getMensaje('pelicula')}">
    <span class="alert alert-danger">${error.getMensaje("pelicula")}</span>
  </c:if>


  <div class="row">
    <label for="hora" class="form-label">Hora</label>
    <select id="hora" name="hora" value="${proyeccion.comienzo}" class="form-select"  required>
      <option>16:00</option>
      <option>18:30</option>
      <option>20:30</option>
    </select>
    <c:if test="${not empty error.getMensaje('poster')}">
      <span class="alert alert-danger">${error.getMensaje("poster")}</span>
    </c:if>
  </div>
  <div class="row">
    <label for="dia">Dia:</label>
    <input type="date" id="dia" name="dia" value="${proyeccion.dia}" class="form-control" required>
    <c:if test="${not empty error.getMensaje('dia')}">
      <span class="alert alert-danger">${error.getMensaje("dia")}</span>
    </c:if>
  </div>

  <div class="col">
    <label for="hora" class="form-label">Sala para la hora {hora} </label>
    <select id="inputState" class="form-select" data-value="${proyeccion.sala.id}">
      <c:forEach var="sala" items="${salas}">
        <option value="${sala.id}">${sala.nombre}</option>
      </c:forEach>
    </select>
  </div>
  <div>
    <button id="submit" type="submit">Enviar</button>
  </div>
</form>
</div>

</body>
</html>



