var ws;
//The amount of users who answered the question
var userCounter = 0;
var answerData;
var correctAnswers;
var currentPlayers;
var displayPodium = false; //Flag for when to start the interval.

var questionTimer = 20; //Initialize the timer for the questions.
var displayingQuestion = false;
var quizTitle = "";
var finalReport;
	
var xhttp = new XMLHttpRequest();
xhttp.onreadystatechange = function() {
	  if (this.readyState == 4 && this.status == 200) {
		    var text = JSON.parse(this.responseText);
		    console.log(text);
		  	var pin = text["pin"];
		  	var userName = text["name"];
		  	var quiz = text["quiz"];
		  	quizTitle = quiz["title"];
		  	var host = document.location.host;
		  	var pathname = document.location.pathname;
		   	ws = new WebSocket("ws://" + host  + "/fastdailymocks/" + "/game/" + userName);
		    console.log("Server Socket Address - ws://" +host  + pathname + "/game/" + userName);
		    ws.onmessage = function(event) {
		        console.log(event.data);
		        var message = JSON.parse(event.data);
		        executeCommand(message);
		    };	
		    ws.onopen = function() {
		    	
		        var json = JSON.stringify({
		            "command":"initGame",
		            "value": JSON.stringify({
		            	"quiz": quiz,
		            	"pin": (pin + "")
		            })
		        });
		        ws.send(json);
		    }
	  }
};
xhttp.open("POST", "getPin", false);
xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
xhttp.send("testData=5");


function executeCommand(gameCommand) {
	if (gameCommand.command == "createdGame") {
		displayLobby(gameCommand.value);
	} else if (gameCommand.command == "addToLobby") {
		addPlayer(gameCommand.value);
	} else if (gameCommand.command == "displayQuestion") {
		displayQuestion(gameCommand.value);
	} else if (gameCommand.command == "roundResults") {
		console.log("roundresults!");
		displayRoundResults(gameCommand.value);
		console.log(gameCommand.value);
	} else if (gameCommand.command == "finishedGame") {
		displayFinalResult(gameCommand.value);
	} else if (gameCommand.command == "receivedAnswer") {
		addUserCounter();
	} else if (gameCommand.command == "roundStats") {
		displayPieChart(gameCommand.value);
	} else if (gameCommand.command == "displayReport") {
		displayReport(gameCommand.value);
	}
}

function displayPieChart(players) {
	console.log("Display pie chart");
	console.log(players);
	displayingQuestion = false;
	var container = document.getElementById("container");
	var text = "";
	var options = [0, 0, 0, 0];
	var answerIndex = players[0].answers.length - 1;
	var noSubmissions = true;
	for (var i = 0; i < players.length; i++) {
		console.log("SELECTED OPTION... " + players[i].answers[answerIndex].option);
		if (players[i].answers[answerIndex].option != -1) {
			noSubmissions = false;
			answerData[players[i].answers[answerIndex].option].values++;
		}
	}
	
	text += "<button type='button' onclick='displayRoundResults();' class='btn btn-primary btn-lg' id='pie_next_button'>NEXT</button>";
	if (noSubmissions) {
		text += "<div class='no-sub-container'><h1 class='no-sub-txt'>NO SUBMISSIONS!</h1></div>";
	} else {
		text += "<div id='chartdiv'></div>";
	}
	text += "<div id='pie_questions'>";
	text += "<div class='container'>";
	text += "<ul class='piechart-list-group'>";

	for (var i = 0; i < answerData.length; i++) {
		var colour = "";
		var icon = "";
		if (i == 0) {
			colour = "#FF0440";
			icon = "fa-star";
		} else if (i == 1) {
			if (answerData.length > 2) {
				colour = "#ffff19";
				} else {
					colour = "#36C12F";
				}
			icon = "fa-heart";
		} else if (i == 2) {
			colour = "#007BC3";
			icon = "fa-circle";
		} else {
			colour = "#36C12F";
			icon = "fa-square";
		}
		text += "<li class='list-group-item' style='background-color:" + colour + "'><i class='fas " + icon + " mx-2'></i>" + answerData[i].question;
		if (correctAnswers[i]) {
			text += "<i class='fas fa-check' style='float:right; color:white;'></i>";
		}
		text += "</li>";		
	}
	text += "</ul>";
	text += "</div></div>";
	
	container.innerHTML = text;
	currentPlayers = players;
	drawPieChart();
}

