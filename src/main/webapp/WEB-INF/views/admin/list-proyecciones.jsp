<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <title>Lista de proyecciones</title>

    <!-- Bootstrap -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/5.0.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.11.4/css/dataTables.bootstrap5.min.css">
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/img/favicon.png" rel="icon">
    <link href="${pageContext.request.contextPath}/resources/img/apple-touch-icon.png" rel="apple-touch-icon">

    <!-- Custom styles for this template -->
    <link href="${pageContext.request.contextPath}/resources/css/album.css" rel="stylesheet"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdn.datatables.net/1.11.4/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.11.4/js/dataTables.bootstrap5.min.js"></script>

    <script>
        $(document).ready(function() {
            $('#proyecciones').DataTable();
        } );
    </script>
</head>

<body>
<jsp:include page="/WEB-INF/layout/navAdmin.jsp" ></jsp:include>
    <section class="text-center">
        <div class="container">
            <h1>Proyecciones activas</h1>
        </div>
    </section>

    <div class="container">
        <c:if test="${not empty proyecciones}">
            <!-- Table with stripped rows -->
            <table id="proyecciones" class="table table-striped order-column">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Película</th>
                    <th scope="col">Sala</th>
                    <th scope="col">Comienzo</th>
                    <th scope="col">Fecha</th>
                    <th scope="col">Acciones</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="proyeccion" items="${proyecciones}">
                    <tr>
                        <th scope="row">${proyeccion.id}</th>
                        <td>${proyeccion.pelicula.titulo}</td>
                        <td>${proyeccion.sala.id}</td>
                        <td>${proyeccion.comienzo}</td>
                        <td>${proyeccion.dia}</td>
                        <td>
                            <a href="${mvc.basePath}/admin/proyeccion/borrar/${proyeccion.id}" class="text-light"><button class="btn btn-danger">Borrar</button></a>
                            <a href="${mvc.basePath}/admin/proyeccion/editar/${proyeccion.id}" class="text-light"><button class="btn btn-info">Editar</button></a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
        <a href="${mvc.basePath}/admin/proyeccion/nueva/0" class="text-light"><button class="btn btn-info">Nueva proyección</button></a>
        <c:if test="${not empty mensaje.texto}">
            <div class="alert alert-success mt-2">
                    ${mensaje.texto}
            </div>
        </c:if>

    </div>
</main>

<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="${pageContext.request.contextPath}/resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/simple-datatables.js"></script>

<!-- Template Main JS File -->
<script src="${pageContext.request.contextPath}/resources/js/main.js"></script>

<script src="${pageContext.request.contextPath}/resources/js/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/holder.min.js"></script>

</body>
</html>