var ws;
var oldScore = 0;

function connect() {
	//var username = document.getElementById("username").value;
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		  if (this.readyState == 4 && this.status == 200) {
			  	var username = this.responseText;
			  	console.log("ajax username: " + username);
			  	var host = document.location.host;
			  	var pathname = document.location.pathname;
			   	ws = new WebSocket("ws://" + host  + "/fastdailymocks/" + "/game/" + username);
			    console.log("Server Socket Address - ws://" +host  + pathname + "/game/" + username);
			    ws.onmessage = function(event) {
			        console.log(event.data);
			        var message = JSON.parse(event.data);
			        executeCommand(message);
			    };	
			    ws.onopen = function() {
			    	sendPin();
			    }
		  }
	};
	xhttp.open("POST", "getUsername", false);
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhttp.send("testData=5");
}

function sendPin() {
    var json = JSON.stringify({
        "command":"checkPin",
        "value": document.getElementById("pinInputField").value
    });
    ws.send(json);
}

function submitNickname() {
	var nickname = document.getElementById("nickname").value;
	if (nickname.length == 0) {
		displayNicknameSmallSizeError();
	} else if (nickname.length > 10) {
		displayNicknameBigSizeError();
	}
	else {
	    var json = JSON.stringify({
	        "command":"checkNickname",
	        "value": document.getElementById("nickname").value
	    });
	    ws.send(json);	
	}
}

function executeCommand(gameCommand) {
	console.log("executing game command: " + gameCommand.command);
	if (gameCommand.command == "notFound") {
		clearPin();
	} else if (gameCommand.command == "foundGame") {
		displayLoadingScreen();
	} else if (gameCommand.command =="validPin") {
		console.log("display nickname screen");
		displayNicknameScreen();
	} else if (gameCommand.command == "displayQuestion") {
		console.log("displayQuestion...");
		displayQuestion(gameCommand.value);
	} else if (gameCommand.command == "displaySingleResult") {
		displaySingleResult(gameCommand.value);
	} else if (gameCommand.command == "finishedGame") {
		displayFinalResult(gameCommand.value);
	} else if (gameCommand.command == "cancelGame") {
		displayCancellation();
	} else if (gameCommand.command == "takenNickname") {
		displayNicknameError();
	} else if (gameCommand.command == "kicked") {
		kicked();
	} else if (gameCommand.command == "invalidPin") {
		invalidPin();
	} else if (gameCommand.command == "pinInProgress") {
		pinInProgress();
	}
}

function pinInProgress() {
	var errorContainer = document.getElementById("nickname-error");
	errorContainer.innerHTML = "The game you're trying to join is in progress!";
}

function invalidPin() {
	var errorContainer = document.getElementById("nickname-error");
	errorContainer.innerHTML = "The entered pin is invalid!";
}

function kicked() {
	alert("You've been kicked!");
	window.location.href = "game";
}

function displayNicknameError() {
	var errorContainer = document.getElementById("nickname-error");
	errorContainer.innerHTML = "That nickname is already in use!";
}

function displayNicknameBigSizeError() {
	var errorContainer = document.getElementById("nickname-error");
	errorContainer.innerHTML = "That nickname is too long!";	
}

function displayNicknameSmallSizeError() {
	var errorContainer = document.getElementById("nickname-error");
	errorContainer.innerHTML = "Please enter a nickname";	
}

function displayCancellation() {
	var container = document.getElementById("container");
	text = "<div>The Game Host Disconnected...</div>";
	text += "<a href='game'>Home</a>";
	container.innerHTML = text;	
}

function displayFinalResult(player) {
	console.log("display final result");
	console.log(player);
	
	if (player.place <= 3) {
		displayPlaced(player);
	} else {
		displayNotPlaced(player);
	}
}

function getTotalCorrectAnswers(player) {
	var counter = 0;
	for (var i = 0; i < player.answers.length; i++) {
		if (player.answers[i].isCorrect) {
			counter++;
		}
	}
	return counter;
}

function returnToPin() {
	window.location.href = "game";
}

