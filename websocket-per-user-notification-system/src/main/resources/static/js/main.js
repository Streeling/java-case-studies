function connect(destination, headerName, token, e) {
  var socket = new SockJS('http://localhost:8080/gs-guide-websocket');
  var stompClient = Stomp.over(socket);

  var headers = {};
  headers[headerName] = token;

  stompClient.connect(headers, function() {
    var url = stompClient.ws._transport.url;
    sessionId = url.replace("ws://localhost:8080/gs-guide-websocket/", "")
        .replace("/websocket", "")
        .replace(/^[0-9]+\//, "");
    console.log("Your current session is: " + sessionId);

    stompClient.subscribe(destination + sessionId, function(response) {
      e.text("Status: " + JSON.parse(response.body).text);
    });
    console.info('connected!');
  });
}
