class Carga {
    constructor(array) {
        this.done = false;
        this.array = array;
    }
    cargaInfo(){
            let asientos = document.querySelectorAll(".seat");
            asientos.forEach(seat => {
                this.array.forEach(sitioLlega => {
                    if (seat.getAttribute('id') === sitioLlega) {
                        seat.classList.add("occupied")
                    }
                });
            });
        this.done = true;
    }
}