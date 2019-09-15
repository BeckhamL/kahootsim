<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.0/css/bootstrap.min.css">
<link href="resources/style/admin_styles/surface_styles.css"
	rel="stylesheet">
<link href="resources/style/admin_styles/style.css" rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css?family=Rubik:700&display=swap"
	rel="stylesheet">

<title>Trainer Profile</title>
</head>
<body>

	<input type="checkbox" id="nav--super-vertical-responsive">
	<label for="nav--super-vertical-responsive">MENU</label>
	<aside
		class="nav--super-vertical g--2 g-m--3 g-s--6 g-t--12 no-margin-vertical"
		role="sidebar">
	<div class="g--12 logo-area no-margin-vertical">
		<h4 class="no-margin-vertical">
			Fast Daily Mocks
			</h5>
	</div>
	<nav class="g--12 no-margin-vertical" role="navigation"> <a
		href="academyAdminHome">Home</a>
	<div class="nav-collapsible">
		<input type="checkbox" id="nav-collapsible-1"> <label
			for="nav-collapsible-1">User Management</label>
		<div class="nav-collapsible-links">
			<a href="addTrainee">Add Trainee</a> <a href="addTrainer">Add
				Trainer</a>
		</div>
	</div>
	<a href="viewReport">View Report</a> <a href="about">About</a> <a
		href="logout">Logout</a> </nav> </aside>
	<main class="g--10 g-m--12 m--2 m-m--0 no-margin-vertical"> <header>
	<h1 class="m--1 g--4 g-s--11 docsHeader">View Reports</h1>
	</header>
	<div class="g--10 m--1">
	
		<form action="viewReport" method="GET">
				<label style="display: block;">Select module</label> 
				<select name= "moduleName">
					<option disabled selected>Select</option>
					<option>FINANCE</option>
					<option>JDBC</option>
					<option>JPA</option>
					<option>JSP</option>
					<option>OOD</option>
					<option>SDLC</option>
					<option>SPRING</option>
					<option>SQL</option>
					<option>TDD</option>
					<option>UNIX</option>
					<option>WEB</option>
					<option>OTHER</option>
				</select>

			<button type="submit" class="btn btn-success btn-lg"
				style="margin-top: 20px;">Find</button>
		</form>
		
		<table class="center">
			<tr class="table-header">
				<td>Quizzes</td>
				<td>Owner</td>
				<td>Module</td>
				<td>Accessibility</td>
			</tr>
			<c:forEach items="${quizzes}" var="quiz">
			<tr>
				<td>${quiz.title}</td>
				<td>${quiz.owner.firstName}</td>
				<td>${quiz.moduleName}</td>
				<c:choose>
    				<c:when test="${quiz.isShareable}">
       				<td>Public</td> 
    				</c:when>    
    			<c:otherwise>
       				<td>Private</td> 
    			</c:otherwise>
				</c:choose>
				<td>
				<form action="showReport" method="GET">
				<button type="submit" class="btn btn-primary btn-sm" value="${quiz.id}" name ="id">View</button>
				</form>
				</td>

			</tr>
			</c:forEach>
		</table>		
	</div>
	</main>
</body>
</html>
