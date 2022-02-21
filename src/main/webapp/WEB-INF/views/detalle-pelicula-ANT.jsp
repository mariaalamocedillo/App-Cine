<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <title>Pelicula</title>
  <!-- Bootstrap -->
  <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">

  <!-- Custom styles for this template -->
  <link href="${pageContext.request.contextPath}/resources/css/album.css" rel="stylesheet"/>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" />
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/creativetimofficial/tailwind-starter-kit/compiled-tailwind.min.css" />

<body class="text-gray-800 antialiased">

<jsp:include page="/WEB-INF/layout/navBar.jsp" ></jsp:include>

<!--MAIN-->
<main>

  <!--CONTENEDOR DE INFO-->
  <section class="relative py-20 mt-5">
    <div class="container mx-auto px-4">
      <div class="items-center flex flex-wrap">
        <div class="w-full md:w-4/12 ml-auto mr-auto px-4">
          <img alt="..." class="max-w-full rounded-lg shadow-lg" src="${pelicula.poster}" />
        </div>
        <div class="w-full md:w-5/12 ml-auto mr-auto px-4">
          <div class="md:pr-12">
            <!--<div class="text-pink-600 p-3 text-center inline-flex items-center justify-center w-16 h-16 mb-6 shadow-lg rounded-full bg-pink-300" >
              <i class="fas fa-rocket text-xl"></i>
            </div>-->
            <h3 class="text-4xl font-semibold mb-3">${pelicula.titulo} </h3>
            <span class="text-2xl">${pelicula.director} |</span><span class="text-gray-900"> ${pelicula.duracion} mins</span>
            <p class="mt-4 text-lg leading-relaxed text-gray-600">
              ${pelicula.descripcion}
            </p>

          <c:if test="${not empty proyecciones}">
            <div id="lista-proyecciones" class="relative m-3 justify-content-center text-center flex flex-col min-w-0 break-words bg-white w-full mb-8 shadow-lg rounded-lg bg-gray-100 ">
              <div class="justify-content-center bg-gray-100 rounded">
                <h6 class="text-xl font-semibold mb-3">Horarios</h6>
                <form action="/reserva/paso1">
                  <select name="fecha" id="fecha" class=" border border-solid border-blue rounded">
                    <c:forEach var="proyeccion" items="${proyecciones}">
                      <option value="${proyeccion.dia}">${proyeccion.dia}</option>
                    </c:forEach>
                  </select>

                  <div id="horas" class="row mt-3 mb-3 justify-content-center">
                      <c:forEach var="proyeccion" items="${proyecciones}">
                      <span class="text-md font-semibold inline-block py-1 px-2 rounded-full text-pink-600 bg-pink-200 last:mr-0 mr-1">
                        <input type="radio" class="btn-check" name="options" id="${proyeccion.comienzo}" value="${proyeccion.comienzo}" autocomplete="off">${proyeccion.comienzo}
                      </span>
                      </c:forEach>
                  </div>
                </form>
              </div>
            </div>
            </c:if>


          </div>
        </div>
      </div>
    </div>
  </section>
</main>


<script src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script>window.jQuery || document.write('<script src="js/jquery-slim.min.js"><\/script>')</script>

<script src="https://unpkg.com/@popperjs/core@2.9.1/dist/umd/popper.min.js" charset="utf-8"></script>
<script>
  function toggleNavbar(collapseID) {
    document.getElementById(collapseID).classList.toggle("hidden");
    document.getElementById(collapseID).classList.toggle("block");
  }
  function openDropdown(event,dropdownID){
    let element = event.target;
    while(element.nodeName !== "BUTTON"){
      element = element.parentNode;
    }
    var popper = Popper.createPopper(element, document.getElementById(dropdownID), {
      placement: 'bottom-start'
    });
    document.getElementById(dropdownID).classList.toggle("hidden");
    document.getElementById(dropdownID).classList.toggle("block");
  }
</script>
  <!-- Bootstrap core JavaScript
================================================== -->
  <!-- Placed at the end of the document so the pages load faster -->
  <script src="${pageContext.request.contextPath}/resources/js/popper.min.js"></script>
  <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
  <script src="${pageContext.request.contextPath}/resources/js/holder.min.js"></script>

