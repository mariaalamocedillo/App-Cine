<%--
  Created by IntelliJ IDEA.
  User: maria
  Date: 14/02/2022
  Time: 19:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--NAVBAR-->
<header id="header" class="header d-flex align-items-center mb-4 mt-0">

    <div class="d-flex align-items-center justify-content-between">
        <a href="${mvc.basePath}/pelicula" class="logo d-flex align-items-center">
            <img src="${pageContext.request.contextPath}/resources/assets/img/logo.png" alt="">
            <span>Cines Petri</span>
        </a>
    </div><!-- End Logo -->

    <nav class="header-nav ms-auto">
        <ul class="d-flex align-items-center">


            <li class="nav-item ">
                <a class="nav-link nav-icon search-bar-toggle " href="#">
                    <i class="bi bi-film"></i> Películas
                </a>
            </li>


            <li class="nav-item dropdown pe-3">

                <a class="nav-link nav-profile d-flex align-items-center pe-0" href="#" data-bs-toggle="dropdown">
                    <img src="${pageContext.request.contextPath}/resources/assets/img/icon.jpg" alt="Profile" class="rounded-circle">
                    <span class="d-none d-md-block dropdown-toggle ps-2">Cuenta</span>
                </a><!-- End Profile Iamge Icon -->

                <ul class="dropdown-menu dropdown-menu-end dropdown-menu-arrow profile">
                    <li class="dropdown-header">
                        <h6>Anónimo</h6>
                    </li>
                    <li>
                        <hr class="dropdown-divider">
                    </li>

                    <li>
                        <a class="dropdown-item d-flex align-items-center" href="${mvc.basePath}/usuario/perfil">
                            <i class="bi bi-person"></i>
                            <span>Mi perfil</span>
                        </a>
                    </li>
                    <li>
                        <hr class="dropdown-divider">
                    </li>

                    <li>
                        <hr class="dropdown-divider">
                    </li>

                    <li>
                        <hr class="dropdown-divider">
                    </li>

                    <li>
                        <a class="dropdown-item d-flex align-items-center" href="${mvc.basePath}/usuario">
                            <i class="bi bi-box-arrow-right"></i>
                            <span>Sign in</span>
                        </a>
                    </li>

                    <li>
                        <a class="dropdown-item d-flex align-items-center" href="${mvc.basePath}/usuario/registro">
                            <i class="bi bi-box-arrow-right"></i>
                            <span>Sign up</span>
                        </a>
                    </li>

                </ul><!-- End Profile Dropdown Items -->
            </li><!-- End Profile Nav -->

        </ul>
    </nav><!-- End Icons Navigation -->

</header><!-- End Header -->
