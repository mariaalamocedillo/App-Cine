const container = document.querySelector('.container');
const seats = document.querySelectorAll('.row .seat:not(.occupied)');
const count = document.getElementById('count');
const total = document.getElementById('total');
const movieSelect = document.getElementById('movie');
const idsSitio = document.getElementById('ids');
const totalForm = document.getElementById('totalForm');
var ids = []

//populateUI();

let ticketPrice = +movieSelect.value;

// Save selected movie index and price
function setMovieData(movieIndex, moviePrice) {
  localStorage.setItem('selectedMovieIndex', movieIndex);
  localStorage.setItem('selectedMoviePrice', moviePrice);
}

// Update total and count
function updateSelectedCount() {
  const selectedSeats = document.querySelectorAll('.row.rowSeats .seat.selected');

  const seatsIndex = [...selectedSeats].map(seat => [...seats].indexOf(seat));

  //localStorage.setItem('selectedSeats', JSON.stringify(seatsIndex));
  //localStorage.setItem('ids', JSON.stringify(ids))
  
  const selectedSeatsCount = selectedSeats.length;

  count.innerText = selectedSeatsCount;
  total.innerText = selectedSeatsCount * ticketPrice;
  totalForm.value = selectedSeatsCount * ticketPrice;
  console.log('hola')
    cargaInfo();
  setMovieData(movieSelect.selectedIndex, movieSelect.value);
}

// Get data from localstorage and populate UI
function populateUI() {
  const selectedSeats = JSON.parse(localStorage.getItem('selectedSeats'));

  if (selectedSeats !== null && selectedSeats.length > 0) {
    seats.forEach((seat, index) => {
      if (selectedSeats.indexOf(index) > -1) {
        seat.classList.add('selected');
      }
    });
  }

  const selectedMovieIndex = localStorage.getItem('selectedMovieIndex');
  const ids = localStorage.getItem('ids');

  if (selectedMovieIndex !== null) {
    movieSelect.selectedIndex = selectedMovieIndex;
  }
}

// Movie select event
movieSelect.addEventListener('change', e => {
  ticketPrice = +e.target.value;
  setMovieData(e.target.selectedIndex, e.target.value);
  updateSelectedCount();
});

// Seat click event
container.addEventListener('click', e => {
  //Dentro del contenedor; si no está ocupado y es un sitio;
    if (e.target.classList.contains('seat') && !e.target.classList.contains('occupied')) {
        //si ya está seleccionado, lo elimina de los ids (si la clase existe, toggle la elimina y devuelve false); si no lo añade 
        var targetId = e.target.getAttribute('id')
        if (!e.target.classList.toggle('selected')){
            ids = remove(ids, targetId)
        } else{
            ids.push(targetId)
        }            
        idsSitio.value = ids;
        updateSelectedCount();
    }
});

function remove(array, id){
    for( var i = 0; i < array.length; i++){ 
        if ( array[i] === id) { 
            array.splice(i, 1); 
            i--; 
        }
    }
    return array;
}
// Initial count and total set
updateSelectedCount();

function cargaInfo(){
    let asientos = document.querySelectorAll(".seat");
    let ocupados = document.getElementById("ocupados").value.replace('[', '').replace(']', '');
    console.log(ocupados)
    console.log(ocupados.split(','))
    let array = ocupados.split(',');
    asientos.forEach(seat => {
        array.forEach(sitioLlega => {
            if (seat.getAttribute('id') === sitioLlega.trim()) {
                seat.classList.add("occupied")
            }
        });
    });
}