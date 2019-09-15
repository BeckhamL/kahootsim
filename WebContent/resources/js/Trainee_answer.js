var ws;

function connect() {
	var nickname = document.getElementById("nickname").value;
    var host = document.location.host;
    var pathname = document.location.pathname;
    ws = new WebSocket("ws://" +host  + "/WebSockets/" + "/game/" + nickname);
    console.log("Server Socket Address - ws://" +host  + pathname + "/game/" + nickname);
    ws.onmessage = function(event) {
        console.log(event.data);
        var message = JSON.parse(event.data);
        executeCommand(message);
    };	
    ws.onopen = function() {
    	sendPin();
    }
}

function executeCommand(gameCommand) {
	console.log("executing game command: " + gameCommand.command);
	if (gameCommand.command == "notFound") {
		clearPin();
	} else if (gameCommand.command == "foundGame") {
		displayLoadingScreen();
	} else if (gameCommand.command == "displayQuestion") {
		console.log("displayQuestion...");
		displayQuestion(gameCommand.value);
	} else if (gameCommand.command == "displaySingleResult") {
		displaySingleResult(gameCommand.value);
	} else if (gameCommand.command == "finishedGame") {
		displayFinalResult(gameCommand.value);
	}
}

function displayFinalResult(player) {
	console.log("display final result");
	console.log(player);
	var container = document.getElementById("container");
	text = "<div>Game Over</div>";
	text += "<a href='player'>Home</a>";
	container.innerHTML = text;
}

function displaySingleResult(player) {
	var container = document.getElementById("container");
	console.log(player);
	var text = "";
	if (player.answers[player.answers.length - 1].isCorrect) {
		text += "<div style='color:green;'>CORRECT!</div>";
	} else {
		text += "<div style='color:red;'>INCORRECT!</div>";
	}
	container.innerHTML = text;
}

function displayQuestion(question) {
	var container = document.getElementById("container");
	var text = "";
	text = text + "<h1>" + question.question + "</h1>";
	text = text + "<form id='questionForm'>";
	for (i = 0; i < question.choices.length; i++) {
		text = text + "<input type='radio' name='radioAnswer' value='" + i + "'>" + question.choices[i].answer + "<br>";
	}
	text = text + "<button type='button' onclick='submitAnswer();'>Submit Answer</button>";
	text = text + "</form>";
	container.innerHTML = text;	
}

function submitAnswer() {
	var radioOptions = document.getElementsByTagName("input");
	
	for (i = 0; i < radioOptions.length; i++) {
		if (radioOptions[i].checked) {
		    var json = JSON.stringify({
		        "command":"submitAnswer",
		        "value": radioOptions[i].value
		    });
		    console.log("PLAYER SELECTED THIS ANSWER: " + radioOptions[i].value);
		    ws.send(json);
		    displayWaiting();
		    break;
		}
	}
}

function displayWaiting() {
	var container = document.getElementById("container");
	container.innerHTML = "<div>Please wait...</div>";
}

function displayLoadingScreen() {
	var container = document.getElementById("container");
	container.innerHTML = "<div>Please wait for the host to start the game...</div>";
}

function sendPin() {
    var json = JSON.stringify({
        "command":"checkPin",
        "value": document.getElementById("pinInputField").value
    });
    ws.send(json);
}