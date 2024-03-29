<%--
  Created by IntelliJ IDEA.
  User: maria
  Date: 23/02/2022
  Time: 20:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--NAVBAR-->
<header id="header" class="header d-flex align-items-center mb-4">

    <div class="d-flex align-items-center justify-content-between">
        <a href="${mvc.basePath}/cartelera" class="logo d-flex align-items-center">
            <img src="${pageContext.request.contextPath}/resources/img/logo.png" alt="">
            <span class="fontPraise">Cines Petri</span>
        </a>
    </div><!-- End Logo -->

    <nav class="header-nav ms-auto">
        <ul class="d-flex align-items-center">


            <li class="nav-item ">
                <a class="nav-link nav-icon search-bar-toggle " href="${mvc.basePath}/cartelera">
                    Cartelera
                </a>
            </li>

            <li class="nav-item ">
                <a class="nav-link nav-icon search-bar-toggle " href="${mvc.basePath}/admin">
                    Menú Admin
                </a>
            </li>


            <li class="nav-item dropdown pe-3">

                <a class="nav-link nav-profile d-flex align-items-center pe-0" href="#" data-bs-toggle="dropdown">
                    <span class="d-none d-md-block dropdown-toggle ps-2">Navegar</span>
                </a><!-- End Profile Iamge Icon -->

                <ul class="dropdown-menu dropdown-menu-end dropdown-menu-arrow profile">
                    <li class="dropdown-header">
                        <h6>Admin</h6>
                    </li>
                    <li>
                        <hr class="dropdown-divider">
                    </li>


                    <li>
                        <a class="dropdown-item d-flex align-items-center" href="${mvc.basePath}/admin/proyeccion">
                            <i class="bi bi-box-arrow-right"></i>
                            <span>Proyecciones</span>
                        </a>
                    </li>

                    <li>
                        <a class="dropdown-item d-flex align-items-center" href="${mvc.basePath}/admin/peliculas">
                            <i class="bi bi-box-arrow-right"></i>
                            <span>Películas</span>
                        </a>
                    </li>

                    <li>
                        <a class="dropdown-item d-flex align-items-center" href="${mvc.basePath}/admin/horarios/">
                            <i class="bi bi-box-arrow-right"></i>
                            <span>Programación proyecciones</span>
                        </a>
                    </li>

                    <li>
                        <a class="dropdown-item d-flex align-items-center" href="${mvc.basePath}/admin/pelicula/nueva">
                            <i class="bi bi-box-arrow-right"></i>
                            <span>Crear película</span>
                        </a>
                    </li>

                    <li>
                        <a class="dropdown-item d-flex align-items-center" href="${mvc.basePath}/admin/proyeccion/nueva/0">
                            <i class="bi bi-box-arrow-right"></i>
                            <span>Crear proyeccion</span>
                        </a>
                    </li>

                </ul><!-- End Profile Dropdown Items -->
            </li><!-- End Profile Nav -->

        </ul>
    </nav><!-- End Icons Navigation -->

</header><!-- End Header -->
