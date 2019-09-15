<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="UTF-8">
<title>Fast Daily Mocks</title>
<link rel="stylesheet"
	href="resources/style/trainee_styles/trainee_question_style.css">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.6.3/css/all.css"
	integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/"
	crossorigin="anonymous">
</head>
<body>

	<div>
		<svg viewBox="0 0 100 100" preserveAspectRatio="none">
		stroke="black" stroke-width="20"

		<form action="choice" method="POST">
			<input type="hidden" id="questionId" value="${question.id}">

			<input type="submit" class="block" id="blocktop" name="choiceId"
				value="${choice.id}">
			<path id="blockA" d="M 0 0 L 50 50 L 100 0 z" stroke="white"
				stroke-width="1"></path>

			<input type="submit" class="block" id="blockleft" name="choiceId"
				value="${choice.id}">
			<path id="blockB" d="M 50 50 L 0 100 L 0 0 z" stroke="white"
				stroke-width="1"></path>

			<input type="submit" class="block" id="blockbottom" name="choiceId"
				value="${choice.id}">
			<path id="blockC" d="M 100 100 L 50 50 L 0 100 z" stroke="white"
				stroke-width="1"></path>

			<input type="submit" class="block" id="blockright" name="choiceId"
				value="${choice.id}">
			<path id="blockD" d="M 50 50 L 100 0 L 100 100 z" stroke="white"
				stroke-width="1"></path>
		</form>
		</svg>
	</div>

	<!--
	<div id="blue_circle">
	  <i class="fas fa-circle fa-5x"></i>
	</div>
	
	<div id="green_square">
	  <i class="fas fa-square fa-5x"></i>
	</div>
	
	<div id="yellow_heart">
	 <i class="fas fa-heart fa-5x"></i>
	</div>
	
	<div id="red_star">
	 <i class="fas fa-star fa-5x"></i>
	</div> -->

	<script
		src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
	<script src="trainee_question.js"></script>
</body>
</html>