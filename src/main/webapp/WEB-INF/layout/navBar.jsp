<%--
  Created by IntelliJ IDEA.
  User: maria
  Date: 14/02/2022
  Time: 19:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--NAVBAR-->
<nav class="top-0 absolute z-50 w-full flex flex-wrap items-center justify-between px-2 py-3 bg-gray-700">
    <div class="container px-4 mx-auto flex flex-wrap items-center justify-between">
        <div class="w-full relative flex justify-between lg:w-auto lg:static lg:block lg:justify-start">
            <a  class="text-sm font-bold leading-relaxed inline-block mr-4 py-2 whitespace-nowrap uppercase text-white"  href="#ENLACE">Cines Petri</a>
            <button  class="cursor-pointer text-xl leading-none px-3 py-1 border border-solid border-transparent rounded bg-transparent block lg:hidden outline-none focus:outline-none"  type="button"  onclick="toggleNavbar('example-collapse-navbar')">
                <i class="text-white fas fa-bars"></i>
            </button>
        </div>
        <div  class="lg:flex flex-grow items-center bg-white bg-transparent lg:shadow-none hidden"  id="example-collapse-navbar">
            <ul class="flex flex-col lg:flex-row list-none mr-auto">
            </ul>
            <ul class="flex flex-col lg:flex-row list-none lg:ml-auto">
                <li class="flex items-center">
                    <button class="text-pink-600 bg-pink-300 border border-solid border-transparent hover:bg-pink-500 hover:text-white active:bg-pink-600 font-bold text-md px-4 py-2 rounded outline-none focus:outline-none mr-1 mb-1 ease-linear transition-all duration-150" type="button" onclick="openDropdown(event,'dropdown-id')">
                        Mi cuenta
                    </button>
                    <div class="hidden bg-white  text-base z-50 float-left py-2 list-none text-left rounded shadow-lg mt-1" style="min-width:12rem" id="dropdown-id">
                        <a href="#ENLACE" class="text-sm py-2 px-4 font-normal block w-full whitespace-nowrap bg-transparent text-blueGray-700">
                            Sign in
                        </a>
                        <a href="#ENLACE" class="text-sm py-2 px-4 font-normal block w-full whitespace-nowrap bg-transparent text-blueGray-700">
                            Sign up
                        </a>
                        <div class="h-0 my-2 border border-solid border-t-0 border-blueGray-800 opacity-25"></div>
                        <a href="#ENLACE" class="text-sm py-2 px-4 font-normal block w-full whitespace-nowrap bg-transparent text-blueGray-700">
                            Proyecciones
                        </a>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</nav>
<!--FIN NAVBAR-->