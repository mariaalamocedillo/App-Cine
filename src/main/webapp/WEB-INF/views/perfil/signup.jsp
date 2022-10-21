<%--
  Created by IntelliJ IDEA.
  User: maria
  Date: 19/02/2022
  Time: 13:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">

    <title>Pages / Register - NiceAdmin Bootstrap Template</title>
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

    <!-- Template Main CSS File -->
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
</head>

<body class="perfil">
<jsp:include page="/WEB-INF/layout/navBar.jsp" ></jsp:include>

<main>
    <div class="container">
        <section class="section register min-vh-100 d-flex flex-column align-items-center justify-content-center py-4">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-lg-4 col-md-6 d-flex flex-column align-items-center justify-content-center">

                        <div class="d-flex justify-content-center py-4">
                            <a href="${mvc.basePath}/cartelera" class="logo d-flex align-items-center w-auto">
                                <img src="${pageContext.request.contextPath}/resources/img/logo.png" alt="">
                                <span class="d-none d-lg-block text-light">Cines Petri</span>
                            </a>
                        </div><!-- End Logo -->

                        <div class="card mb-3">

                            <div class="card-body">

                                <div class="pt-4 pb-2">
                                    <h5 class="card-title text-center pb-0 fs-4">Craer una cuenta</h5>
                                    <p class="text-center small">Introduzca los datos para crear una cuenta</p>
                                </div>

                                <form method="post" action="${mvc.basePath}/usuario/registro/submit" class="row g-3 needs-validation" novalidate>
                                    <input type="text" name="id" value="${cliente.id}" hidden>
                                    <div class="col-12">
                                        <label for="yourName" class="form-label">Nombre completo</label>
                                        <input type="text" name="nombre" value="${cliente.nombre}" class="form-control" id="yourName" required>
                                    </div>

                                    <div class="col-12">
                                        <label for="yourEmail" class="form-label">Email</label>
                                        <input type="email" name="email" value="${cliente.email}" class="form-control" id="yourEmail" required>
                                    </div>

                                    <div class="col-12">
                                        <label for="yourPhn" class="form-label">Teléfono</label>
                                        <div class="input-group">
                                            <span class="input-group-text" id="inputGroupPrepend2">+(34)</span>
                                            <input type="number" name="tlfn" value="${cliente.tlfn}" class="form-control" id="yourPhn" required>
                                        </div>
                                    </div>

                                    <div class="col-12">
                                        <label for="yourPassword" class="form-label">Contraseña</label>
                                        <input type="password" name="contrasena" class="form-control" id="yourPassword" required>
                                    </div>

                                    <div class="col-12">
                                        <div class="form-check">
                                            <input class="form-check-input" name="terms" type="checkbox" value="" id="acceptTerms" required>
                                            <label class="form-check-label" for="acceptTerms">Acepto y estoy de acuerdo los <a href="https://i.imgur.com/qh2CT.jpg">términos y condiciones</a></label>
                                        </div>
                                    </div>

                                    <c:if test="${mensaje.texto != null}">
                                        <div class="col-md-12">
                                            <p class="alert alert-danger" id="success-alert">${mensaje.texto}</p>
                                        </div>
                                    </c:if>

                                    <div class="col-12">
                                        <button class="btn btn-primary w-100" type="submit">Registrarse</button>
                                    </div>
                                    <div class="col-12">
                                        <p class="small mb-0">¿Ya tienes cuenta? <a href="${mvc.basePath}/usuario">Entrar en mi cuenta</a></p>
                                    </div>
                                </form>

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
<script src="${pageContext.request.contextPath}/resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/form-validation.js"></script>

<!-- Template Main JS File -->
<script src="${pageContext.request.contextPath}/resources/js/main.js"></script>

</body>

</html>