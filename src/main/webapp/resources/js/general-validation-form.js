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



const tituloEl = document.querySelector('#titulo');
const directorEl = document.querySelector('#director');
const estudioEl = document.querySelector('#estudio');
const posterEl = document.querySelector('#poster');
const duracionEl = document.querySelector('#duracion');
const sipnosisEl = document.querySelector('#descripcion');

const form = document.querySelector('#formPelicula');


const checkUsername = () => {

    let valid = false;

    const min = 3,
        max = 50;

    const username = usernameEl.value.trim();

    if (!isRequired(username)) {
        showError(usernameEl, 'Debe introducir su nombre.');
    } else if (!isValidUrl(posterEl)) {
        showError(usernameEl, `El nombre debe contener al menos ${min} caracteres.`)
    } else {
        showSuccess(usernameEl);
        valid = true;
    }
    return valid;
};

const checkEmail = () => {
    let valid = false;
    const email = emailEl.value.trim();
    if (!isRequired(email)) {
        showError(emailEl, 'Debe introducir su email.');
    } else if (!isEmailValid(email)) {
        showError(emailEl, 'Email no válido.')
    } else {
        showSuccess(emailEl);
        valid = true;
    }
    return valid;
};

const checkPoster = () => {

    let valid = false;

    const length = 9;

    const phone = telephoneEl.value.trim();

    if (!isRequired(phone)) {
        showError(telephoneEl, 'Debe introducir su teléfono.');
    } else if (phone.length !== length) {
        showError(telephoneEl, `El teléfono debe tener ${length} caracteres.`)
    } else {
        showSuccess(telephoneEl);
        valid = true;
    }
    return valid;
};


const checkPassword = () => {
    let valid = false;


    const password = passwordEl.value.trim();

    if (!isRequired(password)) {
        showError(passwordEl, 'Debe introducir una contraseña.');
    } else if (!isPasswordSecure(password)) {
        showError(passwordEl, 'La contraseña debe tener mínimo 8 caracteres, incluyendo una mayúscula, 1 número y un caracter especial (!@#$%^&*)');
    } else {
        showSuccess(passwordEl);
        valid = true;
    }

    return valid;
};

const checkConfirmPassword = () => {
    let valid = false;
    // check confirm password
    const confirmPassword = confirmPasswordEl.value.trim();
    const password = passwordEl.value.trim();

    if (!isRequired(confirmPassword)) {
        showError(confirmPasswordEl, 'Introduzca la contraseña de nuevo');
    } else if (password !== confirmPassword) {
        showError(confirmPasswordEl, 'Las contraseñas no coinciden');
    } else {
        showSuccess(confirmPasswordEl);
        valid = true;
    }

    return valid;
};

const isEmailValid = (email) => {
    const re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
};

const isPasswordSecure = (password) => {
    const re = new RegExp("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%\^&\*])(?=.{8,})");
    return re.test(password);
};

const isRequired = value => value === '' ? false : true;
const isBetween = (length, min, max) => length < min || length > max ? false : true;


const showError = (input, message) => {
    // get the form-field element
    const formField = input.parentElement;
    // add the error class
    formField.classList.remove('success');
    formField.classList.add('error');

    // show the error message
    const error = formField.querySelector('small');
    error.textContent = message;
};

const showSuccess = (input) => {
    // get the form-field element
    const formField = input.parentElement;

    // remove the error class
    formField.classList.remove('error');
    formField.classList.add('success');

    // hide the error message
    const error = formField.querySelector('small');
    error.textContent = '';
}


form.addEventListener('submit', function (e) {
    // prevent the form from submitting
    e.preventDefault();

    // validate fields
    let isUsernameValid = checkUsername(),
        isEmailValid = checkEmail(),
        isPhoneValid = checkPhone(),
        isPasswordValid = checkPassword(),
        isConfirmPasswordValid = checkConfirmPassword();

    let isFormValid = isUsernameValid &&
        isEmailValid &&
        isPhoneValid &&
        isPasswordValid &&
        isConfirmPasswordValid;

    // submit to the server if the form is valid
    if (isFormValid) {
        console.log("Validoooooooooooooooooooooooooooooooooo")
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
    console.log("jadfnjad")
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
            checkUsername();
            break;
        case 'director':
            checkEmail();
            break;
        case 'estudio':
            checkPhone();
            break;
        case 'contrasena':
            checkPassword();
            break;
        case 'conf-contrasena':
            checkConfirmPassword();
            break;
    }
}));