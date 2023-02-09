# Cines Petri
Esta aplicación web sirve para reservar entradas en proyecciones de un cine desde la comodidad de sus hogares. El usuario puede ver la cartelera actual y la información de las proyecciones disponibles, seleccionar la película y el horario deseados, y ver un diagrama de la sala para seleccionar los asientos deseados. 
Tras esto, el usuario puede revisar un resumen de la compra y realizar el pago con tarjeta. La aplicación también permite a los usuarios ver un historial de sus reservas y canjear reservas activas a través de una página con un QR que almacena la información de la reserva para su verificación en la entrada del cine. Este proyecto utiliza la arquitectura Modelo Vista Controlador.

## Interfaz de administrador
### Vista de Menú
La vista de menú es la página principal de la parte de administrador. Aquí se mostrarán los enlaces a las diferentes vistas, permitiendo al administrador acceder fácilmente a la información y funcionalidades necesarias.
### Vista de Películas
Esta vista contiene una tabla de datos que muestra información detallada sobre las películas disponibles. El administrador tendrá la posibilidad de agregar, eliminar o editar esta información.
### Vista de Proyecciones
Esta vista contiene una tabla de datos que muestra información detallada sobre las proyecciones. El administrador tendrá la posibilidad de agregar, eliminar o editar esta información.
### Vista de Salas
Esta vista contiene un formulario para crear una nueva sala. Solo se solicitará el nombre de la sala, ya que los asientos se asignarán automáticamente.
### Vista de Horarios
Esta vista contiene una tabla que muestra la ocupación de las salas por las proyecciones asignadas para el día seleccionado. El administrador tendrá la posibilidad de, gracias al uso de AJAX, seleccionar una nueva película en los huecos libres por sala y hora, o editar las ya establecidas. 

## Interfaz de usuario cliente

### Vista de login
Esta vista permite al usuario iniciar sesión en la plataforma. Se requiere un correo electrónico y una contraseña para acceder a la cuenta del usuario.
Además, la vista de login está enlazada con la página de registro para facilitar el proceso al usuario.
### Vista de registro
Esta vista permite al usuario registrarse en la plataforma. Se requiere un nombre, teléfono, correo electrónico y contraseña para crear una cuenta. Al igual que la vista de login, esta vista está enlazada con la vista de login para facilitar el proceso.
### Vista de dashboard del usuario
Esta vista muestra el historial de las reservas del usuario y le permite canjear las activas. Con dicho canjeo se genera una página con un código QR que almacena la información de la reserva para su comprobación en la puerta del cine.
### Vista de detalle de película
Esta vista muestra la información de la película; poster, titulo, director, duracion, sipnosis... Además se muestran los dias en los que será proyectada, junto con los enlaces a las horas disponibles para acceder a la reserva.
### Vista de películas
Esta vista muestra tarjetas con la información de todas las películas que ha proyectado el cine.
### Vista de cartelera
Esta vista muestra la cartelera según el día con tarjetas que muestran de manera simplificada la información de la película y las horas disponibles. Se muestran la shoras disponibles en badges, donde al hacer clic se envía al usuario a la vista de reserva.
### Vista de reserva
La primera vista muestra la información de la proyección (película, hora y día). La segunda vista es el diagrama de la sala, para la selección de los asientos. La tercera vista es el resumen de la compra con el precio total y la posibilidad de aplicar los descuentos pertinentes, junto con el pago con tarjeta.
