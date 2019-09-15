<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<head>
  <title>Incorrect</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
  <link rel="stylesheet" href="resources/style/font.css">
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/" crossorigin="anonymous">
  <link rel="stylesheet" href="resources/style/trainee_styles/trainee_question_incorrect_style.css">
</head>
<body>
	<a href="finalResultForNotPlacedTrainee">To Non Placed Leader Board</a>
	<div id="correct_text">
		<i class="fas fa-times fa-8x"></i>
		<h1 class ="title">INCORRECT</h1>
		<div id="points_div">
			<h2 class ="body_text">+0</h2>
		</div>
		<h3>You're in ${currentPlace} place, <br> 552 points behind John </h3>
	</div>
	
	<div id="footer">
		<p id="name">${loggedUser.firstName}</p>
		<p id="total_score" >${totalScore}</p>
	</div>
	
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
 <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
</body>
</html>