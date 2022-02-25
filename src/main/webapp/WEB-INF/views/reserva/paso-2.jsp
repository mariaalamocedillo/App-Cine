<%--
  Created by IntelliJ IDEA.
  User: maria
  Date: 20/02/2022
  Time: 14:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<head>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">

    <title>Reserva asientos</title>
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
    <link href="${pageContext.request.contextPath}/resources/css/seats-style.css" rel="stylesheet">

    <!-- =======================================================
      * Template Name: NiceAdmin - v2.2.2
      * Template URL: https://bootstrapmade.com/nice-admin-bootstrap-admin-html-template/
      * Author: BootstrapMade.com
      * License: https://bootstrapmade.com/license/
      ======================================================== -->
</head>

<body class="imgfondo">
<jsp:include page="/WEB-INF/layout/navBar.jsp" ></jsp:include>

<main>
    <div class="container">

        <section class="section register min-vh-100 d-flex flex-column text-center py-4">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-lg-8 d-flex flex-column align-items-center justify-content-center">

                        <div class="card rounded-xl justify-content-between m-auto w-100">
                            <div class="card-header">
                                <nav>
                                    <ol class="breadcrumb mb-0" style="--bs-breadcrumb-divider: '>';">
                                        <li class="breadcrumb-item">Reserva</li>
                                        <li class="breadcrumb-item active">Asientos</li>
                                        <li class="breadcrumb-item">Pago</li>
                                    </ol>
                                </nav>
                            </div>

                            <div class="card-body">

                                <div class="row">
                                    <div class="justify-content-between m-auto text-center">
                                        <div class="">
                                            <h5 class="card-title text-center pb-0 fs-4">Reserva</h5>
                                        </div>

                                        <div class="cuadro col-xl-8 col-md-9 justify-content-center">


                                            <div class="movie-container" hidden>
                                                <input id="movie" type="text" value="${precio}">
                                            </div>

                                            <ul class="showcase text-light  ">
                                                <li>
                                                    <div class="seat"></div>
                                                    <small>N/A</small>
                                                </li>
                                                <li>
                                                    <div class="seat selected"></div>
                                                    <small>Selected</small>
                                                </li>
                                                <li>
                                                    <div class="seat occupied"></div>
                                                    <small>Occupied</small>
                                                </li>
                                            </ul>

                                            <div class="container justify-content-center">
                                                <div class="screen"></div>
                                                <div class="row rowSeats">
                                                    <div class="seat" id="1a"></div>
                                                    <div class="seat" id="2a"></div>
                                                    <div class="seat" id="3a"></div>
                                                    <div class="seat" id="4a"></div>
                                                    <div class="seat" id="5a"></div>
                                                    <div class="seat" id="6a"></div>
                                                    <div class="seat" id="7a"></div>
                                                    <div class="seat" id="8a"></div>
                                                </div>
                                                <div class="row rowSeats">
                                                    <div class="seat" id="1b"></div>
                                                    <div class="seat" id="2b"></div>
                                                    <div class="seat" id="3b"></div>
                                                    <div class="seat" id="4b"></div>
                                                    <div class="seat" id="5b"></div>
                                                    <div class="seat" id="6b"></div>
                                                    <div class="seat" id="7b"></div>
                                                    <div class="seat" id="8b"></div>
                                                </div>
                                                <div class="row rowSeats">
                                                    <div class="seat" id="1c"></div>
                                                    <div class="seat" id="2c"></div>
                                                    <div class="seat" id="3c"></div>
                                                    <div class="seat" id="4c"></div>
                                                    <div class="seat" id="5c"></div>
                                                    <div class="seat" id="6c"></div>
                                                    <div class="seat" id="7c"></div>
                                                    <div class="seat" id="8c"></div>
                                                </div>
                                                <div class="row rowSeats">
                                                    <div class="seat" id="1d"></div>
                                                    <div class="seat" id="2d"></div>
                                                    <div class="seat" id="3d"></div>
                                                    <div class="seat" id="4d"></div>
                                                    <div class="seat" id="5d"></div>
                                                    <div class="seat" id="6d"></div>
                                                    <div class="seat" id="7d"></div>
                                                    <div class="seat" id="8d"></div>
                                                </div>
                                                <div class="row rowSeats">
                                                    <div class="seat" id="1e"></div>
                                                    <div class="seat" id="2e"></div>
                                                    <div class="seat" id="3e"></div>
                                                    <div class="seat" id="4e"></div>
                                                    <div class="seat" id="5e"></div>
                                                    <div class="seat" id="6e"></div>
                                                    <div class="seat" id="7e"></div>
                                                    <div class="seat" id="8e"></div>
                                                </div>
                                                <div class="row rowSeats">
                                                    <div class="seat" id="1f"></div>
                                                    <div class="seat" id="2f"></div>
                                                    <div class="seat" id="3f"></div>
                                                    <div class="seat" id="4f"></div>
                                                    <div class="seat" id="5f"></div>
                                                    <div class="seat" id="6f"></div>
                                                    <div class="seat" id="7f"></div>
                                                    <div class="seat" id="8f"></div>
                                                </div>
                                            </div>
                                            <p class="text">
                                                Seleccionaste <span id="count">0</span> asientos; el precio se queda en <span id="total">0</span>€
                                            </p>
                                            <c:if test="${mensaje.texto != null}">
                                                <div class="col-md-12">
                                                    <p class="alert alert-danger" id="success-alert">${mensaje.texto}</p>
                                                </div>
                                            </c:if>

                                            <form action="${mvc.basePath}/reserva/paso3" method="post">
                                                <input type="text" name="reserva" value="${reserva.id}" hidden>
                                                <input type="text" id="ids" name="ids" hidden/>
                                                <input type="text" id="totalForm" name="precio" hidden/>
                                                <input type="submit" class="btn btn-primary text-light" value="Siguiente">
                                            </form>



                                        </div><!--fin cuadro-->


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
<input id="ocupados" value="${asientosOcupados}" hidden>
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
<script src="${pageContext.request.contextPath}/resources/js/seats-js.js"></script>

</body>

</html>