function displayPlaced(player) {
	var container = document.getElementById("container");
	var text = "";
	var place = "";
	if (player.place == 1) {
		place = "1st";
	} else if (player.place == 2) {
		place = "2nd";
	} else if (player.place == 3) {
		place = "3rd";
	}
	text += "<div class='trainee_placed_bg'>";
	text += "<div id='align_buttons'>";
	text += "<button type='button' class='btn btn-success btn-lg' id='next_button' onclick='returnToPin()'>PLAY AGAIN</button>";
	text += "</div>";
	text += "<div id='correct_text'>";
	text += "<i class='fas fa-medal fa-10x'></i>";
	text += "<h1 style='font-size: 66px;'>" + place + " PLACE</h1>";
	text += "<div id='points_div'>";
	text += "<h2 style='font-size: 55px;'>" + getTotalCorrectAnswers(player) + "/" + player.answers.length + "<br> QUESTIONS CORRECT</h2>";
	text += "</div>";
	text += "</div>";

	text += "<div id='footer'>";
	text += "<p id='name'>" + player.nickname + "</p>";
	text += "<p id='total_score'>" + player.score + "</p>";
	text += "</div></div>";
	
	container.innerHTML = text;
}

function getSuffix(place) {
	if (place == 1) {
		return "st";
	} else if (place == 2) {
		return "nd";
	} else if (place == 3) {
		return "rd";
	} else {
		return "th";
	}	
}
function displayNotPlaced(player) {
	var container = document.getElementById("container");
	var text = "";
	var suffix = getSuffix(player.place);

	text += "<div class='trainee_not_placed_bg'>";
	text += "<div id='align_buttons'>";
	text += "<button type='button' class='btn btn-success btn-lg' id='next_button' onclick='returnToPin()'>PLAY AGAIN</button>";
	text += "</div>";
	text += "<div id='correct_text'>";
	text += "<i class='fas fa-sad-tear fa-10x'></i>";
	text += "<h1 style='font-size: 66px;'>" + player.place + suffix + " PLACE</h1>";
	text += "<div id='points_div'>";
	text += "<h2 style='font-size: 55px;'>" + getTotalCorrectAnswers(player) + "/" + player.answers.length + "<br> QUESTIONS CORRECT</h2>";
	text += "</div>";
	text += "</div>";

	text += "<div id='footer'>";
	text += "<p id='name'>" + player.nickname + "</p>";
	text += "<p id='total_score' >" + player.score + "</p>";
	text += "</div></div>";
	
	container.innerHTML = text;
}

function displaySingleResult(player) {
	console.log(player);
	if (player.answers[player.answers.length - 1].isCorrect) {
		displayCorrectScreen(player);
	} else {
		displayIncorrectScreen(player);
	}
}

function displayCorrectScreen(player) {
	var container = document.getElementById("container");
	var text = "";
	var suffix = getSuffix(player.place);
	var pointsGained = player.score - oldScore;
	oldScore = player.score;
	text += "<div class='trainee_correct_bg'>";
	text += "<div id='correct_text'>";
	text += "<i class='fas fa-check fa-8x'></i>";
	text += "<h1 style='font-size: 66px;'>GOOD JOB</h1>";
	text += "<div id='points_div'>";
	text += "<h2 style='font-size: 55px;'>+" + pointsGained + "</h2>";
	text += "</div>";
	text += "<h3>You're in " + player.place + suffix + " place, <br> " + player.answerComment + "</h3>";
	text += "</div>";
	
	text += "<div id='footer'>";
	text += "<p id='name'>" + player.nickname + "</p>";
	text += "<p id='total_score' >" + player.score + "</p>";
	text += "</div></div>";
	
	container.innerHTML = text;
}

function displayIncorrectScreen(player) {
	var container = document.getElementById("container");
	var text = "";
	var suffix = getSuffix(player.place);
	text += "<div class='trainee_incorrect_bg'>";
	text +=  "<div id='correct_text'>";
	text += "<i class='fas fa-times fa-8x'></i>"
	text += "<h1 style='font-size: 66px;'>INCORRECT</h1>";
	text += "<div id='points_div'>";
	text += "<h2 style='font-size: 55px;'>+0</h2>";
	text += "</div>";
	text += "<h3>You're in " + player.place + suffix + " place, <br> " + player.answerComment + "</h3>";
	text += "</div>";

	text += "<div id='footer'>";
	text += "<p id='name'>" + player.nickname + "</p>";
	text += "<p id='total_score' >" + player.score + "</p>";
	text += "</div></div>";
	
	container.innerHTML = text;
}

