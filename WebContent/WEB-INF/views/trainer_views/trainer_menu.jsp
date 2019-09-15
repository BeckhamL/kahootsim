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
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.0/css/bootstrap.min.css">
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/" crossorigin="anonymous">
  <link rel="stylesheet" href="resources/style/font.css">
  <link rel="stylesheet" href="resources/style/trainer_styles/trainer_menu_style.css">
</head>
<body>

	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
	  <a class="navbar-brand" href="#">FAST DAILY MOCKS</a>
	  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	  </button>

	  <div class="collapse navbar-collapse" id="navbarSupportedContent">
		<ul class="navbar-nav mr-auto">
		</ul>
		<form class="form-inline my-2 my-lg-0">
		  <button type="button" class="btn btn-warning btn-lg" onclick="location.href='logout'">LOGOUT</button>
		</form>
	  </div>
	</nav>
	
	<div id="container">
	<form class="option_forms" action = "filter" method = "POST" id="filter_form">
	    <div id="align_buttons">
			<button type="button" class="btn btn-success btn-lg" onclick = "location.href='createQuiz'">CREATE</button>
			<input type="submit" class="btn btn-primary btn-lg" value = "FILTER"/>
		</div>
		
		<div id="select_menus">		
			  <div class="form-row align-items-center">
				<div class="col-auto my-1">
				  <label class="mr-sm-2" for="inlineFormCustomSelect">Module</label>
				  <select class="custom-select mr-sm-2" id="inlineFormCustomSelect" name = "selectedModule">
				  	 <c:forEach  items = "${modules}" var ="module" >
						<option value = "${module}">${module}</option>
					</c:forEach> 		 
				  </select>
				</div>
			 </div>
		</div>
	</form>
	
	<div class="list-group">
		<c:forEach items = "${userQuizzes}" var = "quiz">
		  <a href="#" class="list-group-item list-group-item-action flex-column align-items-start" id="list_items">
			<div class="d-flex w-100 justify-content-between">
			  <h5 class="mb-1">Title: ${quiz.title}</h5>
			</div>
			<p class="mb-1">${quiz.moduleName}</p>
			<small>Owner: ${loggedUser.firstName}</small>
			<div class="btn-group" style="float:right;">
			
 			<button type="button" class="btn btn-success" onclick = "play(${quiz.id})">PLAY</button>
			 <form action = "goToAddQuestion" method = "POST">
			  <input type = "hidden" value = "${quiz.id}" name = "editQuiz"/>
			  <input type="submit" class="btn btn-primary" value = "EDIT"/>
			 </form>
			 <form action = "removeQuiz" method = "POST">
			  <input type = "hidden" value = "${quiz.id}" name = "removeQuiz"/>
			  <input type="submit" class="btn btn-danger" value = "REMOVE"/> 
			 </form>
			</div>
		  </a>
		</c:forEach>
	</div>
	</div>
	<script src="resources/js/trainer_scripts/trainer_menu.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0/dist/Chart.min.js"></script>
</body>
</html>