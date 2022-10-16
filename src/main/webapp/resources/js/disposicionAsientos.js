var seleccionados = [];

function genera_tabla(filas, columnas) {
  // Obtener la referencia del elemento body
  var contenedor = document.getElementById("contenedor");
  
  if(document.getElementById('miTabla') != undefined){
    contenedor.removeChild(document.getElementById('miTabla'));
    document.getElementById('listado').value = '';
    seleccionados = [];
  }

  var abecedario = ['a','b','c','d','e','f','g','h','i','j','k','l', 'ñ','m','n','o','p','q','r','s','t','u','v','w','x','y','z'];

  // Crea un elemento <table> y un elemento <tbody>
  var tabla   = document.createElement("table");
  tabla.setAttribute('id', 'miTabla');
  var tblBody = document.createElement("tbody");

  // Crea las celdas
  for (var i = 0; i < filas; i++) {
    // Crea las hileras de la tabla
    var hilera = document.createElement("tr");

    for (var j = 0; j < columnas; j++) {
      // Crea un elemento <td> y un nodo de texto, haz que el nodo de
      // texto sea el contenido de <td>, ubica el elemento <td> al final
      // de la hilera de la tabla
      var celda = document.createElement("td");
      //var textoCelda = document.createTextNode(abecedario[i]+(j+1));
      celda.setAttribute('id', abecedario[j]+(i+1));
      //celda.appendChild(textoCelda);
      hilera.appendChild(celda);
    }

    // agrega la hilera al final de la tabla (al final del elemento tblbody)
    tblBody.appendChild(hilera);
  }

  // posiciona el <tbody> debajo del elemento <table>
  tabla.appendChild(tblBody);
  tabla.classList.add('table');
  tabla.classList.add('table-bordered');
  // appends <table>
  contenedor.appendChild(tabla);
    actualizaTabla();
}


function remove(array, id){
    for( var i = 0; i < array.length; i++){ 
        if ( array[i] === id) { 
            array.splice(i, 1); 
            i--; 
        }
    }
    return array;
}


function actualizaTabla() {
  document.querySelectorAll('#miTabla td').forEach(e => e.addEventListener(("click"), function() {

    if(!e.classList.toggle('selected')){
      seleccionados = remove(seleccionados, e.getAttribute('id'))
    }else{
      seleccionados.push(e.getAttribute('id'));
    }

    document.getElementById('listado').value = seleccionados.sort();

  }));
}