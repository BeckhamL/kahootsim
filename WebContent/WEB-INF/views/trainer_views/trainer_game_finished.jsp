<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" >
<head>
  <meta charset="UTF-8">
  <title>Fast Daily Mocks</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
  <link rel="stylesheet" href="resources/style/trainer_styles/trainer_game_finished_style.css">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.0/css/bootstrap.min.css">
  <link rel="stylesheet" href="resources/style/font.css">
</head>
<body>

   <a href="trainerViewReport"> <button type="button" class="btn btn-primary btn-lg" id="next_button">NEXT</button> </a>
	
	<div class="scoreboard">
	  <div class="scoreboard__podiums">
		<div class="scoreboard__podium js-podium" data-height="200px">
		  <div class="scoreboard__podium-base scoreboard__podium-base--second">
			<div class="scoreboard__podium-rank">2</div>
		  </div>
		   <div class="scoreboard__podium-number">
			   Shihao
			 <small><span class="js-podium-data">1520 Points</span></small>
		  </div>
		
		<!-- First -->
		</div>
		<div class="scoreboard__podium js-podium" data-height="250px">
		   <div class="scoreboard__podium-base scoreboard__podium-base--first">
			<div class="scoreboard__podium-rank">1</div>
			
		  </div>
			<div class="scoreboard__podium-number">
			   Nathan
			  <small><span class="js-podium-data">5242 Points</span></small>
		  </div>
		</div>
		
		<!-- Third -->
		<div class="scoreboard__podium js-podium" data-height="150px">
		   <div class="scoreboard__podium-base scoreboard__podium-base--third">
			   <div class="scoreboard__podium-rank">3</div>
		  </div>
			<div class="scoreboard__podium-number">
			  Greg
			  <small><span class="js-podium-data">920 Points</span></small>
		  </div>
		</div>
	  </div>
	</div>

	<button type="button" class="btn btn-success btn-lg" id="view_report_button">View Report</button>

  <script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js'></script>
  <script src='https://cdnjs.cloudflare.com/ajax/libs/tinysort/2.3.6/tinysort.min.js'></script>
  <script src="resources/js/trainer_scripts/rainer_game_finished_script.js"></script>

</body>
</html>