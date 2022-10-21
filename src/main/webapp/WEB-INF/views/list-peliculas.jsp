<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <title>Lista de peliculas</title>

    <!-- Favicons -->
    <link href="${pageContext.request.contextPath}/resources/assets/img/favicon.png" rel="icon">
    <link href="${pageContext.request.contextPath}/resources/assets/img/apple-touch-icon.png" rel="apple-touch-icon">

    <!-- Vendor CSS Files -->
    <link href="${pageContext.request.contextPath}/resources/assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/assets/vendor/boxicons/css/boxicons.min.css" rel="stylesheet">

    <!-- Template Main CSS File -->
    <link href="${pageContext.request.contextPath}/resources/assets/css/style.css" rel="stylesheet">

</head>

<body class="imgfondo">

<jsp:include page="/WEB-INF/layout/navBar.jsp" ></jsp:include>

<main role="main">

<%--
    <section class="jumbotron text-center">
        <div class="container">
            <h1 class="h-50 text-light fontPraise">Cines Petri®</h1>
            <p>
                <a href="${mvc.basePath}/cartelera/proyectando" class="btn btn-primary my-2">Películas en proyección</a>
                <a href="${mvc.basePath}/cartelera/" class="btn btn-secondary my-2">Explorar todas las películas</a>
            </p>
        </div>
    </section>
--%>

    <div class="album py-5">
        <div class="container">

            <div class="p-segmented-controls p-segmented-controls-alt mb-3">
                <c:forEach var="dia" items="${diasDeProyecciones}" varStatus="loop">
                        <a href="javascript:document.getElementById('peliculas-${loop.index}').style.display='block';void 0"
                                id="${loop.index}" name="diasProyecciones" ${loop.index == 0 ? 'class="active"' : ''}>${dia}</a>
                </c:forEach>
            </div>

            <span id="contenedorPeliculas">
                    <!--Se crea un contenedor por día-->
                <c:forEach var="infoPeli" items="${diasPeliculas}" varStatus="loop">
                    <div class="row" name="contenedorPeliculasUnDia" id="peliculas-${loop.index}" style="${loop.index == 0 ? 'visibility: visible;' : 'visibility: hidden;'} ">
                            <c:forEach var="pelicula" items="${infoPeli.value}">
                                        <div class="col-md-4">
                                            <div class="card mb-4 box-shadow">
                                                <div class="embed-responsive embed-responsive-16by9 boxCard">
                                                    <img class="card-img-top embed-responsive-item" src="${pelicula.poster}" onerror="this.onerror=null; this.src='https://pro2-bar-s3-cdn-cf4.myportfolio.com/103d250a-40b3-4687-9be6-19619654b2bb/5b2dc8f9-8c14-470a-8b49-8ab9841e9e3b_rw_1920.jpg?h=e4b97e8d665ee793187594b165c2e34a'" alt="Card image cap">
                                                </div>
                                                <div class="card-body">
                                                    <h2>${pelicula.titulo}</h2>
                                                    <div class="d-flex justify-content-between align-items-center mt-2">
                                                        <div class="btn-group">
                                                            <a href="${mvc.basePath}/cartelera/${pelicula.id}"><button type="button" class="btn btn-sm btn-outline-secondary bg-info text-white">Detalles</button></a>
                                                        </div>
                                                        <small class="text-muted">${pelicula.duracion} mins</small>
                                                    </div>
                                                </div>
                                                <div class="card-footer">
                                                    <c:forEach var="proyeccion" items="${proyecciones7Dias}">

                                                            <c:if test="${proyeccion.dia == infoPeli.key && proyeccion.pelicula.id == pelicula.id}">
                                                                <a id="${proyeccion.id}" href="${mvc.basePath}/reserva/pelicula/${proyeccion.id}"
                                                                   class="badge rounded-pill bg-warning text-dark">${proyeccion.comienzo}</a>
                                                            </c:if>

                                                    </c:forEach>
                                                </div>
                                            </div>
                                        </div>
                            </c:forEach>
                    </div>
                </c:forEach>
                </span>
            </div>
        </div>
    </div>

</main>

<script src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script>window.jQuery || document.write('<script src="js/jquery-slim.min.js"><\/script>')</script>

<script src="https://unpkg.com/@popperjs/core@2.9.1/dist/umd/popper.min.js" charset="utf-8"></script>

<script src="${pageContext.request.contextPath}/resources/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script>
    (function (document) {
        var p_segmented_controls = document.querySelectorAll(".p-segmented-controls a");
        for (var item of p_segmented_controls) {
            item.addEventListener("click", function (event) {
                event.preventDefault();
                this.parentElement.querySelector("a.active").classList.remove("active");
                var containersDias = document.querySelectorAll("div[name='contenedorPeliculasUnDia']");
                containersDias.forEach(contenedor => {
                    contenedor.style.visibility = "hidden";
                    contenedor.style.display = "none";
                });
                document.getElementById("peliculas-"+this.id).style.visibility = "visible";
                document.getElementById("peliculas-"+this.id).style.removeProperty('display');
                this.classList.add("active");


            });
        }
    })(document)

</script>
<script src="${pageContext.request.contextPath}/resources/assets/js/main.js"></script>

</html>