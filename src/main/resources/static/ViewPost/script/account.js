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


// Перехід на сторінку публікації
//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-/
document.addEventListener('click', function(event) {
    let target = event.target;
    console.log(target.id);

    if((target.tagName === "DIV") && (target.id !== "")) {
        window.location.href = apiAddress + "/post/" + target.id;
    }else if((target.tagName === "BUTTON") && (target.innerText === "Видалити")) {
        let del = apiAddress + "/api/account/post/" + target.id;
        alert("delete");
        POST("DELETE", del, {}, headers).then(()=>{
            GET(url, headers).then(user => {
                let ul = document.getElementById("allPost");
                while (ul.firstChild) {
                    ul.removeChild(ul.firstChild);
                }
                printPost(user);
            });
        });
    }
});


//  Загрузка фото Публікації
//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-/
let buttonAddPost = document.getElementById("addPost")
buttonAddPost.addEventListener("click", ()=>{
console.log("start add post")
let name = document.getElementById("name");
let description = document.getElementById("description");
let category = document.getElementById("category");
let price= document.getElementById("price");

let post = {
    category : category.value,
    name : name.value,
    price : price.value,
    description : description.value
}

console.log("start http post")
let urlAddPost = apiAddress + "/api/account/post";
POST("POST", urlAddPost, post, headers)

    console.log("complete")
});

    // const selectedFile = document.getElementById('input__file__post').files[0];
    // const formData = new FormData();
    // formData.append('file', selectedFile);
    //
    // const headerAuthorization = {
    //     'Authorization' : 'Bearer_' + localStorage.getItem('token')
    // }
    // let urlImg = apiAddress + '/api/account/post/img';
    // fetch(urlImg, {
    //     method: 'POST',
    //     body: formData,
    //     headers: headerAuthorization
    // }).then(() => {
    //     let accountPhoto = document.getElementById("accountPhoto")
    //     accountPhoto.src = apiAddress + "/api/anonymous/user/" + id + "/img"
    // });

//  Загрузка фото користувача
//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-/

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

    for (const post of user.postList) {

    let allPost = document.getElementById('allPost');

    let click = document.createElement("div");
        click.classList.add("click");
        click.id = post.id;

    let img = document.createElement('img');
        img.src = apiAddress + "/api/anonymous/post/" + post.id + "/img";

    let li = document.createElement('li');

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
        deletePost.id = post.id;

                wrapperH2.append(category, description, price, date);
                wrapperButton.append(editPost, deletePost);
            div.append(h2, wrapperH2);
        li.append(click, img, div, wrapperButton);
    allPost.append(li);
    }
}

// document.addEventListener("click", function(event) {
//         let target = event.target;
//         let del = apiAddress + "/post/" + target.id;
//         alert("delete");
//     POST("DELETE", del, headers).then(() => {
//         // GET(url, headers).then(user => {
//         //     printPost(user);
//         // });
//     });
// })

