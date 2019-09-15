
  $(".test-config").click(function() {
    $(".overlay, #dialog-test-config").fadeIn();
  });

  $(".question-add, .question-edit").click(function() {
    $(".overlay, #dialog-question-edit").fadeIn();
  });
  $(".question-delete").click(function() {
    $(".overlay, #dialog-question-delete").fadeIn();
  });
  $(".answer-delete").click(function() {
    $(".overlay, #dialog-answer-delete").fadeIn();
  });

  $(".dialog a.dismiss").click(function() {
    $(".overlay, .dialog").hide();
  });
  
  $(".overlay, .dialog").hide();


var qId;
var questId;
var chId;


function setQuizQuestionId(quizId, questionId) {
	console.log(quizId);
	console.log(questionId);
	qId = quizId;
	questId = questionId;
}


function choiceFunction(choiceId, questionId, quizId) {
	console.log(choiceId);
	console.log(questionId);
	console.log(quizId)
	chId = choiceId;
	questId = questionId;
	qId = quizId;
}

function removeQuestion() {
	var removeUrl = "removeQuestion?currentQuizId=" + qId + "&deletedQuestionId=" + questId + "";
	console.log(removeUrl);
	window.location.href = removeUrl;
}

function removeChoice() {
	var removeUrl = "removeChoice?currentQuizId=" + qId + "&currentQuestionId=" + questId + "&choiceId=" + chId + "";
	console.log(removeUrl);
	window.location.href = removeUrl;
}

function showAddChoiceModal(quizId, questionId) {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			if (this.responseText == "0") {
				alert("You cannot add anymore questions!");
			} else {
				document.getElementById("addChoiceQuizId").value = quizId;
				document.getElementById("addChoiceQuestionId").value = questionId;
				 $(".overlay, #dialog-answer-edit").fadeIn();
			}
		}
	};
	xhttp.open("POST", "checkAddedChoice", false);
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhttp.send("currentQuizId=" + quizId + "&currentQuestionId=" + questionId);
}