function displayFinalResult(players) {
	console.log("display final result");
	console.log(players);
	var container = document.getElementById("container");
    var text = "";
    text += "<button type='button' class='btn btn-primary btn-lg' id='next_button' onclick='finishGame()'>HOME</button>";
	
    text += "<div class='scoreboard'>";
    text += "<div class='scoreboard__podiums'>";
    
    if (players.length >= 2) { 
	    text += "<div class='scoreboard__podium js-podium' data-height='200px'>";
	    text += "<img src='resources/img/second.PNG' style='width:100px; margin-left:auto; margin-right:auto;'>";
	    text += "<div class='scoreboard__podium-base scoreboard__podium-base--second'>";
	    text += "<div class='scoreboard__podium-rank'>2</div>";
	    text += "</div>";
	    text += "<div class='scoreboard__podium-number'>";
	    text += "" + players[1].nickname;
	    text += "<small><span class='js-podium-data'>" + players[1].score + " Points</span></small>";
	    text += "</div>";
	    text += "</div>";
	    
	    
    }
    text += "<!-- First -->";
    text += "<div class='scoreboard__podium js-podium' data-height='250px'>";
    text += "<img src='resources/img/first.PNG' style='width:100px; margin-left:auto; margin-right:auto;'>";
    text += "<div class='scoreboard__podium-base scoreboard__podium-base--first'>";
    text += "<div class='scoreboard__podium-rank'>1</div>";
			
    text += "</div>";
    text += "<div class='scoreboard__podium-number'>";
    text += "" + players[0].nickname;
    text += "<small><span class='js-podium-data'>" + players[0].score + " Points</span></small>";
    text += "</div>";
    text += "</div>";
	
    if (players.length >= 3) {
	    text += "<!-- Third -->";
	    text += "<div class='scoreboard__podium js-podium' data-height='150px'>";
	    text += "<img src='resources/img/third.PNG' style='width:100px; margin-left:auto; margin-right:auto;'>";
	    text += "<div class='scoreboard__podium-base scoreboard__podium-base--third'>";
	    text += "<div class='scoreboard__podium-rank'>3</div>";
	    text += "</div>";
	    text += "<div class='scoreboard__podium-number'>";
	    text += "" + players[2].nickname;
	    text += "<small><span class='js-podium-data'>" + players[2].score + " Points</span></small>";
	    text += "</div>";
	    text += "</div>";
    }
    text += "</div>";
    text += "</div>";
	text += "<button type='button' class='btn btn-success btn-lg' id='view_report_button' onclick='generateReport()'>View Report</button>";
//	text = "<div>Game Over</div>";
//	text += "<a href='admin'>Home</a>";
	container.innerHTML = text;
	displayPodium = true;
}


function displayRoundResults() {
	var container = document.getElementById("container");
	var text = "";
	userCounter = 0;
	text += "<div class='trainer_top3_bg'>";
	text += "<button type='button' onclick='getNextQuestion()' class='btn btn-primary btn-lg' id='top3_scoreboard_next_button'>NEXT</button>";
	text += "<div id='leaderboard_container'>";
	text += "<div class='container'>";
	text += "<h2 style='font-size:66px;'>LEADERBOARD</h2>  ";     
	text += "<br>";
	text += "<table class='table'>";
	text += "<tbody>";
	
	if (currentPlayers.length > 5) {
		for (var i = 0; i < 5; i++) {
			text += "<tr>";
			text += "<td>" + currentPlayers[i].nickname + "</td>";
			text += "<td>" + currentPlayers[i].score + "</td>";
			text += "</tr>";
		}
	}
	else {
		for (var i = 0; i < currentPlayers.length; i++) {
			text += "<tr>";
			text += "<td>" + currentPlayers[i].nickname + "</td>";
			text += "<td>" + currentPlayers[i].score + "</td>";
			text += "</tr>";
		}
	}


	text += "</tbody>";
	text += "</table></div></div></div>";
	container.innerHTML = text;
}

function getNextQuestion() {
    var json = JSON.stringify({
        "command":"nextQuestion",
        "value":""
    });
    ws.send(json);
}

