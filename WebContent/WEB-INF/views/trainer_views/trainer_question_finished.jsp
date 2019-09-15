<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>Fast Daily Mocks</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.0/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.6.3/css/all.css"
	integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/"
	crossorigin="anonymous">
  <link rel="stylesheet" href="resources/style/font.css">
<link rel="stylesheet"
	href="resources/style/trainer_styles/trainer_question_finished_style.css">
<script src="https://www.amcharts.com/lib/4/core.js"></script>
<script src="https://www.amcharts.com/lib/4/charts.js"></script>
<script src="https://www.amcharts.com/lib/4/themes/frozen.js"></script>
<script src="https://www.amcharts.com/lib/4/themes/animated.js"></script>
</head>
<body>

	<div id="align_buttons">
		<a href="trainerShowQuestion"><button type="button"
				class="btn btn-primary btn-lg" id="next_button">NEXT</button></a>
	</div>

	<div id="chartdiv"></div>
	<div id="questions">
		<div class="container">
			<ul class="list-group">
				<li class="list-group-item" style="background-color: #FF0440"
					id="top_space"><i class="fas fa-star mx-2"></i>blah blah</li>
				<li class="list-group-item" style="background-color: #ffff19"
					id="top_space"><i class="fas fa-heart mx-2"></i></i>blah blah</li>
				<li class="list-group-item" style="background-color: #007BC3"
					id="top_space"><i class="fas fa-circle mx-2"></i>blah blah</li>
				<li class="list-group-item" style="background-color: #36cb2f"><i
					class="fas fa-square mx-2"></i>blah blah<i class="fas fa-check"
					style="float: right;"></i></li>
			</ul>
		</div>
	</div>

	<script src="resources/js/trainer_scripts/trainer_question_finished_piechart.js">
	</script>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0/dist/Chart.min.js"></script>
</body>
</html>
