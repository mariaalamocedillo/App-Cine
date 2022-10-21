<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <title>Lista de películas</title>

    <!-- Bootstrap -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/5.0.1/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/img/favicon.png" rel="icon">
    <link href="${pageContext.request.contextPath}/resources/img/apple-touch-icon.png" rel="apple-touch-icon">

    <link rel="stylesheet" href="https://cdn.datatables.net/1.11.4/css/dataTables.bootstrap5.min.css">
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
    <!-- Custom styles for this template -->
    <link href="${pageContext.request.contextPath}/resources/css/album.css" rel="stylesheet"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdn.datatables.net/1.11.4/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.11.4/js/dataTables.bootstrap5.min.js"></script>

    <script>
    $(document).ready(function() {
        $('#peliculas').DataTable();
    } );
    </script>
</head>

<body>
<jsp:include page="/WEB-INF/layout/navAdmin.jsp" ></jsp:include>

<main role="main">
    <section class="text-center">
        <div class="container">
            <h1>Películas</h1>
        </div>
    </section>

    <div class="container">
        <c:if test="${not empty peliculas}">
            <!-- Table with stripped rows -->
            <table id="peliculas" class="table table-striped order-column">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Titulo</th>
                    <th scope="col">Director</th>
                    <th scope="col">Estudio</th>
                    <th scope="col">Num proyecciones</th>
                    <th scope="col">Acciones</th>
                    <th scope="col">Proyecciones</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="peli" items="${peliculas}">
                    <tr>
                        <th scope="row">${peli.id}</th>
                        <td>${peli.titulo}</td>
                        <td>${peli.director}</td>
                        <td>${peli.estudio}</td>
                        <td>${peli.enProyeccion == false ? '-' : peli.getProyecciones().size()}</td>
                        <td>
                            <a href="${mvc.basePath}/admin/borrar/${peli.id}" class="text-light"><button class="btn btn-danger">Borrar</button></a>
                            <a href="${mvc.basePath}/admin/editar/${peli.id}" class="text-light"><button class="btn btn-info">Editar</button></a>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test = "${peli.enProyeccion ==true}">
                                    <a href="${mvc.basePath}/admin/proyeccion/nueva/${peli.id}" class="text-light"><button class="col btn mt-2 ml-2 btn-info" value="${peli.enProyeccion}" type="submit">Crear proyección</button></a>
                                </c:when>
                                <c:otherwise>
                                    <button class="col btn mt-2 ml-2 btn-outline-danger" value="${peli.enProyeccion}" disabled type="submit">No proyectando</button>
                                </c:otherwise>
                            </c:choose>

                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </c:if>

        <c:if test="${mensaje.texto != null}">
            <div class="row">
                <div class="col-md-12">
                    <p class="alert alert-success" id="success-alert">${mensaje.texto}</p>
                </div>
            </div>
        </c:if>

        <a href="${mvc.basePath}/admin/pelicula/nueva" class="text-light"><button class="btn btn-info">Nueva pelicula</button></a>

    </div>
</main>


<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="${pageContext.request.contextPath}/resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- Template Main JS File -->
<script src="${pageContext.request.contextPath}/resources/js/main.js"></script>

<script src="${pageContext.request.contextPath}/resources/js/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/holder.min.js"></script>

</body>
</html>