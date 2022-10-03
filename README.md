# Cines Petri
El objetivo de esta aplicación web es ofrecer un servicio a los clientes del cine que quieren realizar reservas de manera online.

## Interfaz de administrador
Para el administrador de la página web se han creado cuatro vistas que le permitirá configurar las películas y proyecciones del cine. No se han añadido funciones para las salas y asientos del cine, puesto que son datos fijos qeu no cambian (se creó nada más un método para cargar los datos de los asientos, puesto que se hicieron salas con la misma distribución y asientos por comodidad). En futuras versiones se podrá implementar una vista con diversos datos que resuman los beneficios y reservas del cine. El header del administrador tendrá un botón que dirige a la lista de películas que ve el usuario y un desplegable para navegar entre las páginas explicadas a continuación.
### [Películas](http://localhost:8080/cinema/mvc/pelicula/admin)
En esta vista podemos ver una tabla con todos los datos necesarios para el administrados (no mostramos, por ejemplo, el poster o la sipnosis, pero sí el número de proyecciones que tiene activas la película). El tipo de tablas usadas (datatables) permite ordenar por columnas, paginar y buscar cualquier valor de cualquier campo. En dos columnas se añaden botones para realizar tres funciones;
  
- Borrar la película > se elimina directamente
- [Editar la película](http://localhost:8080/cinema/mvc/pelicula/admin/editar/3) > redirige al formulario para editar la película; mostrará los datos que tenía la película originalmente para editarlos
- Crear proyección > redirige al formulario de la proyecciones (explicado más adelante)

En los datos de cada película se ha añadido una columna de valor boolean llamada `enProyeccion`; esto nos sirve para editar fácilmente el estado de dicha película; si está en proyección, podremos crear nuevas proyecciones, pero si no lo está, ese botón estará deshabilitado. En un cine sirve para dejar de proyectar una película que aún tenía proyecciones establecidas, pero que por alguna razón no se puede seguir proyectando, o para preparar las proyecciones de una película que aún no se quiere mostrar. 

Al final de la página podemos ver un botón para crear una [nueva película](http://localhost:8080/cinema/mvc/pelicula/admin/nueva), el cual dirige al mismo formulario que el de editarlo, pero en este caso estará vacío

Respecto al formulario, este contendrá todos los campos que hay que rellenar, excepto el id, con un checkbox para marcar si está en proyección o no
### [Proyecciones](http://localhost:8080/cinema/mvc/admin/proyeccion)
Esta vista es muy similar a la de las películas; se trata de una datatable con los datos de la proyección (dia, hora y sala), junto con dos botones;
- Borrar la proyeccion > se elimina directamente
- Editar la proyeccion > redirige al formulario para editar la proyeccion; muestra los datos que tenía, pero sin poder cambiar la película que tenía asignada

También encontramos un botón para añadir una proyección. Este redirige al formulario, donde en el campo de película se muestra un desplegable con el listado de las películas que se encuentran en proyección.    

Respecto al formulario, dependiendo de dónde hayas clicado para acceder a él, el campo de películas se comportará diferente; si vienes desde 'Crear proyección' de la vista de las películas o desde 'Editar proyección' de la vista de proyecciones, el título no se podrá cambiar, pues ya se ha elegido la película al acceder de esta forma. Si se accede desde 'Nueva proyección', se muestra el desplegable explicado anteriormente.  
- [Nueva proyección (vista proyeciones)](http://localhost:8080/cinema/mvc/admin/proyeccion/nueva/0)
- [Crear proyección (vista películas)](http://localhost:8080/cinema/mvc/admin/proyeccion/nueva/6)
- [Editar proyección](http://localhost:8080/cinema/mvc/admin/proyeccion/editar/4)

Todos los enlaces utilizan el método GET para llegar al controlador con el id de la película o proyección a tratar
***
## Interfaz de Usuario
Esta parte de la aplicación está orientada a los usuarios que quieren ver las películas que proyecta o ha proyectado el cine en cuestión. Respecto al header de la página, tiene un botón para cceder al inicio y otro desplegable con funciones de login o sign up (si no está logeado), o acceso al perfil y logout (si está logeado)

### [Inicio](http://localhost:8080/cinema/mvc/pelicula)
La página de inicio muestra un listado de tarjetas con los póster de las películas, el título y la dureción. Además, muestran un botón con el que se puede aceder a su información. En el caso de que la película se encuentre en proyección, se indica que puede ver los horarios, si no se muetra que no está siendo proyectada, pero se puede acceder a su información

Al principio de la página se muestran dos botones que nos permitirá seleccionar si queremos ver solo las que tienen [proyecciones activa](http://localhost:8080/cinema/mvc/pelicula/proyectando) o todas las películas (el propósito de ver estas películas es que el usuario pueda descubrir más películas).

### [Detalle de película](http://localhost:8080/cinema/mvc/pelicula/5)
Esta página muestra el póster de la película a la izquierda y los datos de la misma en una tarjeta a la derecha. En caso de encontrarse en proyección, mostrará un elemento de acordeón en el que se selecciona el día para ver unas etiquetas que muestran las horas de proyección de ese mismo día. El enlace de estas etiquetas derige a la reserva de la película, con el día del grupo en el que se encuentra y la hora de comienzo de la etiqueta (usa un GET con el id de la proyección).

### [Login](http://localhost:8080/cinema/mvc/usuario), [registro](http://localhost:8080/cinema/mvc/usuario/registro) y [perfil](http://localhost:8080/cinema/mvc/usuario/perfil)

Las dos primeras páginas son las estándar, donde se solicitan cuatro datos para el registro (incluido aceptar los términos y condiciones), y dos para logearse (el email y contraseña). Al completar los formularios, en caso de estar todo correcto, se crea una sesión y se redirige a la página del perfil.

En el perfil se muestra el nombre y email del usuario en la izquierda, justo con el icono estándar, y a la derecha un listado con sus reservas. En el caso de las reservas activas, se muestran las que cumplen con la fecha. Se muestran los datos relativos a la reserva (dia y hora, sala y asientos), además de un enlace (por si quiere consultar la información de la película) y un botón para canjear la reserva. El canjeo de la reserva lleva a una página con un QR donde están registrados los datos que necesita saber el acomodador del cine (para verificar que la reserva es correcta; para ello se añadieron un par de dependencias al `pom.xml` y una clase para crear la imagen, la cual se almacena en el target), junto con los datos que necesita saber el cliente para verificar la sala y los asientos.
También tenemos otra pestaña de reservas antiguas, donde se muestran reservas que ya se pasaron de fecha
Para ver un perfil con reservas, accede con el siguiente: 

    - usuario: jorge@gorka.com
    - contraseña: soyjorge

### Reserva
Para realizar una reserva, debe estar logeado. En caso de no estarlo, se redirige a la página de login, la cual le llevará de vuelta a la reserva de dicha proyección. A lo largo de la reserva se utiliza una sesión para manejar los datos que hay que ir cambiando.
#### [Paso 1 - Confirma proyección y login (si no está logeado)](http://localhost:8080/cinema/mvc/reserva/pelicula/3)
Tras seleccionar la hora de 'detalle película', se muetran la película, la hora y el dia de la proyección que se ha seleccionado. Esto se muestran en dos input desabilitados, simplemente sirven para que el usuario se asegure de que efectivamente es la proyección que quiere ver. Tras aceptar la información, el controlador creará una reserva con los datos de la reserva del cliente, estableciendola como activa, pero no pagada ni reservada.
#### [Paso 2 - Reserva asientos](http://localhost:8080/cinema/mvc/reserva/paso2) (tras completar el 1, se redirige automáticamente)
En esta vista se muestra la distribución de la sala, con los asientos que ya han sido reservados como deshabilitados. El usuario podrá seleccionar entre los asientos libres todos los que quiera, pero mínimo uno. Debajo se incluye una frase que utiliza javascript para actualizar dinámicamente el número de asientos seleccionados y el coste de la reserva. Tras este paso, se actualizan los datos de la reserva como reservada, pero no pagada, y se procede a añadir los asientos como reservados
#### [Paso 3 - Pago](http://localhost:8080/cinema/mvc/reserva/paso2) (tras completar el 2, se redirige automáticamente)
Se procede al pago de la reserva; se muestra un resumen de las entradas compradas (los asientos y el precio individual y total). A la izquierda se muestran los datos para el pago con tarjeta (de momento solo acepta pago con tarjeta). Se validará el número y CVC de la tarjeta. Para pagar correctamente se debe introducir uno de estos números (son ejemplos, el método que lo comprueba acepta solo números válidos, no aleatorios):

    - num tarjeta: 12345678903555 o 012850003580200
    - cvc: número con tres o cuatro dígitos
Tras el pago, se actualiza la reserva como pagada y se dirige al perfil del usuario.
***
## REST API
Respecto al servicio rest, se puede consultar los datos de una película, junto con sus proyecciones accediendo a [este enlace](http://localhost:8080/cinema/mvc/api/pelicula/3). Para mostrar los datos de las proyecciones (relacion many-to-many) se necesitaron las anotaciones de `@JsonManagedReference` en el listado de proyecciones de la entidad película y la anotación de `@JsonBackReference` en el id de la película en la entidad proyección. También se usó la anotación de `@JsonRawValue` en la hora y dia de la proyección amostrar, puesto que el uso de variables de tipo fecha y hora entraba en conflicto al transformar en JSON; con esta anotación simplemente la escribe 'a pelo' (sin comillas). También se puso un `@JsonIgnore` en la entidad de sala, en la lista de asientos, puesto que no es relevante a la hora de mostrar la información de una proyección de la película (todas las salas tienen los mismos; solo nos interesa el nombre e id, incluso sobraría el nombre).

#Problemas encontrados
- En la página de detalle de película, con ciertas películas al abrir el desplegable de acordeón, el póster y la tarjeta se mueve de forma extraña.
- No conseguí crear una forma segura de acceder a la parte de administración; la única opción rápida sería una tabla de usuarios administradores, pero no lo ví necesario dada la embergadura del proyecto

