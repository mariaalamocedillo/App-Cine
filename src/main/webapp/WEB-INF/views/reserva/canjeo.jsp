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
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">

    <title>Canjeo</title>

    <!-- Favicons -->
    <link href="${pageContext.request.contextPath}/resources/assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/assets/vendor/boxicons/css/boxicons.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/assets/img/favicon.png" rel="icon">
    <link href="${pageContext.request.contextPath}/resources/assets/img/apple-touch-icon.png" rel="apple-touch-icon">

    <!-- Google Fonts -->
    <link href="https://fonts.gstatic.com" rel="preconnect">
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">

    <!-- Vendor CSS Files -->
    <link href="${pageContext.request.contextPath}/resources/assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/estilos.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/assets/css/style.css" rel="stylesheet">

</head>

<body class="imgfondo mt-0">
<jsp:include page="/WEB-INF/layout/navBar.jsp" ></jsp:include>

<main>
    <div class="container">

        <section class="section register min-vh-100 d-flex flex-column text-center py-4">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-lg-8 d-flex flex-column align-items-center justify-content-center">

                        <div class="card rounded-xl justify-content-between m-auto w-100">
                            <div class="card-header">
                                Canjeo de la reserva
                            </div>

                            <div class="card-body">
                                <c:if test="${not empty reserva}">
                                <div>
                                    <div class="row">
                                        <img src="${pageContext.request.contextPath}/resources/canjeos/qrCode${reserva.id}.png" alt="qr-reserva">
                                    </div>

                                    <div class="row">
                                        <div class="col-lg-3 col-md-4 col-6 label ">Película</div>
                                        <div class="col-lg-9 col-md-8 col-6">${reserva.getProyeccion().getPelicula().getTitulo()}</div>
                                    </div>

                                    <div class="row">
                                        <div class="col-lg-3 col-md-4 col-6 label ">Comienzo</div>
                                        <div class="col-lg-9 col-md-8 col-6">${reserva.getProyeccion().getDia()} a las ${reserva.getProyeccion().getComienzo()}</div>
                                    </div>

                                    <div class="row">
                                        <div class="col-lg-3 col-md-4 col-6 label ">Sala</div>
                                        <div class="col-lg-9 col-md-8 col-6">${reserva.getProyeccion().getSala().getNombre()}</div>
                                    </div>

                                    <div class="row">
                                        <div class="col-lg-3 col-md-4 col-6 label">Asientos</div>
                                        <div class="col-lg-9 col-md-8 col-6">
                                            <c:forEach var="entradasElem" items="${reserva.entradas}">
                                                <span>${entradasElem.getAsiento().getName()}</span>
                                            </c:forEach>

                                        </div>
                                    </div>
                                </div>
                                </c:if>
                                <c:if test="${ empty reserva}">
                                    No hemos encontrado la reserva
                                </c:if>
                            </div>
                        </div>


                    </div>
                </div>
            </div>

        </section>

    </div>
</main><!-- End #main -->

<a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>

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

<script src="${pageContext.request.contextPath}/resources/js/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/holder.min.js"></script></body>
<script src="${pageContext.request.contextPath}/resources/assets/vendor/tinymce/tinymce.min.js"></script>


<!-- Template Main JS File -->
<script src="${pageContext.request.contextPath}/resources/assets/js/main.js"></script>

</body>

</html>