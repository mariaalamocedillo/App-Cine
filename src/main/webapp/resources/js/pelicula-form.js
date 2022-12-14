const tituloEl = document.querySelector('#titulo');
const directorEl = document.querySelector('#director');
const estudioEl = document.querySelector('#estudio');
const posterEl = document.querySelector('#poster');
const duracionEl = document.querySelector('#duracion');
const sipnosisEl = document.querySelector('#descripcion');

const form = document.querySelector('#formPelicula');


const checkTitle = () => {

    let valid = false;

    const min = 3,
        max = 50;

    const titulo = tituloEl.value.trim();

    if (!isRequired(titulo)) {
        showError(tituloEl, 'Debe introducir el título.');
    } else if (!isBetween(titulo.length, min, max)) {
        showError(tituloEl, `El título debe contener entre ${min} y ${max} caracteres.`)
    } else {
        showSuccess(tituloEl);
        valid = true;
    }
    return valid;
};

const checkDirector = () => {

    let valid = false;

    const min = 3,
        max = 50;

    const director = directorEl.value.trim();

    if (!isRequired(director)) {
        showError(directorEl, 'Debe introducir el director.');
    } else if (!isBetween(director.length, min, max)) {
        showError(directorEl, `El director debe contener entre ${min} y ${max} caracteres.`)
    } else {
        showSuccess(directorEl);
        valid = true;
    }
    return valid;
};

const checkStudio = () => {
        let valid = false;

        const min = 3,
            max = 50;

        const estudio = estudioEl.value.trim();

        if (!isRequired(estudio)) {
            showError(estudioEl, 'Debe introducir el estudio.');
        } else if (!isBetween(estudio.length, min, max)) {
            showError(estudioEl, `El estudio debe contener entre ${min} y ${max} caracteres.`)
        } else {
            showSuccess(estudioEl);
            valid = true;
        }
        return valid;
};

const checkSipnosis = () => {
    let valid = false;

    const min = 3,
        max = 1000;

    const sipnosis = sipnosisEl.value.trim();

    if (!isRequired(sipnosis)) {
        showError(sipnosisEl, 'Debe introducir la sipnosis.');
    } else if (!isBetween(sipnosis.length, min, max)) {
        showError(sipnosisEl, `La sipnosis debe contener entre ${min} y ${max} caracteres.`)
    } else {
        showSuccess(sipnosisEl);
        valid = true;
    }
    return valid;
};

const checkPoster = () => {

    let valid = false;

    const poster = posterEl.value.trim();

    if (!isValidUrl(poster)) {
        showError(posterEl, 'Debe introducir un url válido.');
    } else {
        showSuccess(posterEl);
        valid = true;
    }
    return valid;
};

const checkDuracion = () => {

    let valid = false;

    const duracion = duracionEl.value.trim();

    if (!isRequired(duracion)) {
        showError(duracionEl, 'Debe introducir un valor.');
    } else {
        showSuccess(duracionEl);
        valid = true;
    }
    return valid;
};


form.addEventListener('submit', function (e) {
    // prevent the form from submitting
    e.preventDefault();

    // validate fields
    let isTitleValid = checkTitle(),
        isDescipcionValid = checkSipnosis(),
        isStudioValid = checkStudio(),
        isPosterValid = checkPoster(),
        isDirectorValid = checkDirector(),
        isDuracionValid = checkDuracion();

    let isFormValid = isTitleValid &&
        isDirectorValid &&
        isDuracionValid &&
        isStudioValid &&
        isDescipcionValid &&
        isPosterValid;

    // submit to the server if the form is valid
    if (isFormValid) {
        form.submit();
    }
});

const isValidUrl = urlString=> {
    try {
        return Boolean(new URL(urlString));
    }
    catch(e){
        return false;
    }
}

const debounce = (fn, delay = 500) => {
    let timeoutId;
    return (...args) => {
        // cancel the previous timer
        if (timeoutId) {
            clearTimeout(timeoutId);
        }
        // setup a new timer
        timeoutId = setTimeout(() => {
            fn.apply(null, args)
        }, delay);
    };
};

form.addEventListener('input', debounce(function (e) {
    switch (e.target.id) {
        case 'titulo':
            checkTitle();
            break;
        case 'director':
            checkDirector();
            break;
        case 'estudio':
            checkStudio();
            break;
        case 'descripcion':
            checkSipnosis();
            break;
        case 'poster':
            checkPoster();
            break;
        case 'duracion':
            checkDuracion();
            break;
    }
}));