function displayQuestion(question) {
	var container = document.getElementById("container");
	var text = "";
	console.log(question);
	text += "<div class='answer_button_div'>";
	text += "<svg viewBox='0 0 100 100' preserveAspectRatio='none'>";
	text += "stroke='black' stroke-width='20'";
	if (question.choices.length == 2) {
		text += "<a style='display:block' onclick='submitAnswer(0);' class='block' id ='blocktop'>";
		text += "<path id='blockA' d='M 0 0 L 0 100 L 100 0 z' stroke='white' stroke-width='1'></path>";
		text += "</a>";	
		text += "<a style='display:block' onclick='submitAnswer(1);' class='block' id='blockbottom'>";
		text += "<path style='fill:#36cb2f' id='blockB' d='M 100 100 L 0 100 L 100 0 z' stroke='white' stroke-width='1'></path>";
		text += "</a>";	
	} 
	else if (question.choices.length == 3) {
		text += "<a style='display:block' onclick='submitAnswer(0);' class='block' id='blocktop'>";
		text += "<path id='blockA' d='M 0 0 L 50 50 L 100 0 z' stroke='white' stroke-width='1'></path>";
		text += "</a>";
		text += "<a style='display:block' onclick='submitAnswer(1);' class='block' id ='blockright'>";
		text += "<path id='blockB' d='M 50 50 L 0 33000 L 0 0 z' stroke='white' stroke-width='1'></path>";
		text += "</a>";	
		text += "<a style='display:block' onclick='submitAnswer(2);' class='block' id ='blockleft'>";
		text += "<path id='blockC' d='M 50 50 L 100 0 L 100 33000 z' stroke='white' stroke-width='1'></path>";
		text += "</a>";	
	}
	else if (question.choices.length == 4){
		text += "<a style='display:block' onclick='submitAnswer(0);' class='block' id='blocktop'>";
		text += "<path id='blockA' d='M 0 0 L 50 50 L 100 0 z' stroke='white' stroke-width='1'></path>";
		text += "</a>";
		text += "<a style='display:block' onclick='submitAnswer(1);' class='block' id ='blockleft'>";
		text += "<path id='blockB' d='M 50 50 L 0 100 L 0 0 z' stroke='white' stroke-width='1'></path>";
		text += "</a>";	
		text += "<a style='display:block' onclick='submitAnswer(3);' class='block' id ='blockbottom'>";
		text += "<path id='blockC' d='M 100 100 L 50 50 L 0 100 z' stroke='white' stroke-width='1'></path>";
		text += "</a>";	
		text += "<a style='display:block' onclick='submitAnswer(2);' class='block' id ='blockright'>";
		text += "<path id='blockD' d='M 50 50 L 100 0 L 100 100 z' stroke='white' stroke-width='1'></path>";
		text += "</a>";	
	}
	text += "</svg></div>";
	container.innerHTML = text;	
}

function submitAnswer(answer) {
	console.log("PLAYER SELECTED THIS ANSWER: " + answer);
    var json = JSON.stringify({
        "command":"submitAnswer",
        "value": answer.toString()
    });
    
    ws.send(json);
    displayWaiting();
}

function displayWaiting() {
	var container = document.getElementById("container");
	var text = "";
	text += "<div class='changing_background'>";
	text += "<div class='loader'></div>";
	text += "</div>";
	container.innerHTML = text;
}

function displayLoadingScreen() {
	var container = document.getElementById("container");
	var text = "<div class='changing_background'>";
	text += "<div id='loading_div'>";
	text += "<h1 style='color:white;' id='loading_text'> LOADING GAME</h1>";
	text += "<div class='spinner-border text-light'></div>";
	text += "</div></div>";
	container.innerHTML = text;
	//container.innerHTML = "<div>Please wait for the host to start the game...</div>";
}

function displayNicknameScreen() {
	var container = document.getElementById("container");
	var text = "<div class='changing_background'>";
	text += "<div class='box'>";
	text += "<h1 style='line-height:66px;'>NICKNAME</h1>";
	text += "<span id='nickname-error'></span>";
	text += "<input type='text' placeholder='NICKNAME' id='nickname'>";
	text += "<button type='button' value='JOIN' id='enter_pin' value='Submit' onclick='submitNickname();'>JOIN</button>";
	text += "</div></div>";
	container.innerHTML = text;
}

