export const apiAddress = "http://192.168.1.102:8080"

// Форматування та вивід дати
//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-/
export function getPostDate(date) {
    let month = ['Січня', 'Лютого', 'Березня', 'Квітня', 'Травня', 'Червня', 'Липня', 'Серпня', 'Вересня', 'Жовтня', 'Листопада', 'Грудня'];

    let postDate = new Date(date);
    let today = new Date();

    if (postDate.getDate() === today.getDate()) {
        return "Сьогодні,\u00A0" + postDate.toLocaleTimeString().slice(0, -3);
    }
    if (postDate.getDate() === (today.getDate() - 1)) {
        return "Вчора,\u00A0" + postDate.toLocaleTimeString().slice(0, -3);
    }
    if (postDate.getDate() < (today.getDate() - 1)) {
        return postDate.getDate() + "\u00A0" + month[postDate.getMonth()] + ",\u00A0" + postDate.toLocaleTimeString().slice(0, -3);
    }
}


// GET запит на сервер
//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-/
export function GET(url,
                    headers = {
                        'Content-Type': 'application/json'
                    }) {
    return fetch(url, {
        method: "GET",
        headers: headers
    }).then(response => {
        if (response.ok) {
            return response.json()
        }
        if (response.status === 403) {
            window.location.href = apiAddress + '/authorization';
        }
    })
}

// POST Запит на сервер
//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-/
export function POST(method,
                     url,
                     body,
                     headers = {
                         'Content-Type': 'application/json'
                     }) {
    return fetch(url, {
        method: method,
        body: JSON.stringify(body),
        headers: headers
    }).then(response => {
        if (response.ok) {
            return response.json()
        }
        if (response.status === 403) {
            window.location.href = apiAddress + '/authorization';
        }
    })
}

//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-/


//Hover через JS
//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-/
// let object = document.getElementById('para')
// object.addEventListener("mouseover", function(event) {
//     object.style.backgroundColor = "red"
// });
// object.addEventListener("mouseout", function(event) {
//     object.style.backgroundColor = "black"
// });
//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-/


// Локація
// $.get("http://ipinfo.io", function(response) {
//     console.log(response.city, response.country);
// }, "jsonp");