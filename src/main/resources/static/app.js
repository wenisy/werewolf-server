var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connectAsJudge() {
    var socket = new SockJS('/werewolf-connect');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/user/queue/judge', function (greeting) {
            showGreeting(greeting.body);
        });
    });
}

function connect() {
    var socket = new SockJS('/werewolf-connect');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/user/queue/players', function (greeting) {
            showGreeting(greeting.body);
        });
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function joinRoom() {
    stompClient.send("/app/join", {}, JSON.stringify({
        'seatNum': "123",
        'roomNum': $("#room-no").val()
    }));
}

function createRoom() {
    stompClient.send("/app/create", {}, JSON.stringify({
        'wolf': 3,
        'villager': 3,
        'witch': 1,
        'prophet': 1,
        'hunter': 1,
        'hasSheriff': true
    }));
}

function readyToGame() {
    var seatId = 1;
    stompClient.send("/app/players", {}, JSON.stringify({
        'gameId': $("#room-no").val(),
        'isReady': true
    }));
}

function showGreeting(message) {
    $("#greetings").append('<tr><td><audio controls="" autoplay="" name="media"><source src=' + encodeURI(message) +' type="audio/mp3">' + '</audio></td></tr>');
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect-as-judge" ).click(function() { connectAsJudge(); });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#create" ).click(function() { createRoom(); });
    $( "#join" ).click(function() { joinRoom(); });
    $( "#ready" ).click(function() { readyToGame(); });
});

