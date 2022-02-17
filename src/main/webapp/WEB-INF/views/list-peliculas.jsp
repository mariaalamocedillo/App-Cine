<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <title>Lista de peliculas</title>

    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="${pageContext.request.contextPath}/resources/css/album.css" rel="stylesheet"/>
</head>

<body>

<jsp:include page="/WEB-INF/layout/navBar.jsp" ></jsp:include>

<main role="main">

    <section class="jumbotron text-center">
        <div class="container">
            <h1 class="jumbotron-heading">Cines Petri®</h1>
            <p class="lead text-muted">Something short and leading about the collection below—its contents, the creator, etc. Make it short and sweet, but not too short so folks don't simply skip over it entirely.</p>
            <p>
                <a href="${mvc.basePath}/pelicula/proyectando" class="btn btn-primary my-2">Películas en proyección</a>
                <a href="${mvc.basePath}/pelicula/" class="btn btn-secondary my-2">Todas las películas</a>
            </p>
        </div>
    </section>

    <div class="album py-5 bg-light">
        <div class="container">
            <div class="row float-right" >
                <div class="btn-group" role="group" aria-label="Basic example">
                    <button type="button" class="btn btn-outline-primary">Todas</button>
                    <a href="${mvc.basePath}/pelicula/proyectando"><button type="button" class="btn btn-outline-primary">Proyectando</button></a>
                </div>
            </div>

            <!--CARTAS CON LISTADO DE PELÍCULAS-->
            <c:if test="${not empty peliculas}">
                <div class="row" id="peliculas">
                    <c:forEach var="pelicula" items="${peliculas}">
                        <div class="col-md-4">
                            <div class="card mb-4 box-shadow">
                                <img class="card-img-top" src="${pelicula.poster}" alt="Card image cap">
                                <div class="card-body">
                                    <h3>${pelicula.titulo}</h3>
                                    <div class="d-flex justify-content-between align-items-center">
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
<script src="${pageContext.request.contextPath}/resources/js/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/holder.min.js"></script>
</body>
</html>



