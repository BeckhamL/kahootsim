<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" >
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
  <meta charset="UTF-8">
  <title>Create Quiz</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="resources/style/font.css">
	<link rel="stylesheet" href="resources/style/trainer_styles/trainer_create_quiz_style.css">
</head>
<body>

	<div class="container" id="form_container">
	
 	<sf:form class = "form-capsule" action = "saveQuiz" method = "POST" modelAttribute = "quiz">
 		<h3>Quiz Title</h3>
		<div class="form-group">
		  <sf:label for="i" path = "title">Quiz Title</sf:label>
		  <sf:input type="text" class="form-control" id="i" placeholder="Quiz Title" path = "title"/>
		</div>
		<br>
		<h4>Description</h4>
		<div class="form-group form-group-textarea">
		  <sf:label for="t" path = "description">Description</sf:label>
		  <sf:input type="text" class="form-control" id="t" placeholder="Description(optional)" path = "description"/>
		</div>
		<br>
		
		<h4>Module</h4>
		<div class="form-group form-group-select">
		  <sf:label for="sel1" path = "moduleName">Module List</sf:label>
		  <div class="select-wrapper">
			<sf:select class="form-control" id="sel1" path = "moduleName">
				<sf:options items = "${modules}" value = ""/>
			</sf:select>
		  </div>
		</div>

		<br>

 		<div class="radio-group">
		  <div class="radio">
			<sf:radiobutton class="radio-option" id="r1" name="optradio" path = "isShareable" value = "false"/>
				<sf:label class="radio-option-label" for="r1" path = "isShareable">
				  <div class="radio-option-label-text">Private</div>
				</sf:label>
		  </div>
		  <div class="radio">
			<sf:radiobutton class="radio-option" id="r2" name="optradio" path = "isShareable" value = "true"/>
				<sf:label class="radio-option-label" for="r2" path = "isShareable">
				  <div class="radio-option-label-text">Public</div>
				</sf:label>
		  </div>
		</div>
			<br>
				<br>

		<input type="submit" class="btn btn-primary btn-lg" style="z-index:100;" value = "SAVE"/>
		<button type="button" class="btn btn-danger btn-lg" onclick = "location.href='trainerHome'">CANCEL</button>
	</sf:form>

	</div>

  <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
  <script src='http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js'></script>
  <script  src="/fastdailymocks/resources/js/trainer_scripts/trainer_create_quiz_script.js"></script>

</body>
</html>