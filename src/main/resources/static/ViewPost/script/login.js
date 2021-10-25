import {apiAddress, POST} from "./module.js";


let url = apiAddress + '/login';
let button = document.getElementById('submit');

    button.addEventListener("click", function () {
        let login = document.getElementById('login').value;
        let password = document.getElementById('password').value;
        let body = {
            login: login,
            password: password
        };
        POST('POST', url, body).then(token => {
            if (token === undefined) {
             console.log('validation data');
            }
            else {
                localStorage.setItem('token', token.token);
                window.location.href = '/account';
            }
        })
    });