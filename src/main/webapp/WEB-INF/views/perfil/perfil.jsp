<%--
  Created by IntelliJ IDEA.
  User: maria
  Date: 19/02/2022
  Time: 13:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">

    <title>Mi perfil</title>
    <meta content="" name="description">
    <meta content="" name="keywords">

    <!-- Favicons -->
    <link href="${pageContext.request.contextPath}/resources/img/favicon.png" rel="icon">
    <link href="${pageContext.request.contextPath}/resources/img/apple-touch-icon.png" rel="apple-touch-icon">

    <!-- Google Fonts -->
    <link href="https://fonts.gstatic.com" rel="preconnect">
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">

    <link href="${pageContext.request.contextPath}/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/vendor/boxicons/css/boxicons.min.css" rel="stylesheet">

    <!-- Template Main CSS File -->
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
    <style>
        .box{
            width: 300px;
            height: 300px;
            background: #CCC;
            overflow: hidden;
        }

        .box img{
            width: 100%;
            height: auto;
        }
        @supports(object-fit: cover){
            .box img{
                height: 100%;
                object-fit: cover;
                object-position: center center;
            }
        }
    </style>

</head>

<body class="perfil" style="background-image: url('https://wallpaperaccess.com/full/1988378.jpg');">
<jsp:include page="/WEB-INF/layout/navBar.jsp" ></jsp:include>

<main class="m-3">

    <section class="section profile">
        <div class="row justify-content-center">
            <div class="col-lg-3">

                <div class="card">
                    <div class="card-body profile-card pt-4 d-flex flex-column align-items-center">

                        <img src="${pageContext.request.contextPath}/resources/img/icon.jpg" alt="Profile" class="rounded-circle">
                        <h2>${cliente.nombre}</h2>
                        <h3>Cinéfil@</h3>
                        <h3>${cliente.email}</h3>
                    </div>
                </div>

            </div>

            <div class="col-lg-8">

                <div class="card">
                    <div class="card-body pt-3">
                        <!-- Bordered Tabs -->
                        <ul class="nav nav-tabs nav-tabs-bordered">

                            <li class="nav-item">
                                <button class="nav-link active" data-bs-toggle="tab" data-bs-target="#reservas-activas">Reservas activas</button>
                            </li>

                            <li class="nav-item">
                                <button class="nav-link" data-bs-toggle="tab" data-bs-target="#reservas-antiguas">Reservas antiguas</button>
                            </li>


                        </ul>
                        <div class="tab-content pt-2">
                            <c:if test="${mensaje.texto != null}">
                                <div class="col-md-12">
                                    <p class="alert alert-success" id="success-alert">${mensaje.texto}</p>
                                </div>
                            </c:if>

                            <div class="tab-pane fade show active profile-overview" id="reservas-activas">
                                <h5 class="card-title">Reservas no canjeadas</h5>
                        <c:if test="${not empty reservas}">
                            <c:forEach var="reserva" items="${reservas}">
                                <div class="card mb-3">
                                    <div class="row g-0 mb-0">
                                        <div class="col-md-4 box">
                                            <img src="${reserva.proyeccion.pelicula.getPoster()}" class="figure-img img-fluid rounded rounded-start" alt="...">
                                        </div>
                                        <div class="col">
                                            <div class="card-body">
                                                <h5 class="card-title">Reserva para <a href="${mvc.basePath}/cartelera/${reserva.proyeccion.pelicula.id}">${reserva.proyeccion.pelicula.titulo}</a></h5>
                                                <div class="row">
                                                    <div class="col-lg-3 col-md-4 label ">Día</div>
                                                    <div class="col-lg-6 col-md-4">${reserva.proyeccion.getDia()}</div>
                                                    <div class="col-lg-3 col-md-4">
                                                        <a href="${mvc.basePath}/usuario/canjeo/${reserva.id}"><button class="btn btn-info text-light">Canjear</button></a>
                                                    </div>
                                                </div>

                                                <div class="row">
                                                    <div class="col-lg-3 col-md-4 label ">Comienzo</div>
                                                    <div class="col-lg-9 col-md-8">${reserva.proyeccion.comienzo}</div>
                                                </div>

                                                <div class="row">
                                                    <div class="col-lg-3 col-md-4 label ">Duración</div>
                                                    <div class="col-lg-9 col-md-8">${reserva.proyeccion.pelicula.duracion} mins</div>
                                                </div>

                                                <div class="row">
                                                    <div class="col-lg-3 col-md-4 label ">Sala</div>
                                                    <div class="col-lg-9 col-md-8">${reserva.proyeccion.sala.nombre}</div>
                                                </div>

                                                <div class="row">
                                                    <div class="col-lg-3 col-md-4 label">Asientos</div>
                                                    <div class="col-lg-9 col-md-8">
                                                        <c:forEach var="elemEntrada" items="${reserva.entradas}">
                                                            <span>${elemEntrada.asiento.getName()}</span>
                                                        </c:forEach>
                                                    </div>
                                                </div>



                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:if>
                        <c:if test="${empty reservas}">
                            Actualmente no tiene ninguna reserva activa
                        </c:if>
                            </div>

                            <div class="tab-pane fade profile-edit pt-3" id="reservas-antiguas">
                                <h5 class="card-title">Reservas ya canjeadas</h5>
                                <c:if test="${not empty reservasAntiguas}">
                                    <c:forEach var="reserva" items="${reservasAntiguas}">
                                        <div class="card mb-3">
                                            <div class="row g-0 mb-0">
                                                <div class="col">
                                                    <div class="card-body">
                                                        <h5 class="card-title">Reserva para ${reserva.getProyeccion().getPelicula().getTitulo()}</h5>
                                                        <div class="row">
                                                            <div class="col-md-4 label ">Día</div>
                                                            <div class="col-md-8">${reserva.getProyeccion().getDia()}</div>
                                                        </div>

                                                        <div class="row">
                                                            <div class="col-md-4 label ">Comienzo</div>
                                                            <div class="col-md-8">${reserva.getProyeccion().getComienzo()}</div>
                                                        </div>

<%--
                                                        <div class="row">
                                                            <div class="col-md-4 label">Asientos reservados</div>
                                                            <div class="col-md-8">${reserva.entrada.getAsientos().size()}</div>
                                                        </div>
--%>



                                                    </div>

                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </c:if>
                                <c:if test="${empty reservasAntiguas}">
                                    Actualmente no tiene ninguna reserva antigua
                                </c:if>
                            </div>

                        </div><!-- End Bordered Tabs -->

                    </div>
                </div>

            </div>
        </div>
    </section>

</main><!-- End #main -->

<a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>

<script src="${pageContext.request.contextPath}/resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/vendor/php-email-form/validate.js"></script>

<!-- Template Main JS File -->
<script src="${pageContext.request.contextPath}/resources/js/main.js"></script>

</body>

</html>

