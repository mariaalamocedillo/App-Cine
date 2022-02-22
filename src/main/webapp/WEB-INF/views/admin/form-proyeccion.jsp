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
  <link href="${pageContext.request.contextPath}/resources/assets/img/favicon.png" rel="icon">
  <link href="${pageContext.request.contextPath}/resources/assets/img/apple-touch-icon.png" rel="apple-touch-icon">

  <!-- Google Fonts -->
  <link href="https://fonts.gstatic.com" rel="preconnect">
  <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">

  <!-- Vendor CSS Files -->
  <link href="${pageContext.request.contextPath}/resources/assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/resources/assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/resources/assets/vendor/simple-datatables/style.css" rel="stylesheet">

  <!-- Template Main CSS File -->
  <link href="${pageContext.request.contextPath}/resources/assets/css/style.css" rel="stylesheet">

</head>

<body class="imgfondo">


<main id="main" class="main formul">
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

  <section class="section">
    <div class="row">
      <div class="col-lg-6 justify-content-between m-auto">

        <div class="card">
          <div class="card-body m-3">
            <h3 class="m-5 text-center">Editar proyección</h3>

            <form id="form" action="${mvc.basePath}/admin/proyeccion/nueva/submit">
              <c:if test="${not empty proyeccion.id}">
                <input id="id" name="id" type="hidden" value="${proyeccion.id}"/>
              </c:if>

              <c:if test="${not empty pelicula}">
                <div class="col mb-3 m-auto">
                  <input  id="tituloPelicula" class="form-control" name="id" value="${pelicula.titulo}" disabled/>
                </div>
              </c:if>
              <c:if test="${not empty peliculas}">
                <div class="col mb-3 m-auto">
                  <label for="pelicula" class="form-label">Pelicula</label>
                  <select id="pelicula" name="pelicula" class="form-select" required>
                    <c:forEach var="pelicula" items="${peliculas}">
                      <option value="${pelicula.id}">${pelicula.titulo}</option>
                    </c:forEach>
                  </select>
                </div>
              </c:if>
              <c:if test="${not empty error.getMensaje('pelicula')}">
                <span class="alert alert-danger">${error.getMensaje("pelicula")}</span>
              </c:if>

                <div class="col">
                  <label for="dia">Día</label>
                  <input type="date" id="dia" value="${proyeccion.dia}" name="dia" class="form-control" required>
                </div>


          <!-- Vertical Pills Tabs -->
          <div class="d-flex align-items-start border border-info rounded p-2 mt-3">
            <div class="nav flex-column nav-pills me-3" id="v-pills-tab" role="tablist" aria-orientation="vertical">
              <button class="nav-link active" id="v-pills-home-tab" data-bs-toggle="pill" data-bs-target="#v-pills-home" type="button" role="tab" aria-controls="v-pills-home" aria-selected="true">16:00</button>
              <button class="nav-link" id="v-pills-profile-tab" data-bs-toggle="pill" data-bs-target="#v-pills-profile" type="button" role="tab" aria-controls="v-pills-profile" aria-selected="false">18:30</button>
              <button class="nav-link" id="v-pills-messages-tab" data-bs-toggle="pill" data-bs-target="#v-pills-messages" type="button" role="tab" aria-controls="v-pills-messages" aria-selected="false">21:00</button>
<%--
              <div class="btn-group btn-group-toggle" data-toggle="buttons">
                <label class="btn btn-secondary active">
                  <input type="radio" name="options" id="option1" autocomplete="off" checked> Active
                </label>
                <label class="btn btn-secondary">
                  <input type="radio" name="options" id="option2" autocomplete="off"> Radio
                </label>
                <label class="btn btn-secondary">
                  <input type="radio" name="options" id="option3" autocomplete="off"> Radio
                </label>
              </div>
--%>
            </div>
            <div class="tab-content" id="v-pills-tabContent">
              <div class="tab-pane fade show active" id="v-pills-home" role="tabpanel" aria-labelledby="v-pills-home-tab">
                <div class="col">
                  <label for="comienzo16" class="form-label">Sala para las 16:00 </label>
                  <select id="comienzo16" class="form-select">
                    <option>sala xx</option>
                    <option>sala xx</option>
                    <option>sala xx</option>
                  </select>
                </div>
              </div>
              <div class="tab-pane fade" id="v-pills-profile" role="tabpanel" aria-labelledby="v-pills-profile-tab">
                <div class="col">
                  <label for="comienzo18" class="form-label">Sala para las 18:30 </label>
                  <select id="comienzo18" class="form-select">
                    <option value="">sala xx</option>
                    <option>sala xx</option>
                    <option>sala xx</option>
                  </select>
                </div>
              </div>
              <div class="tab-pane fade" id="v-pills-messages" role="tabpanel" aria-labelledby="v-pills-messages-tab">
                <div class="col">
                  <label for="comienzo21" class="form-label">Sala para las 21:00 </label>
                  <select id="comienzo21" class="form-select">
                    <option>sala xx</option>
                    <option>sala xx</option>
                    <option>sala xx</option>
                  </select>
                </div>
              </div>
            </div>
          </div>
          <!-- End Vertical Pills Tabs -->

              <div class="row m-3">
                <div class="col text-center">
                  <button type="submit" class="btn btn-primary">Guardar datos</button>
                </div>
              </div>
            </form><!-- End General Form Elements -->

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
<script src="${pageContext.request.contextPath}/resources/assets/vendor/php-email-form/validate.js"></script>

<!-- Template Main JS File -->
<script src="${pageContext.request.contextPath}/resources/assets/js/main.js"></script>

</body>

</html>