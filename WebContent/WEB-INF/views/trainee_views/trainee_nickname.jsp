<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
  <title>Nickname</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="resources/style/font.css">
  <link rel="stylesheet" href="resources/style/trainee_styles/background_color_change.css">
  <link rel="stylesheet" href="resources/style/trainee_styles/trainee_nickname_style.css">
</head>
<body>

<div class="changing_background">
	
	<div class="box">
		<h1 style="line-height:66px;">NICKNAME</h1>

		<form action="waitPage" method="POST">
			<input name = "nickname" type = "text" placeholder = "NICKNAME" >
			<input type="submit" value="JOIN" id="enter_pin">
		</form>		
		
	</div>
</div>

 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
 <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
 <script src = "resources/js/trainee_answer.js"></script>
 
</body>
</html>