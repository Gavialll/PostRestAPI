
// Test
///////////////////////////////////////////////////////////////////////////////////////////


//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-/


//Main
//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-/
import {apiAddress, getPostDate, GET} from "./module.js";

// localStorage.removeItem('token');

let url = apiAddress + "/api/anonymous/post";

GET(url).then(data => addToPage(data));

// Генеруємо елементи на сторінку
function addToPage(elements) {
    elements.forEach(element => {
        let allPost = document.querySelector('.allPost');

        let post = document.createElement('div');
            post.classList.add('post');

        let hoverMenu = document.createElement('div');
            hoverMenu.classList.add('hoverMenu');

        let postImg = document.createElement('img');
            postImg.classList.add('post_img');
            postImg.src = urlGetImg(element.id);

        let like = document.createElement('div');
            like.classList.add('like');

        let likeImg = document.createElement("img");
            likeImg.src = '/ViewPost/Image/nav/wishlist-icon.svg'

        let postInfo = document.createElement('div');
            postInfo.classList.add('postInfo');

        let name = document.createElement('h2');
        let city = document.createElement('h2');
        let date = document.createElement('h2');
        let price = document.createElement('h2');

            name.innerText = element.name;
            city.innerText = 'Львів';
            price.innerText = element.price + "\u00A0грн";
            date.innerText = getPostDate(element.date);

        let action = document.createElement('div');
            action.classList.add('action');

        let wrapper = document.createElement('div');
            wrapper.classList.add('wrapperAutor');

        let wrapper1 = document.createElement('div');
            wrapper1.classList.add('wrapperAutor');

        let wrapper2 = document.createElement('div');
            wrapper2.classList.add('wrapperAutor');

        let autor = document.createElement('h2');
            autor.innerText = "Автор:\u00A0";

        let autor1 = document.createElement('h2');
            GET(urlGetUserById(element.userId))
                .then(user => autor1.innerText = user.firstName)

        let category = document.createElement('h2');
            category.innerText = 'Категорія:\u00A0';

        let category1 = document.createElement('h2');
            GET(urlGetCategory(element.category))
                .then(category => category1.innerText = category.name);

        let size = document.createElement('h2');
            size.innerText = 'Перегляди:\u00A0';

        let size1 = document.createElement('h2');
            size1.innerText = element.price;

        let wrapperButton = document.createElement('div');
            wrapperButton.classList.add('wrapperButton');

        let call = document.createElement('button');
            call.innerText = 'Позвонити';

        let send = document.createElement('button');
            send.innerText = 'Написати';

                        wrapperButton.append(send, call);
                        wrapper.append(autor, autor1);
                        wrapper1.append(category, category1);
                        wrapper2.append(size, size1);
                    action.append(wrapper, wrapper1, wrapper2, wrapperButton);
                    postInfo.append(name, city, date, price)
                    like.append(likeImg);
                hoverMenu.append(postImg, like, postInfo, action);
            post.append(hoverMenu);
        allPost.append(post);
    });
}
//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-/


//Function
//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-/
function urlGetImg(id) {
    return apiAddress + '/api/anonymous/post/' + id + '/img'
}
function urlGetUserById(id) {
    return apiAddress + '/api/anonymous/user/' + id;
}
function urlGetCategory(id) {
    return apiAddress + '/api/anonymous/category/' + id;
}
//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-/