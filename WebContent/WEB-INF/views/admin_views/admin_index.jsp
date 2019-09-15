<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>

<link href="resources/style/admin_styles/surface_styles.css"
	rel="stylesheet">
<link href="resources/style/admin_styles/style.css" rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css?family=Rubik:700&display=swap"
	rel="stylesheet">

<title>FDM Admin Page</title>
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
			<a href="addTrainee.html">Add Trainee</a> <a href="addTrainer.html">Add
				Trainer</a>
		</div>
	</div>
	<a href="viewReport.html">View Report</a> <a href="about.html">About</a>
	<a href="logout">Logout</a> </nav> </aside>
	<main class="g--10 g-m--12 m--2 m-m--0 no-margin-vertical" role="main">
	<header class="container--wrap">
	<h1 class="m--1 g--4 g-s--12 docsHeader">Home</h1>
	</header>
	<div class="g--10 m--1">
		<h2 class="fade-in-from-top">Welcome ${loggedUser.firstName}</h2>
	</div>
	<div class="g--10 m--1 container container--wrap--s">
		<div class="g--4 g-s--12 fade-in-from-top card anim-delay--5">
			<h5>Trainees</h5>
			<h3>${numberOfTrainees}</h3>
		</div>
		<div
			class="g--4 g-s--12 fade-in-from-top anim-delay--10 card nudge--left nudge--right no-nudge--s">
			<h5>Trainers</h5>
			<h3>${numberofTrainers}</h3>
		</div>
		<div class="g--4 g-s--12 fade-in-from-top anim-delay--15 card">
			<h5>Batches</h5>
			<h3>${numberOfBatches}</h3>
		</div>
	</div>
	</main>
</body>
</html>
