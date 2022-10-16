<%--
  Created by IntelliJ IDEA.
  User: maria
  Date: 16/10/2022
  Time: 20:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
    <style>

        .selected {
            background: red !important;
        }
        table{
            border-collapse: collapse;
        }
        tr, td, th{
            border: 2px black solid;
            border-collapse: collapse;
            height: 20px;
            width: 20px;

        }
    </style>

</head>

<body class="imgfondo">
<jsp:include page="/WEB-INF/layout/navBar.jsp" ></jsp:include>

<main>
    <div class="container">

        <section class="section register min-vh-100 d-flex flex-column align-items-center justify-content-center py-4">

            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-lg-10 d-flex flex-column align-items-center justify-content-center">

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

                                <div class="card col-lg-5 col-sm-10 rounded-xl  m-auto">
                                    <div class="card-body">

                                        <div class="row">
                                            <div class="justify-content-between m-auto">
                                                <div class="">
                                                    <h5 class="card-title text-center pb-0 fs-4">Disposición de sala</h5>
                                                </div>


                                            <div id="contenedor">

                                            <h1>${ultimoAsiento}</h1>
                                            <h1>${asientos}</h1>

                                            </div>


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
<script>
    // Example starter JavaScript for disabling form submissions if there are invalid fields
    (function() {
        'use strict';

        window.addEventListener('load', function() {

            generarTabla(${filas}, ${columnas});

        }, false);
    })();


</script>
<!-- Template Main JS File -->
<script src="${pageContext.request.contextPath}/resources/assets/js/main.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/disposicionAsientos.js"></script>

</body>

</html>
