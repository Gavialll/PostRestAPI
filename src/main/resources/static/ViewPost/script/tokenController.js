import {apiAddress} from "./module.js";

if(localStorage.getItem('token') === null) {
    console.log(localStorage.getItem('token'));
    window.location.href = apiAddress + '/authorization';
}