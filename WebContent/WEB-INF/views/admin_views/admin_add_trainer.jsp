<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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

<title>Add Trainer</title>
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
	<main class="g--10 g-m--12 m--2 m-m--0 no-margin-vertical"> <header>
	<h1 class="m--1 g--4 g-s--11 docsHeader">Add Trainer</h1>
	</header>
	<div class="g--10 m--1">
		<p style="color:#ff2200;"> ${errorMessage}<p> 
		<form action="registerTrainer" method="POST">
			<input type="text" required placeholder="First Name" name="firstName" />
			<input type="text" required placeholder="Last Name" name="lastName" />
			<input type="email" required placeholder="Email" name="email" /> <input
				type="password" required placeholder="Password" name="password" />

			<div id="select_location">
				<label style="display: block;">Location</label> <select
					name="location">
					<option>Toronto</option>
					<option>London</option>
					<option>New York</option>
					<option>Leeds</option>
				</select>
			</div>

			<div id="select_date">
				<label style="display: block;">Start date:</label> <input
					type="date" required
					pattern="(?:19|20)[0-9]{2}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-9])|(?:(?!02)(?:0[1-9]|1[0-2])-(?:30))|(?:(?:0[13578]|1[02])-31))"
					title="Enter a date in this format YYYY/MM/DD" name="startTime"
					placeholder="YYYY-MM-DD" />
			</div>
			<input type="submit" value="ADD" class="btn btn-success btn-lg">
			<input type="button" name="Cancel" value="Cancel"
				class="btn btn-danger btn-lg"
				onClick="window.location.href='academyAdminHome';" />
		</form>
	</div>
	</main>
</body>
</html>