</body>
</html>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta content="width=device-width, initial-scale=1.0" name="viewport">

  <title>Components / Accordion - NiceAdmin Bootstrap Template</title>
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
  <link href="${pageContext.request.contextPath}/resources/assets/vendor/boxicons/css/boxicons.min.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/resources/assets/vendor/quill/quill.snow.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/resources/assets/vendor/quill/quill.bubble.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/resources/assets/vendor/remixicon/remixicon.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/resources/assets/vendor/simple-datatables/style.css" rel="stylesheet">

  <!-- Template Main CSS File -->
  <link href="${pageContext.request.contextPath}/resources/assets/css/style.css" rel="stylesheet">

  <!-- =======================================================
    * Template Name: NiceAdmin - v2.2.2
    * Template URL: https://bootstrapmade.com/nice-admin-bootstrap-admin-html-template/
    * Author: BootstrapMade.com
    * License: https://bootstrapmade.com/license/
    ======================================================== -->
</head>

<body>
<main id="main" class="main">

  <div class="pagetitle">
    <h1>Blank Page</h1>
    <nav>
      <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="index.html">Home</a></li>
        <li class="breadcrumb-item">Pages</li>
        <li class="breadcrumb-item active">Blank</li>
      </ol>
    </nav>
  </div><!-- End Page Title -->

  <section class="section">
    <div class="row">
      <div class="col-lg-6">

        <div class="container rounded-lg">
          <div class="card-body">
            <div class="mt-3 px-4 d-flex rounded-lg">
              <img alt="..." class="img-fluid rounded-lg shadow-lg" width="auto" src="${pelicula.poster}">
            </div>
          </div>
        </div>

      </div>

      <div class="col-lg-6">

        <div class="card">
          <div class="card-body">
            <h2 class="title m-3">${pelicula.titulo} - A growing company</h2>
            <h5 class="m-3 ">${pelicula.director} Pelicula |<span class="" id="duracion"> ${pelicula.duracion} mins </span></h5>
            <p class="m-3">
              ${pelicula.descripcion}
              DESCRIPCIONThe extension comes with three pre-built pages to help you get
              started faster. You can change the text and images and you're
              good to go.
            </p>

            <c:if test="${not empty proyecciones}">
              <h2>Proyecciones</h2>
              <div class="d-flex align-items-start">
                <div class="nav flex-column nav-pills me-3" id="v-pills-tab" role="tablist" aria-orientation="vertical">

                  <c:forEach var="proyeccion" items="${proyecciones}">
                    <button class="nav-link active" id="v-pills-${proyeccion.dia}-tab" data-bs-toggle="pill" data-bs-target="#v-pills-${proyeccion.dia}" type="button" role="tab" aria-controls="v-pills-${proyeccion.dia}" aria-selected="true">${proyeccion.dia}</button>
                  </c:forEach>

                </div>
                tiene ue mandar un array tipo: {dia, [hora, hora, hora]}

                <div class="tab-content" id="v-pills-tabContent">
                  <c:forEach var="proyeccion" items="${proyecciones}">
                    <div class="tab-pane fade show active" id="v-pills-${proyeccion.dia}" role="tabpanel" aria-labelledby="v-pills-${proyeccion.dia}-tab">
                      <div class="col">
                        <label for="${proyeccion.comienzo}" class="form-label">Hora de proyeccion</label>
                        <select id="inputState" class="form-select">
                          <c:forEach var="comienzo" items="${proyecciones}">
                            <option>sala xx</option>
                          </c:forEach>

                        </select>
                      </div>
                    </div>
                  </c:forEach>
                </div>
              </div>

            </c:if>







          </div>
        </div>

      </div>
    </div>
  </section>

</main><!-- End #main -->

<!-- ======= Footer ======= -->
<footer id="footer" class="footer">
  <div class="copyright">
    &copy; Copyright <strong><span>NiceAdmin</span></strong>. All Rights Reserved
  </div>
  <div class="credits">
    <!-- All the links in the footer should remain intact. -->
    <!-- You can delete the links only if you purchased the pro version. -->
    <!-- Licensing information: https://bootstrapmade.com/license/ -->
    <!-- Purchase the pro version with working PHP/AJAX contact form: https://bootstrapmade.com/nice-admin-bootstrap-admin-html-template/ -->
    Designed by <a href="https://bootstrapmade.com/">BootstrapMade</a>
  </div>
</footer><!-- End Footer -->

<a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>

<!-- Vendor JS Files -->
<script src="${pageContext.request.contextPath}/resources/assets/vendor/apexcharts/apexcharts.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/assets/vendor/chart.js/chart.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/assets/vendor/echarts/echarts.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/assets/vendor/quill/quill.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/assets/vendor/simple-datatables/simple-datatables.js"></script>
<script src="${pageContext.request.contextPath}/resources/assets/vendor/tinymce/tinymce.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/assets/vendor/php-email-form/validate.js"></script>

<!-- Template Main JS File -->
<script src="${pageContext.request.contextPath}/resources/assets/js/main.js"></script>

</body>

</html>