function forceEndQuestion() {
	displayingQuestion = false;
    var json = JSON.stringify({
        "command":"forceEndQuestion",
        "value": ""
    });
    ws.send(json);
}

function addUserCounter() {
	userCounter++;
	var countContainer = document.getElementById("uCounter");
	countContainer.setAttribute("value", userCounter.toString());
}

function subtractSecond() {
	if (questionTimer == 0) {
		forceEndQuestion();
	} else {
		questionTimer--;
		var timeContainer = document.getElementById("tCounter");
		timeContainer.setAttribute("value", questionTimer.toString());
	}
}

function displayQuestion(question) {
	var container = document.getElementById("container");
	var text = "";
	userCounter = 0;
	
	answerData = [];
	correctAnswers = [];
	for (var i = 0; i < question.choices.length; i++) {
		answerData.push({
			"question": question.choices[i].answer,
			"values": 0
		});
		correctAnswers.push(question.choices[i].isCorrect);
	}
	
	displayingQuestion = true;
	questionTimer = question.timeLimit;

	text += "<div class='show_question_bg'>";
	text += "<div id='align_buttons'>";
	text += "<button type='button' class='btn btn-primary btn-lg' id='next_button' onclick='forceEndQuestion()'>NEXT</button>";

	text += "</div>";
	text += "<div id='timer_clock'>";
	text += "<form class='button_form' id='timer'>";
	text += "<input type='submit' value='" + questionTimer + "' id='tCounter' class='timerAndCounter' disabled='disabled'/>";
	text += "</form>";
	
	text += "<form class='button_form' id='userCounter'>";
	text += "<input type='submit' id='uCounter' value='0' class='timerAndCounter' disabled='disabled'/>";
	text += "</form>";
	text += "</div>";

	text += "<div class='header'>";
	text += "<h1 style='font-size:30px;'>" + question.question + "</h1>";
	text += "</div>";

	text += "<img src='resources/img/giphy.gif' id='image'>";
	text += "</div>";
	text += "<div class='question_footer'>";
	text += "<div id='questions_div'>";
	text += "<div class='display_question_container'>";
	text += " <ul class='show-question-group'>";
	for (var i = 0; i < question.choices.length; i++) {
		var colour = "";
		var icon = "";
		if (i == 0) {
			colour = "#FF0440";
			icon = "fa-star";
		} else if (i == 1) {
			if (question.choices.length > 2) {
			colour = "#ffff19";
			} else {
				colour = "#36C12F";
			}
			icon = "fa-heart";
		} else if (i == 2) {
			colour = "#007BC3";
			icon = "fa-circle";
		} else {
			colour = "#36C12F";
			icon = "fa-square";
		}
		text += "<li class='list-group-item' style='background-color:" + colour + "' id='question_top_space'><i class='fas " + icon + " mx-2'></i>" + question.choices[i].answer + "</li>";
	}
//	text += "<li class='list-group-item' style='background-color:#FF0440' id='question_top_space'><i class='fas fa-star mx-2'></i>" + question.choices[0].answer + "</li>";
//	text += "<li class='list-group-item' style='background-color:#ffff19' id='question_top_space'><i class='fas fa-heart mx-2'></i></i>" + question.choices[1].answer + "</li>";
//	text += "<li class='list-group-item' style='background-color:#007BC3' id='question_top_space'><i class='fas fa-circle mx-2'></i>" + question.choices[2].answer + "</li>";
//	text += "<li class='list-group-item' style='background-color:#36C12F' id='question_top_space'><i class='fas fa-square mx-2'></i>" + question.choices[3].answer + "</li>";
	
	text += "</ul>";
	text += "</div></div></div>";
	
	container.innerHTML = text;
	
}

function addPlayer(players) {
	console.log(players);
	var playerContainer = document.getElementById("row_container");
	var index = 0;
	var text = "";
	for (i = 0; i < players.length; i++) { 
		if (players[i].nickname != undefined) {
			if (index == 0) {
				text += "<div class='row'>";
			}
			text += "<div class='col-sm-4' id='row_value' onclick='removeUser(this)'>" + players[i].nickname + "</div>";
			if (index == 2 || i == players.length - 1) {
				text += "</div>";
				index = 0;
			} else {
				index++;
			}
		}
	}
	playerContainer.innerHTML = text;
}

