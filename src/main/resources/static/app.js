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
        stompClient.subscribe('/user/queue/players', function (greeting) {
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
        'seatNum': $("#seat-no").val(),
        'roomNum': $("#room-no").val()
    }));
}

function createRoom() {
    stompClient.send("/app/create", {}, JSON.stringify({
        'wolf': 1,
        'villager': 1,
        'witch': 0,
        'prophet': 1,
        'hunter': 0,
        'hasSheriff': true
    }));
}

function readyToGame() {
    stompClient.send("/app/players", {}, JSON.stringify({
        'seatNum': $("#seat-no").val(),
        'roomNum': $("#room-no").val(),
        'isReady': true
    }));
}

function showGreeting(message) {
    $("#greetings").append('<tr><td><audio controls="" autoplay="" name="media"><source src=' + encodeURI(message) +' type="audio/mp3">' + '</audio></td></tr>');
}

function killPeople() {
    stompClient.send("/app/play", {}, JSON.stringify({
        'seatNum': $("#seat-no").val(),
        'roomNum': $("#room-no").val(),
        'action': 'kill',
        'target': $("#kill-no").val()
    }));
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
    $( "#kill" ).click(function() { killPeople(); });
});

