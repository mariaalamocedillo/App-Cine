<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <title>Lista de peliculas</title>

    <!-- Favicons -->
    <link href="${pageContext.request.contextPath}/resources/img/favicon.png" rel="icon">
    <link href="${pageContext.request.contextPath}/resources/img/apple-touch-icon.png" rel="apple-touch-icon">

    <!-- Vendor CSS Files -->
    <link href="${pageContext.request.contextPath}/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/vendor/boxicons/css/boxicons.min.css" rel="stylesheet">

    <!-- Template Main CSS File -->
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">

</head>

<body class="imgfondo">

<jsp:include page="/WEB-INF/layout/navBar.jsp" ></jsp:include>

<main role="main">

<%----%>
    <section class="jumbotron text-center">
        <div class="container">
            <h1 class="h-50 text-light fontPraise">Cines Petri®</h1>
            <p>
                <a href="${mvc.basePath}/cartelera/descubrir" class="btn btn-primary my-2">Explorar todas las películas</a>
            </p>
        </div>
    </section>


    <div class="album py-5">
        <div class="container">

            <div class="p-segmented-controls p-segmented-controls-alt mb-3">
                <c:forEach var="dia" items="${diasDeProyecciones}" varStatus="loop">
                        <a href="javascript:void 0" id="${dia.key}" name="diasProyecciones" ${loop.index == 0 ? 'class="active"' : ''}>${dia.value}</a>
                </c:forEach>
            </div>

            <span id="contenedorPeliculas">
                    <!--Se crea un contenedor para mostrar las películas-->
                    <div class="row" id="contenedorPeliculasUnDia">

                    </div>
            </span>

        </div>
    </div>

</main>

<script
        src="https://code.jquery.com/jquery-3.6.0.min.js"
        integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
        crossorigin="anonymous"
></script>
<script>window.jQuery || document.write('<script src="js/jquery-slim.min.js"><\/script>')</script>

<script src="https://unpkg.com/@popperjs/core@2.9.1/dist/umd/popper.min.js" charset="utf-8"></script>

<script src="${pageContext.request.contextPath}/resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script>
    const DIAS = $("[name='diasProyecciones']");
    //al cargarse el DOM, se realiza fetch de la primera fecha de proyecciones disponible
    $(document).ready(function() {
        hacerFetch(DIAS[0].id);

        //al clicar en un elemento <a> con dicho nombre se desactiva el anterior y se activa el clicado, así como se realiza el fetch de dicho dia
        $('a[name="diasProyecciones"]').click(function(){
            $(`a[name="diasProyecciones"].active`).removeClass('active'); //buscamos el que tiene la clase de activo y se ñp eliminamos para ponersela al clicado
            $(this).addClass('active');
            hacerFetch($(this).attr('id'));
        });
    });

    //funcion que recoge los datos de las peliculas proyectadas cierto dia por medio de fetch y crea las tarjetas para cada una de ellas
    function hacerFetch(dia) {
        const url = 'http://localhost:8080/cinema/mvc/api/pelicula/cartelera/' + dia;
        fetch(url)
            .then((resp) => resp.json())
            .then(function(data) {
                let peliculas = data;
                //vaciamos el contenedor para volver a rellenarlo con el contenido del nuevo dia
                $('#contenedorPeliculasUnDia').empty();

                return peliculas.map(function(pelicula) {

                    $('#contenedorPeliculasUnDia').append($(creaDivPelicula(pelicula)));

                    //creacion de badges de las horas de proyeccion y añadido a las tarjetas
                    let proyeccionesBadges = '';
                    $.each(pelicula.proyecciones, function(index, value) {
                        proyeccionesBadges += badgesProyeccion(value, dia);
                    });
                    $("#proyecciones-"+ pelicula.id).append(proyeccionesBadges);


                })
            })
            .catch(function(error) {
                console.log(error);
            });
    }


    function creaDivPelicula(pelicula){
        return `             <div class="col-md-4">
                                            <div class="card mb-4 box-shadow">
                                                <div class="embed-responsive embed-responsive-16by9 boxCard">
                                                    <img class="card-img-top embed-responsive-item" src="` + pelicula.poster + `" onerror="this.onerror=null; this.src='https://pro2-bar-s3-cdn-cf4.myportfolio.com/103d250a-40b3-4687-9be6-19619654b2bb/5b2dc8f9-8c14-470a-8b49-8ab9841e9e3b_rw_1920.jpg?h=e4b97e8d665ee793187594b165c2e34a'" alt="Card image cap">
                                                </div>
                                                <div class="card-body">
                                                    <h2>` + pelicula.titulo + `</h2>
                                                    <div class="d-flex justify-content-between align-items-center mt-2">
                                                        <div class="btn-group">
                                                            <a href="${mvc.basePath}/cartelera/` + pelicula.id + `"><button type="button" class="btn btn-sm btn-outline-secondary bg-info text-white">Detalles</button></a>
                                                        </div>
                                                        <small class="text-muted">` + pelicula.duracion + ` mins</small>
                                                    </div>
                                                </div>
                                                <div class="card-footer" id="proyecciones-` + pelicula.id + `">

                                                </div>
                                            </div>
                                        </div>
                                        `;
    }

    function badgesProyeccion(proyeccion, diaRecibido) {
        if(proyeccion.dia === diaRecibido) {
            return `<a id="` + proyeccion.id + ` " href="${mvc.basePath}/reserva/pelicula/` + proyeccion.id +`"
                class="badge rounded-pill bg-warning text-dark">` + proyeccion.comienzo + ` </a>`;
        } else {
            return '';
        }
    }

</script>
<script src="${pageContext.request.contextPath}/resources/js/main.js"></script>

</html>