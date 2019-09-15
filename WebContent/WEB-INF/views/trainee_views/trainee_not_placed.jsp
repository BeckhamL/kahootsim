<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
  <title>Fast Daily Mocks</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="resources/style/font.css">
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/" crossorigin="anonymous">
  <link rel="stylesheet" href="resources/style/trainee_styles/trainee_not_placed_style.css">
</head>
<body>

	<div id="correct_text">
		<i class="fas fa-sad-tear fa-10x"></i>
		<h1 class ="title">${currentPlace}</h1>
		<div id="points_div">
			<h2 class ="body_text">11/20 <br> QUESTIONS CORRECT</h2>
		</div>
	</div>
	
	<div id="footer">
		<p id="name">${loggedInUser.firstName}</p>
		<p id="total_score" >${totalScore}</p>
	</div>
	
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
 <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
</body>
</html>