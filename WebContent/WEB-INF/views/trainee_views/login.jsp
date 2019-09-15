<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
  <title>Fast Daily Mocks</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
  <link rel="stylesheet" href="resources/style/font.css">
  <link rel="stylesheet" href="resources/style/trainee_styles/login_style.css">
  <link rel="stylesheet" href="resources/style/trainee_styles/background_color_change.css">
</head>
<body>

	<div class="changing_background">
		<div class="box">
			<h1 style="line-height:66px;">WELCOME</h1>

			<sf:form action="authenticate" method="POST" modelAttribute="userModel">
				<sf:input type="text" placeholder="EMAIL" path="email" /><br>
				<sf:errors path="email"></sf:errors>
				<sf:input type="password" placeholder="PASSWORD" path="password" /><br>
				<sf:errors path="password"></sf:errors><br>
				<p style="color:#ff2200;"> ${errorMessage}<p> 
				<input type="submit" value="LOGIN" id="login_button">
			</sf:form>		
		</div>
	</div>

 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
 <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
</body>
</html>