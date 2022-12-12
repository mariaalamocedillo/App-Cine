<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta content="width=device-width, initial-scale=1.0" name="viewport">

  <title>Editar proyeccion</title>
  <meta content="" name="description">
  <meta content="" name="keywords">

  <!-- Favicons -->
  <link href="${pageContext.request.contextPath}/resources/img/favicon.png" rel="icon">
  <link href="${pageContext.request.contextPath}/resources/img/apple-touch-icon.png" rel="apple-touch-icon">

  <!-- Google Fonts -->
  <link href="https://fonts.gstatic.com" rel="preconnect">
  <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">

  <!-- Vendor CSS Files -->
  <link href="${pageContext.request.contextPath}/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/resources/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/resources/vendor/simple-datatables/style.css" rel="stylesheet">

  <!-- Template Main CSS File -->
  <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">

</head>

<body class="imgfondo">
<jsp:include page="/WEB-INF/layout/navAdmin.jsp" ></jsp:include>


<main id="main" class="main formul">

  <section class="section">
    <div class="row">
      <div class="col-lg-6 justify-content-between m-auto">

        <div class="card">
          <div class="card-body m-3">
            <h3 class="m-5 text-center">${not empty proyeccion.id ? 'Editar' : 'Nueva'} proyección</h3>

            <form id="form" method="post" action="${mvc.basePath}/admin/proyeccion/submit">

              <c:if test="${not empty proyeccion.id}">
                <input id="id" name="id" type="hidden" value="${proyeccion.id}"/>
                <input class="form-control" name="peliculaId" value="${proyeccion.pelicula.id}" hidden/>
                <input id="titulo" class="form-control" value="${proyeccion.pelicula.titulo}" disabled/>
              </c:if>

              <c:if test="${not empty pelicula}">
                <div class="col mb-3 m-auto">
                  <label for="tituloPelicula" class="form-label">Película</label>
                  <input id="tituloPelicula" class="form-control" value="${pelicula.titulo}" disabled/>
                  <input id="peliculaId" class="form-control" name="peliculaId" value="${pelicula.id}" hidden/>
                </div>
              </c:if>
              <c:if test="${not empty peliculas}">
                <div class="col mb-3 m-auto">
                  <label for="pelicula" class="form-label">Pelicula</label>
                  <select id="pelicula" name="peliculaId" class="form-select" required>
                    <c:forEach var="pelicula" items="${peliculas}">
                      <option value="${pelicula.id}">${pelicula.titulo}</option>
                    </c:forEach>
                  </select>
                </div>
                <div class="invalid-feedback">
                  <span class="alert alert-danger">Debe seleccionar una película</span>
                </div>
              </c:if>

              <div class="col">
                <label for="dia">Día</label>
                <c:choose>
                  <c:when test = "${not empty proyeccion}">
                    <input type="date" id="dia" value="${proyeccion.dia}" name="dia" class="form-control" required>
                  </c:when>
                  <c:otherwise>
                    <input type="date" id="dia" name="dia" class="form-control" required>
                  </c:otherwise>
                </c:choose>
              </div>
              <div class="invalid-feedback">
                <span class="alert alert-danger">Debe seleccionar un día</span>
              </div>

              <div class="col">
                <label for="comienzo">Hora de comienzo</label>
                <select id="comienzo" name="comienzo" class="form-select">
                  <option>16:00</option>
                  <option>18:30</option>
                  <option>21:00</option>
                  <option>23:30</option>
                </select>
              </div>
              <div class="invalid-feedback">
                <span class="alert alert-danger">Debe seleccionar una hora</span>
              </div>

              <!-- Vertical Pills Tabs -->
            <div class="col">
              <label for="sala" class="form-label">Sala</label>
              <select id="sala" name="sala" class="form-select">
                <c:forEach var="sala" items="${salas}">
                  <option value="${sala.id}" ${not empty proyeccion.id && proyeccion.sala.id == sala.id ? 'selected' : ''}>${sala.nombre}</option>
                </c:forEach>
              </select>
            </div>
              <div class="invalid-feedback">
                <span class="alert alert-danger">Debe seleccionar una sala</span>
              </div>
              <c:if test="${not empty mensaje.texto}">
                <div class="alert alert-success">
                    ${mensaje.texto}
                </div>
              </c:if>

              <div class="row m-3">
                <div class="col text-center">
                  <button type="submit" class="btn btn-primary">Guardar datos</button>
                </div>
              </div>
            </form>

          </div>
        </div>

      </div>

    </div>
  </section>

</main><!-- End #main -->


<a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>

<!-- Vendor JS Files -->
<script src="${pageContext.request.contextPath}/resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/form-signup-validation.js"></script>

<!-- Template Main JS File -->
<script src="${pageContext.request.contextPath}/resources/js/main.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/general-validation-form.js"></script>

</body>

</html>