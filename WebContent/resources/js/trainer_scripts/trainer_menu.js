/**
 * 
 */
function play(quizId) {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		  if (this.readyState == 4 && this.status == 200) {
			  if (this.responseText == "0") {
				  alert("This quiz has no questions!");
			  } else if (this.responseText == "2") {
				  alert("This quiz has a question with invalid choices");
			  } else {
				  window.location.href = "game_menu?quizId=" + quizId;
			  }
		  }
	};
	xhttp.open("POST", "game_menu", false);
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhttp.send("quizId=" + quizId);
	
}