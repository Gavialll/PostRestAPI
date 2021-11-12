import {getPostDate, GET, apiAddress, POST} from "./module.js";

let url = apiAddress + "/api/account";

const headers = {
    'Content-Type': 'application/json',
    'Authorization':  localStorage.getItem('token')
}

const headerAuthorization = {
    'Authorization': localStorage.getItem('token')
}
GET(url, headers).then(user => {
    localStorage.setItem('id', user.id);
    printUser(user);
    printPost(user);
});


//  Загрузка фото користувача
//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-/
document.getElementById('input__file').onchange = function (e) {
    const selectedFile = document.getElementById('input__file').files[0];
    const formData = new FormData();
          formData.append('file', selectedFile);

    let urlImg = apiAddress + '/api/account/img';
    fetch(urlImg, {method: 'POST', body: formData, headers: headerAuthorization})
        .then(() => {
        let accountPhoto = document.getElementById("accountPhoto")
            accountPhoto.src = apiAddress + "/api/anonymous/user/" + localStorage.getItem('id') + "/img"
    });
}

//  Update інформації корстувача
//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-/
    let editUser = document.getElementById('editUser');
        editUser.addEventListener('click', function () {
    let firstName = document.getElementById("firstName");
    let lastName = document.getElementById('lastName');
    let email = document.getElementById('email');
    let phone = document.getElementById('number');
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
function printUser(user) {
    let accountPhoto = document.getElementById("accountPhoto");
        accountPhoto.src = apiAddress + "/api/anonymous/user/" + user.id + "/img";
        accountPhoto.style.borderRadius = "50%"
    let firstName = document.getElementById("firstName");
        firstName.value = user.firstName;
    let lastName = document.getElementById('lastName');

    if (user.lastName === null) {
        lastName.value = "";
    }else {
        lastName.value = user.lastName;
    }

    let email = document.getElementById('email');
        email.value = user.login;
    let phone = document.getElementById('number');
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
function printPost(user) {
    let myPost = document.querySelector(".myPost");
    let btnWrapperAddPost = document.createElement("div");
        btnWrapperAddPost.classList.add("submit");
        btnWrapperAddPost.id = "submit";

    let btnAddPost = document.createElement("button");
    btnAddPost.id = "addNewPost";
    btnAddPost.innerText = "Додати нове оголошення";
    btnAddPost.addEventListener("click", ()=>{newPost()})
    btnWrapperAddPost.append(btnAddPost);
    myPost.append(btnWrapperAddPost);
    let allPost = document.createElement('ul');
    allPost.id = "allPost";
    myPost.append(allPost);

    if (user.postList.length === 0) {
        btnWrapperAddPost.style.display = "none";
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
            addPostButton.addEventListener("click", () => newPost())

                div.append(h2, addPostButton)
            li.append(div);
        allPost.append(li);
    }
    else {


        for (const post of user.postList) {

            let click = document.createElement("div");
            click.classList.add("click");
            click.id = post.id;
            click.addEventListener('click', function (event) {
                window.location.href = apiAddress + "/post/" + event.target.id;
            });

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
            editPost.id = post.id;
            editPost.addEventListener("click", ()=>{
                    url = apiAddress + "/api/account/post?id=" + post.id;
                    GET(url, headerAuthorization, {}).then(arrPost => {
                        editPosts(arrPost[0]);
                    })
            })

            let deletePost = document.createElement('button');
            deletePost.innerText = 'Видалити';
            deletePost.id = post.id;
            deletePost.addEventListener("click", function (event) {
                let del = apiAddress + "/api/account/post/" + event.target.id;
                alert("delete");
                POST("DELETE", del, {}, headers)
                    .then(() => {
                        let ul = document.getElementById("allPost");
                        let parent = event.target.parentNode.parentNode;
                        ul.removeChild(parent);
                    });
            });
            wrapperH2.append(category, description, price, date);
            wrapperButton.append(editPost, deletePost);
            div.append(h2, wrapperH2);
            li.append(click, img, div, wrapperButton);
            allPost.append(li);
        }
    }
}

function newPost(){
    let newPost = document.createElement("div");
        newPost.id = "newPost";
        newPost.className = "wrapperAddPost"
    let name = document.createElement("div");
        name.className = "name";
    let postPhotoWrapper = document.createElement("div");
        postPhotoWrapper.className = "accountPhoto";
    let postPhoto = document.createElement("img");
        postPhoto.id = "postPhoto";
        postPhoto.src = "/ViewPost/Image/nav/camera.svg";
    let inputWrapper = document.createElement("div");
        inputWrapper.className = "input__wrapper";
    let form = document.createElement("form");
        form.method = "POST";
        form.enctype = "multipart/form-data";
    let inputFile = document.createElement("input");
        inputFile.type = "file";
        inputFile.name = "file";
        inputFile.id = "input__file__post";
        inputFile.className = "input input__file"
    let label = document.createElement("label");
        label.htmlFor = "input__file__post";
        label.className = "input__file-button";
    let span1 = document.createElement("span");
        span1.className = "input__file-icon-wrapper";
    let imgUpload = document.createElement("img")
        imgUpload.className = "input__file-icon"
        imgUpload.src = "/ViewPost/Image/nav/download-alt.svg";
        imgUpload.alt = "Вибрати Файл";
        imgUpload.width = 25;
    let span2 = document.createElement("span").innerText = "Назва";
    let inputName = document.createElement("input");
        inputName.type = "text";
        inputName.id = "name";

    let span3 = document.createElement("span").innerText = "Опис";
    let inputDescription = document.createElement("input");
        inputDescription.type = "text";
        inputDescription.id = "description";

    let span4 = document.createElement("span").innerText = "Категорія";
    let inputCategory = document.createElement("input");
        inputCategory.type = "text";
        inputCategory.id = "category";

    let span5 = document.createElement("span").innerText = "Ціна";
    let inputPrice = document.createElement("input");
        inputPrice.type = "number";
        inputPrice.id = "price";

    let wrapperSubmit = document.createElement("div")
        wrapperSubmit.className = "submit";

    let btnAdd = document.createElement("button")
        btnAdd.id = "addPost";
        btnAdd.innerText = "Додати";

    let btnCancel = document.createElement("button");
        btnCancel.id = "cancel";
        btnCancel.innerText = "Скасувати";
        btnCancel.addEventListener("click",()=> window.location.reload());

        let myPost = document.querySelector(".myPost");


        span1.append(imgUpload);
                                label.append(span1)
                            form.append(inputFile);
                        inputWrapper.append(form, label);
                    postPhotoWrapper.append(postPhoto, inputWrapper)
                name.append(postPhotoWrapper,
                    span2,
                    inputName,
                    span3,
                    inputDescription,
                    span4,
                    inputCategory,
                    span5,
                    inputPrice,
                    wrapperSubmit)
                wrapperSubmit.append(btnAdd, btnCancel)
            newPost.append(name);
        myPost.append(newPost)

    document.getElementById("allPost").remove();
    document.getElementById("submit").remove();


    // Додавання нової публікації
//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-/
// Після вибору фото змінити src для img
    document.getElementById('input__file__post').onchange = function () {
        const selectedFile = document.getElementById('input__file__post').files[0];
        let postPhoto = document.getElementById("postPhoto")
        let reader = new FileReader();
        reader.onloadend = function() {
            postPhoto.src = reader.result;
        }
        reader.readAsDataURL(selectedFile);
    }

// Кнопка додавання публікації
    let buttonAddPost = document.getElementById("addPost")
    buttonAddPost.addEventListener("click", () => {
        let name = document.getElementById("name");
        let description = document.getElementById("description");
        let category = document.getElementById("category");
        let price = document.getElementById("price");

        let post = {
            category: category.value,
            name: name.value,
            price: price.value,
            description: description.value
        }

        let urlAddPost = apiAddress + "/api/account/post";
        POST("POST", urlAddPost, post, headers)
            .then(post => {
                const selectedFile = document.getElementById('input__file__post').files[0];
                const formData = new FormData();
                formData.append('file', selectedFile);

                let urlImg = apiAddress + "/api/account/post/" + post.id + "/img";
                fetch(urlImg, {method: 'POST', body: formData, headers: headerAuthorization})
                    .then(()=>{
                        window.location.href = apiAddress + "/post/" + post.id;
                    });
            });
    });
}

function editPosts(post){
    let newPost = document.createElement("div");
    newPost.id = "newPost";
    newPost.className = "wrapperAddPost"
    let name = document.createElement("div");
    name.className = "name";
    let postPhotoWrapper = document.createElement("div");
    postPhotoWrapper.className = "accountPhoto";
    let postPhoto = document.createElement("img");
    postPhoto.id = "postPhoto";
    postPhoto.src = apiAddress + "/api/anonymous/post/" + post.id + "/img";
    let inputWrapper = document.createElement("div");
    inputWrapper.className = "input__wrapper";
    let form = document.createElement("form");
    form.method = "POST";
    form.enctype = "multipart/form-data";
    let inputFile = document.createElement("input");
    inputFile.type = "file";
    inputFile.name = "file";
    inputFile.id = "input__file__post";
    inputFile.className = "input input__file"
    let label = document.createElement("label");
    label.htmlFor = "input__file__post";
    label.className = "input__file-button";
    let span1 = document.createElement("span");
    span1.className = "input__file-icon-wrapper";
    let imgUpload = document.createElement("img")
    imgUpload.className = "input__file-icon"
    imgUpload.src = "/ViewPost/Image/nav/download-alt.svg";
    imgUpload.alt = "Вибрати Файл";
    imgUpload.width = 25;
    let span2 = document.createElement("span").innerText = "Назва";
    let inputName = document.createElement("input");
    inputName.type = "text";
    inputName.id = "name";
    inputName.value = post.name;

    let span3 = document.createElement("span").innerText = "Опис";
    let inputDescription = document.createElement("input");
    inputDescription.type = "text";
    inputDescription.id = "description";
    inputDescription.value = post.description;

    let span4 = document.createElement("span").innerText = "Категорія";
    let inputCategory = document.createElement("input");
    inputCategory.type = "text";
    inputCategory.id = "category";
    inputCategory.value = post.category;

    let span5 = document.createElement("span").innerText =  "Ціна";
    let inputPrice = document.createElement("input");
    inputPrice.type = "number";
    inputPrice.id = "price";
    inputPrice.value = post.price;

    let wrapperSubmit = document.createElement("div")
    wrapperSubmit.className = "submit";

    let btnAdd = document.createElement("button")
    btnAdd.id = "addPost";
    btnAdd.innerText = "Змінити";

    let btnCancel = document.createElement("button");
    btnCancel.id = "cancel";
    btnCancel.innerText = "Скасувати";
    btnCancel.addEventListener("click",()=> window.location.reload());

    let myPost = document.querySelector(".myPost");


    span1.append(imgUpload);
    label.append(span1)
    form.append(inputFile);
    inputWrapper.append(form, label);
    postPhotoWrapper.append(postPhoto, inputWrapper)
    name.append(postPhotoWrapper,
        span2,
        inputName,
        span3,
        inputDescription,
        span4,
        inputCategory,
        span5,
        inputPrice,
        wrapperSubmit)
    wrapperSubmit.append(btnAdd, btnCancel)
    newPost.append(name);
    myPost.append(newPost)

    document.getElementById("allPost").remove();
    document.getElementById("submit").remove();


    // Додавання нової публікації
//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-/
// Після вибору фото змінити src для img
    document.getElementById('input__file__post').onchange = function () {
        const selectedFile = document.getElementById('input__file__post').files[0];
        let postPhoto = document.getElementById("postPhoto")
        let reader = new FileReader();
        reader.onloadend = function() {
            postPhoto.src = reader.result;
        }
        reader.readAsDataURL(selectedFile);
    }

// Кнопка додавання публікації
    let buttonAddPost = document.getElementById("addPost")
    buttonAddPost.addEventListener("click", () => {
        let name = document.getElementById("name");
        let description = document.getElementById("description");
        let category = document.getElementById("category");
        let price = document.getElementById("price");

        let postUpdate = {
            category: category.value,
            name: name.value,
            price: price.value,
            description: description.value
        }

        let urlAddPost = apiAddress + "/api/account/post/" + post.id;
        POST("PUT", urlAddPost, postUpdate, headers)
            .then(post => {
                const selectedFile = document.getElementById('input__file__post').files[0];
                const formData = new FormData();
                formData.append('file', selectedFile);

                let urlImg = apiAddress + "/api/account/post/" + post.id + "/img";
                fetch(urlImg, {method: 'POST', body: formData, headers: headerAuthorization})
                    .then(()=>{
                        window.location.href = apiAddress + "/post/" + post.id;
                    });
            });
    });
}
