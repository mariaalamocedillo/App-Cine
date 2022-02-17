<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <title>Pelicula</title>
  <!-- Bootstrap -->
  <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">

  <!-- Custom styles for this template -->
  <link href="${pageContext.request.contextPath}/resources/css/album.css" rel="stylesheet"/>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" />
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/creativetimofficial/tailwind-starter-kit/compiled-tailwind.min.css" />

<body class="text-gray-800 antialiased">

<jsp:include page="/WEB-INF/layout/navBar.jsp" ></jsp:include>

<!--MAIN-->
<main>

  <!--CONTENEDOR DE INFO-->
  <section class="relative py-20 mt-5">
    <div class="container mx-auto px-4">
      <div class="items-center flex flex-wrap">
        <div class="w-full md:w-4/12 ml-auto mr-auto px-4">
          <img alt="..." class="max-w-full rounded-lg shadow-lg" src="${pelicula.poster}" />
        </div>
        <div class="w-full md:w-5/12 ml-auto mr-auto px-4">
          <div class="md:pr-12">
            <!--<div class="text-pink-600 p-3 text-center inline-flex items-center justify-center w-16 h-16 mb-6 shadow-lg rounded-full bg-pink-300" >
              <i class="fas fa-rocket text-xl"></i>
            </div>-->
            <h3 class="text-4xl font-semibold mb-3">${pelicula.titulo} </h3>
            <span class="text-2xl">${pelicula.director} |</span><span class="text-gray-900"> ${pelicula.duracion} mins</span>
            <p class="mt-4 text-lg leading-relaxed text-gray-600">
              ${pelicula.descripcion}
            </p>

          <c:if test="${not empty proyecciones}">
            <div id="lista-proyecciones" class="relative m-3 justify-content-center text-center flex flex-col min-w-0 break-words bg-white w-full mb-8 shadow-lg rounded-lg bg-gray-100 ">
              <div class="justify-content-center bg-gray-100 rounded">
                <h6 class="text-xl font-semibold mb-3">Horarios</h6>
                <form action="#ENLACEACTIONPARARESERVAR">
                  <select name="fecha" id="fecha" class=" border border-solid border-blue rounded">
                    <c:forEach var="proyeccion" items="${proyecciones}">
                      <option value="${proyeccion.dia}">${proyeccion.dia}</option>
                    </c:forEach>
                  </select>

                  <div id="horas" class="row mt-3 mb-3 justify-content-center">
                      <c:forEach var="proyeccion" items="${proyecciones}">
                      <span class="text-md font-semibold inline-block py-1 px-2 rounded-full text-pink-600 bg-pink-200 last:mr-0 mr-1">
                        <a href="#ENLACE??????">${proyeccion.comienzo}</a>
                      </span>
                      </c:forEach>
                  </div>
                </form>
              </div>
            </div>
            </c:if>


          </div>
        </div>
      </div>
    </div>
  </section>
</main>



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
  <!-- Placed at the end of the document so the pages load faster -->
  <script src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
  <script>window.jQuery || document.write('<script src="js/jquery-slim.min.js"><\/script>')</script>
  <script src="${pageContext.request.contextPath}/resources/js/popper.min.js"></script>
  <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
  <script src="${pageContext.request.contextPath}/resources/js/holder.min.js"></script>

</body>
</html>
