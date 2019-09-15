<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
  <title>View Report</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.0/css/bootstrap.min.css">
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/" crossorigin="anonymous">
  <link rel="stylesheet" href="resources/style/font.css">
  <link rel="stylesheet" href="resources/style/trainer_styles/trainer_view_report_style.css">
</head>
<body>

	<a href ="trainerHome"><button type="button" class="btn btn-danger btn-lg" id="exit_button">EXIT</button> </a>
	
    <div id="leaderboard_container">
		<div class="container">
		  <h2 style="font-size:66px;">LEADERBOARD</h2>       
		  <br>
		  <table class="table">
			<tbody>
			  <tr>
				<td>John</td>
				<td>15223</td>
			  </tr>
			  <tr>
				<td>Mary</td>
				<td>5163</td>
			  </tr>
			  <tr>
				<td>Gary</td>
				<td>2440</td>
			  </tr>
			  <tr>
				<td>Ted</td>
				<td>1531</td>
			  </tr>
			  <tr>
				<td>Shaun</td>
				<td>190</td>
			  </tr>
			</tbody>
		  </table>
		</div>
	</div>
	
	<div class="list-group">
	  <a href="#" class="list-group-item list-group-item-action flex-column align-items-start" id="list_items">
		<div class="d-flex w-100 justify-content-between">
		  <h5 class="mb-1">QUESTION 1: blah blah blah</h5>
		</div>
		<p class="mb-1">5/7 CORRECT</p>
		<div class="btn-group" style="float:right;">
		  <button type="button" class="btn btn-primary">VIEW</button>
		</div>
	  </a>
	  <a href="#" class="list-group-item list-group-item-action flex-column align-items-start" id="list_items">
		<div class="d-flex w-100 justify-content-between">
		  <h5 class="mb-1">QUESTION 2: blah blah blah</h5>
		</div>
		<p class="mb-1">6/7 CORRECT</p>
		<div class="btn-group" style="float:right;">
		  <button type="button" class="btn btn-primary">VIEW</button>
		</div>
	  </a>
	  <a href="#" class="list-group-item list-group-item-action flex-column align-items-start" id="list_items">
		<div class="d-flex w-100 justify-content-between">
		 <h5 class="mb-1">QUESTION 3: blah blah blah</h5>
		</div>
		<p class="mb-1">7/7 CORRECT</p>
		<div class="btn-group" style="float:right;">
		  <button type="button" class="btn btn-primary">VIEW</button>
		</div>
	  </a>
	  <a href="#" class="list-group-item list-group-item-action flex-column align-items-start" id="list_items">
		<div class="d-flex w-100 justify-content-between">
		 <h5 class="mb-1">QUESTION 4: blah blah blah</h5>
		</div>
		<p class="mb-1">2/7 CORRECT</p>
		<div class="btn-group" style="float:right;">
		  <button type="button" class="btn btn-primary">VIEW</button>
		</div>
	  </a>
	</div>
	
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0/dist/Chart.min.js"></script>
</body>
</html>
