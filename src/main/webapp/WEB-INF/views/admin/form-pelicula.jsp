<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="utf-8">
  <meta content="width=device-width, initial-scale=1.0" name="viewport">

  <title>Editar película</title>
  <meta content="" name="description">
  <meta content="" name="keywords">

  <!-- Favicons -->
  <link href="${pageContext.request.contextPath}/resources/assets/img/favicon.png" rel="icon">
  <link href="${pageContext.request.contextPath}/resources/assets/img/apple-touch-icon.png" rel="apple-touch-icon">

  <!-- Google Fonts -->
  <link href="https://fonts.gstatic.com" rel="preconnect">
  <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">

  <!-- Vendor CSS Files -->
  <link href="${pageContext.request.contextPath}/resources/assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/resources/assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">

  <!-- Template Main CSS File -->
  <link href="${pageContext.request.contextPath}/resources/assets/css/style.css" rel="stylesheet">

</head>

<body class="imgfondo">
<jsp:include page="/WEB-INF/layout/navAdmin.jsp" ></jsp:include>

<main id="main" class="main formul" >

  <section class="section">
    <div class="row">
      <div class="col-lg-6 justify-content-between m-auto">
        <c:if test="${not empty error.errores}}">
          <div class="row">
            <div class="col-md-12">
              <p class="alert alert-danger">${error.errores}</p>
            </div>
          </div>
        </c:if>
        <div class="card">
          <div class="card-body m-3">
            <h3 class="m-5 text-center">${not empty pelicula.id ? 'Editar' : 'Nueva'} película</h3>

            <!-- General Form Elements -->
            <form action="${mvc.basePath}/pelicula/admin/nueva/submit" method="post">
              <c:if test="${not empty pelicula.id}">
                <input id="id" name="id" type="hidden" value="${pelicula.id}"/>
              </c:if>

              <div class="col mb-3">
                <label for="titulo" class=" col-form-label">Título</label>
                <input type="text" id="titulo" name="titulo" class="form-control" value="${pelicula.titulo}" required>
                <c:if test="${not empty error.getMensaje('titulo')}">
                  <span class="alert alert-danger">${error.getMensaje("titulo")}</span>
                </c:if>
              </div>

              <div class="row">
                <div class="col mb-3">
                  <label for="director" class="col-form-label">Director</label>
                  <input type="text" id="director" name="director" class="form-control" value="${pelicula.director}" required>
                  <c:if test="${not empty error.getMensaje('director')}">
                    <span class="alert alert-danger">${error.getMensaje("director")}</span>
                  </c:if>
                </div>
                <div class="col mb-3">
                  <label for="estudio" class="col-form-label">Estudio</label>
                  <input type="text" id="estudio" name="estudio" class="form-control" value="${pelicula.estudio}" required>
                  <c:if test="${not empty error.getMensaje('estudio')}">
                    <span class="alert alert-danger">${error.getMensaje("estudio")}</span>
                  </c:if>
                </div>
              </div>
              <div class="col mb-3">
                <label for="poster" class=" col-form-label">Url del póster</label>
                <input type="url" id="poster" name="poster" class="form-control" value="${pelicula.poster}" required>
                <c:if test="${not empty error.getMensaje('poster')}">
                  <span class="alert alert-danger">${error.getMensaje("poster")}</span>
                </c:if>
              </div>

              <div class="col mb-3">
                <label for="duracion" class="col-form-label">Duración</label>
                <input type="number" name="duracion" id="duracion" class="form-control" value="${pelicula.duracion}" required>
                <c:if test="${not empty error.getMensaje('duracion')}">
                    <span class="alert alert-danger">${error.getMensaje("duracion")}</span>
                </c:if>
              </div>

              <div class="col mb-3">
                <label for="descripcion" class="col-form-label">Sipnosis</label>
                <input type="text" class="form-control" id="descripcion" name="descripcion" value="${pelicula.descripcion}"/>
                <c:if test="${not empty error.getMensaje('descripcion')}">
                  <span class="alert alert-danger">${error.getMensaje("descripcion")}</span>
                </c:if>
              </div>
              <div>
                <div class="form-check">
                  <input class="form-check-input" type="checkbox" id="enProyeccion" name="enProyeccion" value="${pelicula.enProyeccion}">
                  <label class="form-check-label" for="enProyeccion">
                    En proyeccion
                  </label>
                  <c:if test="${not empty error.getMensaje('enProyeccion')}">
                    <span class="alert alert-danger">${error.getMensaje("enProyeccion")}</span>
                  </c:if>
                </div>
              </div>

              <div class="row mb-3">
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

<!-- ======= Footer ======= -->
</footer><!-- End Footer -->

<a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>

<!-- Vendor JS Files -->
<script src="${pageContext.request.contextPath}/resources/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/form-validation.js"></script>

<!-- Template Main JS File -->
<script src="${pageContext.request.contextPath}/resources/assets/js/main.js"></script>

</body>

</html>