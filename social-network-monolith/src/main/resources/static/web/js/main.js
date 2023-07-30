var stompClient = null;

function connect() {
    var token = $("#token").val();
    var author = $("#authorLogin").val();
    console.log(`Connecting to feed of author: ${author}`);
    var socket = new SockJS(`/ws?access_token=${token}`);
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        $("#socket-content").attr("hidden", null);
        stompClient.subscribe(`/exchange/post/${author}`, function (msg) {
            let messageJson = JSON.parse(msg.body);
            appendMessage(messageJson);
        });
    });
}

function appendMessage(message) {
    $("#feed").append(`<tr><td>${message.id}</td><td>${message.text}</tr>`);
}

function login() {
    let login = $("#login").val();
    let pass = $("#password").val();

    var xhr = new XMLHttpRequest();

    var json = JSON.stringify({
        "id": login,
        "password": pass
    });

    xhr.open("POST", '/login', true)
    xhr.setRequestHeader('Content-type', 'application/json');

    xhr.onreadystatechange = function () {
        if (this.readyState != 4) return;
        processLoginresponse(this.responseText);
    }

    // Отсылаем объект в формате JSON и с Content-Type application/json
    // Сервер должен уметь такой Content-Type принимать и раскодировать
    xhr.send(json);
}

function processLoginresponse(response) {
    if (response != null && response != "") {
        let token = JSON.parse(response).token;
        $("#token").val(token);
        $("#subscribe-content").attr("hidden", null);
        $("#auth-content").attr("hidden", "true");
    }
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#submitLogin").click(function () { login(); });
    $("#connect").click(function () { connect(); });

});