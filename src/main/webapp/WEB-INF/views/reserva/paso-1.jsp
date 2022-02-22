<%--
  Created by IntelliJ IDEA.
  User: maria
  Date: 20/02/2022
  Time: 14:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">

    <title>Reserva</title>
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

    <!-- Template Main CSS File -->
    <link href="${pageContext.request.contextPath}/resources/assets/css/style.css" rel="stylesheet">

    <!-- =======================================================
      * Template Name: NiceAdmin - v2.2.2
      * Template URL: https://bootstrapmade.com/nice-admin-bootstrap-admin-html-template/
      * Author: BootstrapMade.com
      * License: https://bootstrapmade.com/license/
      ======================================================== -->
</head>

<body class="imgfondo">

<main>
    <div class="container">

        <section class="section register min-vh-100 d-flex flex-column align-items-center justify-content-center py-4">

            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-lg-10 d-flex flex-column align-items-center justify-content-center">

                        <div class="d-flex justify-content-center py-4">
                            <a href="${mvc.basePath}/pelicula" class="logo d-flex align-items-center w-auto">
                                <img src="${pageContext.request.contextPath}/resources/assets/img/logo.png" alt="">
                                <span class="d-none d-lg-block">Cines Petri</span>
                            </a>
                        </div><!-- End Logo -->

                        <div class="card rounded-xl justify-content-between m-auto w-100">
                            <div class="card-header">
                                <nav>
                                    <ol class="breadcrumb mb-0" style="--bs-breadcrumb-divider: '>';">
                                        <li class="breadcrumb-item active">Reserva</li>
                                        <li class="breadcrumb-item">Asientos</li>
                                        <li class="breadcrumb-item">Pago</li>
                                    </ol>
                                </nav>
                            </div>

                            <!--IMAGEN-->
                            <div class="row">
                                <div class="col-lg-6 text-center">
                                    <div class="justify-content-between m-auto">
                                        <img class="" src="https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcSdQfe69qSKksn3drVCLfT1b2ADMzDBfgJQWmX9Ni0wZLzVptof" alt="" width="400">
                                    </div>

                                </div>

                                <div class="card col-lg-5 col-sm-10 rounded-xl  m-auto">
                                    <div class="card-body">

                                        <div class="row">
                                            <div class="justify-content-between m-auto">
                                                <div class="">
                                                    <h5 class="card-title text-center pb-0 fs-4">Reserva</h5>
                                                </div>

                                                <form class="row g-3 needs-validation" action="${mvc.basePath}/reserva/paso2" method="post">
                                                    <input type="text" name="id" value="${proyeccion.id}" hidden>
                                                    <h5>${proyeccion.pelicula.titulo} | <span class="small">duracion</span></h5>

                                                    <div class="col-lg-6">
                                                        <label for="dia">Día</label>
                                                        <input type="date" id="dia" name="dia" class="form-control" value="${proyeccion.dia}" disabled>
                                                    </div>

                                                    <div class="col-lg-6">
                                                        <label for="comienzo">Hora sesión</label>
                                                        <select id="comienzo" name="comienzo" class="form-select" aria-label="Default select example" disabled>
                                                            <option selected>${proyeccion.comienzo}</option>
                                                        </select>
                                                    </div>

                                                    <div class="col-lg-6">
                                                        <label for="comienzo">Hora sesión</label>
                                                        <input type="email" class="form-control" name="email">
                                                    </div>

                                                    <div class="col-lg-6">
                                                        <label for="comienzo">Hora sesión</label>
                                                        <input type="password" class="form-control" name="psswd">
                                                    </div>

                                                    <c:if test="${mensaje.texto != null}">
                                                        <div class="col-md-12">
                                                            <p class="alert alert-danger" id="success-alert">${mensaje.texto}</p>
                                                        </div>
                                                    </c:if>
                                                    <div class="col-12 mt-3 text-center">
                                                        <button class="btn btn-primary w-50" type="submit">Siguiente</button>
                                                    </div>
                                                </form>

                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </section>

    </div>
</main><!-- End #main -->

<a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>

<!-- Vendor JS Files -->
<script src="${pageContext.request.contextPath}/resources/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/assets/vendor/quill/quill.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/assets/vendor/php-email-form/validate.js"></script>

<!-- Template Main JS File -->
<script src="${pageContext.request.contextPath}/resources/assets/js/main.js"></script>

</body>

</html>
