import {getPostDate, GET, apiAddress, POST} from "./module.js";

//Підставлення інформації про публікацію
//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-/
let name = document.getElementById("name");
let price = document.getElementById("price");
let category = document.getElementById("category");
let date = document.getElementById("date");
let description = document.getElementById("description");

//Витягую id з URL
let idPost = window.location.pathname.slice(6);

GET(getPostById(idPost)).then(post => {
    name.innerText = post.name;
    price.innerText = post.price + "грн";
    GET(getCategoryName(post.category))
        .then(c => category.innerText = c.name);
    date.innerText = getPostDate(post.date);
    description.innerText = post.description;
})


//Вивід характеристик публікації
//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-/
let viewCharacteristics = document.getElementById("characteristics");
viewCharacteristics.addEventListener("click", () => {

    let comment = document.getElementById("comment");
    comment.style.borderBottom = "0px solid #245462";
    let characteristics = document.getElementById("characteristics");
    characteristics.style.borderBottom = "3px solid #245462";

    let wrapperComments = document.getElementById("wrapperComments");
    wrapperComments.remove();

    //тут має бути вивід характеристикк
});


//Кнопка для виводу коментарів
//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-/
let viewComment = document.getElementById("comment");
viewComment.addEventListener("click", () => {
printComment();

});

//Вивід коментарів
//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-/
function printComment() {
    let comment = document.getElementById("comment");
    comment.style.borderBottom = "3px solid #245462";
    let characteristics = document.getElementById("characteristics");
    characteristics.style.borderBottom = "0px solid #245462";

    let remove = document.getElementById("wrapperComments");
    if (remove !== null) {
        remove.remove();
    }

    let div = document.querySelector('.wrapperCommentsCharacteristics');
    let wrapperComments = document.createElement("div");
    wrapperComments.id = "wrapperComments";
    wrapperComments.classList.add("wrapperComments");
    let comments = document.createElement("div");
    comments.classList.add("comments");

    wrapperComments.append(comments);
    div.append(wrapperComments)


    //Витягую коментарі
    GET(getComments(idPost)).then(text => {
        text.forEach(message => {
            let comment = document.createElement("div");
            comment.classList.add("comment");

            let divComment = document.createElement("div");

            let img = document.createElement("img");
            img.src = getUserImgById(message.senderId)
            GET(getUserById(message.senderId)).then(user => {
                let h4 = document.createElement("h2");
                h4.innerText = user.firstName;
                let p = document.createElement("p");
                p.innerText = message.message;

                divComment.append(h4, p);
            });

            comment.append(img, divComment);
            comments.append(comment);
        });
    });


    //Генерую поле вводу коментаря і кнопку
    let wrapperFormComment = document.createElement("div");
    wrapperFormComment.classList.add("wrapperFormComment");

    let textArea = document.createElement("textarea");
    textArea.id = "commentText";
    textArea.placeholder = "Введіть коментар";

    let send = document.createElement("button");
    send.id = "send";
    send.innerText = "Надіслати";

    wrapperFormComment.append(textArea, send);
    comments.before(wrapperFormComment);

    //Кнопка відправлення повідомлення
    let sends = document.getElementById("send");
    sends.addEventListener('click', () => {

        let message = document.getElementById("commentText").value;

        const headers = {
            'Authorization': 'Bearer_' + localStorage.getItem('token')
        }
        fetch(addCommentToPost(idPost), {method: "POST", body: message, headers: headers})
            .then(() => {
                let wrapperComments = document.getElementById("wrapperComments");
                wrapperComments.remove();
                printComment();
            });
    });
}

function getPostById(id) {
    return apiAddress + "/api/anonymous/post/" + id;
}
function addCommentToPost(postId){
    return apiAddress + "/api/account/post/" + postId + "/comment";
}
function getCategoryName(id){
    return apiAddress + "/api/anonymous/category/" + id;
}
function getComments(postId){
    return apiAddress + "/api/anonymous/post/" + postId + "/comment";
}
function getUserById(userId){
    return apiAddress + "/api/anonymous/user/" + userId;
}
function getUserImgById(userId){
    return apiAddress + "/api/anonymous/user/" + userId + "/img";
}



