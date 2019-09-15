<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
  <title>Fast Daily Mocks - Trainee</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
  <link rel="stylesheet" href="resources/style/font.css">
  <link rel="stylesheet" href="resources/style/trainee_styles/trainee_loading_game_style.css">
  <link rel="stylesheet" href="resources/style/trainee_styles/background_color_change.css">
  <link rel="stylesheet" href="resources/style/trainee_styles/trainee_pin_style.css">
  <link rel="stylesheet" href="resources/style/trainee_styles/trainee_nickname_style.css">
    <link rel="stylesheet" href="resources/style/trainee_styles/trainee_question_style.css">
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/" crossorigin="anonymous">
  <link rel="stylesheet" href="resources/style/trainee_styles/trainee_question_correct_style.css">
  <link rel="stylesheet" href="resources/style/trainee_styles/trainee_question_incorrect_style.css">
    <link rel="stylesheet" href="resources/style/trainee_styles/trainee_loading_between_questions_style.css">
    <link rel="stylesheet" href="resources/style/trainee_styles/trainee_not_placed_style.css">
    <link rel="stylesheet" href="resources/style/trainee_styles/trainee_placed_style.css">
</head>
<body>
<div id="container">
	<div class="changing_background">
				
		<div class="box">
			<button type="button" style="margin-top: -40px;" class="btn btn-danger" id="next_button" onclick="location.href='logout';">LOGOUT</button>
			<h1 style="line-height:66px;">ENTER PIN</h1>

				<input type="text" id="pinInputField" name="pin" placeholder="PIN">
				<div id="nickname-error"></div>
				<button type="button" id="enter_pin" onclick="connect()">SUBMIT</button>

			
		</div>
	
	</div>
</div>

 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
 <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
 <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
 <script src="resources/js/sockets/player.js"></script>
</body>
</html>