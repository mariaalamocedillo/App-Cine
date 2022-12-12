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

    <title>Horarios</title>
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

<body class="imgfondo">
<jsp:include page="/WEB-INF/layout/navAdmin.jsp" ></jsp:include>

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
                                        <li class="breadcrumb-item active">Programación horarios-salas</li>
                                    </ol>
                                </nav>
                            </div>

                            <!--IMAGEN-->
                            <div class="row">

                                <div class="col rounded-xl  m-auto">
                                    <div class="card-body">

                                        <div class="row">
                                            <div class="justify-content-between m-auto">
                                                <%--*** TITULO Y FORM DATEPICKER ***--%>
                                                <div class="row">
                                                    <div class="col-8">
                                                        <h5 class="card-title text-center pb-0 fs-4">Ocupacion de las salas dia ${dia}</h5>
                                                    </div>
                                                    <div class="col mb-2">
                                                            <div class="row pt-2 float-end">
                                                                <div class="col pt-2 ">
                                                                    <label for="diaInput">Elija un dia:</label>
                                                                    <input id="diaInput" name="dia" type="date">
                                                                </div>
                                                            </div>
                                                    </div>
                                                </div>
                                                <%--*** TABLA ***--%>

                                                    <c:if test="${mensaje.texto != null}">
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <p class="alert alert-success" id="success-alert">${mensaje.texto}</p>
                                                            </div>
                                                        </div>
                                                    </c:if>

                                                    <table class="table table-striped table-bordered table-hover">
                                                    <thead>
                                                    <tr>
                                                        <th></th>
                                                            <c:forEach var="hora" items="${horarios}">
                                                                <th>${hora}</th>
                                                            </c:forEach>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:forEach var="sala" items="${infoSalas}" varStatus="loopSala">  <%--hashMap de sala  :  List<proyeccion> -> e id="${sala.key.id + "-" + horario}"--%>
                                                            <tr>
                                                                <td>${sala.key.nombre}</td>
                                                            <c:forEach var="horario" items="${horarios}" varStatus="loopHora">
                                                                <td>
                                                                    <c:set var="encontrada" value="false"/>
                                                                    <c:forEach var="proyeccion" items="${sala.value}">
                                                                        <c:if test="${proyeccion.comienzo == horario}">
                                                                            <a href="${mvc.basePath}/admin/proyeccion/editar/${proyeccion.id}" class="">
                                                                                    ${proyeccion.pelicula.titulo}
                                                                            </a>
                                                                            <c:set var="encontrada" value="true"/>
                                                                        </c:if>
                                                                    </c:forEach>
                                                                    <c:if test="${!encontrada}">

                                                                        <c:if test="${not empty peliculas}">
                                                                            <div class="col mb-3 m-auto">
                                                                                <select id="pelicula-${loopSala.index}-${loopHora.index}" name="peliculaId" class="form-select"
                                                                                        onchange="cargaEnlaceProyeccion('${loopHora.index}', '${loopSala.index}', this.value, '${dia}', '${horario}', '${sala.key.id}')">
                                                                                    <option value="-">Seleccione una película</option>
                                                                                    <c:forEach var="pelicula" items="${peliculas}">
                                                                                        <option value="${pelicula.id}">${pelicula.titulo} ${pelicula.enProyeccion ? '' : ' (NP)'}</option>
                                                                                    </c:forEach>
                                                                                </select>
                                                                            </div>
                                                                        </c:if>
                                                                        <a  id="enlaceProyeccion-${loopHora.index}-${loopSala.index}">
                                                                            <button type="button" id="btnProyeccion-${loopHora.index}-${loopSala.index}"
                                                                                    class="btn btn-link btn-info text-light text-decoration-none"
                                                                                    disabled="true">
                                                                                Crear proyección
                                                                            </button>
                                                                        </a>
                                                                    </c:if>
                                                                </td>
                                                            </c:forEach>
                                                            </tr>
                                                        </c:forEach>
                                                    </tbody>
                                                </table>
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
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script>window.jQuery || document.write('<script src="js/jquery-slim.min.js"><\/script>')</script>

<script src="https://unpkg.com/@popperjs/core@2.9.1/dist/umd/popper.min.js" charset="utf-8"></script>

<!-- Vendor JS Files -->
<script src="${pageContext.request.contextPath}/resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/vendor/quill/quill.min.js"></script>
<%--<script src="${pageContext.request.contextPath}/resources/js/form-validation.js"></script>--%>
<!-- Template Main JS File -->
<script src="${pageContext.request.contextPath}/resources/js/main.js"></script>

<script>
    function cargaEnlaceProyeccion(indexS, indexH, idPelicula, dia, hora, idSala){
        var boton = document.getElementById('btnProyeccion-'+indexS+'-'+indexH);
        var enlace = document.getElementById('enlaceProyeccion-'+indexS+'-'+indexH);

        if(idPelicula !== '-'){
            enlace.setAttribute('href',
                '${mvc.basePath}/admin/proyeccion/datosNueva/'
                +idPelicula+'/'+dia+'/'+hora+'/'+idSala);
            boton.disabled=false;
            enlace.disabled=false;
        }else {
            enlace.removeAttribute('href');
            enlace.disabled=true;
            boton.disabled=true;
        }
    }


    //redireccion según se cambia el dia del que se quiere ver el horario
    $( "#diaInput" ).change(function() {
        window.location.href = "${mvc.basePath}/admin/horarios/dia/"+ this.value;
    });

</script>

</body>

</html>
