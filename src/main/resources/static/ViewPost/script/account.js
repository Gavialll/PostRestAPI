import {getPostDate, GET, apiAddress, POST} from "./module.js";
let url = apiAddress + "/api/account";
let id;

//Main
//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-/
const headers = {
    'Content-Type': 'application/json',
    'Authorization' : 'Bearer_' + localStorage.getItem('token')
}
GET(url, headers).then(user => {
    printUser(user);
    printPost(user);
});


// document.addEventListener('click',e => {
//     console.log(e.id);
// });


//  Загрузка фото користувача
//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-/
document.getElementById('input__file').onchange =  function(e) {
    const selectedFile = document.getElementById('input__file').files[0];
    const formData = new FormData();
    formData.append('file', selectedFile);

    const headerAuthorization = {
        'Authorization' : 'Bearer_' + localStorage.getItem('token')
    }
    let urlImg = apiAddress + '/api/account/img';
        fetch(urlImg, {
            method: 'POST',
            body: formData,
            headers: headerAuthorization
        }).then(() => {
            let accountPhoto = document.getElementById("accountPhoto")
                accountPhoto.src = apiAddress + "/api/anonymous/user/" + id + "/img"
        });

}

//  Update інформації корстувача
//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-/
let editUser = document.getElementById('editUser');
editUser.addEventListener('click', function () {
    let firstName = document.getElementById("firstName");
    let lastName = document.getElementById('lastName');
    let email = document.getElementById('email');
    let phone =  document.getElementById('number');
    let city = document.getElementById('city');

    let user = {
        firstName: firstName.value,
        lastName: lastName.value,
        login: email.value,
        city: city.value,
        number: phone.value
    }

    let updateUser = apiAddress + '/api/account';
    POST('PUT', updateUser, user, headers).then(() => {
        location.reload();
    });
});

//Підставлення інформації про користувача
//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-/
function printUser(user){
    let accountPhoto = document.getElementById("accountPhoto");
        accountPhoto.src = apiAddress + "/api/anonymous/user/" + user.id + "/img";
        accountPhoto.style.borderRadius = "50%"
        id = user.id;
    let firstName = document.getElementById("firstName");
        firstName.value = user.firstName;
    let lastName = document.getElementById('lastName');
    if(user.lastName === null){
        lastName.value = "";
    }
    else{
        lastName.value = user.lastName;
    }
    let email = document.getElementById('email');
        email.value = user.login;
    let phone =  document.getElementById('number');
        phone.value = user.number;
    let city = document.getElementById('city');
        city.value = user.city;
    let exit = document.getElementById('exit');
    exit.addEventListener("click", function () {
        localStorage.removeItem('token');
        location.reload();
    });
}

//Вивід всіх оголошень
//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-/
function printPost(user){
    if(user.postList.length === 0) {
        let allPost = document.getElementById('allPost');

        let li = document.createElement('li');
        let div = document.createElement('div');
        div.classList.add('text');
        div.style.display = "flex";
        div.style.flexDirection = 'column';
        div.style.width = "100%";
        div.style.height = "max-content";
        div.style.justifyContent = "center";
        div.style.alignItems = "center";

        let h2 = document.createElement('h2');
        h2.innerText = 'У вас ще немає оголошень';
        h2.style.marginBottom = "15px";

        let addPostButton = document.createElement('button');
        addPostButton.innerText = "Додати нове оголошення";


        div.append(h2, addPostButton)
        li.append(div);
        allPost.append(li);
    }

    document.addEventListener('click', function(e) {
        console.log(e.target.id);
    });

    for (const post of user.postList) {

    let allPost = document.getElementById('allPost');

    let img = document.createElement('img');
        img.src = apiAddress + "/api/anonymous/post/" + post.id + "/img";

    let li = document.createElement('li');
        li.id = post.id;

    let div = document.createElement('div');
        div.classList.add('text');

    let wrapperButton = document.createElement('div');
        wrapperButton.classList.add('column')

    let h2 = document.createElement('h2');
        h2.innerText = post.name;

    let wrapperH2 = document.createElement('div');
        wrapperH2.classList.add('wrapperInfoPost')

    let category = document.createElement('h3');
        let urlCategory = apiAddress + "/api/anonymous/category/" + post.category;
        GET(urlCategory).then(categoryName => category.innerText = categoryName.name);

    let description = document.createElement('h3');
        description.innerText = post.description;

    let price = document.createElement('h3');
        price.innerText = post.price;

    let date = document.createElement('h5');
        date.innerText = getPostDate(post.date);

    let editPost = document.createElement('button');
        editPost.innerText = "Редагувати";

    let deletePost = document.createElement('button');
        deletePost.innerText = 'Видалити';

                wrapperH2.append(category, description, price, date);
                wrapperButton.append(editPost, deletePost);
            div.append(h2, wrapperH2);
        li.append(img, div, wrapperButton);
    allPost.append(li);
    }
}

