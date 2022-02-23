<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <title>Lista de peliculas</title>

    <!-- Bootstrap -->
    <!-- Vendor CSS Files -->
    <link href="${pageContext.request.contextPath}/resources/assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/assets/vendor/boxicons/css/boxicons.min.css" rel="stylesheet">

    <!-- Template Main CSS File -->
    <link href="${pageContext.request.contextPath}/resources/assets/css/style.css" rel="stylesheet">

</head>

<body>

<jsp:include page="/WEB-INF/layout/navBar.jsp" ></jsp:include>

<main role="main">

    <section class="jumbotron text-center">
        <div class="container">
            <h1 class="h-50">Cines Petri®</h1>
            <p class="lead text-muted">Something short and leading about the collection below—its contents, the creator, etc. Make it short and sweet, but not too short so folks don't simply skip over it entirely.</p>
            <p>
                <a href="${mvc.basePath}/pelicula/proyectando" class="btn btn-primary my-2">Películas en proyección</a>
                <a href="${mvc.basePath}/pelicula/" class="btn btn-secondary my-2">Todas las películas</a>
            </p>
        </div>
    </section>

    <div class="album py-5 bg-light">
        <div class="container">

            <!--CARTAS CON LISTADO DE PELÍCULAS-->
            <c:if test="${not empty peliculas}">
                <div class="row" id="peliculas">
                    <c:forEach var="pelicula" items="${peliculas}">
                        <div class="col-md-4">
                            <div class="card mb-4 box-shadow">
                                <img class="card-img-top" src="${pelicula.poster}" alt="Card image cap">
                                <div class="card-body">
                                    <h2>${pelicula.titulo}</h2>
                                    <div class="d-flex justify-content-between align-items-center mt-2">
                                        <div class="btn-group">
                                            <c:if test="${pelicula.enProyeccion}">
                                                <a href="${mvc.basePath}/pelicula/${pelicula.id}"><button type="button" class="btn btn-sm btn-outline-secondary bg-info text-white">Horarios</button></a>
                                            </c:if>
                                            <c:if test="${!pelicula.enProyeccion}">
                                                <button type="button" class="btn btn-sm btn-outline-secondary" disabled>No proyectando</button>
                                            </c:if>

                                        </div>
                                        <small class="text-muted">${pelicula.duracion} mins</small>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:if>
            </div>
        </div>
    </div>

</main>

<footer class="text-muted">
    <div class="container">
        <p class="float-right">
            <a href="#">Back to top</a>
        </p>
        <p>Album example is &copy; Bootstrap, but please download and customize it for yourself!</p>
    </div>
</footer>

<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script>window.jQuery || document.write('<script src="js/jquery-slim.min.js"><\/script>')</script>

<script src="https://unpkg.com/@popperjs/core@2.9.1/dist/umd/popper.min.js" charset="utf-8"></script>
<script>
    function toggleNavbar(collapseID) {
        document.getElementById(collapseID).classList.toggle("hidden");
        document.getElementById(collapseID).classList.toggle("block");
    }
    function openDropdown(event,dropdownID){
        let element = event.target;
        while(element.nodeName !== "BUTTON"){
            element = element.parentNode;
        }
        var popper = Popper.createPopper(element, document.getElementById(dropdownID), {
            placement: 'bottom-start'
        });
        document.getElementById(dropdownID).classList.toggle("hidden");
        document.getElementById(dropdownID).classList.toggle("block");
    }
</script>
<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster
<script src="${pageContext.request.contextPath}/resources/js/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/holder.min.js"></script></body>-->
<script src="${pageContext.request.contextPath}/resources/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/assets/vendor/tinymce/tinymce.min.js"></script>

<script src="${pageContext.request.contextPath}/resources/assets/js/main.js"></script>

</html>