function removeUser(userElement) {
	var removedNickname = userElement.innerHTML;
    var json = JSON.stringify({
        "command":"removeNickname",
        "value": removedNickname
    });
    ws.send(json);
}

function displayLobby(pin) {
	var container = document.getElementById("container");
	var text = "";
	text += "<div class='changing_background'>";
	text += "<h1 id='main_text'>" + quizTitle + ": " + pin + "</h1>";
	text += "</div>";
	text += "<div class='container-fluid' id='row_container'>";
	text += "</div>";
	text += "<button type='button' onclick='startGame();' class='btn btn-warning btn-lg' id='lobby_button'>PLAY</button>";
	container.innerHTML = text;
	//container.innerHTML = "<h1>Lobby</h1><h3>Players</h3><div id='lobbyPlayers'></div><button type='button' onclick='startGame();'>Start Game</button>";
}

function displayReport(report) {
	console.log(report);
	finalReport = report;
	var container = document.getElementById("container");
	var text = "";
	var questionsCorrect = [];
	for (var i = 0; i < report.questions.length; i++) {
		questionsCorrect.push(0);
	}
	for (var i = 0; i < report.players.length; i++) {
		for (var j = 0; j < report.players[i].answers.length; j++) {
			if (report.players[i].answers[j].isCorrect) {
				questionsCorrect[j]++;
			}
		}
	}
	text += "<button type='button' onclick='finishGame()' class='btn btn-danger btn-lg' id='report_exit_button'>EXIT</button>";
	text += "<button type='button' onclick='saveAndExit()' class='btn btn-danger btn-lg' id='report_exit_button'>Save Results</button>";
	text += "<div id='leaderboard_container'>";
	text += "<div class='container'>";
	text += "<h2 style='font-size:66px;'>LEADERBOARD</h2>";       
	text += "<br>";
	text += "<div class='scoreboard-group'>";
	text += "<table class='table'>";
	text += "</div>";
	text += "<tbody>";
	for (var i = 0; i < report.players.length; i++) {
		if (report.players[i].nickname != undefined) {
			text += "<tr>";
			text += "<td>" + report.players[i].nickname + "</td>";
			text += "<td>" + report.players[i].score + "</td>";
			text += "</tr>";
		}
	}
	text += "</tbody>";
	text += "</table>";
	text += "</div>";
	text += "</div>";
	
	text += "<div class='list-group'>";
	
	for (var i = 0; i < report.questions.length; i++) {
		text += "<a href='#' class='list-group-item list-group-item-action flex-column align-items-start' id='list_items'>";
		text += "<div class='d-flex w-100 justify-content-between'>";
		text += "<h5 class='mb-1'>QUESTION " + (i + 1) + ": " + report.questions[i].question + "</h5>";
		text += "</div>";
		text += "<p class='mb-1 overall-correct'>" + questionsCorrect[i] + "/" + report.players.length + " CORRECT</p>";
		text += "</a>";
	}
	
	text += "</div>";
	container.innerHTML = text;
}

function generateReport() {
    var json = JSON.stringify({
        "command":"generateReport",
        "value": ""
    });
    ws.send(json);	
}

function finishGame() {
	//Save data through a post request to the server
    var json = JSON.stringify({
        "command":"endGame",
        "value": ""
    });
    ws.send(json);
	window.location.href = "trainerHome";
}

function saveAndExit() {
	var xhttp2 = new XMLHttpRequest();
	xhttp2.onreadystatechange = function() {
		  if (this.readyState == 4 && this.status == 200) {
			    var text = JSON.parse(this.responseText);
			    alert("Saved Report!");
			    finishGame();
		  } else if (this.status == 200) {
			  alert("saved...");
			  finishGame();
		  } else {
			  console.log(this.status);
		  }
	};
	xhttp2.open("POST", "generateReport", false);
	xhttp2.setRequestHeader("Content-Type", "application/json");
	xhttp2.send(JSON.stringify(finalReport));
}

function startGame() {
    var json = JSON.stringify({
        "command":"startGame",
        "value":""
    });
    ws.send(json);
}

function send() {
    var json = JSON.stringify({
        "command":"initGame",
        "value": "1"
    });
    ws.send(json);
}