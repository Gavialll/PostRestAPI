import {apiAddress} from "./module.js";

if(localStorage.getItem('token') === null) {
    window.location.href = apiAddress + '/authorization';
}