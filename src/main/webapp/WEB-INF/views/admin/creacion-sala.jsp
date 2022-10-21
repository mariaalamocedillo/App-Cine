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
    <link href="${pageContext.request.contextPath}/resources/css/seats-style.css" rel="stylesheet">

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
                                        <li class="breadcrumb-item active">Creación de sala</li>
                                    </ol>
                                </nav>
                            </div>

                            <div class="row">
                                <div class="col-lg-6 text-center"></div>
                                <div class="card col-lg-5 col-sm-10 rounded-xl  m-auto">
                                    <div class="card-body">

                                        <div class="row">
                                            <div class="justify-content-between m-auto">
                                                <div class="">
                                                    <h5 class="card-title text-center pb-0 fs-4">Creación de sala</h5>
                                                </div>

                                                <form class="row g-3 needs-validation" action="${mvc.basePath}/admin/sala/submit" method="post">

                                                    <c:if test="${not empty sala.id}">
                                                        <input id="id" name="id" type="hidden" value="${sala.id}"/>
                                                    </c:if>
                                                    <label for="nombre">Nombre de la sala</label>
                                                    <input type="text" id="nombre" name="nombre" value="${sala.nombre}">

                                                    <div class="container justify-content-center">
                                                        <div class="screen"></div>
                                                        <div id="contenedor"></div>
                                                    </div>



                                                    <label for="filas">Filas</label>
                                                    <input type="number" id="filas" autofocus>
                                                    <label for="columnas">columnas</label>
                                                    <input type="number" id="columnas">
                                                    <input type="button" value="Genera una tabla" onclick="genera_tabla(document.getElementById('filas').value, document.getElementById('columnas').value)">

                                                    <input type="text" id="listado" name="listado" hidden>


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

<!-- Vendor JS Files -->
<script src="${pageContext.request.contextPath}/resources/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/assets/vendor/quill/quill.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/assets/vendor/php-email-form/validate.js"></script>
<script>
    // Example starter JavaScript for disabling form submissions if there are invalid fields
    (function() {
        'use strict';

        window.addEventListener('load', function() {
            // Fetch all the forms we want to apply custom Bootstrap validation styles to
            var forms = document.getElementsByClassName('needs-validation');

            // Loop over them and prevent submission
            var validation = Array.prototype.filter.call(forms, function(form) {
                form.addEventListener('submit', function(event) {
                    if (form.checkValidity() === false) {
                        event.preventDefault();
                        event.stopPropagation();
                    }
                    form.classList.add('was-validated');
                }, false);
            });
        }, false);
    })();
</script>
<!-- Template Main JS File -->
<script src="${pageContext.request.contextPath}/resources/assets/js/main.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/disposicionAsientos.js"></script>

</body>

</html>
