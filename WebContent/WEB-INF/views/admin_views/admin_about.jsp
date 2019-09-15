<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	<link href="resources/style/admin_styles/surface_styles.css" rel="stylesheet">
	<link href="resources/style/admin_styles/style.css" rel="stylesheet">
	<link rel="stylesheet" href="/maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
	<link href="https://fonts.googleapis.com/css?family=Rubik:700&display=swap" rel="stylesheet">
	<title>About</title>

</head>
<body>
	<input type="checkbox" id="nav--super-vertical-responsive">
		<label for="nav--super-vertical-responsive">MENU</label>
	<aside class="nav--super-vertical g--2 g-m--3 g-s--6 g-t--12 no-margin-vertical" role="sidebar">
		<div class="g--12 logo-area no-margin-vertical">
			<h4 class=" no-margin-vertical">Fast Daily Mocks</h5>
		</div>
		<nav class="g--12 no-margin-vertical" role="navigation">
			<a href="academyAdminHome">Home</a>
			<div class="nav-collapsible">
				<input type="checkbox" id="nav-collapsible-1">
				<label for="nav-collapsible-1">User Management</label>
				<div class="nav-collapsible-links">
					<a href="addTrainee">Add Trainee</a>
					<a href="addTrainer">Add Trainer</a>
				</div>
			</div>
			<a href="viewReport">View Report</a>
			<a href="about">About</a>
			<a href="logout">Logout</a>
		</nav>
	</aside>
	<main class="g--10 g-m--12 m--2 m-m--0 no-margin-vertical">
		<header>
			<h1 class="m--1 g--4 g-s--12 docsHeader">About</h1>
		</header>
		<div class="g--10 m--1">
			<p>The project is delivered by Summerhill.java</p>
			<div class="g--12">
			<h3>License</h3>
			<p>The frontend is based on the online css framework <a href="https://mildrenben.github.io/surface/">Surface code license</a> under <a href="https://github.com/mildrenben/surface/blob/master/license.md">MIT code license</a>  </p>
			</div>
		</div>
	</main>
</body>
</html>
