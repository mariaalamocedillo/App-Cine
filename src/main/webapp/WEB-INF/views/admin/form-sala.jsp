<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="utf-8">
  <meta content="width=device-width, initial-scale=1.0" name="viewport">

  <title>Editar película</title>
  <meta content="" name="description">
  <meta content="" name="keywords">

  <!-- Favicons -->
  <link href="${pageContext.request.contextPath}/resources/img/favicon.png" rel="icon">
  <link href="${pageContext.request.contextPath}/resources/img/apple-touch-icon.png" rel="apple-touch-icon">

  <!-- Google Fonts -->
  <link href="https://fonts.gstatic.com" rel="preconnect">
  <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">

  <!-- Vendor CSS Files -->
  <link href="${pageContext.request.contextPath}/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/resources/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">

  <!-- Template Main CSS File -->
  <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
  <style>
    :root {
      --error-color: #dc3545;
      --success-color: #28a745;
      --warning-color: #ffc107;
    }

    input:focus {
      outline: none;
    }

    .error input {
      border-color: var(--error-color);
    }

    .success input {
      border-color: var(--success-color);
    }

    small {
      color: var(--error-color);
    }
  </style>

</head>

<body class="imgfondo">
<jsp:include page="/WEB-INF/layout/navAdmin.jsp" ></jsp:include>

<main id="main" class="main formul" >

  <section class="section">
    <div class="row">
      <div class="col-lg-6 justify-content-between m-auto">
        <div class="card">
          <div class="card-body m-3">
            <h3 class="m-5 text-center">Nueva sala</h3>

            <!-- General Form Elements -->
            <form action="${mvc.basePath}/admin/sala/submit" id="formSala" method="post">
              <c:if test="${not empty sala.id}">
                <input id="id" name="id" type="hidden" value="${sala.id}"/>
              </c:if>

              <div class="col mb-3">
                <label for="nombre" class=" col-form-label">Nombre de la sala</label>
                <input type="text" id="nombre" name="nombre" class="form-control" value="${sala.nombre}" required>
                <small></small>
              </div>

              <div class="row mb-3">
                <div class="col text-center">
                  <button type="submit" class="btn btn-primary">Guardar datos</button>
                </div>
              </div>

            </form>

          </div>
        </div>

      </div>

    </div>
  </section>

</main><!-- End #main -->

<a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>

<!-- Vendor JS Files -->
<script src="${pageContext.request.contextPath}/resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- Template Main JS File -->
<script>
  (function() {
    'use strict';

    window.addEventListener('load', function() {
      // Fetch all the forms we want to apply custom Bootstrap validation styles to
      var forms = document.getElementsByClassName('needs-validation');

      // Loop over them and prevent submission
      var validation = Array.prototype.filter.call(forms, function(form) {
        form.addEventListener('submit', function(event) {
          if (form.checkValidity() === false) {
            event.preventDefault();
            event.stopPropagation();
          }
          form.classList.add('was-validated');
        }, false);
      });
    }, false);
  })();
</script>
<script src="${pageContext.request.contextPath}/resources/js/main.js"></script>

</body>

</html>