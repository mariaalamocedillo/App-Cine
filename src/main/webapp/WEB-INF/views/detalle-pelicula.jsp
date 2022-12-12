<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta content="width=device-width, initial-scale=1.0" name="viewport">

  <title>${pelicula.titulo}</title>
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
  <link href="${pageContext.request.contextPath}/resources/vendor/boxicons/css/boxicons.min.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/resources/vendor/remixicon/remixicon.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/resources/vendor/simple-datatables/style.css" rel="stylesheet">

  <!-- Template Main CSS File -->
  <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">

</head>

<body class="imgfondo">
<jsp:include page="/WEB-INF/layout/navBar.jsp" ></jsp:include>

<main id="main" class="main formul">

  <div class="pagetitle">
    <h1 class="text-light">${pelicula.titulo}</h1>
    <nav>
      <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="${mvc.basePath}/cartelera/">Inicio</a></li>
        <li class="breadcrumb-item">Detalles de ${pelicula.titulo}</li>
      </ol>
    </nav>
  </div><!-- End Page Title -->

  <section class="section">
    <div class="row">
      <div class="col-lg-4 m-auto">

        <div class="container rounded-lg">
          <div class="card-body">
            <div class="mt-3 px-4 d-flex rounded-lg">
              <img alt="..." class="img-fluid rounded-lg shadow-lg" width="auto" src="${pelicula.poster}">
            </div>
          </div>
        </div>

      </div>

      <div class="col-lg-8 m-auto">
        <div class="card">
          <div class="card-body">
            <h1 class="title m-3">${pelicula.titulo}</h1>
            <h5 class="m-3 ">${pelicula.director} |<span class="text-muted" id="duracion"> ${pelicula.duracion} mins </span></h5>
            <p class="m-3">
              ${pelicula.descripcion}
            </p>

        <c:if test="${not empty organizacionProyecciones && not empty proyecciones}">
          <h3 class="text-center">Proyecciones</h3>


          <div class="row">
            <c:forEach var="info" items="${organizacionProyecciones}">
            <div class="col-3">
                <div class="card rounded-3 text-center">
                  <div class="card-body card-proyeccion">
                    <h5 class="card-title">${info.key}<%-- DIA PROYECCION  --%></h5>

                  <%-- HORAS DE PROYECCIONES  --%>
                    <c:forEach var="id" items="${info.value}">
                      <c:forEach var="proyeccion" items="${proyecciones}">

                        <c:if test="${id == proyeccion.id}">
                          <a id="${info.key}" href="${mvc.basePath}/reserva/pelicula/${proyeccion.id}"
                             class="badge rounded-pill bg-warning text-dark fs-6">${proyeccion.comienzo}</a>
                        </c:if>

                      </c:forEach>
                    </c:forEach>
                  </div>
                </div>
            </div>
              </c:forEach>
          </div>
        </c:if>
          </div>
        </div>

      </div>
    </div>
  </section>

</main><!-- End #main -->


<a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>

<!-- Vendor JS Files -->
<script src="${pageContext.request.contextPath}/resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/vendor/simple-datatables/simple-datatables.js"></script>
<script src="${pageContext.request.contextPath}/resources/vendor/php-email-form/validate.js"></script>

<!-- Template Main JS File -->
<script src="${pageContext.request.contextPath}/resources/js/main.js"></script>

</body>

</html>

